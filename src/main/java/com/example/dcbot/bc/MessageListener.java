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
                .flatMap(message -> {
                    String content = message.getContent();
                    String userName = message.getAuthor().get().getUsername();
                    String httpsPattern = "^https://.*";
                    if (content.matches(httpsPattern)) {
                        XUrlReplaceService service = new XUrlReplaceService();
                        String replacedUrl = service.vxTwitterBuilder(userName, content);
                        if (replacedUrl.equals("")) {
                            return Mono.empty();
                        } else {
                            Snowflake msgId = message.getId();
                            String greet = messageSource.getMessage(Const.X_URL_REPLACE_SUCCESS,
                                    new String[] { userName }, null);
                            System.out.println(greet);
                            StringBuilder replyStb = new StringBuilder();
                            replyStb.append(greet);
                            replyStb.append("\n");
                            replyStb.append(replacedUrl);
                            return message.getChannel()
                                    .flatMap(channel -> channel.createMessage(replyStb.toString())
                                            .withMessageReference(msgId));
                        }
                    }
                    return Mono.empty();
                })
                .then();

    }

}