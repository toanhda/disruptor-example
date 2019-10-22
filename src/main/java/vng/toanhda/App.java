package vng.toanhda;

import io.vertx.core.Vertx;
import vng.toanhda.config.ServerConfig;
import vng.toanhda.database.SQLClientProvider;
import vng.toanhda.database.SQLClientProviderProviderImpl;
import vng.toanhda.database.SQLClientProviderProviderVertXImpl;
import vng.toanhda.database.SQLClientProviderVertX;
import vng.toanhda.disruptor.DisruptorCreator;
import vng.toanhda.disruptor.StorageConsumer;
import vng.toanhda.disruptor.StorageEvent;
import vng.toanhda.grpc.GrpcServer;
import vng.toanhda.service.PingServiceHandler;
import vng.toanhda.utils.FileConfigLoader;
import vng.toanhda.vertx.VertxCommon;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException, SQLException {

        ServerConfig serverConfig = FileConfigLoader.load(ServerConfig.class);
        serverConfig.verify();

        // Start disruptor
        DisruptorCreator.getInstance(
                DisruptorCreator.DISRUPTOR_NAME_GET, StorageEvent.EVENT_FACTORY, serverConfig.getDisruptorConfig().getBufferSize());

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
