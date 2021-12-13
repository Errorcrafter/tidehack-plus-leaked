// fuck you rat go brrrrrrrr
package risingtide.tidehack.mixin;

import risingtide.tidehack.systems.modules.Modules;
import risingtide.tidehack.systems.modules.misc.BetterTab;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerListHud.class)
public class PlayerListHudMixin {

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I", ordinal = 0), index = 1)
    private int modifyCount(int count) {
        BetterTab module = Modules.get().get(BetterTab.class);

        return module.isActive() ? module.tabSize.get() : 80;
    }

    @Inject(method = "getPlayerName", at = @At("HEAD"), cancellable = true)
    public void getPlayerName(PlayerListEntry playerListEntry, CallbackInfoReturnable<Text> info) {
        BetterTab betterTab = Modules.get().get(BetterTab.class);

        if (betterTab.isActive()) info.setReturnValue(betterTab.getPlayerName(playerListEntry));
    }

}
