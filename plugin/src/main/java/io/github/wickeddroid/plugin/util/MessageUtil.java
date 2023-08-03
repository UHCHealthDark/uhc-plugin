package io.github.wickeddroid.plugin.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public final class MessageUtil {

  public static final MiniMessage MINI_MESSAGE = MiniMessage
          .builder()
          .build();

  private MessageUtil() {}

  public static Component parseStringToComponent(final String text) {
    return MINI_MESSAGE.deserialize(text);
  }

  public static Component parseStringToComponent(
          final String text,
          final TagResolver... tagResolvers
  ) {
    return MINI_MESSAGE.deserialize(text, tagResolvers);
  }
}
