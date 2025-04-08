package com.babacarthiam.stack;

import software.amazon.awscdk.*;
import software.amazon.awscdk.services.ec2.InstanceClass;
import software.amazon.awscdk.services.ec2.InstanceSize;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.rds.DatabaseInstance;
import software.amazon.awscdk.services.rds.DatabaseInstanceEngine;
import software.amazon.awscdk.services.rds.MySqlInstanceEngineProps;
import software.amazon.awscdk.services.rds.MysqlEngineVersion;
import software.amazon.awscdk.services.ec2.InstanceType;
import software.amazon.awscdk.services.rds.Credentials;

public class LocalStack extends Stack {

  private final Vpc vpc;

  public LocalStack(final App scope, final String id, StackProps props) {
    super(scope, id, props);

    this.vpc = createVpc();

    DatabaseInstance patientServiceDb = createDatabaseInstance();
  }

  private Vpc createVpc() {
    return Vpc.Builder
        .create(this, "JavaSpringMicroserviceVPC").vpcName("JavaSpringMicroserviceVPC")
        .maxAzs(1)
        .natGateways(0)
        .build();
  }

  private DatabaseInstance createDatabaseInstance() {
    return DatabaseInstance.Builder
        .create(this, "PatientServiceDB")
        .engine(DatabaseInstanceEngine.mysql(MySqlInstanceEngineProps.builder()
            .version(MysqlEngineVersion.VER_8_0_37)
            .build()))
        .vpc(this.vpc)
        .instanceType(InstanceType.of(InstanceClass.BURSTABLE2, InstanceSize.MICRO))
        .allocatedStorage(20)
        .credentials(Credentials.fromGeneratedSecret("root"))
        .databaseName("patient-service-db")
        .port(3306)  // Explicitly set port
        .backupRetention(software.amazon.awscdk.Duration.days(0))  // Disable backups for LocalStack
        .deletionProtection(false)
        .storageEncrypted(false)  // Disable encryption for LocalStack
        .publiclyAccessible(true)  // Make it accessible
        .removalPolicy(RemovalPolicy.DESTROY)
        .build();
  }


  public static void main(final String[] args) {
    App app = new App(AppProps.builder().outdir("./infrastructure/cdk.out").build());

    StackProps stackProps = StackProps.builder()
        .synthesizer(new BootstraplessSynthesizer())
        .env(Environment.builder()
            .region("us-east-1")
            .account("000000000000")
            .build())
        .build();

    new LocalStack(app, "localstack", stackProps);
    app.synth();

    System.out.println("LocalStack synthesizing in progress...");
  }
}