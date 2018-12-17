#!/usr/bin/env bash
aws cloudformation delete-stack --stack-name backend-dev
aws cloudformation delete-stack --stack-name backend-dev-project2
aws cloudformation delete-stack --stack-name backend-live
aws cloudformation delete-stack --stack-name backend-live-project2

### PF1
aws cloudformation delete-stack --stack-name pf1-dev
aws cloudformation delete-stack --stack-name pf1-dev-project2
aws cloudformation delete-stack --stack-name pf1-live
aws cloudformation delete-stack --stack-name pf1-live-project2
