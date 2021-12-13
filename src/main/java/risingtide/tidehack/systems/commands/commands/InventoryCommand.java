// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import risingtide.tidehack.TideHack;
import risingtide.tidehack.systems.commands.Command;
import risingtide.tidehack.systems.commands.arguments.PlayerArgumentType;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class InventoryCommand extends Command {
    public InventoryCommand() {
        super("inventory", "Allows you to see parts of another player's inventory.", "inv", "invsee");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("player", PlayerArgumentType.player()).executes(context -> {
            TideHack.screenToOpen = new InventoryScreen(PlayerArgumentType.getPlayer(context));
            return SINGLE_SUCCESS;
        }));

    }

}
