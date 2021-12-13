// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.render.hud.modules;

import risingtide.tidehack.systems.modules.render.hud.HUD;
import risingtide.tidehack.utils.Utils;

public class SpeedHud extends DoubleTextHudElement {
    public SpeedHud(HUD hud) {
        super(hud, "speed", "Displays your horizontal speed.", "bps: ");
    }

    @Override
    protected String getRight() {
        if (isInEditor()) return "0,0";
        return String.format("%.1f", Utils.getPlayerSpeed());
    }
}
