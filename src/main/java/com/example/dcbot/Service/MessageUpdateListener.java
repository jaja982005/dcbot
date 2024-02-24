package com.example.dcbot.Service;

import org.springframework.stereotype.Service;

import com.example.dcbot.bc.EventListener;
import com.example.dcbot.bc.MessageListener;

import discord4j.core.event.domain.message.MessageUpdateEvent;
import reactor.core.publisher.Mono;

@Service

public class MessageUpdateListener extends MessageListener implements EventListener<MessageUpdateEvent> {

    public MessageUpdateListener(XUrlReplaceService service) {
        super(service);
    }

    @Override

    public Class<MessageUpdateEvent> getEventType() {

        return MessageUpdateEvent.class;

    }

    @Override

    public Mono<Void> execute(MessageUpdateEvent event) {

        return Mono.just(event)

                .filter(MessageUpdateEvent::isContentChanged)

                .flatMap(MessageUpdateEvent::getMessage)

                .flatMap(super::processCommand);

    }

}