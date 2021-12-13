// fuck you rat go brrrrrrrr
package risingtide.tidehack.gui.tabs;

import risingtide.tidehack.gui.GuiTheme;
import risingtide.tidehack.gui.utils.Cell;
import risingtide.tidehack.gui.widgets.WWidget;
import risingtide.tidehack.gui.widgets.containers.WWindow;

public class WindowTabScreen extends TabScreen {
    private final WWindow window;

    public WindowTabScreen(GuiTheme theme, Tab tab) {
        super(theme, tab);

        window = super.add(theme.window(tab.name)).center().widget();
    }

    @Override
    public <W extends WWidget> Cell<W> add(W widget) {
        return window.add(widget);
    }

    @Override
    public void clear() {
        window.clear();
    }
}
