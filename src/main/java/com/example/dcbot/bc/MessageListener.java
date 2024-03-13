package com.example.dcbot.bc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.example.dcbot.Service.XUrlReplaceService;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public abstract class MessageListener {
    @Autowired
    protected MessageSource messageSource;

    private final XUrlReplaceService service;
    Logger botrunlog = LoggerFactory.getLogger("BotRunLogger");

    public MessageListener(XUrlReplaceService service) {
        this.service = service;
    }

    public Mono<Void> processCommand(Message eventMessage) {
        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                // get the member information
                .flatMap(message -> message.getAuthorAsMember())
                .flatMap(member -> {
                    String content = eventMessage.getContent();
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
                            String greet = messageSource.getMessage(Const.X_URL_REPLACE_SUCCESS,
                                    new String[] { userName }, null);
                            StringBuilder replyStb = new StringBuilder();
                            replyStb.append(greet);
                            replyStb.append("\n");
                            replyStb.append(replacedUrl);

                            // log the message if it success
                            StringBuilder logMsgStb = new StringBuilder();
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