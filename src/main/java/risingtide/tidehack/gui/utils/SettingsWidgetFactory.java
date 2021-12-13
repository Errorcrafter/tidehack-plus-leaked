// fuck you rat go brrrrrrrr
package risingtide.tidehack.gui.utils;

import risingtide.tidehack.gui.GuiTheme;
import risingtide.tidehack.gui.widgets.WWidget;
import risingtide.tidehack.settings.Settings;

public interface SettingsWidgetFactory {
    WWidget create(GuiTheme theme, Settings settings, String filter);
}
