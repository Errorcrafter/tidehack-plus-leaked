// fuck you rat go brrrrrrrr
package risingtide.tidehack.gui.tabs;

import risingtide.tidehack.gui.GuiTheme;
import net.minecraft.client.gui.screen.Screen;
import risingtide.tidehack.utils.Utils;

public abstract class Tab {
    public final String name;

    public Tab(String name) {
        this.name = name;
    }

    public void openScreen(GuiTheme theme) {
        TabScreen screen = this.createScreen(theme);
        screen.addDirect(theme.topBar()).top().centerX();
        Utils.mc.openScreen(screen);
    }

    protected abstract TabScreen createScreen(GuiTheme theme);

    public abstract boolean isScreen(Screen screen);
}
