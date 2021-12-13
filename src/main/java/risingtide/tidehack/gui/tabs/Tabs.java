// fuck you rat go brrrrrrrr
package risingtide.tidehack.gui.tabs;

import risingtide.tidehack.gui.tabs.builtin.*;
import risingtide.tidehack.gui.tabs.builtin.*;

import java.util.ArrayList;
import java.util.List;

public class Tabs {
    private static final List<Tab> tabs = new ArrayList<>();

    public static void init() {
        add(new ModulesTab());
        add(new ConfigTab());
        add(new GuiTab());
        add(new HudTab());
        add(new FriendsTab());
        add(new MacrosTab());
        add(new ProfilesTab());
        add(new BaritoneTab());
    }

    public static void add(Tab tab) {
        tabs.add(tab);
    }

    public static List<Tab> get() {
        return tabs;
    }
}
