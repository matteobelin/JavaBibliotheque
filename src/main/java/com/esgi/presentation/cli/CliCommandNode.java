package com.esgi.presentation.cli;

import com.esgi.presentation.AppLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class CliCommandNode {

    public abstract String getName();

    public abstract String getDescription();

    public abstract List<CliCommandNode> getChildrenCommands();

    public List<CliCommandNodeOption> getCommandOptions() {
        return List.of();
    }

    public ExitCode run(String[] args) {
        String childCommandName = args[0];
        String[] childCommandArgs = Arrays.copyOfRange(args, 1, args.length);

        Optional<CliCommandNode> childCommand = this.findFirstChildCommandByName(childCommandName);

        return childCommand
                .map(command -> command.run(childCommandArgs))
                .orElseGet(() -> this.childCommandNotFound(childCommandName));
    }

    protected Optional<CliCommandNode> findFirstChildCommandByName(String childCommandName) {
        return this.getChildrenCommands().stream()
                .filter(command -> command.getName().equals(childCommandName))
                .findFirst();
    }

    protected ExitCode childCommandNotFound(String childCommandName) {
        String message = String.format("Command '%s' not found", childCommandName);
        AppLogger.error(message);
        return ExitCode.COMMAND_NOT_FOUND;
    }

    protected List<String> getValuesFromArgs(String[] args) {
        List<String> values = new ArrayList<>();

        for (String arg : args) {
            boolean isAnOption = arg.startsWith("-");
            if (isAnOption) {
                continue;
            }

            values.add(arg);
        }

        return values;
    }

    protected List<CliCommandNodeOption> getOptionsFromArgs(String[] args) {
        List<CliCommandNodeOption> options = new ArrayList<>();

        for (String arg : args) {
            boolean isNotAnOption = !arg.startsWith("-");
            if (isNotAnOption) {
                continue;
            }

            String optionName = arg.replaceFirst("-", "");
            var option = this.findOptionByName(optionName);
            if (option.isPresent()) {
                options.add(option.get());
            } else {
                String warnMessage = String.format("The option `%s` does not exist for this command.", optionName);
                AppLogger.warn(warnMessage);
            }
        }

        return options;
    }

    protected Optional<CliCommandNodeOption> findOptionByName(String optionName) {
        return this.getCommandOptions().stream()
                .filter(option ->
                        option.getName().equals(optionName) || option.getShortName().equals(optionName)
                ).findFirst();
    }
}
