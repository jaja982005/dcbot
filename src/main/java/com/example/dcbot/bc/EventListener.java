package com.example.dcbot.bc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import discord4j.core.event.domain.Event;
import reactor.core.publisher.Mono;

public interface EventListener<T extends Event> {

    Logger dcrunlog = LoggerFactory.getLogger("BotRunLogger");

    Class<T> getEventType();

    Mono<Void> execute(T event);

    default Mono<Void> handleError(Throwable error) {
        String errorMsg = "Unable to process " + getEventType().getSimpleName();
        dcrunlog.error(errorMsg, error);

        return Mono.empty();

    }

}