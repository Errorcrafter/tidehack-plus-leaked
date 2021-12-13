// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.movement;

import risingtide.tidehack.settings.EnumSetting;
import risingtide.tidehack.settings.Setting;
import risingtide.tidehack.settings.SettingGroup;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;
import risingtide.tidehack.systems.modules.Modules;
import risingtide.tidehack.systems.modules.render.Freecam;

public class Sneak extends Module {
    public enum Mode {
        Packet,
        Vanilla
    }
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
            .name("mode")
            .description("Which method to sneak.")
            .defaultValue(Mode.Vanilla)
            .build()
    );

    public Sneak() {
        super (Categories.Movement, "sneak", "Sneaks for you");
    }

    public boolean doPacket() {
        return isActive() && !Modules.get().isActive(Freecam.class) && mode.get() == Mode.Packet;
    }

    public boolean doVanilla() {
        return isActive() && !Modules.get().isActive(Freecam.class) && mode.get() == Mode.Vanilla;
    }
}
