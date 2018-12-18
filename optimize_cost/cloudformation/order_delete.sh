#!/usr/bin/env bash
aws cloudformation delete-stack --stack-name not-participating
aws cloudformation delete-stack --stack-name no-order-1
aws cloudformation delete-stack --stack-name order-1
aws cloudformation delete-stack --stack-name order-2
aws cloudformation delete-stack --stack-name order-3



