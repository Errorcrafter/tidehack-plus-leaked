// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.render.hud.modules;

import risingtide.tidehack.settings.ColorSetting;
import risingtide.tidehack.settings.Setting;
import risingtide.tidehack.settings.SettingGroup;
import risingtide.tidehack.systems.modules.Modules;
import risingtide.tidehack.systems.modules.misc.NameProtect;
import risingtide.tidehack.systems.modules.render.hud.HUD;
import risingtide.tidehack.utils.render.color.SettingColor;

public class WelcomeHud extends DoubleTextHudElement {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<SettingColor> color = sgGeneral.add(new ColorSetting.Builder()
            .name("color")
            .description("Color of welcome text.")
            .defaultValue(new SettingColor(120, 43, 153))
            .build()
    );

    public WelcomeHud(HUD hud) {
        super(hud, "welcome", "Displays a welcome message.", "good day, ");
        rightColor = color.get();
    }

    @Override
    protected String getRight() {
        return Modules.get().get(NameProtect.class).getName(mc.getSession().getUsername()) + ".";
    }
}
