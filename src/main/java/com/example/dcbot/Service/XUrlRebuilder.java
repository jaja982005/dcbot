package com.example.dcbot.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XUrlRebuilder {

    Logger dcrunlog = LoggerFactory.getLogger("BotRunLogger");

    public String vxTwitterBuilder(String userName, String target) {
        String result = "";
        if (target.contains("https://x.com")) {
            result = target.replaceFirst("x.com", "vxtwitter.com");
            dcrunlog.info(userName + " - " + target);
        } else if (target.contains("https://twitter.com")) {
            result = target.replaceFirst("twitter.com", "vxtwitter.com");
            dcrunlog.info(userName + " - " + target);
        }

        return result;
    }
}
