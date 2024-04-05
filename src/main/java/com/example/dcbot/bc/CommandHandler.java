package com.example.dcbot.bc;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private static final Map<String, String> commandHandler = new HashMap<>();
    static {
        commandHandler.put("!isStart", Const.THE_BOT_IS_RUNNING);
        commandHandler.put("!help", Const.HELP);
        commandHandler.put("!enableBot", Const.ENABLE_BOT_IN_CHAT);
        commandHandler.put("!disableBot", Const.DISABLE_BOT_IN_CHAT);
    }

    public static String getResponse(String commandKey) {
        return commandHandler.getOrDefault(commandKey, Const.CAN_NOT_FOUND_KEY_FLAG);
    }
}
