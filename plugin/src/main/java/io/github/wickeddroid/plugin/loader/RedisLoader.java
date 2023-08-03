package io.github.wickeddroid.plugin.loader;

import io.github.wickeddroid.api.loader.Loader;
import io.github.wickeddroid.plugin.util.PluginUtil;

public class RedisLoader implements Loader {

  @Override
  public void load() {
    /*try (final var jedis = new Jedis("redis://wicked:Poto123!@redis-17605.c10.us-east-1-3.ec2.cloud.redislabs.com:17605")) {
      jedis.publish(PluginUtil.VERIFY_CHANNEL, "hola pe");
    }*/
  }
}
