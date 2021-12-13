// fuck you rat go brrrrrrrr
package risingtide.tidehack.utils.render.color;

import risingtide.tidehack.TideHack;
import risingtide.tidehack.events.world.TickEvent;
import risingtide.tidehack.gui.GuiThemes;
import risingtide.tidehack.gui.WidgetScreen;
import risingtide.tidehack.gui.tabs.builtin.ConfigTab;
import risingtide.tidehack.settings.ColorSetting;
import risingtide.tidehack.settings.Setting;
import risingtide.tidehack.settings.SettingGroup;
import risingtide.tidehack.systems.waypoints.Waypoint;
import risingtide.tidehack.systems.waypoints.Waypoints;
import risingtide.tidehack.utils.misc.UnorderedArrayList;
import meteordevelopment.orbit.EventHandler;

import java.util.List;

import static risingtide.tidehack.utils.Utils.mc;

public class RainbowColors {

    public static final RainbowColor GLOBAL = new RainbowColor().setSpeed(ConfigTab.rainbowSpeed.get() / 100);

    private static final List<Setting<SettingColor>> colorSettings = new UnorderedArrayList<>();
    private static final List<SettingColor> colors = new UnorderedArrayList<>();
    private static final List<Runnable> listeners = new UnorderedArrayList<>();

    public static void init() {
        TideHack.EVENT_BUS.subscribe(RainbowColors.class);
    }

    public static void addSetting(Setting<SettingColor> setting) {
        colorSettings.add(setting);
    }

    public static void removeSetting(Setting<SettingColor> setting) {
        colorSettings.remove(setting);
    }

    public static void add(SettingColor color) {
        colors.add(color);
    }

    public static void register(Runnable runnable) {
        listeners.add(runnable);
    }

    @EventHandler
    private static void onTick(TickEvent.Post event) {
        GLOBAL.getNext();

        for (Setting<SettingColor> setting : colorSettings) {
            if (setting.module == null || setting.module.isActive()) setting.get().update();
        }

        for (SettingColor color : colors) {
            color.update();
        }

        for (Waypoint waypoint : Waypoints.get()) {
            waypoint.color.update();
        }

        if (mc.currentScreen instanceof WidgetScreen) {
            for (SettingGroup group : GuiThemes.get().settings) {
                for (Setting<?> setting : group) {
                    if (setting instanceof ColorSetting) ((SettingColor) setting.get()).update();
                }
            }
        }

        for (Runnable listener : listeners) listener.run();
    }
}
