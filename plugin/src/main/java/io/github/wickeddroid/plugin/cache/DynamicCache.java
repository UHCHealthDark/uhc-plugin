package io.github.wickeddroid.plugin.cache;

import io.github.wickeddroid.api.cache.Cache;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DynamicCache<K, V> implements Cache<K, V> {

  private final Map<K, V> map = new ConcurrentHashMap<>();
  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
  private final int delay;
  private final TimeUnit timeUnit;

  public DynamicCache(
          final int delay,
          final @NotNull TimeUnit timeUnit
  ) {
    this.timeUnit = timeUnit;
    this.delay = delay;
  }

  @Override
  public void save(
          final @NotNull K key,
          final @NotNull V value
  ) {
    this.map.put(key, value);

    this.scheduler.schedule(() -> {
      if (this.get(key) == null) {
        return;
      }

      this.map.remove(key);
    }, this.delay, this.timeUnit);
  }

  @Override
  public void invalidate(K key) {
    this.map.remove(key);
  }

  @Override
  public V get(K key) {
    return this.map.get(key);
  }

  @Override
  public Collection<V> values() {
    return this.map.values();
  }
}
