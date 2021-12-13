// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.render.hud.modules;

import risingtide.tidehack.systems.config.Config;
import risingtide.tidehack.systems.modules.render.hud.HUD;

public class WatermarkHud extends DoubleTextHudElement {
    public WatermarkHud(HUD hud) {
        super(hud, "watermark", "Displays a TideHack watermark.", "tidehack b");
    }

    @Override
    protected String getRight() {
        if (Config.get().devBuild.isEmpty()) {
            return Config.get().version.toString();
        }

        return Config.get().version + " " + Config.get().devBuild;
    }
}
