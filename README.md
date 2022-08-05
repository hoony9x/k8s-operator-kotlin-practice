# Kubernetes Operator Kotlin Practice

[Java Operator SDK](https://github.com/java-operator-sdk/java-operator-sdk) 를 이용한 연습.

## Purpose Overview
- [Java Operator SDK](https://github.com/java-operator-sdk/java-operator-sdk) 를 이용하여 K8s Operator 를 만들어본다.

## Detailed Steps
- 간단하게 [`nginx` Container Image](https://hub.docker.com/_/nginx) 를 이용하여 CR 의 생성/추가/삭제에 따라 아래 작업을 한다.
  - `nginx` `Deployment` 의 `replicas` 조정
  - `nginx` 의 `Service` Expose

## Reference
- https://github.com/java-operator-sdk/java-operator-sdk