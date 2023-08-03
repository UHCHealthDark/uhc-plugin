package io.github.wickeddroid.plugin.message;

import io.github.wickeddroid.plugin.util.MessageUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import team.unnamed.inject.Inject;

import java.util.regex.Pattern;

public class MessageHandler {

  private static final Pattern PATTERN_LABEL = Pattern.compile("<param-([^<>]+)>");
  @Inject private Messages messages;

  public void send(
          final Audience receiver,
          final String message
  ) {
    receiver.sendMessage(
            MessageUtil.parseStringToComponent(
                    "<prefix> <white>" + message,
                    Placeholder.parsed("prefix", messages.prefix())
            )
    );
  }

  public void send(
          final Audience receiver,
          final String message,
          final String... replacements
  ) {
    final var matcher = PATTERN_LABEL.matcher(message);

    final var tagResolver = TagResolver
            .builder();

    var replacement = 0;

    while (matcher.find()) {
      if (replacement >= replacements.length) {
        break;
      }

      tagResolver.resolver(
              Placeholder.parsed(
                      "param-" + matcher.group(1),
                      replacements[replacement]
              )
      );

      ++replacement;
    }

    receiver.sendMessage(
            MessageUtil.parseStringToComponent(
                    "<prefix> <white>" + message,
                    Placeholder.parsed("prefix", messages.prefix()),
                    tagResolver.build()
            )
    );
  }
}