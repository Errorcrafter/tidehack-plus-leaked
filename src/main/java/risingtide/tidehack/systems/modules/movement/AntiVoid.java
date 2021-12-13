// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.movement;

import risingtide.tidehack.events.world.TickEvent;
import risingtide.tidehack.settings.EnumSetting;
import risingtide.tidehack.settings.Setting;
import risingtide.tidehack.settings.SettingGroup;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;
import risingtide.tidehack.systems.modules.Modules;
import risingtide.tidehack.utils.Utils;
import meteordevelopment.orbit.EventHandler;

public class AntiVoid extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
            .name("mode")
            .description("The method to prevent you from falling into the void.")
            .defaultValue(Mode.Jump)
            .onChanged(a -> onActivate())
            .build()
    );

    private boolean wasFlightEnabled, hasRun;

    public AntiVoid() {
        super(Categories.Movement, "anti-void", "Attempts to prevent you from falling into the void.");
    }

    @Override
    public void onActivate() {
        if (mode.get() == Mode.Flight) wasFlightEnabled = Modules.get().isActive(Flight.class);
    }

    @Override
    public void onDeactivate() {
        if (!wasFlightEnabled && mode.get() == Mode.Flight && Utils.canUpdate() && Modules.get().isActive(Flight.class)) {
            Modules.get().get(Flight.class).toggle();
        }
    }

    @EventHandler
    private void onPreTick(TickEvent.Pre event) {
        if (mc.player.getY() > 0 || mc.player.getY() < -15) {
            if (hasRun && mode.get() == Mode.Flight && Modules.get().isActive(Flight.class)) {
                Modules.get().get(Flight.class).toggle();
                hasRun = false;
            }
            return;
        }

        switch (mode.get()) {
            case Flight:
                if (!Modules.get().isActive(Flight.class)) Modules.get().get(Flight.class).toggle();
                hasRun = true;
                break;
            case Jump:
                mc.player.jump();
                break;
        }
    }

    public enum Mode {
        Flight,
        Jump
    }

}
