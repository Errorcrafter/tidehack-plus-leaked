// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.render.hud.modules;

import risingtide.tidehack.settings.BoolSetting;
import risingtide.tidehack.settings.Setting;
import risingtide.tidehack.settings.SettingGroup;
import risingtide.tidehack.systems.modules.render.hud.HUD;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class LookingAtHud extends DoubleTextHudElement {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> blockPosition = sgGeneral.add(new BoolSetting.Builder()
            .name("block-position")
            .description("Displays block's position.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> entityPosition = sgGeneral.add(new BoolSetting.Builder()
            .name("entity-position")
            .description("Displays entity's position.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> waterLogged = sgGeneral.add(new BoolSetting.Builder()
        .name("show-waterlogged-status")
        .description("Displays if a block is waterlogged or not")
        .defaultValue(true)
        .build()
    );

    public LookingAtHud(HUD hud) {
        super(hud, "looking-at", "Displays what entity or block you are looking at.", "Looking At: ");
    }

    @Override
    protected String getRight() {
        if (isInEditor()) return blockPosition.get() ? "Obsidian [0, 0, 0]" : "Obsidian";

        if (mc.crosshairTarget.getType() == HitResult.Type.BLOCK) {
            String name = mc.world.getBlockState(((BlockHitResult) mc.crosshairTarget).getBlockPos()).getBlock().getName().getString();
            BlockPos pos = ((BlockHitResult) mc.crosshairTarget).getBlockPos();
            String result = blockPosition.get() ? String.format("%s [%d, %d, %d]", name, pos.getX(), pos.getY(), pos.getZ()) : name;

            if(waterLogged.get()) result = blockPosition.get() ? String.format("%s %s[%d, %d, %d]", name, mc.world.getFluidState(pos).isIn(FluidTags.WATER) ? "(Waterlogged) " : "", pos.getX(), pos.getY(), pos.getZ()) : name;

            return result;
        }
        else if (mc.crosshairTarget.getType() == HitResult.Type.ENTITY) {
            String name = ((EntityHitResult) mc.crosshairTarget).getEntity().getDisplayName().getString();
            Vec3d pos = mc.crosshairTarget.getPos();

            return entityPosition.get() ? String.format("%s [%d, %d, %d]", name, (int) pos.x, (int) pos.y, (int) pos.z) : name;
        }

        return "";
    }
}
