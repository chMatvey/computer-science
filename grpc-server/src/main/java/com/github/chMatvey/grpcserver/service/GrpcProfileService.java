package com.github.chMatvey.grpcserver.service;

import com.github.chMatvey.grpcServer.ProfileOuterClass;
import com.github.chMatvey.grpcServer.ProfileServiceGrpc;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
@Slf4j
public class GrpcProfileService extends ProfileServiceGrpc.ProfileServiceImplBase {
    private static final int LIMIT = 5;

    @Override
    public void getCurrentProfile(Empty request, StreamObserver<ProfileOuterClass.Profile> responseObserver) {
        log.info("Get current profile");
        responseObserver.onNext(ProfileOuterClass.Profile.newBuilder()
                .setId(1)
                .setUsername("username")
                .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void deleteCurrentProfile(Empty request, StreamObserver<Empty> responseObserver) {
        log.info("Delete current profile");
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<ProfileOuterClass.Profile> clientStream(StreamObserver<Empty> responseObserver) {
        return new StreamObserver<>() {
            int pointCount;

            @Override
            public void onNext(ProfileOuterClass.Profile profile) {
                log.info("Client message: {}", profile);
                if (++pointCount == LIMIT) {
                    responseObserver.onNext(Empty.newBuilder().build());
                    responseObserver.onCompleted();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("Client stream error", throwable);
                responseObserver.onError(throwable);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void serverStream(Empty request, StreamObserver<ProfileOuterClass.Profile> responseObserver) {
        for (int i = 0; i < LIMIT; i++)
            responseObserver.onNext(ProfileOuterClass.Profile.newBuilder()
                    .setId(i)
                    .setUsername("test")
                    .build()
            );
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<ProfileOuterClass.Profile> biDirectionalStream(StreamObserver<ProfileOuterClass.Profile> responseObserver) {
        return new StreamObserver<>() {
            int pointCount;

            @Override
            public void onNext(ProfileOuterClass.Profile profile) {
                log.info("Client message: {}", profile);
                responseObserver.onNext(ProfileOuterClass.Profile.newBuilder()
                        .setId(++pointCount)
                        .setUsername("test")
                        .build()
                );
                if (pointCount == LIMIT) {
                    responseObserver.onNext(profile);
                    responseObserver.onCompleted();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("Bi direction stream error", throwable);
                responseObserver.onError(throwable);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
