#!/bin/bash
set -e # Stops the script if any command fails

aws --endpoint-url=http://localhost.localstack.cloud:4566 cloudformation delete-stack \
    --stack-name java-spring-microservice

aws --endpoint-url=http://localhost.localstack.cloud:4566 cloudformation deploy \
    --stack-name java-spring-microservice \
    --template-file "./cdk.out/localstack.template.json"

aws --endpoint-url=http://localhost.localstack.cloud:4566 elbv2 describe-load-balancers \
    --query "LoadBalancers[0].DNSName" --output text