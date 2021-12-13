// fuck you rat go brrrrrrrr
package risingtide.tidehack.gui.tabs;

import risingtide.tidehack.gui.GuiTheme;
import risingtide.tidehack.gui.WidgetScreen;
import risingtide.tidehack.gui.utils.Cell;
import risingtide.tidehack.gui.widgets.WWidget;

public class TabScreen extends WidgetScreen {
    public final Tab tab;

    public TabScreen(GuiTheme theme, Tab tab) {
        super(theme, tab.name);
        this.tab = tab;
    }

    public <T extends WWidget> Cell<T> addDirect(T widget) {
        return super.add(widget);
    }
}
