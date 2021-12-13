// fuck you rat go brrrrrrrr
package risingtide.tidehack.events.meteor;

import risingtide.tidehack.events.Cancellable;
import risingtide.tidehack.utils.misc.input.KeyAction;

public class MouseButtonEvent extends Cancellable {
    private static final MouseButtonEvent INSTANCE = new MouseButtonEvent();

    public int button;
    public KeyAction action;

    public static MouseButtonEvent get(int button, KeyAction action) {
        INSTANCE.setCancelled(false);
        INSTANCE.button = button;
        INSTANCE.action = action;
        return INSTANCE;
    }
}
