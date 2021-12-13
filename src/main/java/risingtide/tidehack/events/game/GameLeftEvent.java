// fuck you rat go brrrrrrrr
package risingtide.tidehack.events.game;

public class GameLeftEvent {
    private static final GameLeftEvent INSTANCE = new GameLeftEvent();

    public static GameLeftEvent get() {
        return INSTANCE;
    }
}
