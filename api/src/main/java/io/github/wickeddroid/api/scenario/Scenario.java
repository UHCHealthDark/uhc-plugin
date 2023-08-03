package io.github.wickeddroid.api.scenario;

import org.bukkit.Material;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Scenario {

  String name();
  String key();
  String[] description();
  Material material();
  boolean experimental() default false;
}
