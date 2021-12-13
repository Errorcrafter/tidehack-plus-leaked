// fuck you rat go brrrrrrrr
package risingtide.tidehack.gui.themes.meteor.widgets;

import risingtide.tidehack.gui.renderer.GuiRenderer;
import risingtide.tidehack.gui.themes.meteor.MeteorWidget;
import risingtide.tidehack.gui.widgets.WMultiLabel;
import risingtide.tidehack.utils.render.color.Color;

public class WMeteorMultiLabel extends WMultiLabel implements MeteorWidget {
    public WMeteorMultiLabel(String text, boolean title, double maxWidth) {
        super(text, title, maxWidth);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        double h = theme.textHeight(title);
        Color color = theme().textColor.get();

        for (int i = 0; i < lines.size(); i++) {
            renderer.text(lines.get(i), x, y + h * i, color, false);
        }
    }
}
