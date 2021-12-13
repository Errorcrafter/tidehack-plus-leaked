// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.world;

import risingtide.tidehack.gui.GuiTheme;
import risingtide.tidehack.gui.widgets.WWidget;
import risingtide.tidehack.gui.widgets.containers.WHorizontalList;
import risingtide.tidehack.gui.widgets.pressable.WButton;
import risingtide.tidehack.settings.DoubleSetting;
import risingtide.tidehack.settings.Setting;
import risingtide.tidehack.settings.SettingGroup;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Modules;
import risingtide.tidehack.systems.modules.Module;
import risingtide.tidehack.systems.modules.misc.TPSSync;

public class Timer extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> multiplier = sgGeneral.add(new DoubleSetting.Builder()
            .name("multiplier")
            .description("The timer multiplier amount.")
            .defaultValue(1)
            .min(0.1)
            .sliderMin(1)
            .build()
    );

    public static final double OFF = 1;
    private double override = 1;

    public Timer() {
        super(Categories.World, "timer", "Changes the speed of everything in your game.");
    }

    public double getMultiplier() {
        return override != OFF ? override : (isActive() ? multiplier.get() : OFF);
    }

    public void setOverride(double override) {
        this.override = override;
    }

    @Override
    public WWidget getWidget(GuiTheme theme) {
        if (Modules.get().get(TPSSync.class).isActive()) {
            WHorizontalList list = theme.horizontalList();
            list.add(theme.label("Multiplier is overwritten by TPSSync."));
            WButton disableBtn = list.add(theme.button("Disable TPSSync")).widget();
            disableBtn.action = () -> {
                TPSSync tpsSync = Modules.get().get(TPSSync.class);
                if (tpsSync.isActive()) tpsSync.toggle();
                list.visible = false;
            };
            return list;
        }
        return null;
    }
}
