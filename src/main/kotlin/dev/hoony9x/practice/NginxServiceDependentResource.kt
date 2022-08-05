package dev.hoony9x.practice

import dev.hoony9x.practice.K8sOperatorKotlinPractice.Companion.OPERATOR_LABEL
import io.fabric8.kubernetes.api.model.IntOrString
import io.fabric8.kubernetes.api.model.Service
import io.fabric8.kubernetes.api.model.ServiceBuilder
import io.javaoperatorsdk.operator.ReconcilerUtils
import io.javaoperatorsdk.operator.api.reconciler.Context
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent

@KubernetesDependent(labelSelector = "app.kubernetes.io/managed-by=$OPERATOR_LABEL")
class NginxServiceDependentResource : CRUDKubernetesDependentResource<Service, Nginx>(Service::class.java) {
    override fun desired(nginx: Nginx, context: Context<Nginx>): Service {
        val metadata = nginx.metadata

        val service = ReconcilerUtils.loadYaml(
            Service::class.java,
            javaClass,
            "/dev.hoony9x.practice/service.yaml"
        )

        return ServiceBuilder(service)
            .editMetadata()
            .withName(metadata.name)
            .withNamespace(metadata.namespace)
            .addToLabels("app", metadata.name)
            .addToLabels("app.kubernetes.io/part-of", metadata.name)
            .addToLabels("app.kubernetes.io/managed-by", OPERATOR_LABEL)
            .endMetadata()
            .editSpec()
            .addToSelector("app", metadata.name)
            .editFirstPort()
            .withProtocol("TCP")
            .withTargetPort(IntOrString(nginx.spec.port))
            .withPort(nginx.spec.exposePort)
            .endPort()
            .endSpec()
            .build()
    }
}
