package vn.zalopay.zas.config;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.zalopay.zas.disruptor.DisruptorConfig;
import vn.zalopay.zas.grpc.GrpcConfig;
import vn.zalopay.zas.utils.JsonProtoUtils;
import vn.zalopay.zas.vertx.VertxConfig;

@Getter
@Setter
public class ServerConfig {
  private static final Logger logger =
      LoggerFactory.getLogger(ServerConfig.class.getCanonicalName());

  private VertxConfig vertxConfig;
  private GrpcConfig grpcConfig;
  private DisruptorConfig disruptorConfig;

  public void verify() {
    assert vertxConfig != null;
    assert grpcConfig != null;
    assert disruptorConfig != null;

    logger.info("> vertxConfig={}", JsonProtoUtils.printGson(vertxConfig));
    logger.info("> grpcConfig={}", JsonProtoUtils.printGson(grpcConfig));
    logger.info("> disruptorConfig={}", JsonProtoUtils.printGson(disruptorConfig));
  }
}
