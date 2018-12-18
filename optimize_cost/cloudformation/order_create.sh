#!/usr/bin/env bash
aws cloudformation create-stack --stack-name not-participating  --template-body file://./cloudformation/order/no.yaml --parameters file://./cloudformation/order/no.json
aws cloudformation create-stack --stack-name no-order-1  --template-body file://./cloudformation/order/order.yaml --parameters file://./cloudformation/order/not-ordered.json
aws cloudformation create-stack --stack-name order-1  --template-body file://./cloudformation/order/order.yaml --parameters file://./cloudformation/order/order1.json
aws cloudformation create-stack --stack-name order-2  --template-body file://./cloudformation/order/order.yaml --parameters file://./cloudformation/order/order2.json
aws cloudformation create-stack --stack-name order-3  --template-body file://./cloudformation/order/order.yaml --parameters file://./cloudformation/order/order3.json
