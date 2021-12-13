// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.combat;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import risingtide.tidehack.settings.DoubleSetting;
import risingtide.tidehack.settings.EntityTypeListSetting;
import risingtide.tidehack.settings.Setting;
import risingtide.tidehack.settings.SettingGroup;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;
import risingtide.tidehack.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

public class Hitboxes extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Object2BooleanMap<EntityType<?>>> entities = sgGeneral.add(new EntityTypeListSetting.Builder()
        .name("entities")
        .description("Which entities to target.")
        .defaultValue(Utils.asObject2BooleanOpenHashMap(EntityType.PLAYER))
        .build()
    );

    private final Setting<Double> value = sgGeneral.add(new DoubleSetting.Builder()
        .name("expand")
        .description("How much to expand the hitbox of the entity.")
        .defaultValue(0.5)
        .build()
    );

    public Hitboxes() {
        super(Categories.Combat, "hitboxes", "Expands an entity's hitboxes.");
    }

    public double getEntityValue(Entity entity) {
        if (!isActive()) return 0;
        if (entities.get().getBoolean(entity.getType())) return value.get();
        return 0;
    }
}
