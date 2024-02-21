package com.example.dcbot.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.dcbot.bc.EventListener;

public class XUrlRebuilder {
    Logger dcEventListenerLogger = LoggerFactory.getLogger(EventListener.class);

    public String vxTwitterBuilder(String target) {
        String result = "";
        if (target.contains("x.com")) {
            result = target.replaceFirst("x.com", "vxtwitter.com");
        } else if (target.contains("twitter.com")) {
            result = target.replaceFirst("twitter.com", "vxtwitter.com");
        } else {
            return "false";
        }

        return result;
    }
}
