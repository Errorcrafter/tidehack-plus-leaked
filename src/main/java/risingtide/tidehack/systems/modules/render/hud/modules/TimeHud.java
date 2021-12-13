// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.render.hud.modules;

import risingtide.tidehack.settings.EnumSetting;
import risingtide.tidehack.settings.Setting;
import risingtide.tidehack.settings.SettingGroup;
import risingtide.tidehack.systems.modules.render.hud.HUD;
import risingtide.tidehack.utils.Utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class TimeHud extends DoubleTextHudElement {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<TimeType> timeType = sgGeneral.add(new EnumSetting.Builder<TimeType>()
            .name("use-time")
            .description("Which time to use.")
            .defaultValue(TimeType.World)
            .build()
    );

    public TimeHud(HUD hud) {
        super(hud, "time", "Displays the world time.", "ingame time: ");
    }

    @Override
    protected String getRight() {
        return switch (timeType.get()) {
            case World -> isInEditor() ? "00:00" : Utils.getWorldTime();
            case Local ->  LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
        };
    }

    public enum TimeType {
        World,
        Local
    }
}
