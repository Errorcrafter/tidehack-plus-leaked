// fuck you rat go brrrrrrrr
package risingtide.tidehack.gui.tabs.builtin;

import risingtide.tidehack.gui.GuiTheme;
import risingtide.tidehack.gui.GuiThemes;
import risingtide.tidehack.gui.tabs.Tab;
import risingtide.tidehack.gui.tabs.TabScreen;
import risingtide.tidehack.gui.tabs.WindowTabScreen;
import risingtide.tidehack.gui.widgets.containers.WTable;
import risingtide.tidehack.gui.widgets.input.WDropdown;
import net.minecraft.client.gui.screen.Screen;
import risingtide.tidehack.utils.Utils;

public class GuiTab extends Tab {
    public GuiTab() {
        super("gui");
    }

    @Override
    public TabScreen createScreen(GuiTheme theme) {
        return new GuiScreen(theme, this);
    }

    @Override
    public boolean isScreen(Screen screen) {
        return screen instanceof GuiScreen;
    }

    private static class GuiScreen extends WindowTabScreen {
        public GuiScreen(GuiTheme theme, Tab tab) {
            super(theme, tab);

            WTable table = add(theme.table()).expandX().widget();

            table.add(theme.label("Theme:"));
            WDropdown<String> themeW = table.add(theme.dropdown(GuiThemes.getNames(), GuiThemes.get().name)).widget();
            themeW.action = () -> {
                GuiThemes.select(themeW.get());

                Utils.mc.openScreen(null);
                tab.openScreen(GuiThemes.get());
            };

            theme.settings.onActivated();
            add(theme.settings(theme.settings)).expandX();
        }
    }
}
