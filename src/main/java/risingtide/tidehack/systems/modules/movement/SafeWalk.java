// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.movement;

import risingtide.tidehack.events.entity.player.ClipAtLedgeEvent;
import risingtide.tidehack.events.world.CollisionShapeEvent;
import risingtide.tidehack.settings.*;
import risingtide.tidehack.settings.BlockListSetting;
import risingtide.tidehack.settings.BoolSetting;
import risingtide.tidehack.settings.Setting;
import risingtide.tidehack.settings.SettingGroup;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.*;
import net.minecraft.util.shape.VoxelShapes;

import java.util.Arrays;
import java.util.List;

public class SafeWalk extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> ledge = sgGeneral.add(new BoolSetting.Builder()
        .name("ledge")
        .description("Prevents you from walking of blocks, like pressing shift.")
        .defaultValue(true)
        .build()
    );

    private final Setting<List<Block>> blocks = sgGeneral.add(new BlockListSetting.Builder()
        .name("blocks")
        .description("Which blocks to prevent on walking")
        .filter(this::blockFilter)
        .defaultValue(Arrays.asList())
        .build()
    );

    private final Setting<Boolean> magma = sgGeneral.add(new BoolSetting.Builder()
        .name("magma")
        .description("Prevents you from walking over magma blocks.")
        .defaultValue(false)
        .build()
    );

    public SafeWalk() {
        super(Categories.Movement, "safe-walk", "Prevents you from walking off blocks or on blocks that you dont want.");
    }

    @EventHandler
    private void onClipAtLedge(ClipAtLedgeEvent event) {
        if (!mc.player.isSneaking()) event.setClip(ledge.get());
    }

    @EventHandler
    private void onCollisionShape(CollisionShapeEvent event) {
        if (mc.world == null || mc.player == null) return;
        if (event.type != CollisionShapeEvent.CollisionType.BLOCK) return;
        if (blocks.get().contains(event.state.getBlock())) {
            event.shape = VoxelShapes.fullCube();
        }
        else if (magma.get() && !mc.player.isSneaking()
            && event.state.isAir()
            && mc.world.getBlockState(event.pos.down()).getBlock() == Blocks.MAGMA_BLOCK) {
            event.shape = VoxelShapes.fullCube();
        }
    }

    private boolean blockFilter(Block block) {
        if (block instanceof AbstractFireBlock) return true;
        if (block instanceof AbstractPressurePlateBlock) return true;
        if (block instanceof TripwireBlock) return true;
        if (block instanceof TripwireHookBlock) return true;
        if (block instanceof CobwebBlock) return true;
        if (block instanceof CampfireBlock) return true;
        if (block instanceof SweetBerryBushBlock) return true;
        if (block instanceof CactusBlock) return true;
        if (block instanceof AbstractRailBlock) return true;
        if (block instanceof TrapdoorBlock) return true;
        if (block instanceof PowderSnowBlock) return true;
        if (block instanceof AbstractCauldronBlock) return true;
        return false;
    }
}
