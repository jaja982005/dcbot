package com.example.dcbot.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.dcbot.Repository.RedisHashRepository;
import com.example.dcbot.bc.Const;

@Service
public class ActiveCommandsService {
    private final RedisHashRepository redisHashRepository;

    public ActiveCommandsService(RedisHashRepository redisHashRepository) {
        this.redisHashRepository = redisHashRepository;
    }

    public void commands(String commandId, String channelId, String userName) {
        if (commandId.equals(Const.ENABLE_BOT_IN_CHAT)) {
            enableBot(channelId, userName);
        } else if (commandId.equals(Const.DISABLE_BOT_IN_CHAT)) {
            disableBot(channelId, userName);
        }
    }

    public void enableBot(String channelId, String userName) {
        String key = "channel_Id:" + channelId;
        Map<String, Object> map = new HashMap<>();
        map.put("update_user", userName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime now = LocalDateTime.now();
        String updateTime = now.format(formatter);
        map.put("update_time", updateTime);
        map.put("states", Const.ENABLE);
        redisHashRepository.hset(key, map);
    }

    public void disableBot(String channelId, String userName) {
        String key = "channel_Id:" + channelId;
        Map<String, Object> map = new HashMap<>();
        map.put("update_user", userName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime now = LocalDateTime.now();
        String updateTime = now.format(formatter);
        map.put("update_time", updateTime);
        map.put("states", Const.DISABLE);
        redisHashRepository.hset(key, map);
    }
}
