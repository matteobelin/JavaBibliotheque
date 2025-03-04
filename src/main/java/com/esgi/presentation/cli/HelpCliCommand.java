package com.esgi.presentation.cli;

import com.esgi.presentation.AppLogger;

import java.util.List;
import java.util.Optional;

public class HelpCliCommand extends CliCommandNode {
    public static final String NAME = "help";

    private final List<CliCommandNode> availableCommands;

    private int currentIndentLevel = 0;

    public HelpCliCommand(List<CliCommandNode> availableCommands) {
        this.availableCommands = availableCommands;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return "Provide help for the available commands";
    }

    @Override
    public List<CliCommandNode> getChildrenCommands() {
        return List.of();
    }

    @Override
    public ExitCode run(String[] args) {
        if(List.of(args).isEmpty()) {
            this.availableCommands.forEach(this::writeHelpMessageForCliCommand);
            return ExitCode.OK;
        }

        String commandName = args[0];
        Optional<CliCommandNode> command = this.findFirstAvailableCommandByName(commandName);

        if(command.isPresent()) {
            this.writeHelpMessageForCliCommand(command.get());
            return ExitCode.OK;
        }

        this.childCommandNotFound(commandName);
        return ExitCode.ARGUMENT_INVALID;
    }

    private void writeHelpMessageForCliCommand(CliCommandNode command) {
        String helpMessage = this.getHelpMessageForCommand(command);
        this.writeWithIndent(helpMessage);

        boolean hasOptions = !command.getCommandOptions().isEmpty();
        if (hasOptions) {
            this.currentIndentLevel++;
            this.writeWithIndent("[OPTIONS]");
            command.getCommandOptions().forEach(this::writeHelpMessageForCommandOption);
            this.currentIndentLevel--;
        }

        boolean hasChildrenCommands = !command.getChildrenCommands().isEmpty();
        if (hasChildrenCommands) {
            this.currentIndentLevel++;
            this.writeWithIndent("[SUB COMMANDS]");
            command.getChildrenCommands().forEach(this::writeHelpMessageForCliCommand);
        }
    }

    private String getHelpMessageForCommand(CliCommandNode command) {
        return String.format("%s : %s", command.getName(), command.getDescription());
    }

    private void writeHelpMessageForCommandOption(CliCommandNodeOption commandOption) {
        String optionName = commandOption.getName();
        String optionShortName = commandOption.getShortName();
        String optionDescription = commandOption.getDescription();

        String optionHelpMessage = String.format("--%s or -%s : %s", optionName, optionShortName, optionDescription);
        this.writeWithIndent(optionHelpMessage);
    }

    private void writeWithIndent(String message) {
        String indentedMessage = "\t".repeat(Math.max(0, currentIndentLevel)) + message;
        AppLogger.info(indentedMessage);
    }

    protected Optional<CliCommandNode> findFirstAvailableCommandByName(String commandName) {
        return this.availableCommands.stream()
                .filter(command -> command.getName().equals(commandName))
                .findFirst();
    }
}
