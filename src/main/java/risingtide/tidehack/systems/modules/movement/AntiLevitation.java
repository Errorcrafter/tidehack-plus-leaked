// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.movement;

import risingtide.tidehack.settings.BoolSetting;
import risingtide.tidehack.settings.Setting;
import risingtide.tidehack.settings.SettingGroup;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;

public class AntiLevitation extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> applyGravity = sgGeneral.add(new BoolSetting.Builder()
            .name("gravity")
            .description("Applies gravity.")
            .defaultValue(false)
            .build()
    );

    public AntiLevitation() {
        super(Categories.Movement, "anti-levitation", "Prevents the levitation effect from working.");
    }

    public boolean isApplyGravity() {
        return applyGravity.get();
    }
}
