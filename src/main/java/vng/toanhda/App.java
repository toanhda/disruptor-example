package vng.toanhda;

import io.grpc.BindableService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.grpc.VertxServer;
import io.vertx.grpc.VertxServerBuilder;
import vng.toanhda.database.SQLClientProvider;
import vng.toanhda.database.SQLClientProviderProviderIml;
import vng.toanhda.database.SQLClientProviderProviderVertXIml;
import vng.toanhda.database.SQLClientProviderVertX;
import vng.toanhda.disruptor.StorageConsumer;
import vng.toanhda.disruptor.StorageEvent;
import vng.toanhda.grpc.GrpcServer;
import vng.toanhda.service.PingServiceHandler;
import vng.toanhda.disruptor.DisruptorCreator;
import vng.toanhda.vertx.VertxCommon;
import vng.toanhda.vertx.VertxConfig;

import java.io.IOException;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws IOException, SQLException {
        // Start disruptor
        DisruptorCreator.getInstance(
                DisruptorCreator.DISRUPTOR_NAME_GET, StorageEvent.EVENT_FACTORY, 1024, new StorageConsumer());

        // Initialize service
        VertxConfig vertxConfig = new VertxConfig();
        Vertx vertx = VertxCommon.getVertxInstance(vertxConfig);
        SQLClientProviderVertX clientProviderVertX = new SQLClientProviderProviderVertXIml(vertx);
        clientProviderVertX.initialize();
        SQLClientProvider clientProvider = new SQLClientProviderProviderIml();
        PingServiceHandler pingService = new PingServiceHandler(clientProviderVertX, clientProvider);

        // Start gRPC
        GrpcServer grpcServer = new GrpcServer(vertx, pingService);
        grpcServer.start();

    }
}
