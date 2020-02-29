package me.comu.exeter.commands.economy;

import me.comu.exeter.core.Core;
import me.comu.exeter.interfaces.ICommand;
import me.comu.exeter.logging.Logger;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.*;
import java.util.stream.Collectors;

public class BaltopCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        StringBuilder stringBuffer = new StringBuilder();
        LinkedHashMap<String, Integer> collect = EconomyManager.getUsers().entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        int counter2 = 1;
        for (String x : collect.keySet()) {
            User user = event.getJDA().getUserById(x);
            if (counter2 != 11)
                try {
                    String name = user.getName() + "#" + user.getDiscriminator();
                    stringBuffer.append("**" + counter2 + "**. " + name + " - " + EconomyManager.getBalance(user.getId()) + " credits\n");
                    counter2++;
                } catch (NullPointerException ex) {
                    event.getChannel().sendMessage("The economy config contains an invalid user, please resolve this issue. (" + x + ")").queue();
                }

        }
        event.getChannel().sendMessage(EmbedUtils.embedMessage("**Top 10 Rich List:**\n" + stringBuffer.toString()).build()).queue();

    }

    @Override
    public String getHelp() {
        return "Shows the richest people on the bot\n" + "`" + Core.PREFIX + getInvoke() + "`\nAliases: `" + Arrays.deepToString(getAlias()) + "`";
    }

    @Override
    public String getInvoke() {
        return "richlist";
    }

    @Override
    public String[] getAlias() {
        return new String[]{"baltop", "ecoleaderboard", "ecoleaderboards", "ecolb"};
    }

    @Override
    public Category getCategory() {
        return Category.ECONOMY;
    }
}