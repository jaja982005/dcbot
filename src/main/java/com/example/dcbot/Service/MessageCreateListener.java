package com.example.dcbot.Service;

import org.springframework.stereotype.Service;

import com.example.dcbot.bc.EventListener;
import com.example.dcbot.bc.MessageListener;

import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

/**
 * service for chanel has new message
 */
@Service
public class MessageCreateListener extends MessageListener implements EventListener<MessageCreateEvent> {

    public MessageCreateListener(XUrlReplaceService service) {
        super(service);
        // TODO Auto-generated constructor stub
    }

    @Override

    public Class<MessageCreateEvent> getEventType() {

        return MessageCreateEvent.class;

    }

    @Override

    public Mono<Void> execute(MessageCreateEvent event) {

        return processCommand(event.getMessage());

    }

}