package com.example.dcbot.bc;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private static final Map<String, String> commandHandler = new HashMap<>();
    static {
        commandHandler.put("!isStart", Const.THE_BOT_IS_RUNNING);
    }

    public static String getResponse(String commandKey) {
        return commandHandler.getOrDefault(commandKey, Const.CAN_NOT_FOUND_KEY_FLAG);
    }
}
