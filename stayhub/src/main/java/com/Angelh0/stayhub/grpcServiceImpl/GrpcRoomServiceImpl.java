package com.Angelh0.stayhub.grpcServiceImpl;

import com.Angelh0.stayhub.dto.RoomDTO;
import com.Angelh0.stayhub.dto.SearchRoomDTO;
import com.Angelh0.stayhub.service.RoomService;
import com.roomServiceGrpc.grpc.ReservationRequest;
import com.roomServiceGrpc.grpc.RoomResponse;
import com.roomServiceGrpc.grpc.RoomServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@GrpcService
public class GrpcRoomServiceImpl extends RoomServiceGrpc.RoomServiceImplBase {

    @Autowired
    private RoomService roomService;

    @Override
    public void getInfoRoom(ReservationRequest request, StreamObserver<RoomResponse> responseObserver) {
        String uuidString = request.getUuidRoom();

        // Obtenemos info del room
        RoomDTO room = roomService.getRooms(java.util.UUID.fromString(uuidString));

        // Construimos response
        RoomResponse objectRoom = RoomResponse.newBuilder()
                .setUuidRoom(room.getUuid().toString())
                .setPrice(room.getPrice())
                .build();

        responseObserver.onNext(objectRoom);
        responseObserver.onCompleted();
    }
}
