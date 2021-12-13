// fuck you rat go brrrrrrrr
package risingtide.tidehack.gui.themes.meteor;

import risingtide.tidehack.gui.renderer.GuiRenderer;
import risingtide.tidehack.gui.utils.BaseWidget;
import risingtide.tidehack.gui.widgets.WWidget;
import risingtide.tidehack.utils.render.color.Color;

public interface MeteorWidget extends BaseWidget {
    default MeteorGuiTheme theme() {
        return (MeteorGuiTheme) getTheme();
    }

    default void renderBackground(GuiRenderer renderer, WWidget widget, boolean pressed, boolean mouseOver) {
        MeteorGuiTheme theme = theme();
        double s = theme.scale(2);

        renderer.quad(widget.x + s, widget.y + s, widget.width - s * 2, widget.height - s * 2, theme.backgroundColor.get(pressed, mouseOver));

        Color outlineColor = theme.outlineColor.get(pressed, mouseOver);
        renderer.quad(widget.x, widget.y, widget.width, s, outlineColor);
        renderer.quad(widget.x, widget.y + widget.height - s, widget.width, s, outlineColor);
        renderer.quad(widget.x, widget.y + s, s, widget.height - s * 2, outlineColor);
        renderer.quad(widget.x + widget.width - s, widget.y + s, s, widget.height - s * 2, outlineColor);
    }
}
