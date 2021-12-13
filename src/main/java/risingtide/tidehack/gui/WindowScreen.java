// fuck you rat go brrrrrrrr
package risingtide.tidehack.gui;

import risingtide.tidehack.gui.utils.Cell;
import risingtide.tidehack.gui.widgets.WWidget;
import risingtide.tidehack.gui.widgets.containers.WWindow;

public abstract class WindowScreen extends WidgetScreen {
    private final WWindow window;

    public WindowScreen(GuiTheme theme, String title) {
        super(theme, title);

        window = super.add(theme.window(title)).center().widget();
        window.view.scrollOnlyWhenMouseOver = false;
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
