package de.aelpecyem.elementaristics.reg;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import de.aelpecyem.elementaristics.common.misc.commands.CommandSetMagan;
import de.aelpecyem.elementaristics.lib.Constants;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class ModCommands {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralCommandNode<CommandSource> cmdTut = dispatcher.register(
                Commands.literal(Constants.MOD_ID)
                        .then(CommandSetMagan.register(dispatcher))
        );
    }
}
