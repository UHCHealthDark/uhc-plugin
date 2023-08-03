package io.github.wickeddroid.plugin.command.flow.factory;

import io.github.wickeddroid.plugin.command.flow.part.UhcTimeFormatPart;
import me.fixeddev.commandflow.annotated.part.PartFactory;
import me.fixeddev.commandflow.part.CommandPart;

import java.lang.annotation.Annotation;
import java.util.List;

public class UhcTimeFormatFactory implements PartFactory {
  @Override
  public CommandPart createPart(String name, List<? extends Annotation> modifiers) {
    return new UhcTimeFormatPart();
  }
}
