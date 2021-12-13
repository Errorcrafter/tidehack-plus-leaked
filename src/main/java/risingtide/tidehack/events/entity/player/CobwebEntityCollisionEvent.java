// fuck you rat go brrrrrrrr
package risingtide.tidehack.events.entity.player;

import risingtide.tidehack.events.Cancellable;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class CobwebEntityCollisionEvent extends Cancellable {
    private static final CobwebEntityCollisionEvent INSTANCE = new CobwebEntityCollisionEvent();

    public BlockState state;
    public BlockPos blockPos;

    public static CobwebEntityCollisionEvent get(BlockState state, BlockPos blockPos) {
        INSTANCE.setCancelled(false);
        INSTANCE.state = state;
        INSTANCE.blockPos = blockPos;
        return INSTANCE;
    }
}
