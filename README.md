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

## Troubleshooting

### Kotlin Class 로 CRD 정의 시 YAML 자동 생성 되지 않던 문제
- 현재 CRD 의 YAML 생성은 `io.fabric8:crd-generator-apt` 에 의존.
  - 이 Generator 는 [딱 하나의 Class](https://github.com/fabric8io/kubernetes-client/blob/master/crd-generator/apt/src/main/java/io/fabric8/crd/generator/apt/CustomResourceAnnotationProcessor.java) 로 구성됨.
- 보다시피 Annotation Processor 인데, Gradle 에서 `compileKotlin` 단계에서 annotation processing 이 자동으로 되지 않음.
- 이를 되게 하기 위해서는 `kapt` 라는 plugin 을 추가 후 `kapt` 로 대상 라이브러리를 지정해야 함.
  - e.g. `build.gradle.kts` 의 `dependencies` block 에 `kapt("io.fabric8:crd-generator-apt:5.12.3")` 처럼 지정.
- 여기까지 하면 이제 YAML 생성이 잘 된다.
  - 단, [공식 문서](https://javaoperatorsdk.io/docs/features#automatic-generation-of-crds)에 적힌 것과 다른 Path 에 생성되므로, Gradle Build 시 로그를 확인할 것.
- Reference: https://stackoverflow.com/questions/57949323/kotlin-annotation-processor-not-working-what-am-i-missing


## Reference
- https://github.com/java-operator-sdk/java-operator-sdk
