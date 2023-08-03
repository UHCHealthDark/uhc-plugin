package io.github.wickeddroid.api.cache;

import java.util.Collection;

public interface Cache<K, V> {
  void save(K key, V value);

  void invalidate(K key);

  V get(K key);

  Collection<V> values();
}
