package com.malyi.discordcivobot;

import discord4j.core.DiscordClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DiscordCivobotApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(DiscordCivobotApplication.class, args);
        var client = context.getBean(DiscordClient.class);
        var handler = context.getBean(CommandHandler.class);
        handler.registerCommands(client);
    }

}
