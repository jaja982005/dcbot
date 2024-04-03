package com.example.dcbot.Service.Command;

import org.springframework.stereotype.Service;

import com.example.dcbot.Controller.CommandListener;
import com.example.dcbot.bc.EventListener;

import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

/**
 * service for chanel has new message
 */
@Service
public class CommandMessageCreateListener extends CommandListener implements EventListener<MessageCreateEvent> {

    public CommandMessageCreateListener(CommandService service) {
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
