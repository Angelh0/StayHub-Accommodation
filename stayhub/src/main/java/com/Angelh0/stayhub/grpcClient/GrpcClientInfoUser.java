package com.Angelh0.stayhub.grpcClient;

import com.Angelh0.stayhub.dto.UserInfoDTO;
import com.infoUserGrpc.grpc.getInfoRequest;
import com.infoUserGrpc.grpc.infoGrpc;
import com.infoUserGrpc.grpc.infoUserResponse;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GrpcClientInfoUser {

    private final ManagedChannel channel =
            NettyChannelBuilder
                    .forTarget("stayhub-user:9093")
                    .usePlaintext()
                    .build();

    private final infoGrpc.infoBlockingStub stub =
            infoGrpc.newBlockingStub(channel);

    public UserInfoDTO getInfoUser(UUID uuid) {
        getInfoRequest request = getInfoRequest.newBuilder()
                .setUuid(uuid.toString())
                .build();

        infoUserResponse response = stub.getInfoUser(request);

        UserInfoDTO userInfo = new UserInfoDTO();
        userInfo.setUuid(response.getUuid());
        userInfo.setFirstName(response.getFirstName());
        userInfo.setLastName(response.getLastName());
        userInfo.setEmail(response.getEmail());

        return userInfo;
    }



}
