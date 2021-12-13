// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.render.hud.modules;

import risingtide.tidehack.systems.modules.render.hud.HUD;
import risingtide.tidehack.utils.Utils;

public class ServerHud extends DoubleTextHudElement {
    public ServerHud(HUD hud) {
        super(hud, "server", "Displays the server you're currently in.", "server: ");
    }

    @Override
    protected String getRight() {
        if (!Utils.canUpdate()) return "none";

        return Utils.getWorldName();
    }
}



