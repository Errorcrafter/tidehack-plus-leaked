// fuck you rat go brrrrrrrr
package risingtide.tidehack.gui.themes.meteor.widgets;

import risingtide.tidehack.gui.renderer.GuiRenderer;
import risingtide.tidehack.gui.themes.meteor.MeteorWidget;
import risingtide.tidehack.gui.widgets.WTooltip;

public class WMeteorTooltip extends WTooltip implements MeteorWidget {
    public WMeteorTooltip(String text) {
        super(text);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        renderer.quad(this, theme().backgroundColor.get());
    }
}
