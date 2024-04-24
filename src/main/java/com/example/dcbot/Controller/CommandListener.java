package com.example.dcbot.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.example.dcbot.DTO.CommandResult;
import com.example.dcbot.Service.Command.CommandService;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public abstract class CommandListener {
    @Autowired
    protected MessageSource messageSource;
    private final CommandService service;
    Logger botrunlog = LoggerFactory.getLogger("BotRunLogger");

    public CommandListener(CommandService service) {
        this.service = service;
    }

    public Mono<Void> processCommand(Message eventMessage) {
        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(message -> message.getAuthorAsMember())
                .flatMap(member -> {
                    String content = eventMessage.getContent();

                    // check the bot is enbale in this chat
                    String channelId = eventMessage.getChannelId().asString();
                    if (!content.startsWith("!enableBot") && !content.startsWith("!help")) {
                        if (!service.isEnbale(channelId)) {
                            return Mono.empty();
                        }
                    }

                    if (content.startsWith("!")) {

                        Snowflake msgId = eventMessage.getId();
                        String userName = member.getDisplayName();

                        CommandResult responseString = service.dcBotCommand(channelId, userName, content);
                        if (responseString.getResult()) {
                            String greet = responseString.getcommand().getContent();

                            // log the message if it success
                            String logMsg = channelId + " - " + userName + " : " + content;
                            botrunlog.info(logMsg);

                            return eventMessage.getChannel()
                                    .flatMap(channel -> channel.createMessage(greet).withMessageReference(msgId));
                        } else {
                            return Mono.empty();
                        }
                    } else {
                        return Mono.empty();
                    }
                }).then();
    }

}
