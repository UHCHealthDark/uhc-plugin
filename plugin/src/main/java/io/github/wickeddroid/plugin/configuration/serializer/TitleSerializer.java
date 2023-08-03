package io.github.wickeddroid.plugin.configuration.serializer;

import io.github.wickeddroid.plugin.util.MessageUtil;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.title.Title;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;

public enum TitleSerializer implements TypeSerializer<Title> {
  INSTANCE;

  private ConfigurationNode nonVirtualNode(final ConfigurationNode source, final Object... path) throws SerializationException {
    if (!source.hasChild(path)) {
      throw new SerializationException("Required field " + Arrays.toString(path) + " was not present in node");
    }

    return source.node(path);
  }
  @Override
  public Title deserialize(Type type, ConfigurationNode node) throws SerializationException {
    final var title = nonVirtualNode(node, "title");

    if (title.empty()) {
      throw new IllegalArgumentException("title cannot be empty");
    }

    final var subtitle = nonVirtualNode(node, "subtitle");

    if (subtitle.empty()) {
      throw new IllegalArgumentException("subtitle cannot be empty");
    }

    final var fadeInNode = nonVirtualNode(node, "fadeIn");

    var fadeIn = fadeInNode.getInt();

    if (fadeInNode.empty() || fadeIn == 0) {
      fadeIn = 1;
    }

    final var stayNode = nonVirtualNode(node, "stay");
    var stay = fadeInNode.getInt();

    if (stayNode.empty() || stay == 0) {
      stay = 3;
    }

    final var fadeOutNode = nonVirtualNode(node, "fadeIn");
    var fadeOut = fadeInNode.getInt();

    if (fadeOutNode.empty() || fadeOut == 0) {
      fadeOut = 1;
    }

    return Title.title(
            MessageUtil.parseStringToComponent(title.getString()),
            MessageUtil.parseStringToComponent(subtitle.getString()),
            Title.Times.times(
                    Duration.ofSeconds(fadeIn),
                    Duration.ofSeconds(stay),
                    Duration.ofSeconds(fadeOut)
            )
    );
  }

  @Override
  public void serialize(Type type, @Nullable Title title, ConfigurationNode node) throws SerializationException {
    if (title == null) {
      node.raw(null);
      return;
    }

    node.node("title").set(PlainTextComponentSerializer
            .plainText().serialize(
                    title.title()
            ));
    node.node("subtitle").set(PlainTextComponentSerializer
            .plainText().serialize(
                    title.subtitle()
            ));

    node.node("fadeIn").set(Objects.requireNonNull(title.times())
            .fadeIn().get(ChronoUnit.SECONDS));
    node.node("stay").set(Objects.requireNonNull(title.times())
            .stay().get(ChronoUnit.SECONDS));
    node.node("fadeOut").set(Objects.requireNonNull(title.times())
            .fadeOut().get(ChronoUnit.SECONDS));
  }
}
