// fuck you rat go brrrrrrrr
package risingtide.tidehack.gui.themes.meteor.widgets.pressable;

import risingtide.tidehack.gui.renderer.GuiRenderer;
import risingtide.tidehack.gui.themes.meteor.MeteorGuiTheme;
import risingtide.tidehack.gui.themes.meteor.MeteorWidget;
import risingtide.tidehack.gui.widgets.pressable.WPlus;

public class WMeteorPlus extends WPlus implements MeteorWidget {
    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        MeteorGuiTheme theme = theme();
        double pad = pad();
        double s = theme.scale(3);

        renderBackground(renderer, this, pressed, mouseOver);
        renderer.quad(x + pad, y + height / 2 - s / 2, width - pad * 2, s, theme.plusColor.get());
        renderer.quad(x + width / 2 - s / 2, y + pad, s, height - pad * 2, theme.plusColor.get());
    }
}
