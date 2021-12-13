// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.render;

import risingtide.tidehack.events.render.Render3DEvent;
import risingtide.tidehack.settings.IntSetting;
import risingtide.tidehack.settings.Setting;
import risingtide.tidehack.settings.SettingGroup;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;

public class CustomFOV extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Integer> fovSetting = sgGeneral.add(new IntSetting.Builder()
            .name("fov")
            .description("Your custom fov.")
            .defaultValue(100)
            .sliderMin(1)
            .sliderMax(179)
            .build()
    );

    private double fov;

    public CustomFOV() {
        super(Categories.Render, "custom-fov", "Allows your FOV to be more customizable.");
    }

    @Override
    public void onActivate() {
        fov = mc.options.fov;
        mc.options.fov = fovSetting.get();
    }

    @EventHandler
    private void onRender(Render3DEvent event) {
        if (fovSetting.get() != mc.options.fov) {
            mc.options.fov = fovSetting.get();
        }
    }

    @Override
    public void onDeactivate() {
     mc.options.fov = fov;
    }
}
