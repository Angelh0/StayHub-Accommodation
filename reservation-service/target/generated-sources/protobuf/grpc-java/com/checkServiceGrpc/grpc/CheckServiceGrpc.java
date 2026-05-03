package com.checkServiceGrpc.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.66.0)",
    comments = "Source: CheckInCheckOut.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class CheckServiceGrpc {

  private CheckServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "CheckService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.checkServiceGrpc.grpc.CheckRequest,
      com.checkServiceGrpc.grpc.CheckResponse> getGetCheckRoomMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getCheckRoom",
      requestType = com.checkServiceGrpc.grpc.CheckRequest.class,
      responseType = com.checkServiceGrpc.grpc.CheckResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.checkServiceGrpc.grpc.CheckRequest,
      com.checkServiceGrpc.grpc.CheckResponse> getGetCheckRoomMethod() {
    io.grpc.MethodDescriptor<com.checkServiceGrpc.grpc.CheckRequest, com.checkServiceGrpc.grpc.CheckResponse> getGetCheckRoomMethod;
    if ((getGetCheckRoomMethod = CheckServiceGrpc.getGetCheckRoomMethod) == null) {
      synchronized (CheckServiceGrpc.class) {
        if ((getGetCheckRoomMethod = CheckServiceGrpc.getGetCheckRoomMethod) == null) {
          CheckServiceGrpc.getGetCheckRoomMethod = getGetCheckRoomMethod =
              io.grpc.MethodDescriptor.<com.checkServiceGrpc.grpc.CheckRequest, com.checkServiceGrpc.grpc.CheckResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getCheckRoom"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.checkServiceGrpc.grpc.CheckRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.checkServiceGrpc.grpc.CheckResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CheckServiceMethodDescriptorSupplier("getCheckRoom"))
              .build();
        }
      }
    }
    return getGetCheckRoomMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CheckServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CheckServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CheckServiceStub>() {
        @java.lang.Override
        public CheckServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CheckServiceStub(channel, callOptions);
        }
      };
    return CheckServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CheckServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CheckServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CheckServiceBlockingStub>() {
        @java.lang.Override
        public CheckServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CheckServiceBlockingStub(channel, callOptions);
        }
      };
    return CheckServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CheckServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CheckServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CheckServiceFutureStub>() {
        @java.lang.Override
        public CheckServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CheckServiceFutureStub(channel, callOptions);
        }
      };
    return CheckServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getCheckRoom(com.checkServiceGrpc.grpc.CheckRequest request,
        io.grpc.stub.StreamObserver<com.checkServiceGrpc.grpc.CheckResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetCheckRoomMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service CheckService.
   */
  public static abstract class CheckServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return CheckServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service CheckService.
   */
  public static final class CheckServiceStub
      extends io.grpc.stub.AbstractAsyncStub<CheckServiceStub> {
    private CheckServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CheckServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CheckServiceStub(channel, callOptions);
    }

    /**
     */
    public void getCheckRoom(com.checkServiceGrpc.grpc.CheckRequest request,
        io.grpc.stub.StreamObserver<com.checkServiceGrpc.grpc.CheckResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCheckRoomMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service CheckService.
   */
  public static final class CheckServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<CheckServiceBlockingStub> {
    private CheckServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CheckServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CheckServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.checkServiceGrpc.grpc.CheckResponse getCheckRoom(com.checkServiceGrpc.grpc.CheckRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCheckRoomMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service CheckService.
   */
  public static final class CheckServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<CheckServiceFutureStub> {
    private CheckServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CheckServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CheckServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.checkServiceGrpc.grpc.CheckResponse> getCheckRoom(
        com.checkServiceGrpc.grpc.CheckRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCheckRoomMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_CHECK_ROOM = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_CHECK_ROOM:
          serviceImpl.getCheckRoom((com.checkServiceGrpc.grpc.CheckRequest) request,
              (io.grpc.stub.StreamObserver<com.checkServiceGrpc.grpc.CheckResponse>) responseObserver);
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

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getGetCheckRoomMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.checkServiceGrpc.grpc.CheckRequest,
              com.checkServiceGrpc.grpc.CheckResponse>(
                service, METHODID_GET_CHECK_ROOM)))
        .build();
  }

  private static abstract class CheckServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CheckServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.checkServiceGrpc.grpc.checkProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CheckService");
    }
  }

  private static final class CheckServiceFileDescriptorSupplier
      extends CheckServiceBaseDescriptorSupplier {
    CheckServiceFileDescriptorSupplier() {}
  }

  private static final class CheckServiceMethodDescriptorSupplier
      extends CheckServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    CheckServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (CheckServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CheckServiceFileDescriptorSupplier())
              .addMethod(getGetCheckRoomMethod())
              .build();
        }
      }
    }
    return result;
  }
}
