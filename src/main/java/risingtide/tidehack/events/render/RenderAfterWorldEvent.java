// fuck you rat go brrrrrrrr
package risingtide.tidehack.events.render;

public class RenderAfterWorldEvent {
    private static final RenderAfterWorldEvent INSTANCE = new RenderAfterWorldEvent();

    public static RenderAfterWorldEvent get() {
        return INSTANCE;
    }
}
