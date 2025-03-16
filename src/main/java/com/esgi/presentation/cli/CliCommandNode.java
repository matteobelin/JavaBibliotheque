package com.esgi.presentation.cli;

import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.OptionRequiresValueException;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.utils.ArgsParserUtils;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
public abstract class CliCommandNode {
    private final String name;
    private final String description;

    @Getter
    protected final List<CliCommandNode> childrenCommands = new ArrayList<>();

    @Getter
    protected final List<CliCommandNodeOption> commandOptions = new ArrayList<>();

    protected CliCommandNode(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ExitCode run(String[] args) throws InternalErrorException {
        if (args.length == 0) {
            AppLogger.error("The command '%s' requires an argument".formatted(this.getName()));
            return ExitCode.ARGUMENT_MISSING;
        }

        String childCommandName = args[0];
        String[] childCommandArgs = Arrays.copyOfRange(args, 1, args.length);

        Optional<CliCommandNode> childCommand = this.findFirstChildCommandByName(childCommandName);

        if (childCommand.isPresent()) {
            return childCommand.get().run(childCommandArgs);
        }

        return this.childCommandNotFound(childCommandName);
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

    protected List<String> extractValuesFromArgs(String[] args) {
        return ArgsParserUtils.extractValuesFromArgs(args, this.getCommandOptions());
    }

    protected List<CliCommandNodeOption> extractOptionsFromArgs(String[] args) throws OptionRequiresValueException {
        List<CliCommandNodeOption> options = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            if (ArgsParserUtils.isNotAnOption(arg)) {
                continue;
            }

            var option = ArgsParserUtils.findOptionByName(this.getCommandOptions(), arg);

            if (option.isEmpty()) {
                String warnMessage = String.format("The option `%s` does not exist for this command.", arg);
                AppLogger.warn(warnMessage);
                continue;
            }

            if (option.get().requiresValue()) {
                if (i + 1 >= args.length || ArgsParserUtils.isAnOption(args[i + 1])) {
                    throw new OptionRequiresValueException(arg);
                }

                option.get().setValue(args[i + 1]);
                i++;
            }

            options.add(option.get());
        }

        return options;
    }
}
