// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.render.hud.modules;

import risingtide.tidehack.mixin.ClientPlayerInteractionManagerAccessor;
import risingtide.tidehack.systems.modules.render.hud.HUD;

public class BreakingBlockHud extends DoubleTextHudElement {
    public BreakingBlockHud(HUD hud) {
        super(hud, "breaking-block", "Displays percentage of the block you are breaking.", "breaking block: ");
    }

    @Override
    protected String getRight() {
        if (isInEditor()) return "0%";

        return String.format("%.0f%%", ((ClientPlayerInteractionManagerAccessor) mc.interactionManager).getBreakingProgress() * 100);
    }
}
