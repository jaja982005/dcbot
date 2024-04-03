package com.example.dcbot.DTO;

public class CommandResult {
    // command is exit
    private Boolean result;

    // command
    private CommandAttribute command;

    // Getter for result
    public Boolean getResult() {
        return result;
    }

    // Setter for result
    public void setResult(Boolean result) {
        this.result = result;
    }

    // Getter for command
    public CommandAttribute getcommand() {
        return command;
    }

    // Setter for command
    public void setcommand(CommandAttribute command) {
        this.command = command;
    }
}
