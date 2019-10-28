package disruptor.protobuf;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: ping.proto")
public final class PingServiceGrpc {

  private PingServiceGrpc() {}

  private static <T> io.grpc.stub.StreamObserver<T> toObserver(final io.vertx.core.Handler<io.vertx.core.AsyncResult<T>> handler) {
    return new io.grpc.stub.StreamObserver<T>() {
      private volatile boolean resolved = false;
      @Override
      public void onNext(T value) {
        if (!resolved) {
          resolved = true;
          handler.handle(io.vertx.core.Future.succeededFuture(value));
        }
      }

      @Override
      public void onError(Throwable t) {
        if (!resolved) {
          resolved = true;
          handler.handle(io.vertx.core.Future.failedFuture(t));
        }
      }

      @Override
      public void onCompleted() {
        if (!resolved) {
          resolved = true;
          handler.handle(io.vertx.core.Future.succeededFuture());
        }
      }
    };
  }

  public static final String SERVICE_NAME = "disruptor.protobuf.PingService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<disruptor.protobuf.PingRequest,
      disruptor.protobuf.PingResponse> getPingMethod;

  public static io.grpc.MethodDescriptor<disruptor.protobuf.PingRequest,
      disruptor.protobuf.PingResponse> getPingMethod() {
    io.grpc.MethodDescriptor<disruptor.protobuf.PingRequest, disruptor.protobuf.PingResponse> getPingMethod;
    if ((getPingMethod = PingServiceGrpc.getPingMethod) == null) {
      synchronized (PingServiceGrpc.class) {
        if ((getPingMethod = PingServiceGrpc.getPingMethod) == null) {
          PingServiceGrpc.getPingMethod = getPingMethod = 
              io.grpc.MethodDescriptor.<disruptor.protobuf.PingRequest, disruptor.protobuf.PingResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "disruptor.protobuf.PingService", "ping"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  disruptor.protobuf.PingRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  disruptor.protobuf.PingResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new PingServiceMethodDescriptorSupplier("ping"))
                  .build();
          }
        }
     }
     return getPingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<disruptor.protobuf.PingRequest,
      disruptor.protobuf.PingResponse> getPingWithDisruptorMethod;

  public static io.grpc.MethodDescriptor<disruptor.protobuf.PingRequest,
      disruptor.protobuf.PingResponse> getPingWithDisruptorMethod() {
    io.grpc.MethodDescriptor<disruptor.protobuf.PingRequest, disruptor.protobuf.PingResponse> getPingWithDisruptorMethod;
    if ((getPingWithDisruptorMethod = PingServiceGrpc.getPingWithDisruptorMethod) == null) {
      synchronized (PingServiceGrpc.class) {
        if ((getPingWithDisruptorMethod = PingServiceGrpc.getPingWithDisruptorMethod) == null) {
          PingServiceGrpc.getPingWithDisruptorMethod = getPingWithDisruptorMethod = 
              io.grpc.MethodDescriptor.<disruptor.protobuf.PingRequest, disruptor.protobuf.PingResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "disruptor.protobuf.PingService", "pingWithDisruptor"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  disruptor.protobuf.PingRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  disruptor.protobuf.PingResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new PingServiceMethodDescriptorSupplier("pingWithDisruptor"))
                  .build();
          }
        }
     }
     return getPingWithDisruptorMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PingServiceStub newStub(io.grpc.Channel channel) {
    return new PingServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PingServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new PingServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PingServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new PingServiceFutureStub(channel);
  }

  /**
   * Creates a new vertx stub that supports all call types for the service
   */
  public static PingServiceVertxStub newVertxStub(io.grpc.Channel channel) {
    return new PingServiceVertxStub(channel);
  }

