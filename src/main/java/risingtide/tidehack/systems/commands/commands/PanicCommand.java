// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import risingtide.tidehack.systems.commands.Command;
import risingtide.tidehack.systems.modules.Module;
import risingtide.tidehack.systems.modules.Modules;
import net.minecraft.command.CommandSource;

import java.util.ArrayList;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class PanicCommand extends Command {
    public PanicCommand() {
        super("panic", "Disables all modules.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            new ArrayList<>(Modules.get().getActive()).forEach(Module::toggle);

            return SINGLE_SUCCESS;
        });
    }
}
