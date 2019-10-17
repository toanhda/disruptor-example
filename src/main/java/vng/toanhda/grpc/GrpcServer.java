package vng.toanhda.grpc;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.grpc.VertxServerBuilder;
import vng.toanhda.service.PingServiceHandler;

public class GrpcServer {
    private Vertx vertx;
    private PingServiceHandler pingService;
    public GrpcServer(Vertx vertx, PingServiceHandler pingService) {
        this.vertx = vertx;
        this.pingService = pingService;
    }

    public void start(GrpcConfig grpcConfig) {
        vertx.deployVerticle(
                () -> new AbstractVerticle() {
                    @Override
                    public void start() throws Exception {
                        VertxServerBuilder
                                .forAddress(vertx, "0.0.0.0", grpcConfig.getPort())
                                .addService(pingService)
                                .build()
                                .start();
                        System.out.println("Server is scaling !!!!");

                    }
                },
                new DeploymentOptions()
                        .setInstances(grpcConfig.getNumInstances()));
    }

}
