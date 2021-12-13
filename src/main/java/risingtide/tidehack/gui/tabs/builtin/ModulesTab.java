// fuck you rat go brrrrrrrr
package risingtide.tidehack.gui.tabs.builtin;

import risingtide.tidehack.gui.GuiTheme;
import risingtide.tidehack.gui.GuiThemes;
import risingtide.tidehack.gui.tabs.Tab;
import risingtide.tidehack.gui.tabs.TabScreen;
import net.minecraft.client.gui.screen.Screen;

public class ModulesTab extends Tab {
    public ModulesTab() {
        super("modules");
    }

    @Override
    public TabScreen createScreen(GuiTheme theme) {
        return theme.modulesScreen();
    }

    @Override
    public boolean isScreen(Screen screen) {
        return GuiThemes.get().isModulesScreen(screen);
    }
}
