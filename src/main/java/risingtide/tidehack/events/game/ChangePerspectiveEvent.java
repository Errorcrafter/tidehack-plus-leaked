// fuck you rat go brrrrrrrr
package risingtide.tidehack.events.game;

import risingtide.tidehack.events.Cancellable;
import net.minecraft.client.option.Perspective;

public class ChangePerspectiveEvent extends Cancellable {
    private static final ChangePerspectiveEvent INSTANCE = new ChangePerspectiveEvent();

    public Perspective perspective;

    public static ChangePerspectiveEvent get(Perspective perspective) {
        INSTANCE.setCancelled(false);
        INSTANCE.perspective = perspective;
        return INSTANCE;
    }
}
