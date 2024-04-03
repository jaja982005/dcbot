package com.example.dcbot.Controller;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public abstract class CommentListener {
    public Mono<Void> processCommand(Message eventMessage) {
        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(message -> message.getAuthorAsMember())
                .flatMap(member -> {
                    String content = eventMessage.getContent();
                    String responseString = "a";
                    return eventMessage.getChannel()
                            .flatMap(channel -> channel.createMessage(responseString));
                }).then();
    }

}
