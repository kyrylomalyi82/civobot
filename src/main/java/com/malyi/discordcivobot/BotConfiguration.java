package com.malyi.discordcivobot;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfiguration {

    @Value("${token}")
    private String token;

    @Bean
    public DiscordClient discordClient() {
         return DiscordClientBuilder.create(token).build();
        }

    }
