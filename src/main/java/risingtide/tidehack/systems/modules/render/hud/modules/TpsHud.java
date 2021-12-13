// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.render.hud.modules;

import risingtide.tidehack.systems.modules.render.hud.HUD;
import risingtide.tidehack.utils.world.TickRate;

public class TpsHud extends DoubleTextHudElement {
    public TpsHud(HUD hud) {
        super(hud, "tps", "Displays the server's TPS.", "tps: ");
    }

    @Override
    protected String getRight() {
        return String.format("%.1f", TickRate.INSTANCE.getTickRate());
    }
}
