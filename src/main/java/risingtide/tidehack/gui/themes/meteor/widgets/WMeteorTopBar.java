// fuck you rat go brrrrrrrr
package risingtide.tidehack.gui.themes.meteor.widgets;

import risingtide.tidehack.gui.themes.meteor.MeteorWidget;
import risingtide.tidehack.gui.widgets.WTopBar;
import risingtide.tidehack.utils.render.color.Color;

public class WMeteorTopBar extends WTopBar implements MeteorWidget {
    @Override
    protected Color getButtonColor(boolean pressed, boolean hovered) {
        return theme().backgroundColor.get(pressed, hovered);
    }

    @Override
    protected Color getNameColor() {
        return theme().textColor.get();
    }
}
