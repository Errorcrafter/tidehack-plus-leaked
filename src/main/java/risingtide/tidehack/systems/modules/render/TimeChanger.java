// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.render;

import risingtide.tidehack.events.packets.PacketEvent;
import risingtide.tidehack.events.world.TickEvent;
import risingtide.tidehack.settings.DoubleSetting;
import risingtide.tidehack.settings.Setting;
import risingtide.tidehack.settings.SettingGroup;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;

public class TimeChanger extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> time = sgGeneral.add(new DoubleSetting.Builder()
            .name("time")
            .description("The specified time to be set.")
            .defaultValue(0)
            .sliderMin(-20000)
            .sliderMax(20000)
            .build()
    );

    long oldTime;

    public TimeChanger() {
        super(Categories.Render, "time-changer", "Makes you able to set a custom time.");
    }

    @Override
    public void onActivate() {
        oldTime = mc.world.getTime();
    }

    @Override
    public void onDeactivate() {
        mc.world.setTimeOfDay(oldTime);
    }

    @EventHandler
    private void onPacketReceive(PacketEvent.Receive event) {
        if (event.packet instanceof WorldTimeUpdateS2CPacket) {
            oldTime = ((WorldTimeUpdateS2CPacket) event.packet).getTime();
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        mc.world.setTimeOfDay(time.get().longValue());
    }
}
