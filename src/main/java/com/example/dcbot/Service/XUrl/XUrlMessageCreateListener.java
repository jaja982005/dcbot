package com.example.dcbot.Service.XUrl;

import org.springframework.stereotype.Service;

import com.example.dcbot.Controller.XUrlListener;
import com.example.dcbot.Service.Command.CommandService;
import com.example.dcbot.bc.EventListener;

import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

/**
 * service for chanel has new message
 */
@Service
public class XUrlMessageCreateListener extends XUrlListener implements EventListener<MessageCreateEvent> {

    public XUrlMessageCreateListener(XUrlReplaceService service, CommandService commandService) {
        super(service, commandService);
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