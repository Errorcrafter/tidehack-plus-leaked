// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import risingtide.tidehack.gui.GuiThemes;
import risingtide.tidehack.settings.Setting;
import risingtide.tidehack.systems.commands.Command;
import risingtide.tidehack.systems.commands.arguments.ModuleArgumentType;
import risingtide.tidehack.systems.modules.Module;
import risingtide.tidehack.systems.modules.Modules;
import risingtide.tidehack.systems.modules.render.hud.HUD;
import risingtide.tidehack.utils.player.ChatUtils;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class ResetCommand extends Command {

    public ResetCommand() {
        super("reset", "Resets specified settings.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(literal("settings")
                .then(argument("module", ModuleArgumentType.module()).executes(context -> {
                    Module module = context.getArgument("module", Module.class);
                    module.settings.forEach(group -> group.forEach(Setting::reset));
                    module.info("Reset all settings.");
                    return SINGLE_SUCCESS;
                }))
                .then(literal("all").executes(context -> {
                    Modules.get().getAll().forEach(module -> module.settings.forEach(group -> group.forEach(Setting::reset)));
                    ChatUtils.info("Modules", "Reset all module's settings");
                    return SINGLE_SUCCESS;
                }))
        ).then(literal("gui").executes(context -> {
            GuiThemes.get().clearWindowConfigs();
            ChatUtils.info("The ClickGUI positioning has been reset.");
            return SINGLE_SUCCESS;
        })).then(literal("bind")
                .then(argument("module", ModuleArgumentType.module()).executes(context -> {
                    Module module = context.getArgument("module", Module.class);

                    module.keybind.set(true, -1);
                    module.info("Reset bind.");

                    return SINGLE_SUCCESS;
                }))
                .then(literal("all").executes(context -> {
                    Modules.get().getAll().forEach(module -> module.keybind.set(true, -1));
                    ChatUtils.info("Modules", "Reset all binds");
                    return SINGLE_SUCCESS;
                }))
        ).then(literal("hud").executes(context -> {
            Modules.get().get(HUD.class).reset.run();
            Modules.get().get(HUD.class).info("Reset HUD elements.");
            return SINGLE_SUCCESS;
        }));
    }
}
