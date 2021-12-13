// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.render.hud.modules;

import risingtide.tidehack.systems.modules.render.hud.HUD;
import risingtide.tidehack.utils.render.color.Color;
import risingtide.tidehack.utils.world.TickRate;

public class LagNotifierHud extends DoubleTextHudElement {
    private static final Color RED = new Color(225, 45, 45);
    private static final Color AMBER = new Color(235, 158, 52);
    private static final Color YELLOW = new Color(255, 255, 5);

    public LagNotifierHud(HUD hud) {
        super(hud, "lag-notifier", "Displays if the server is lagging in ticks.", "time since last tick: ");
    }

    @Override
    protected String getRight() {
        if (isInEditor()) {
            rightColor = RED;
            visible = true;
            return "4,3";
        }

        float timeSinceLastTick = TickRate.INSTANCE.getTimeSinceLastTick();

        if (timeSinceLastTick > 10) rightColor = RED;
        else if (timeSinceLastTick > 3) rightColor = AMBER;
        else rightColor = YELLOW;

        visible = timeSinceLastTick >= 1f;
        return String.format("%.1f", timeSinceLastTick);
    }
}
