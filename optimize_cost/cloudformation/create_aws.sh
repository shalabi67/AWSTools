#!/usr/bin/env bash
aws cloudformation create-stack --stack-name backend-dev  --template-body file://./cloudformation/team.yaml --parameters file://./cloudformation/backend.json
aws cloudformation create-stack --stack-name backend-dev-project2  --template-body file://./cloudformation/team.yaml --parameters file://./cloudformation/backend-project.json
aws cloudformation create-stack --stack-name backend-live  --template-body file://./cloudformation/team.yaml --parameters file://./cloudformation/backend-live.json
aws cloudformation create-stack --stack-name backend-live-project2  --template-body file://./cloudformation/team.yaml --parameters file://./cloudformation/backend-live-project.json


### PF1
#aws cloudformation create-stack --stack-name pf1-dev  --template-body file://./cloudformation/team.yaml --parameters file://./cloudformation/pf1.json
aws cloudformation create-stack --stack-name pf1-dev-project2  --template-body file://./cloudformation/team.yaml --parameters file://./cloudformation/backend-project.json
aws cloudformation create-stack --stack-name pf1-live  --template-body file://./cloudformation/team.yaml --parameters file://./cloudformation/pf1-live.json
#aws cloudformation create-stack --stack-name pf1-live-project2  --template-body file://./cloudformation/team.yaml --parameters file://./cloudformation/pf1-live-project.json
