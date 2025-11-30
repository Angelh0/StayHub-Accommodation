package com.Angelh0.stayhub.grpcClient;

import com.Angelh0.stayhub.dto.room.RoomAvailabilityDTO;
import com.checkAvailability.grpc.ReservationCheckServiceGrpc;
import com.checkAvailability.grpc.ReservationRequest;
import com.checkAvailability.grpc.ReservationResponse;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GrpcClientFutureReservation {

    private final ManagedChannel channel = NettyChannelBuilder
            .forTarget("localhost:9091")
            .usePlaintext()
            .build();

    private final ReservationCheckServiceGrpc.ReservationCheckServiceBlockingStub stub =
            ReservationCheckServiceGrpc.newBlockingStub(channel);

    public RoomAvailabilityDTO getFutureReservation(UUID uuid) {
        ReservationRequest request = ReservationRequest.newBuilder()
                .setRoomUuid(uuid.toString())
                .build();

        ReservationResponse response = stub.checkFutureReservation(request);

        RoomAvailabilityDTO dto = new RoomAvailabilityDTO();
        dto.setUuidRoom(UUID.fromString(response.getRoomUuid()));
        dto.setAvailable(response.getAvailable());

        return dto;
    }
}