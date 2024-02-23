package com.example.dcbot.bc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.example.dcbot.Service.XUrlReplaceService;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public abstract class MessageListener {
    @Autowired
    protected MessageSource messageSource;

    public Mono<Void> processCommand(Message eventMessage) {
        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(message -> message.getAuthorAsMember())
                .flatMap(member -> {
                    String content = eventMessage.getContent();
                    String userName = member.getDisplayName();
                    String httpsPattern = "^https://.*";

                    if (content.matches(httpsPattern)) {
                        XUrlReplaceService service = new XUrlReplaceService();
                        String replacedUrl = service.vxTwitterBuilder(userName, content);
                        if (replacedUrl.isEmpty()) {
                            return Mono.empty();
                        } else {
                            Snowflake msgId = eventMessage.getId();
                            String greet = messageSource.getMessage(Const.X_URL_REPLACE_SUCCESS,
                                    new String[] { userName }, null);
                            System.out.println(greet);
                            StringBuilder replyStb = new StringBuilder();
                            replyStb.append(greet);
                            replyStb.append("\n");
                            replyStb.append(replacedUrl);
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