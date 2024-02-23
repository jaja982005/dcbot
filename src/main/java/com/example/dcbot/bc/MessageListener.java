package com.example.dcbot.bc;

import com.example.dcbot.Service.XUrlRebuilder;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public abstract class MessageListener {
    public Mono<Void> processCommand(Message eventMessage) {
        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(message -> {
                    String content = message.getContent();
                    String userName = message.getAuthor().get().getUsername();
                    String httpsPattern = "^https://.*";
                    if (content.matches(httpsPattern)) {
                        XUrlRebuilder xUrlRebuilder = new XUrlRebuilder();
                        String reply = xUrlRebuilder.vxTwitterBuilder(userName, content);
                        if (reply.equals("")) {
                            return Mono.empty();
                        } else {
                            Snowflake msgId = message.getId();
                            return message.getChannel()
                                    .flatMap(channel -> channel.createMessage(reply).withMessageReference(msgId));
                        }
                    }
                    return Mono.empty();
                })
                .then();

    }

}