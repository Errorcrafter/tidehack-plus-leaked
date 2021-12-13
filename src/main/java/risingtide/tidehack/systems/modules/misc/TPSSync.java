// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.misc;

import risingtide.tidehack.events.world.TickEvent;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;
import risingtide.tidehack.systems.modules.Modules;
import risingtide.tidehack.systems.modules.world.Timer;
import risingtide.tidehack.utils.world.TickRate;
import meteordevelopment.orbit.EventHandler;

public class TPSSync extends Module {
    public TPSSync() {
        super(Categories.Misc, "tps-sync", "Syncs the clients TPS with the server's.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        Modules.get().get(Timer.class).setOverride((TickRate.INSTANCE.getTickRate() >= 1 ? TickRate.INSTANCE.getTickRate() : 1) / 20);
    }

    @Override
    public void onDeactivate() {
        Modules.get().get(Timer.class).setOverride(Timer.OFF);
    }
}
