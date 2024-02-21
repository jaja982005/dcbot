package com.example.dcbot.bc;

import com.example.dcbot.Service.XUrlRebuilder;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public abstract class MessageListener {
    public Mono<Void> processCommand(Message eventMessage) {

        // return Mono.just(eventMessage)

        // .filter(message -> message.getAuthor().map(user ->
        // !user.isBot()).orElse(false))

        // .filter(message -> message.getContent().equalsIgnoreCase("!todo"))

        // .flatMap(Message::getChannel)

        // .flatMap(channel -> channel
        // .createMessage("Things to do today:\n - write a bot\n - eat lunch\n - play
        // agame"))

        // .then();
        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(message -> {
                    String content = message.getContent();
                    String httpsPattern = "^https://.*";
                    if (content.matches(httpsPattern)) {
                        XUrlRebuilder xUrlRebuilder = new XUrlRebuilder();
                        String reply = xUrlRebuilder.vxTwitterBuilder(content);
                        if (reply.equals("false")) {
                            return Mono.empty();
                        } else {
                            return message.getChannel().flatMap(channel -> channel.createMessage(reply));
                        }
                    }
                    return Mono.empty();
                })
                .then();

    }

}