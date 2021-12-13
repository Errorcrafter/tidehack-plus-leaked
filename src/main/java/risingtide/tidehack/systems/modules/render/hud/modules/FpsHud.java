// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.render.hud.modules;

import risingtide.tidehack.mixin.MinecraftClientAccessor;
import risingtide.tidehack.systems.modules.render.hud.HUD;

public class FpsHud extends DoubleTextHudElement {
    public FpsHud(HUD hud) {
        super(hud, "fps", "Displays your FPS.", "fps: ");
    }

    @Override
    protected String getRight() {
        return Integer.toString(((MinecraftClientAccessor) mc).getFps());
    }
}
