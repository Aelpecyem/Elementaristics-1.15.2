package de.aelpecyem.elementaristics.common.misc.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import de.aelpecyem.elementaristics.common.capability.magan.IMaganCapability;
import de.aelpecyem.elementaristics.common.capability.magan.MaganCapability;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class CommandSetMagan  {

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("setMagan")
                        .requires(cs -> cs.hasPermissionLevel(2))
                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                            .then(Commands.argument("player", EntityArgument.players())
                                .executes(context -> run(context, EntityArgument.getPlayer(context, "player"), IntegerArgumentType.getInteger(context, "amount"))))
                        .executes(ctx -> run(ctx, ctx.getSource().asPlayer(), IntegerArgumentType.getInteger(ctx, "amount")))
                    );
    }

    private static int run(CommandContext<CommandSource> context, PlayerEntity player, int amount) {
        IMaganCapability cap = MaganCapability.Util.getCapability(player);
        cap.setMagan(amount);
        context.getSource().sendFeedback(new TranslationTextComponent("command.elem.set_magan.success", player.getDisplayName(), Math.min(amount, cap.getMaxMagan())), false);
        return 1;
    }
}
