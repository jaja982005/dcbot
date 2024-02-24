package com.example.dcbot.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class XUrlReplaceService {
    Logger botrunlog = LoggerFactory.getLogger("BotRunLogger");
    @Autowired
    protected MessageSource messageSource;

    public String vxTwitterBuilder(String userName, String target) {
        String result = "";
        if (target.contains("https://x.com")) {
            result = target.replaceFirst("x.com", "vxtwitter.com");
        } else if (target.contains("https://twitter.com")) {
            result = target.replaceFirst("twitter.com", "vxtwitter.com");
        }
        return result;
    }
}
