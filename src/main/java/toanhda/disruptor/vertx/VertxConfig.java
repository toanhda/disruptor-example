package toanhda.disruptor.vertx;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VertxConfig {
  private int prometheusPort = -1;
  private int workerPoolSize = Runtime.getRuntime().availableProcessors() * 2;
}
