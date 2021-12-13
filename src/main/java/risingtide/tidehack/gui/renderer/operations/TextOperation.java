// fuck you rat go brrrrrrrr
package risingtide.tidehack.gui.renderer.operations;

import risingtide.tidehack.gui.renderer.GuiRenderOperation;
import risingtide.tidehack.renderer.text.TextRenderer;

public class TextOperation extends GuiRenderOperation<TextOperation> {
    private String text;
    private TextRenderer renderer;

    public boolean title;

    public TextOperation set(String text, TextRenderer renderer, boolean title) {
        this.text = text;
        this.renderer = renderer;
        this.title = title;

        return this;
    }

    @Override
    protected void onRun() {
        renderer.render(text, x, y, color);
    }
}
