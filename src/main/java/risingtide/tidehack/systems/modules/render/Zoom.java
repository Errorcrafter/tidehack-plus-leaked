// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.render;

import risingtide.tidehack.events.meteor.MouseScrollEvent;
import risingtide.tidehack.events.render.GetFovEvent;
import risingtide.tidehack.events.world.TickEvent;
import risingtide.tidehack.settings.BoolSetting;
import risingtide.tidehack.settings.DoubleSetting;
import risingtide.tidehack.settings.Setting;
import risingtide.tidehack.settings.SettingGroup;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;

public class Zoom extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> zoom = sgGeneral.add(new DoubleSetting.Builder()
            .name("zoom")
            .description("How much to zoom.")
            .defaultValue(6)
            .min(1)
            .build()
    );

    private final Setting<Double> scrollSensitivity = sgGeneral.add(new DoubleSetting.Builder()
            .name("scroll-sensitivity")
            .description("Allows you to change zoom value using scroll wheel. 0 to disable.")
            .defaultValue(1)
            .min(0)
            .build()
    );

    private final Setting<Boolean> cinematic = sgGeneral.add(new BoolSetting.Builder()
            .name("cinematic")
            .description("Enables cinematic camera.")
            .defaultValue(false)
            .build()
    );

    private boolean preCinematic;
    private double preMouseSensitivity;
    private double value;
    private double lastFov;

    public Zoom() {
        super(Categories.Render, "zoom", "Zooms your view.");
    }

    @Override
    public void onActivate() {
        preCinematic = mc.options.smoothCameraEnabled;
        preMouseSensitivity = mc.options.mouseSensitivity;
        value = zoom.get();
        lastFov = mc.options.fov;
    }

    @Override
    public void onDeactivate() {
        mc.options.smoothCameraEnabled = preCinematic;
        mc.options.mouseSensitivity = preMouseSensitivity;

        mc.worldRenderer.scheduleTerrainUpdate();
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        mc.options.smoothCameraEnabled = cinematic.get();

        if (!cinematic.get()) {
            mc.options.mouseSensitivity = preMouseSensitivity / Math.max(value * 0.5, 1);
        }
    }

    @EventHandler
    private void onMouseScroll(MouseScrollEvent event) {
        if (scrollSensitivity.get() > 0) {
            value += event.value * 0.25 * (scrollSensitivity.get() * value);
            if (value < 1) value = 1;

            event.cancel();
        }
    }

    @EventHandler
    private void onGetFov(GetFovEvent event) {
        event.fov /= value;

        if (lastFov != event.fov) mc.worldRenderer.scheduleTerrainUpdate();
        lastFov = event.fov;
    }
}
