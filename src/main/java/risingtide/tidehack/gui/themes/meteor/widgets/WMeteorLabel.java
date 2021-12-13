// fuck you rat go brrrrrrrr
package risingtide.tidehack.gui.themes.meteor.widgets;

import risingtide.tidehack.gui.renderer.GuiRenderer;
import risingtide.tidehack.gui.themes.meteor.MeteorWidget;
import risingtide.tidehack.gui.widgets.WLabel;

public class WMeteorLabel extends WLabel implements MeteorWidget {
    public WMeteorLabel(String text, boolean title) {
        super(text, title);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        if (!text.isEmpty()) {
            renderer.text(text, x, y, color != null ? color : (title ? theme().titleTextColor.get() : theme().textColor.get()), title);
        }
    }
}
