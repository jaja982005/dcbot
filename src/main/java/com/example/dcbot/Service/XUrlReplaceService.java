package com.example.dcbot.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class XUrlReplaceService {
    Logger botrunlog = LoggerFactory.getLogger("BotRunLogger");

    public String vxTwitterBuilder(String userName, String target) {
        String result = "";
        if (target.contains("https://x.com")) {
            result = target.replaceFirst("x.com", "vxtwitter.com");
        } else if (target.contains("https://twitter.com")) {
            result = target.replaceFirst("twitter.com", "vxtwitter.com");
        }
        if (!result.equals("")) {
            StringBuilder logMsgStb = new StringBuilder();
            logMsgStb.append(userName);
            logMsgStb.append(" : ");
            logMsgStb.append(target);
            botrunlog.info(logMsgStb.toString());
        }

        return result;
    }
}
