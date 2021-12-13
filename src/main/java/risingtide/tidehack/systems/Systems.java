// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems;

import risingtide.tidehack.TideHack;
import risingtide.tidehack.systems.accounts.Accounts;
import risingtide.tidehack.systems.commands.Commands;
import risingtide.tidehack.systems.config.Config;
import risingtide.tidehack.systems.friends.Friends;
import risingtide.tidehack.systems.macros.Macros;
import risingtide.tidehack.systems.modules.Modules;
import risingtide.tidehack.systems.profiles.Profiles;
import risingtide.tidehack.systems.proxies.Proxies;
import risingtide.tidehack.systems.waypoints.Waypoints;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Systems {
    @SuppressWarnings("rawtypes")
    private static final Map<Class<? extends System>, System<?>> systems = new HashMap<>();

    private static final List<Runnable> preLoadTasks = new ArrayList<>(1);
    private static System<?> config;

    public static void init() {
        config = add(new Config());
        config.load();
        config.init();

        add(new Modules());
        add(new Commands());
        add(new Friends());
        add(new Macros());
        add(new Accounts());
        add(new Waypoints());
        add(new Profiles());
        add(new Proxies());

        for (System<?> system : systems.values()) {
            if (system != config) system.init();
        }
    }

    private static System<?> add(System<?> system) {
        systems.put(system.getClass(), system);
        TideHack.EVENT_BUS.subscribe(system);

        return system;
    }

    public static void save(File folder) {
        TideHack.LOG.info("Saving");
        long start = java.lang.System.currentTimeMillis();

        for (System<?> system : systems.values()) system.save(folder);

        TideHack.LOG.info("Saved in {} milliseconds.", java.lang.System.currentTimeMillis() - start);
    }

    public static void save() {
        save(null);
    }

    public static void addPreLoadTask(Runnable task) {
        preLoadTasks.add(task);
    }

    public static void load(File folder) {
        TideHack.LOG.info("Loading");
        long start = java.lang.System.currentTimeMillis();

        for (Runnable task : preLoadTasks) task.run();

        for (System<?> system : systems.values()) {
            if (system != config) system.load(folder);
        }

        TideHack.LOG.info("Loaded in {} milliseconds", java.lang.System.currentTimeMillis() - start);
    }

    public static void load() {
        load(null);
    }

    @SuppressWarnings("unchecked")
    public static <T extends System<?>> T get(Class<T> klass) {
        return (T) systems.get(klass);
    }
}
