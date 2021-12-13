// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.misc;

import risingtide.tidehack.events.game.OpenScreenEvent;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;
import risingtide.tidehack.systems.modules.Modules;
import risingtide.tidehack.systems.modules.render.WaypointsModule;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.gui.screen.DeathScreen;

public class AutoRespawn extends Module {
    public AutoRespawn() {
        super(Categories.Player, "auto-respawn", "Automatically respawns after death.");
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onOpenScreenEvent(OpenScreenEvent event) {
        if (!(event.screen instanceof DeathScreen)) return;

        Modules.get().get(WaypointsModule.class).addDeath(mc.player.getPos());
        mc.player.requestRespawn();
        event.cancel();
    }
}
