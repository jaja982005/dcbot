package com.example.dcbot.Service.Command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.example.dcbot.DTO.CommandAttribute;
import com.example.dcbot.DTO.CommandResult;
import com.example.dcbot.Repository.RedisHashRepository;
import com.example.dcbot.Service.ActiveCommandsService;
import com.example.dcbot.bc.CommandHandler;
import com.example.dcbot.bc.Const;

@Service
public class CommandService {
    @Autowired
    protected MessageSource messageSource;
    private final ActiveCommandsService service;
    private final RedisHashRepository redisHashRepository;

    public CommandService(ActiveCommandsService service, RedisHashRepository redisHashRepository) {
        this.service = service;
        this.redisHashRepository = redisHashRepository;
    }

    /* check the bot is enbale in the chat */
    public Boolean isEnbale(String channelId) {
        Boolean isEnbale = false;
        String key = "channel_Id:" + channelId;
        if (redisHashRepository.hashExists(key)) {
            if (redisHashRepository.getValue(key, "states").toString().equals(Const.ENABLE)) {
                isEnbale = true;
            }
        }
        return isEnbale;
    }

    /* find the comman and run it */
    public CommandResult dcBotCommand(String channelId, String userName, String targetContent) {
        String[] targetContentSplitedArray = targetContent.split(" ");
        String targetCommand = targetContentSplitedArray[0];

        CommandResult result = new CommandResult();
        String commandId = CommandHandler.getResponse(targetCommand);

        // command is not exit
        if (commandId.equals(Const.CAN_NOT_FOUND_KEY_FLAG)) {
            result.setResult(false);
            return result;
        }

        // command is exit
        result.setResult(true);
        String[] commandIdArray = commandId.split("\\.");
        if (commandIdArray[0].equals(Const.RESPONSE_COMMAND) || commandIdArray[0].equals(Const.ACTIVE_COMMAND)) {
            if (commandIdArray[0].equals(Const.ACTIVE_COMMAND)) {
                service.commands(commandId, channelId, userName);
            }
            // set the command attribute
            CommandAttribute commandAttribute = new CommandAttribute();
            commandAttribute.setActive(commandIdArray[0]);
            @SuppressWarnings("null")
            String content = messageSource.getMessage(commandId, new String[] {}, null);
            commandAttribute.setContent(content);
            result.setcommand(commandAttribute);
        } else {
            result.setResult(false);
        }

        return result;
    }
}
