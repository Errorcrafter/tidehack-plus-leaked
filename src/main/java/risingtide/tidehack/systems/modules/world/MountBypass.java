// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.world;

import risingtide.tidehack.events.packets.PacketEvent;
import risingtide.tidehack.mixininterface.IPlayerInteractEntityC2SPacket;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;

public class MountBypass extends Module {
    private boolean dontCancel;

    public MountBypass() {
        super(Categories.World, "mount-bypass", "Allows you to bypass the IllegalStacks plugin and put chests on entities.");
    }

    @EventHandler
    public void onSendPacket(PacketEvent.Send event) {
        if (dontCancel) {
            dontCancel = false;
            return;
        }

        if (event.packet instanceof IPlayerInteractEntityC2SPacket packet) {
            if (packet.getType() == PlayerInteractEntityC2SPacket.InteractType.INTERACT_AT && packet.getEntity() instanceof AbstractDonkeyEntity) event.cancel();
        }
    }
}
