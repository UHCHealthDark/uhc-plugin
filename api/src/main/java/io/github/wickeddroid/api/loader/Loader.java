package io.github.wickeddroid.api.loader;

public interface Loader {

  void load();
  default void unload() {};
}
