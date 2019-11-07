package toanhda.disruptor.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.micrometer.MicrometerMetricsOptions;
import io.vertx.micrometer.VertxJmxMetricsOptions;
import io.vertx.micrometer.VertxPrometheusOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import toanhda.disruptor.metrics.Tracker;

public class VertxCommon {
  private static final Logger logger = LogManager.getLogger(VertxCommon.class.getCanonicalName());

  public static Vertx getVertxInstance(VertxConfig vertxConfig) {
    Vertx vertx =
        Vertx.vertx(
            new VertxOptions()
                .setMetricsOptions(
                    new MicrometerMetricsOptions()
                        .setPrometheusOptions(
                            new VertxPrometheusOptions()
                                .setEnabled(true)
                                .setStartEmbeddedServer(true)
                                .setPublishQuantiles(true)
                                .setEmbeddedServerOptions(
                                    new HttpServerOptions()
                                        .setPort(vertxConfig.getPrometheusPort()))
                                .setEmbeddedServerEndpoint("/metrics"))
                        .setJmxMetricsOptions(
                            new VertxJmxMetricsOptions()
                                .setEnabled(true)
                                .setStep(5)
                                .setDomain("toanhda.diruptor"))
                        .setEnabled(true))
                .setPreferNativeTransport(true)
                .setWorkerPoolSize(vertxConfig.getWorkerPoolSize()));

    vertx.exceptionHandler(
        throwable -> {
          Tracker.builder().systemName("disruptor").method("exception").build().count();
          throwable.printStackTrace();
        });

    // True when native is available
    boolean usingNative = vertx.isNativeTransportEnabled();
    System.out.println("Running with native: " + usingNative);
    logger.info("Running with native: " + usingNative);

    return vertx;
  }
}
