package vn.zalopay.zas.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.vertx.micrometer.backends.BackendRegistries;
import lombok.Builder;

import java.util.concurrent.TimeUnit;

public class Tracker {

  static final MeterRegistry registry = BackendRegistries.getDefaultNow();
  private final String systemName;
  private final Timer.Sample sample;
  private final String method;
  private final long code;

  @Builder
  private Tracker(Timer.Sample sample, String systemName, String method, long code) {
    this.systemName = systemName;
    this.method = method;
    this.code = code;
    this.sample = sample;
  }

  public long record() {
    return nanosToMillis(
        sample.stop(
            Timer.builder(systemName)
                .tag("method", method)
                .tag("code", String.valueOf(code))
                .publishPercentileHistogram()
                .register(registry)));
  }

  public long count() {
    return nanosToMillis(
        sample.stop(
            Timer.builder(systemName)
                .tag("method", method)
                .tag("code", String.valueOf(code))
                .register(registry)));
  }

  private long nanosToMillis(long value) {
    return TimeUnit.MILLISECONDS.convert(value, TimeUnit.NANOSECONDS);
  }

  public static class TrackerBuilder {
    private String systemName;
    private Timer.Sample sample;
    private String method;
    private long code;

    public TrackerBuilder() {
      this.sample = Timer.start(registry);
    }

    public void setSystemName(String systemName) {
      this.systemName = systemName;
    }

    public void setMethod(String method) {
      this.method = method;
    }

    public void setCode(long code) {
      this.code = code;
    }
  }
}
