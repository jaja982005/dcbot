package com.example.dcbot.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.example.dcbot.DTO.CommandResult;
import com.example.dcbot.Service.Command.CommandService;
import com.example.dcbot.bc.Const;

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
                    if (content.startsWith("!")) {

                        Snowflake msgId = eventMessage.getId();

                        CommandResult responseString = service.dcBotCommand(content);
                        if (responseString.getResult()
                                && responseString.getcommand().getActive().equals(Const.RESPONSE_COMMAND)) {
                            String greet = responseString.getcommand().getContent();
                            // log the message if it success
                            StringBuilder logMsgStb = new StringBuilder();
                            String userName = member.getDisplayName();
                            logMsgStb.append(userName);
                            logMsgStb.append(" : ");
                            logMsgStb.append(content);
                            botrunlog.info(logMsgStb.toString());

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
