// fuck you rat go brrrrrrrr
package risingtide.tidehack.mixin;

import baritone.api.BaritoneAPI;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import risingtide.tidehack.TideHack;
import risingtide.tidehack.events.entity.DropItemsEvent;
import risingtide.tidehack.events.entity.player.SendMovementPacketsEvent;
import risingtide.tidehack.events.game.SendMessageEvent;
import risingtide.tidehack.systems.commands.Commands;
import risingtide.tidehack.systems.config.Config;
import risingtide.tidehack.systems.modules.Modules;
import risingtide.tidehack.systems.modules.movement.NoSlow;
import risingtide.tidehack.systems.modules.movement.Scaffold;
import risingtide.tidehack.systems.modules.movement.Sneak;
import risingtide.tidehack.systems.modules.movement.Velocity;
import risingtide.tidehack.systems.modules.player.Portals;
import risingtide.tidehack.utils.player.ChatUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    @Shadow @Final public ClientPlayNetworkHandler networkHandler;

    private boolean ignoreChatMessage;

    @Shadow public abstract void sendChatMessage(String string);

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "dropSelectedItem", at = @At("HEAD"), cancellable = true)
    private void onDropSelectedItem(boolean dropEntireStack, CallbackInfoReturnable<Boolean> info) {
        if (TideHack.EVENT_BUS.post(DropItemsEvent.get(getMainHandStack())).isCancelled()) info.setReturnValue(false);
    }

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void onSendChatMessage(String message, CallbackInfo info) {
        if (ignoreChatMessage) return;

        if (!message.startsWith(Config.get().prefix) && !message.startsWith("/") && !message.startsWith(BaritoneAPI.getSettings().prefix.value)) {
            SendMessageEvent event = TideHack.EVENT_BUS.post(SendMessageEvent.get(message));

            if (!event.isCancelled()) {
                ignoreChatMessage = true;
                sendChatMessage(event.message);
                ignoreChatMessage = false;
            }

            info.cancel();
            return;
        }

        if (message.startsWith(Config.get().prefix)) {
            try {
                Commands.get().dispatch(message.substring(Config.get().prefix.length()));
            } catch (CommandSyntaxException e) {
                ChatUtils.error(e.getMessage());
            }
            info.cancel();
        }
    }

    @Redirect(method = "updateNausea", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;currentScreen:Lnet/minecraft/client/gui/screen/Screen;"))
    private Screen updateNauseaGetCurrentScreenProxy(MinecraftClient client) {
        if (Modules.get().isActive(Portals.class)) return null;
        return client.currentScreen;
    }

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
    private boolean redirectUsingItem(ClientPlayerEntity player) {
        if (Modules.get().get(NoSlow.class).items()) return false;
        return player.isUsingItem();
    }

    @Inject(method = "isSneaking", at = @At("HEAD"), cancellable = true)
    private void onIsSneaking(CallbackInfoReturnable<Boolean> info) {
        if (Modules.get().isActive(Scaffold.class)) info.setReturnValue(false);
    }

    @Inject(method = "shouldSlowDown", at = @At("HEAD"), cancellable = true)
    private void onShouldSlowDown(CallbackInfoReturnable<Boolean> info) {
        if (Modules.get().get(NoSlow.class).sneaking()) {
            info.setReturnValue(shouldLeaveSwimmingPose());
        }
    }

    @Inject(method = "pushOutOfBlocks", at = @At("HEAD"), cancellable = true)
    private void onPushOutOfBlocks(double x, double d, CallbackInfo info) {
        Velocity velocity = Modules.get().get(Velocity.class);
        if (velocity.isActive() && velocity.blocks.get()) {
            info.cancel();
        }
    }

    // Rotations

    @Inject(method = "sendMovementPackets", at = @At("HEAD"))
    private void onSendMovementPacketsHead(CallbackInfo info) {
        TideHack.EVENT_BUS.post(SendMovementPacketsEvent.Pre.get());
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;)V", ordinal = 0))
    private void onTickHasVehicleBeforeSendPackets(CallbackInfo info) {
        TideHack.EVENT_BUS.post(SendMovementPacketsEvent.Pre.get());
    }

    @Inject(method = "sendMovementPackets", at = @At("TAIL"))
    private void onSendMovementPacketsTail(CallbackInfo info) {
        TideHack.EVENT_BUS.post(SendMovementPacketsEvent.Post.get());
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;)V", ordinal = 1, shift = At.Shift.AFTER))
    private void onTickHasVehicleAfterSendPackets(CallbackInfo info) {
        TideHack.EVENT_BUS.post(SendMovementPacketsEvent.Post.get());
    }

    // Sneak
    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isSneaking()Z"))
    private boolean isSneaking(ClientPlayerEntity clientPlayerEntity) {
        return Modules.get().get(Sneak.class).doPacket() || Modules.get().get(NoSlow.class).airStrict() || clientPlayerEntity.isSneaking();
    }
}
