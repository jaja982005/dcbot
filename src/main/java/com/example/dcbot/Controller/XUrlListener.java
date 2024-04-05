package com.example.dcbot.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.example.dcbot.Service.Command.CommandService;
import com.example.dcbot.Service.XUrl.XUrlReplaceService;
import com.example.dcbot.bc.Const;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public abstract class XUrlListener {
    @Autowired
    protected MessageSource messageSource;

    private final XUrlReplaceService service;
    private final CommandService commandService;

    Logger botrunlog = LoggerFactory.getLogger("BotRunLogger");

    public XUrlListener(XUrlReplaceService service, CommandService commandService) {
        this.service = service;
        this.commandService = commandService;

    }

    public Mono<Void> processCommand(Message eventMessage) {
        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                // get the member information
                .flatMap(message -> message.getAuthorAsMember())
                .flatMap(member -> {

                    String content = eventMessage.getContent();
                    // check the bot is enbale in this chat
                    String channelId = eventMessage.getChannelId().asString();
                    if (!commandService.isEnbale(channelId)) {
                        return Mono.empty();
                    }
                    // get the user display name
                    String userName = member.getDisplayName();
                    String httpsPattern = "^https://.*";
                    if (!content.contains("/status/")) {
                        return Mono.empty();
                    }
                    if (content.matches(httpsPattern)) {
                        // XUrlReplaceService service = new XUrlReplaceService();
                        String replacedUrl = service.vxTwitterBuilder(userName, content);
                        if (replacedUrl.isEmpty()) {
                            return Mono.empty();
                        } else {
                            Snowflake msgId = eventMessage.getId();
                            // create the reply content
                            @SuppressWarnings("null")
                            String greet = messageSource.getMessage(Const.X_URL_REPLACE_SUCCESS,
                                    new String[] { userName }, null);
                            StringBuilder replyStb = new StringBuilder();
                            replyStb.append(greet);
                            replyStb.append("\n");
                            replyStb.append(replacedUrl);

                            // log the message if it success
                            StringBuilder logMsgStb = new StringBuilder();
                            logMsgStb.append(channelId);
                            logMsgStb.append(" - ");
                            logMsgStb.append(userName);
                            logMsgStb.append(" : ");
                            logMsgStb.append(content);
                            botrunlog.info(logMsgStb.toString());

                            // reply the replaced url
                            return eventMessage.getChannel()
                                    .flatMap(channel -> channel.createMessage(replyStb.toString())
                                            .withMessageReference(msgId));
                        }
                    }
                    return Mono.empty();
                })
                .then();
    }

}