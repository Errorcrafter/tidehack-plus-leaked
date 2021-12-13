// fuck you rat go brrrrrrrr
package risingtide.tidehack.mixin;

import risingtide.tidehack.utils.misc.FakeClientPlayer;
import risingtide.tidehack.utils.network.Capes;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static risingtide.tidehack.utils.Utils.mc;

@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin {
    @Inject(method = "getCapeTexture", at = @At("HEAD"), cancellable = true)
    private void onGetCapeTexture(CallbackInfoReturnable<Identifier> info) {
        Identifier id = Capes.get((PlayerEntity) (Object) this);
        if (id != null) info.setReturnValue(id);
    }

    // Player model rendering in main menu

    @Inject(method = "getPlayerListEntry", at = @At("HEAD"), cancellable = true)
    private void onGetPlayerListEntry(CallbackInfoReturnable<PlayerListEntry> info) {
        if (mc.getNetworkHandler() == null) info.setReturnValue(FakeClientPlayer.getPlayerListEntry());
    }

    @Inject(method = "isSpectator", at = @At("HEAD"), cancellable = true)
    private void onIsSpectator(CallbackInfoReturnable<Boolean> info) {
        if (mc.getNetworkHandler() == null) info.setReturnValue(false);
    }

    @Inject(method = "isCreative", at = @At("HEAD"), cancellable = true)
    private void onIsCreative(CallbackInfoReturnable<Boolean> info) {
        if (mc.getNetworkHandler() == null) info.setReturnValue(false);
    }
}
