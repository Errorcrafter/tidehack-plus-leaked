// fuck you rat go brrrrrrrr
package risingtide.tidehack.mixin;

import risingtide.tidehack.gui.GuiThemes;
import risingtide.tidehack.systems.modules.Modules;
import risingtide.tidehack.systems.modules.misc.NameProtect;
import risingtide.tidehack.systems.proxies.Proxies;
import risingtide.tidehack.systems.proxies.Proxy;
import risingtide.tidehack.utils.render.color.Color;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerScreen.class)
public class MultiplayerScreenMixin extends Screen {
    private int textColor1;
    private int textColor2;

    private String loggedInAs;
    private int loggedInAsLength;

    public MultiplayerScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo info) {
        textColor1 = Color.fromRGBA(255, 255, 255, 255);
        textColor2 = Color.fromRGBA(175, 175, 175, 255);

        loggedInAs = "Logged in as ";
        loggedInAsLength = textRenderer.getWidth(loggedInAs);

        addDrawableChild(new ButtonWidget(this.width - 75 - 3, 3, 75, 20, new LiteralText("Accounts"), button -> {
            client.openScreen(GuiThemes.get().accountsScreen());
        }));

        addDrawableChild(new ButtonWidget(this.width - 75 - 3 - 75 - 2, 3, 75, 20, new LiteralText("Proxies"), button -> {
            client.openScreen(GuiThemes.get().proxiesScreen());
        }));
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
        float x = 3;
        float y = 3;

        // Logged in as
        textRenderer.drawWithShadow(matrices, loggedInAs, x, y, textColor1);
        textRenderer.drawWithShadow(matrices, Modules.get().get(NameProtect.class).getName(client.getSession().getUsername()), x + loggedInAsLength, y, textColor2);

        y += textRenderer.fontHeight + 2;

        // Proxy
        Proxy proxy = Proxies.get().getEnabled();

        String left = proxy != null ? "Using proxy " : "Not using a proxy";
        String right = proxy != null ? "(" + proxy.name + ") " + proxy.ip + ":" + proxy.port : null;

        textRenderer.drawWithShadow(matrices, left, x, y, textColor1);
        if (right != null) textRenderer.drawWithShadow(matrices, right, x + textRenderer.getWidth(left), y, textColor2);
    }
}
