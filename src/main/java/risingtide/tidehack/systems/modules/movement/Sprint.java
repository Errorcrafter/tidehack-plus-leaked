// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.movement;

import risingtide.tidehack.events.world.TickEvent;
import risingtide.tidehack.settings.BoolSetting;
import risingtide.tidehack.settings.Setting;
import risingtide.tidehack.settings.SettingGroup;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;

public class Sprint extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> whenStationary = sgGeneral.add(new BoolSetting.Builder()
            .name("when-stationary")
            .description("Continues sprinting even if you do not move.")
            .defaultValue(true)
            .build()
    );

    public Sprint() {
        super(Categories.Movement, "sprint", "Automatically sprints.");
    }

    @Override
    public void onDeactivate() {
        mc.player.setSprinting(false);
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player.forwardSpeed > 0 && !whenStationary.get()) {
            mc.player.setSprinting(true);
        } else if (whenStationary.get()) {
            mc.player.setSprinting(true);
        }
    }
}
