package com.roomServiceGrpc.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.66.0)",
    comments = "Source: Room.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class RoomServiceGrpc {

  private RoomServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "RoomService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.roomServiceGrpc.grpc.ReservationRequest,
      com.roomServiceGrpc.grpc.RoomResponse> getGetInfoRoomMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getInfoRoom",
      requestType = com.roomServiceGrpc.grpc.ReservationRequest.class,
      responseType = com.roomServiceGrpc.grpc.RoomResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.roomServiceGrpc.grpc.ReservationRequest,
      com.roomServiceGrpc.grpc.RoomResponse> getGetInfoRoomMethod() {
    io.grpc.MethodDescriptor<com.roomServiceGrpc.grpc.ReservationRequest, com.roomServiceGrpc.grpc.RoomResponse> getGetInfoRoomMethod;
    if ((getGetInfoRoomMethod = RoomServiceGrpc.getGetInfoRoomMethod) == null) {
      synchronized (RoomServiceGrpc.class) {
        if ((getGetInfoRoomMethod = RoomServiceGrpc.getGetInfoRoomMethod) == null) {
          RoomServiceGrpc.getGetInfoRoomMethod = getGetInfoRoomMethod =
              io.grpc.MethodDescriptor.<com.roomServiceGrpc.grpc.ReservationRequest, com.roomServiceGrpc.grpc.RoomResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getInfoRoom"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.roomServiceGrpc.grpc.ReservationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.roomServiceGrpc.grpc.RoomResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RoomServiceMethodDescriptorSupplier("getInfoRoom"))
              .build();
        }
      }
    }
    return getGetInfoRoomMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static RoomServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RoomServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RoomServiceStub>() {
        @java.lang.Override
        public RoomServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RoomServiceStub(channel, callOptions);
        }
      };
    return RoomServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static RoomServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RoomServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RoomServiceBlockingStub>() {
        @java.lang.Override
        public RoomServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RoomServiceBlockingStub(channel, callOptions);
        }
      };
    return RoomServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static RoomServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RoomServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RoomServiceFutureStub>() {
        @java.lang.Override
        public RoomServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RoomServiceFutureStub(channel, callOptions);
        }
      };
    return RoomServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getInfoRoom(com.roomServiceGrpc.grpc.ReservationRequest request,
        io.grpc.stub.StreamObserver<com.roomServiceGrpc.grpc.RoomResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetInfoRoomMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service RoomService.
   */
  public static abstract class RoomServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return RoomServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service RoomService.
   */
  public static final class RoomServiceStub
      extends io.grpc.stub.AbstractAsyncStub<RoomServiceStub> {
    private RoomServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RoomServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RoomServiceStub(channel, callOptions);
    }

    /**
     */
    public void getInfoRoom(com.roomServiceGrpc.grpc.ReservationRequest request,
        io.grpc.stub.StreamObserver<com.roomServiceGrpc.grpc.RoomResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetInfoRoomMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service RoomService.
   */
  public static final class RoomServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<RoomServiceBlockingStub> {
    private RoomServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RoomServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RoomServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.roomServiceGrpc.grpc.RoomResponse getInfoRoom(com.roomServiceGrpc.grpc.ReservationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetInfoRoomMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service RoomService.
   */
  public static final class RoomServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<RoomServiceFutureStub> {
    private RoomServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RoomServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RoomServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.roomServiceGrpc.grpc.RoomResponse> getInfoRoom(
        com.roomServiceGrpc.grpc.ReservationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetInfoRoomMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_INFO_ROOM = 0;

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
        case METHODID_GET_INFO_ROOM:
          serviceImpl.getInfoRoom((com.roomServiceGrpc.grpc.ReservationRequest) request,
              (io.grpc.stub.StreamObserver<com.roomServiceGrpc.grpc.RoomResponse>) responseObserver);
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
          getGetInfoRoomMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.roomServiceGrpc.grpc.ReservationRequest,
              com.roomServiceGrpc.grpc.RoomResponse>(
                service, METHODID_GET_INFO_ROOM)))
        .build();
  }

  private static abstract class RoomServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    RoomServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.roomServiceGrpc.grpc.RoomServiceProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("RoomService");
    }
  }

  private static final class RoomServiceFileDescriptorSupplier
      extends RoomServiceBaseDescriptorSupplier {
    RoomServiceFileDescriptorSupplier() {}
  }

  private static final class RoomServiceMethodDescriptorSupplier
      extends RoomServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    RoomServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (RoomServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new RoomServiceFileDescriptorSupplier())
              .addMethod(getGetInfoRoomMethod())
              .build();
        }
      }
    }
    return result;
  }
}
