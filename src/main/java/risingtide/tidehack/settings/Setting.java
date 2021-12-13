// fuck you rat go brrrrrrrr
package risingtide.tidehack.settings;

import risingtide.tidehack.systems.modules.Module;
import risingtide.tidehack.utils.Utils;
import risingtide.tidehack.utils.misc.IGetter;
import risingtide.tidehack.utils.misc.ISerializable;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class Setting<T> implements IGetter<T>, ISerializable<T> {
    private static final List<String> NO_SUGGESTIONS = new ArrayList<>(0);

    public final String name, title, description;

    protected final T defaultValue;
    protected T value;

    private final Consumer<T> onChanged;
    public final Consumer<Setting<T>> onModuleActivated;
    private final IVisible visible;

    public Module module;
    public boolean lastWasVisible;

    public Setting(String name, String description, T defaultValue, Consumer<T> onChanged, Consumer<Setting<T>> onModuleActivated, IVisible visible) {
        this.name = name;
        this.title = Utils.nameToTitle(name);
        this.description = description;
        this.defaultValue = defaultValue;
        reset(false);
        this.onChanged = onChanged;
        this.onModuleActivated = onModuleActivated;
        this.visible = visible;
    }

    @Override
    public T get() {
        return value;
    }

    public boolean set(T value) {
        if (!isValueValid(value)) return false;
        this.value = value;
        changed();
        return true;
    }

    public void reset(boolean callbacks) {
        value = defaultValue;
        if (callbacks) changed();
    }

    public void reset() {
        reset(true);
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public boolean parse(String str) {
        T newValue = parseImpl(str);

        if (newValue != null) {
            if (isValueValid(newValue)) {
                value = newValue;
                changed();
            }
        }

        return newValue != null;
    }

    public void changed() {
        if (onChanged != null) onChanged.accept(value);
    }

    public void onActivated() {
        if (onModuleActivated != null) onModuleActivated.accept(this);
    }

    public boolean isVisible() {
        return visible == null || visible.isVisible();
    }

    protected abstract T parseImpl(String str);

    protected abstract boolean isValueValid(T value);

    public Iterable<Identifier> getIdentifierSuggestions() {
        return null;
    }

    public List<String> getSuggestions() {
        return NO_SUGGESTIONS;
    }

    protected NbtCompound saveGeneral() {
        NbtCompound tag = new NbtCompound();
        tag.putString("name", name);
        return tag;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Setting<?> setting = (Setting<?>) o;
        return Objects.equals(name, setting.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public static <T> T parseId(Registry<T> registry, String name) {
        name = name.trim();

        Identifier id;
        if (name.contains(":")) id = new Identifier(name);
        else id = new Identifier("minecraft", name);
        if (registry.containsId(id)) return registry.get(id);

        return null;
    }
}
