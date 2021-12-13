// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.movement;

import risingtide.tidehack.events.meteor.KeyEvent;
import risingtide.tidehack.events.world.TickEvent;
import risingtide.tidehack.settings.BoolSetting;
import risingtide.tidehack.settings.Setting;
import risingtide.tidehack.settings.SettingGroup;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;
import risingtide.tidehack.systems.modules.Modules;
import risingtide.tidehack.systems.modules.render.Freecam;
import risingtide.tidehack.utils.misc.input.KeyAction;
import meteordevelopment.orbit.EventHandler;

public class AirJump extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> maintainLevel = sgGeneral.add(new BoolSetting.Builder()
            .name("maintain-level")
            .description("Maintains your current Y level when holding the jump key.")
            .defaultValue(false)
            .build()
    );

    private int level;

    public AirJump() {
        super(Categories.Movement, "air-jump", "Lets you jump in the air.");
    }

    @Override
    public void onActivate() {
        level = mc.player.getBlockPos().getY();
    }

    @EventHandler
    private void onKey(KeyEvent event) {
        if (Modules.get().isActive(Freecam.class) || mc.currentScreen != null || mc.player.isOnGround()) return;

        if (event.action != KeyAction.Press) return;

        if (mc.options.keyJump.matchesKey(event.key, 0)) {
            level = mc.player.getBlockPos().getY();
            mc.player.jump();
        }
        else if (mc.options.keySneak.matchesKey(event.key, 0)) {
            level--;
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (Modules.get().isActive(Freecam.class) || mc.player.isOnGround()) return;

        if (maintainLevel.get() && mc.player.getBlockPos().getY() == level && mc.options.keyJump.isPressed()) {
            mc.player.jump();
        }
    }
}
