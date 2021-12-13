// fuck you rat go brrrrrrrr
package risingtide.tidehack.events.meteor;

import risingtide.tidehack.events.Cancellable;

public class MouseScrollEvent extends Cancellable {
    private static final MouseScrollEvent INSTANCE = new MouseScrollEvent();

    public double value;

    public static MouseScrollEvent get(double value) {
        INSTANCE.setCancelled(false);
        INSTANCE.value = value;

        return INSTANCE;
    }
}
