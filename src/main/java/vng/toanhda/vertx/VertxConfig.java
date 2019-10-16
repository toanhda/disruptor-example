package vng.toanhda.vertx;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VertxConfig {
    private int prometheusPort = 8080;
    private int workerPoolSize = Runtime.getRuntime().availableProcessors() * 2;
}
