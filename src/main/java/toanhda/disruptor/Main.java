package toanhda.disruptor;

import io.vertx.core.Vertx;
import toanhda.disruptor.config.ServerConfig;
import toanhda.disruptor.database.SQLClientProvider;
import toanhda.disruptor.database.SQLClientProviderProviderImpl;
import toanhda.disruptor.vertx.VertxCommon;
import toanhda.disruptor.database.SQLClientProviderProviderVertXImpl;
import toanhda.disruptor.database.SQLClientProviderVertX;
import toanhda.disruptor.disruptor.DisruptorCreator;
import toanhda.disruptor.disruptor.StorageEvent;
import toanhda.disruptor.grpc.GrpcServer;
import toanhda.disruptor.service.PingServiceHandler;
import toanhda.disruptor.utils.FileConfigLoader;

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