  /**
   */
  public static abstract class PingServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void ping(disruptor.protobuf.PingRequest request,
        io.grpc.stub.StreamObserver<disruptor.protobuf.PingResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPingMethod(), responseObserver);
    }

    /**
     */
    public void pingWithDisruptor(disruptor.protobuf.PingRequest request,
        io.grpc.stub.StreamObserver<disruptor.protobuf.PingResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPingWithDisruptorMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getPingMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                disruptor.protobuf.PingRequest,
                disruptor.protobuf.PingResponse>(
                  this, METHODID_PING)))
          .addMethod(
            getPingWithDisruptorMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                disruptor.protobuf.PingRequest,
                disruptor.protobuf.PingResponse>(
                  this, METHODID_PING_WITH_DISRUPTOR)))
          .build();
    }
  }

  /**
   */
  public static final class PingServiceStub extends io.grpc.stub.AbstractStub<PingServiceStub> {
    public PingServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    public PingServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PingServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PingServiceStub(channel, callOptions);
    }

    /**
     */
    public void ping(disruptor.protobuf.PingRequest request,
        io.grpc.stub.StreamObserver<disruptor.protobuf.PingResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPingMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void pingWithDisruptor(disruptor.protobuf.PingRequest request,
        io.grpc.stub.StreamObserver<disruptor.protobuf.PingResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPingWithDisruptorMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PingServiceBlockingStub extends io.grpc.stub.AbstractStub<PingServiceBlockingStub> {
    public PingServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    public PingServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PingServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PingServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public disruptor.protobuf.PingResponse ping(disruptor.protobuf.PingRequest request) {
      return blockingUnaryCall(
          getChannel(), getPingMethod(), getCallOptions(), request);
    }

    /**
     */
    public disruptor.protobuf.PingResponse pingWithDisruptor(disruptor.protobuf.PingRequest request) {
      return blockingUnaryCall(
          getChannel(), getPingWithDisruptorMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PingServiceFutureStub extends io.grpc.stub.AbstractStub<PingServiceFutureStub> {
    public PingServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    public PingServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PingServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PingServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<disruptor.protobuf.PingResponse> ping(
        disruptor.protobuf.PingRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPingMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<disruptor.protobuf.PingResponse> pingWithDisruptor(
        disruptor.protobuf.PingRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPingWithDisruptorMethod(), getCallOptions()), request);
    }
  }

  /**
   */
  public static abstract class PingServiceVertxImplBase implements io.grpc.BindableService {

    /**
     */
    public void ping(disruptor.protobuf.PingRequest request,
        io.vertx.core.Future<disruptor.protobuf.PingResponse> response) {
      asyncUnimplementedUnaryCall(getPingMethod(), PingServiceGrpc.toObserver(response.completer()));
    }

    /**
     */
    public void pingWithDisruptor(disruptor.protobuf.PingRequest request,
        io.vertx.core.Future<disruptor.protobuf.PingResponse> response) {
      asyncUnimplementedUnaryCall(getPingWithDisruptorMethod(), PingServiceGrpc.toObserver(response.completer()));
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getPingMethod(),
            asyncUnaryCall(
              new VertxMethodHandlers<
                disruptor.protobuf.PingRequest,
                disruptor.protobuf.PingResponse>(
                  this, METHODID_PING)))
          .addMethod(
            getPingWithDisruptorMethod(),
            asyncUnaryCall(
              new VertxMethodHandlers<
                disruptor.protobuf.PingRequest,
                disruptor.protobuf.PingResponse>(
                  this, METHODID_PING_WITH_DISRUPTOR)))
          .build();
    }
  }

  /**
   */
  public static final class PingServiceVertxStub extends io.grpc.stub.AbstractStub<PingServiceVertxStub> {
    public PingServiceVertxStub(io.grpc.Channel channel) {
      super(channel);
    }

    public PingServiceVertxStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PingServiceVertxStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PingServiceVertxStub(channel, callOptions);
    }

    /**
     */
    public void ping(disruptor.protobuf.PingRequest request,
        io.vertx.core.Handler<io.vertx.core.AsyncResult<disruptor.protobuf.PingResponse>> response) {
      asyncUnaryCall(
          getChannel().newCall(getPingMethod(), getCallOptions()), request, PingServiceGrpc.toObserver(response));
    }

    /**
     */
    public void pingWithDisruptor(disruptor.protobuf.PingRequest request,
        io.vertx.core.Handler<io.vertx.core.AsyncResult<disruptor.protobuf.PingResponse>> response) {
      asyncUnaryCall(
          getChannel().newCall(getPingWithDisruptorMethod(), getCallOptions()), request, PingServiceGrpc.toObserver(response));
    }
  }

  private static final int METHODID_PING = 0;
  private static final int METHODID_PING_WITH_DISRUPTOR = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PingServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PingServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PING:
          serviceImpl.ping((disruptor.protobuf.PingRequest) request,
              (io.grpc.stub.StreamObserver<disruptor.protobuf.PingResponse>) responseObserver);
          break;
        case METHODID_PING_WITH_DISRUPTOR:
          serviceImpl.pingWithDisruptor((disruptor.protobuf.PingRequest) request,
              (io.grpc.stub.StreamObserver<disruptor.protobuf.PingResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class VertxMethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PingServiceVertxImplBase serviceImpl;
    private final int methodId;

    VertxMethodHandlers(PingServiceVertxImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PING:
          serviceImpl.ping((disruptor.protobuf.PingRequest) request,
              (io.vertx.core.Future<disruptor.protobuf.PingResponse>) io.vertx.core.Future.<disruptor.protobuf.PingResponse>future().setHandler(ar -> {
                if (ar.succeeded()) {
                  ((io.grpc.stub.StreamObserver<disruptor.protobuf.PingResponse>) responseObserver).onNext(ar.result());
                  responseObserver.onCompleted();
                } else {
                  responseObserver.onError(ar.cause());
                }
              }));
          break;
        case METHODID_PING_WITH_DISRUPTOR:
          serviceImpl.pingWithDisruptor((disruptor.protobuf.PingRequest) request,
              (io.vertx.core.Future<disruptor.protobuf.PingResponse>) io.vertx.core.Future.<disruptor.protobuf.PingResponse>future().setHandler(ar -> {
                if (ar.succeeded()) {
                  ((io.grpc.stub.StreamObserver<disruptor.protobuf.PingResponse>) responseObserver).onNext(ar.result());
                  responseObserver.onCompleted();
                } else {
                  responseObserver.onError(ar.cause());
                }
              }));
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class PingServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PingServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return disruptor.protobuf.Ping.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PingService");
    }
  }

  private static final class PingServiceFileDescriptorSupplier
      extends PingServiceBaseDescriptorSupplier {
    PingServiceFileDescriptorSupplier() {}
  }

  private static final class PingServiceMethodDescriptorSupplier
      extends PingServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PingServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PingServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PingServiceFileDescriptorSupplier())
              .addMethod(getPingMethod())
              .addMethod(getPingWithDisruptorMethod())
              .build();
        }
      }
    }
    return result;
  }
}
