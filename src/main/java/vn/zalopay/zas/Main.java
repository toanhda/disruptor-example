package vn.zalopay.zas;

import io.vertx.core.Vertx;
import vn.zalopay.zas.config.ServerConfig;
import vn.zalopay.zas.database.SQLClientProvider;
import vn.zalopay.zas.database.SQLClientProviderProviderImpl;
import vn.zalopay.zas.vertx.VertxCommon;
import vn.zalopay.zas.database.SQLClientProviderProviderVertXImpl;
import vn.zalopay.zas.database.SQLClientProviderVertX;
import vn.zalopay.zas.disruptor.DisruptorCreator;
import vn.zalopay.zas.disruptor.StorageEvent;
import vn.zalopay.zas.grpc.GrpcServer;
import vn.zalopay.zas.service.PingServiceHandler;
import vn.zalopay.zas.utils.FileConfigLoader;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
  public static void main(String[] args) throws IOException, SQLException {

    ServerConfig serverConfig = FileConfigLoader.load(ServerConfig.class);
    serverConfig.verify();

    // Start disruptor
    DisruptorCreator.getInstance(StorageEvent.EVENT_FACTORY, serverConfig.getDisruptorConfig());

    // Initialize service
    Vertx vertx = VertxCommon.getVertxInstance(serverConfig.getVertxConfig());
    SQLClientProviderVertX clientProviderVertX = new SQLClientProviderProviderVertXImpl(vertx);
    clientProviderVertX.initialize();
    SQLClientProvider clientProvider = new SQLClientProviderProviderImpl();
    PingServiceHandler pingService = new PingServiceHandler(clientProviderVertX, clientProvider);

    // Start gRPC
    GrpcServer grpcServer = new GrpcServer(vertx, pingService);
    grpcServer.start(serverConfig.getGrpcConfig());
  }
}
