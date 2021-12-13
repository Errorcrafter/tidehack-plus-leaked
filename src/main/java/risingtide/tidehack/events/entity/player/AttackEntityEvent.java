// fuck you rat go brrrrrrrr
package risingtide.tidehack.events.entity.player;

import risingtide.tidehack.events.Cancellable;
import net.minecraft.entity.Entity;

public class AttackEntityEvent extends Cancellable {

    private static final AttackEntityEvent INSTANCE = new AttackEntityEvent();

    public Entity entity;

    public static AttackEntityEvent get(Entity entity) {
        INSTANCE.setCancelled(false);
        INSTANCE.entity = entity;
        return INSTANCE;
    }
}
