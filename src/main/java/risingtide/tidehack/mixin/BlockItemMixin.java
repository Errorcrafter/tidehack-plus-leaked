// fuck you rat go brrrrrrrr
package risingtide.tidehack.mixin;

import risingtide.tidehack.TideHack;
import risingtide.tidehack.events.entity.player.PlaceBlockEvent;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItemMixin {
    @Inject(method = "place(Lnet/minecraft/item/ItemPlacementContext;Lnet/minecraft/block/BlockState;)Z", at = @At("HEAD"))
    private void onPlace(ItemPlacementContext context, BlockState state, CallbackInfoReturnable<Boolean> info) {
        TideHack.EVENT_BUS.post(PlaceBlockEvent.get(context.getBlockPos(), state.getBlock()));
    }
}
