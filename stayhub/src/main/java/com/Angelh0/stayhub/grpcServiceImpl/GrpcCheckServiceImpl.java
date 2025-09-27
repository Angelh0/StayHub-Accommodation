package com.Angelh0.stayhub.grpcServiceImpl;

import com.Angelh0.stayhub.dto.SearchRoomDTO;
import com.Angelh0.stayhub.service.SearchService;
import com.checkServiceGrpc.grpc.CheckRequest;
import com.checkServiceGrpc.grpc.CheckResponse;
import com.checkServiceGrpc.grpc.CheckServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class GrpcCheckServiceImpl extends CheckServiceGrpc.CheckServiceImplBase {

    @Autowired
    private SearchService searchService;

    @Override
    public void getCheckRoom(CheckRequest request, StreamObserver<CheckResponse> responseObserver) {

        SearchRoomDTO dto = searchService.getChecks();

        CheckResponse response = CheckResponse.newBuilder()
                .setCheckIn(dto.getCheckIn().toString())
                .setCheckOut(dto.getCheckOut().toString())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
