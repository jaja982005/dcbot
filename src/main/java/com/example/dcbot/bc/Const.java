package com.example.dcbot.bc;

public class Const {
    private Const() {
    }

    /* command */
    /* replace twitter or x to vxtwitter msgId */
    public static final String X_URL_REPLACE_SUCCESS = "active.info.0001";
    /* enable the bot in this caht */
    public static final String ENABLE_BOT_IN_CHAT = "active.info.0002";
    /* disable the bot in this chat */
    public static final String DISABLE_BOT_IN_CHAT = "active.info.0003";

    /* the bot help */
    public static final String HELP = "response.content.0000";
    /* the bot is runnning msgId */
    public static final String THE_BOT_IS_RUNNING = "response.content.0001";

    /* enable */
    public static final String ENABLE = "enable";
    /* disable */
    public static final String DISABLE = "disable";

    /* flag when the command can not find */
    public static final String CAN_NOT_FOUND_KEY_FLAG = "1";

    /* the command is active */
    public static final String ACTIVE_COMMAND = "active";

    /* the command is response */
    public static final String RESPONSE_COMMAND = "response";
}
