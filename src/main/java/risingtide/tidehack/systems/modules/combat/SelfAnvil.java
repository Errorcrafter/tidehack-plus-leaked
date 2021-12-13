// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.combat;

import risingtide.tidehack.events.game.OpenScreenEvent;
import risingtide.tidehack.events.world.TickEvent;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;
import risingtide.tidehack.utils.player.InvUtils;
import risingtide.tidehack.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;

public class SelfAnvil extends Module {
    public SelfAnvil() {
        super(Categories.Combat, "self-anvil", "Automatically places an anvil on you to prevent other players from going into your hole.");
    }

    @EventHandler
    private void onOpenScreen(OpenScreenEvent event) {
        if (event.screen instanceof AnvilScreen) event.cancel();
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (BlockUtils.place(mc.player.getBlockPos().add(0, 2, 0), InvUtils.findInHotbar(itemStack -> Block.getBlockFromItem(itemStack.getItem()) instanceof AnvilBlock), 0)) {
            toggle();
        }
    }
}
