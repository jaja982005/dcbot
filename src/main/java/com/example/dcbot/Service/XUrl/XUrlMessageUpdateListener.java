package com.example.dcbot.Service.XUrl;

import org.springframework.stereotype.Service;

import com.example.dcbot.Controller.XUrlListener;
import com.example.dcbot.Service.Command.CommandService;
import com.example.dcbot.bc.EventListener;

import discord4j.core.event.domain.message.MessageUpdateEvent;
import reactor.core.publisher.Mono;

@Service

public class XUrlMessageUpdateListener extends XUrlListener implements EventListener<MessageUpdateEvent> {

    public XUrlMessageUpdateListener(XUrlReplaceService service, CommandService commandService) {
        super(service, commandService);
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