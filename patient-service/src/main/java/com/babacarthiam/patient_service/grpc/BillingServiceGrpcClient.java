package com.babacarthiam.patient_service.grpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.babacarthiam.billing_service.BillingRequest;
import com.babacarthiam.billing_service.BillingResponse;
import com.babacarthiam.billing_service.BillingServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Service
public class BillingServiceGrpcClient {

    private final Logger logger = LoggerFactory.getLogger(BillingServiceGrpcClient.class);

    private final BillingServiceGrpc.BillingServiceBlockingStub billingServiceBlockingStub;

    public BillingServiceGrpcClient(@Value("${billing.service.address:localhost}") String billingServiceAddress, @Value("${billing.service.port:9001}") int billingServicePort) {

        logger.info("Connecting to Billing Service at {}:{}", billingServiceAddress, billingServicePort);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(billingServiceAddress, billingServicePort).usePlaintext().build();

        billingServiceBlockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillingResponse createBillingAccount(String patientId, String name, String email) {
        BillingRequest request = BillingRequest.newBuilder()
                .setPatientId(patientId)
                .setName(name)
                .setEmail(email)
                .build();

        BillingResponse response = billingServiceBlockingStub.createBillingAccount(request);
        logger.info("Received response from Billing Service: {}", response);
        return response;        
    }

}
