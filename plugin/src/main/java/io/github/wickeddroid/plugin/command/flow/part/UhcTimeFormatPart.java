package io.github.wickeddroid.plugin.command.flow.part;

import io.github.wickeddroid.api.game.UhcTimeFormat;
import me.fixeddev.commandflow.CommandContext;
import me.fixeddev.commandflow.exception.ArgumentParseException;
import me.fixeddev.commandflow.part.ArgumentPart;
import me.fixeddev.commandflow.part.CommandPart;
import me.fixeddev.commandflow.stack.ArgumentStack;

import java.util.*;

public class UhcTimeFormatPart implements ArgumentPart {

  @Override
  public List<?> parseValue(CommandContext context, ArgumentStack stack, CommandPart caller) throws ArgumentParseException {
    return Collections.singletonList(UhcTimeFormat.valueOf(stack.next()));
  }

  @Override
  public List<String> getSuggestions(CommandContext commandContext, ArgumentStack stack) {
    final var prefix = stack.hasNext() ? stack.next() : null;

    if (prefix == null) {
      return Collections.emptyList();
    }

    final var possibleTimeFormat = prefix.toUpperCase();
    final var suggestions = new ArrayList<String>();

    for (final var time : UhcTimeFormat.values()) {
      final var name = time.toString();

      if (possibleTimeFormat.isEmpty() || name.startsWith(possibleTimeFormat)) {
        suggestions.add(name);
      }
    }

    return suggestions;
  }

  @Override
  public String getName() {
    return null;
  }
}
