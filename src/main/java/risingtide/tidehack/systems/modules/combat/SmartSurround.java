// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.combat;

//Created by squidoodly 15/04/2020

import risingtide.tidehack.events.entity.EntityAddedEvent;
import risingtide.tidehack.events.world.TickEvent;
import risingtide.tidehack.settings.BoolSetting;
import risingtide.tidehack.settings.DoubleSetting;
import risingtide.tidehack.settings.Setting;
import risingtide.tidehack.settings.SettingGroup;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;
import risingtide.tidehack.utils.player.DamageUtils;
import risingtide.tidehack.utils.player.FindItemResult;
import risingtide.tidehack.utils.player.InvUtils;
import risingtide.tidehack.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;

public class SmartSurround extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> minDamage = sgGeneral.add(new DoubleSetting.Builder()
            .name("min-damage")
            .description("The minimum damage before this activates.")
            .defaultValue(5.5)
            .build()
    );

    private final Setting<Boolean> rotate = sgGeneral.add(new BoolSetting.Builder()
            .name("rotate")
            .description("Forces you to rotate towards the blocks being placed.")
            .defaultValue(true)
            .build()
    );

    private int rPosX;
    private int rPosZ;
    private Entity crystal;

    public SmartSurround() {
        super(Categories.Combat, "smart-surround", "Attempts to save you from crystals automatically.");
    }

    @EventHandler
    private void onSpawn(EntityAddedEvent event) {
        crystal = event.entity;

        if (event.entity.getType() == EntityType.END_CRYSTAL) {
            if (DamageUtils.crystalDamage(mc.player, event.entity.getPos()) > minDamage.get()) {
                rPosX = mc.player.getBlockPos().getX() - event.entity.getBlockPos().getX();
                rPosZ = mc.player.getBlockPos().getZ() - event.entity.getBlockPos().getZ();
            }
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        FindItemResult obsidian = InvUtils.findInHotbar(Items.OBSIDIAN, Items.CRYING_OBSIDIAN);

        if (!obsidian.found()) return;

        int prevSlot = mc.player.getInventory().selectedSlot;

        if ((rPosX >= 2) && (rPosZ == 0)) {
            placeObi(crystal, rPosX - 1, 0, obsidian);
        } else if ((rPosX > 1) && (rPosZ > 1)) {
            placeObi(crystal, rPosX, rPosZ - 1, obsidian);
            placeObi(crystal, rPosX - 1, rPosZ, obsidian);
        } else if ((rPosX == 0) && (rPosZ >= 2)) {
            placeObi(crystal, 0, rPosZ - 1, obsidian);
        } else if ((rPosX < -1) && (rPosZ < -1)) {
            placeObi(crystal, rPosX, rPosZ + 1, obsidian);
            placeObi(crystal, rPosX + 1, rPosZ, obsidian);
        } else if ((rPosX == 0) && (rPosZ <= -2)) {
            placeObi(crystal, 0, rPosZ + 1, obsidian);
        } else if ((rPosX > 1) && (rPosZ < -1)) {
            placeObi(crystal, rPosX, rPosZ + 1, obsidian);
            placeObi(crystal, rPosX - 1, rPosZ, obsidian);
        } else if ((rPosX <= -2) && (rPosZ == 0)) {
            placeObi(crystal, rPosX + 1, 0, obsidian);
        } else if ((rPosX < -1) && (rPosZ > 1)) {
            placeObi(crystal, rPosX, rPosZ - 1, obsidian);
            placeObi(crystal, rPosX + 1, rPosZ, obsidian);
        }

        InvUtils.swap(prevSlot, false);
    }

    private void placeObi(Entity crystal, int x, int z, FindItemResult findItemResult) {
        BlockUtils.place(crystal.getBlockPos().add(x, -1, z), findItemResult, rotate.get(), 100, false, true, false);
    }
}
