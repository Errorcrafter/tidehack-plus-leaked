// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.misc;

import io.netty.buffer.Unpooled;
import risingtide.tidehack.TideHack;
import risingtide.tidehack.events.packets.PacketEvent;
import risingtide.tidehack.mixin.CustomPayloadC2SPacketAccessor;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;

public class VanillaSpoof extends Module {
    public VanillaSpoof() {
        super(Categories.Misc, "vanilla-spoof", "When connecting to a server it spoofs the client name to be 'vanilla'.");

        TideHack.EVENT_BUS.subscribe(new Listener());
    }

    private class Listener {
        @EventHandler
        private void onPacketSend(PacketEvent.Send event) {
            if (!isActive() || !(event.packet instanceof CustomPayloadC2SPacket)) return;
            CustomPayloadC2SPacketAccessor packet = (CustomPayloadC2SPacketAccessor) event.packet;
            Identifier id = packet.getChannel();

            if (id.equals(CustomPayloadC2SPacket.BRAND)) {
                packet.setData(new PacketByteBuf(Unpooled.buffer()).writeString("vanilla"));
            }
            else if (StringUtils.containsIgnoreCase(packet.getData().toString(StandardCharsets.UTF_8), "fabric")) {
                event.cancel();
            }
        }
    }
}
