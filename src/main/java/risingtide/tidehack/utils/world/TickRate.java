// fuck you rat go brrrrrrrr
package risingtide.tidehack.utils.world;

import risingtide.tidehack.TideHack;
import risingtide.tidehack.events.game.GameJoinedEvent;
import risingtide.tidehack.events.packets.PacketEvent;
import risingtide.tidehack.utils.Utils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;

import java.util.Arrays;

/**
 * Copied from <a href="https://github.com/S-B99/kamiblue/blob/feature/master/src/main/java/me/zeroeightsix/kami/util/LagCompensator.java">KAMI Blue</a>
 */
public class TickRate {
    public static TickRate INSTANCE = new TickRate();

    private final float[] tickRates = new float[20];
    private int nextIndex = 0;
    private long timeLastTimeUpdate = -1;
    private long timeGameJoined;

    private TickRate() {
        TideHack.EVENT_BUS.subscribe(this);
    }

    @EventHandler
    private void onReceivePacket(PacketEvent.Receive event) {
        if (event.packet instanceof WorldTimeUpdateS2CPacket) {
            if (timeLastTimeUpdate != -1L) {
                float timeElapsed = (float) (System.currentTimeMillis() - timeLastTimeUpdate) / 1000.0F;
                tickRates[(nextIndex % tickRates.length)] = Utils.clamp(20.0f / timeElapsed, 0.0f, 20.0f);
                nextIndex += 1;
            }
            timeLastTimeUpdate = System.currentTimeMillis();
        }
    }

    @EventHandler
    private void onGameJoined(GameJoinedEvent event) {
        Arrays.fill(tickRates, 0);
        nextIndex = 0;
        timeLastTimeUpdate = -1;
        timeGameJoined = System.currentTimeMillis();
    }

    public float getTickRate() {
        if (!Utils.canUpdate()) return 0;
        if (System.currentTimeMillis() - timeGameJoined < 4000) return 20;

        float numTicks = 0.0f;
        float sumTickRates = 0.0f;
        for (float tickRate : tickRates) {
            if (tickRate > 0.0f) {
                sumTickRates += tickRate;
                numTicks += 1.0f;
            }
        }
        return Utils.clamp(sumTickRates / numTicks, 0.0f, 20.0f);
    }

    public float getTimeSinceLastTick() {
        if (System.currentTimeMillis() - timeGameJoined < 4000) return 0;
        return (System.currentTimeMillis() - timeLastTimeUpdate) / 1000f;
    }
}
