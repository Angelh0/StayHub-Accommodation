package com.Angelh0.stayhub.grpcClient;

import com.Angelh0.stayhub.dto.room.RoomAvailabilityDTO;
import com.Angelh0.stayhub.dto.room.RoomDTO;
import com.Angelh0.stayhub.dto.room.UpdateRoomDTO;
import com.checkAvailability.grpc.ReservationAvailabilityServiceGrpc;
import com.checkAvailability.grpc.ReservationStatusChangeRequest;
import com.checkAvailability.grpc.ReservationStatusChangeResponse;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class GrpcClientRoomStatusChange {

    private final ManagedChannel channel =
            NettyChannelBuilder.forTarget("localhost:9091")
                    .usePlaintext()
                    .build();

    private final ReservationAvailabilityServiceGrpc.ReservationAvailabilityServiceBlockingStub stub =
            ReservationAvailabilityServiceGrpc.newBlockingStub(channel);

    public RoomAvailabilityDTO checkStatusRoom(UUID uuid, UUID uuidOwner, String startDate, String endDate) {

        ReservationStatusChangeRequest request = ReservationStatusChangeRequest.newBuilder()
                .setRoomUuid(String.valueOf(uuid))
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setUuidOwner(String.valueOf(uuidOwner))
                .build();

        ReservationStatusChangeResponse response = stub.checkRoomStatusChange(request);

        RoomAvailabilityDTO roomDTO = new RoomAvailabilityDTO();
        roomDTO.setUuidRoom(UUID.fromString(response.getRoomUuid()));
        roomDTO.setAvailable(response.getAvailable());
        roomDTO.setMessage(response.getMessage());

        return roomDTO;
    }
}
