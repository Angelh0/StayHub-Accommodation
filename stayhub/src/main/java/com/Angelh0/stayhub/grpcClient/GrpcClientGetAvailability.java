package com.Angelh0.stayhub.grpcClient;

import com.Angelh0.stayhub.dto.room.RoomAvailabilityDTO;
import com.checkAvailability.grpc.AvailabilityRequest;
import com.checkAvailability.grpc.AvailabilityResponse;
import com.checkAvailability.grpc.RoomAvailability;
import com.checkAvailability.grpc.availabilityServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GrpcClientGetAvailability {

    private final ManagedChannel channel =
            NettyChannelBuilder
                    .forTarget("stayhub-reservation:9091")
                    .usePlaintext()
                    .build();

    private final availabilityServiceGrpc.availabilityServiceBlockingStub stub =
            availabilityServiceGrpc.newBlockingStub(channel);

    public List<RoomAvailabilityDTO> getAvailability(List<String> roomUuid, String checkIn, String checkOut) {
        AvailabilityRequest request = AvailabilityRequest.newBuilder()
                .addAllRoomUuid(roomUuid)
                .setCheckIn(checkIn)
                .setCheckOut(checkOut)
                .build();

        AvailabilityResponse response = stub.checkAvailability(request);

        List<RoomAvailabilityDTO> rooms = new ArrayList<>();

        for (RoomAvailability roomAvailability : response.getRoomsList()) {
            RoomAvailabilityDTO dto = new RoomAvailabilityDTO();
            dto.setUuidRoom(UUID.fromString(roomAvailability.getRoomUuid()));
            dto.setAvailable(roomAvailability.getAvailable());
            rooms.add(dto);
        }
        return rooms;
    }
}
