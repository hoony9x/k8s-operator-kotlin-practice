# Kubernetes Operator Kotlin Practice

[Java Operator SDK](https://github.com/java-operator-sdk/java-operator-sdk) 를 이용한 연습.

## Purpose Overview
- [Java Operator SDK](https://github.com/java-operator-sdk/java-operator-sdk) 를 이용하여 K8s Operator 를 만들어본다.

## Detailed Steps
- 간단하게 [`nginx` Container Image](https://hub.docker.com/_/nginx) 를 이용하여 CR 의 생성/추가/삭제에 따라 아래 작업을 한다.
  - `nginx` `Deployment` 의 `replicas` 조정
  - `nginx` 의 `Service` Expose

## Progress Status
- 아래 Custom Resource 배포 시 `Deployment` 와 `Service` 가 자동 생성되도록 하는 단계까지 완료.

```yaml
apiVersion: dev.hoony9x.practice/v1
kind: Nginx
metadata:
  name: practice
  namespace: operator-practice
spec:
  port: 8080
  exposePort: 80
  replicas: 3
  version: latest
```

## Reference
- https://github.com/java-operator-sdk/java-operator-sdk
