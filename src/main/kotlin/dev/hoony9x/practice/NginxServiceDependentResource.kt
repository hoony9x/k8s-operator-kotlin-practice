package dev.hoony9x.practice

import dev.hoony9x.practice.K8sOperatorKotlinPractice.Companion.OPERATOR_LABEL
import io.fabric8.kubernetes.api.model.Service
import io.fabric8.kubernetes.api.model.ServiceBuilder
import io.javaoperatorsdk.operator.ReconcilerUtils
import io.javaoperatorsdk.operator.api.reconciler.Context
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent

@KubernetesDependent(labelSelector = "app.kubernetes.io/managed-by=$OPERATOR_LABEL")
class NginxServiceDependentResource: CRUDKubernetesDependentResource<Service, Nginx>(Service::class.java) {
    override fun desired(schema: Nginx, context: Context<Nginx>): Service {
        val metadata = schema.metadata
        return ServiceBuilder(ReconcilerUtils.loadYaml(Service::class.java, javaClass, "service.yaml"))
            .editMetadata()
            .withName(metadata.name)
            .withNamespace(metadata.namespace)
            .addToLabels("app.kubernetes.io/managed-by", OPERATOR_LABEL)
            .endMetadata()
            .editSpec()
            .addToSelector("app", metadata.name)
            .endSpec()
            .build()
    }
}
