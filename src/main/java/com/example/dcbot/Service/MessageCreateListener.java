package com.example.dcbot.Service;

import org.springframework.stereotype.Service;

import com.example.dcbot.Controller.XUrlListener;
import com.example.dcbot.bc.EventListener;

import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

/**
 * service for chanel has new message
 */
@Service
public class MessageCreateListener extends XUrlListener implements EventListener<MessageCreateEvent> {

    public MessageCreateListener(XUrlReplaceService service) {
        super(service);

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