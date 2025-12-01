package com.Angelh0.stayhub.grpcClient;

import com.Angelh0.stayhub.dto.room.RoomAvailabilityDTO;
import com.validateServiceGrpc.grpc.ValidateValuesGrpc;
import com.validateServiceGrpc.grpc.ValidateValuesRequest;
import com.validateServiceGrpc.grpc.ValidateValuesResponse;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class GrpcClientValidateCountry {

    private final ManagedChannel channel = NettyChannelBuilder
            .forTarget("localhost:9092")
            .usePlaintext()
            .build();

    private final ValidateValuesGrpc.ValidateValuesBlockingStub stub =
            ValidateValuesGrpc.newBlockingStub(channel);

    public RoomAvailabilityDTO validateValues(String city, String country) {
        ValidateValuesRequest request = ValidateValuesRequest.newBuilder()
                .setCity(city)
                .setCountry(country)
                .build();

        ValidateValuesResponse response = stub.validateValuesAccommodation(request);

        RoomAvailabilityDTO roomDTO = new RoomAvailabilityDTO();
        roomDTO.setMessage(response.getMessage());
        roomDTO.setAvailable(response.getValidate());
        return roomDTO;
    }
}
