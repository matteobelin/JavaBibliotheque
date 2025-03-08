package com.esgi.presentation.cli;

import com.esgi.core.exceptions.OptionRequiresValueException;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.CommandAccessLevel;
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
    private final CommandAccessLevel accessLevel;

    @Getter
    protected final List<CliCommandNode> childrenCommands = new ArrayList<>();

    @Getter
    protected final List<CliCommandNodeOption> commandOptions = new ArrayList<>();

    protected CliCommandNode(String name, String description, CommandAccessLevel accessLevel) {
        this.name = name;
        this.description = description;
        this.accessLevel = accessLevel;
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
