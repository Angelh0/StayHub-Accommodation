package com.Angelh0.stayhub.grpcClient;

import com.Angelh0.stayhub.dto.room.CreateBlockDTO;
import com.checkAvailability.grpc.ReservationAvailabilityServiceGrpc;
import com.checkAvailability.grpc.ReservationStatusChangeRequest;
import com.checkAvailability.grpc.ReservationStatusChangeResponse;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GrpcClientBlockRooms {

    private final ManagedChannel channel = NettyChannelBuilder
            .forTarget("stayhub-reservation:9091")
            .usePlaintext()
            .build();

    private final ReservationAvailabilityServiceGrpc.ReservationAvailabilityServiceBlockingStub stub =
            ReservationAvailabilityServiceGrpc.newBlockingStub(channel);

    public ReservationStatusChangeResponse sendBlockRequest(UUID roomUuid, UUID ownerUuid, CreateBlockDTO dto) {

        ReservationStatusChangeRequest request = ReservationStatusChangeRequest.newBuilder()
                .setRoomUuid(roomUuid.toString())
                .setUuidOwner(ownerUuid.toString())
                .setStartDate(dto.getBlockStartDate().toString())
                .setEndDate(dto.getEndStartDate().toString())
                .setBlockType(dto.getBlockType().name())
                .setReason(dto.getReason() != null ? dto.getReason() : "")
                .build();

        return stub.checkRoomStatusChange(request);
    }
}