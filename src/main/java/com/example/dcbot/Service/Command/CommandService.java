package com.example.dcbot.Service.Command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.example.dcbot.DTO.CommandAttribute;
import com.example.dcbot.DTO.CommandResult;
import com.example.dcbot.bc.CommandHandler;
import com.example.dcbot.bc.Const;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class CommandService {
    @Autowired
    protected MessageSource messageSource;

    public CommandResult dcBotCommand(String target) {
        String[] targetArray = target.split(" ");
        String targetCommand = targetArray[0];

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
        if (commandIdArray[0].equals(Const.RESPONSE_COMMAND)) {
            result.setResult(true);
            // set the command attribute
            CommandAttribute commandAttribute = new CommandAttribute();
            commandAttribute.setActive(commandIdArray[0]);
            @SuppressWarnings("null")
            String content = messageSource.getMessage(commandId, new String[] {}, null);
            commandAttribute.setContent(content);
            result.setcommand(commandAttribute);
        }

        return result;
    }
}
