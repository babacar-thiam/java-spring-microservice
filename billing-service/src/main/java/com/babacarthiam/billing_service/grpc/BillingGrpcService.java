package com.babacarthiam.billing_service.grpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.babacarthiam.billing_service.BillingRequest;
import com.babacarthiam.billing_service.BillingResponse;
import com.babacarthiam.billing_service.BillingServiceGrpc.BillingServiceImplBase;
import com.babacarthiam.billing_service.entity.BillingRequestEntity;
import com.babacarthiam.billing_service.repository.BillingRequestRepository;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {

    private final Logger logger = LoggerFactory.getLogger(BillingGrpcService.class);

    private final BillingRequestRepository billingRequestRepository;

    public BillingGrpcService(BillingRequestRepository billingRequestRepository) {
        this.billingRequestRepository = billingRequestRepository;
    }

    @Override
    public void createBillingAccount(BillingRequest billingRequest, StreamObserver<BillingResponse> responseObserver) {

        logger.info("Received request to create billing account: {}", billingRequest);

        BillingRequestEntity billingRequestEntity = new BillingRequestEntity();
        billingRequestEntity.setPatientId(billingRequest.getPatientId());
        billingRequestEntity.setPatientName(billingRequest.getName());
        billingRequestEntity.setPatientEmail(billingRequest.getEmail());
        billingRequestEntity.setStatus("PENDING");
        billingRequestRepository.save(billingRequestEntity);
        logger.info("Billing request saved to database: {}", billingRequestEntity);

        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId(billingRequestEntity.getId().toString())
                .setStatus(billingRequestEntity.getStatus())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
        logger.info("Billing account created successfully with ID: {}", response.getAccountId());
    }
}