package dev.hoony9x.practice

import dev.hoony9x.practice.K8sOperatorKotlinPractice.Companion.OPERATOR_LABEL
import io.fabric8.kubernetes.api.model.ContainerPort
import io.fabric8.kubernetes.api.model.apps.Deployment
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder
import io.javaoperatorsdk.operator.ReconcilerUtils
import io.javaoperatorsdk.operator.api.reconciler.Context
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent

@KubernetesDependent(labelSelector = "app.kubernetes.io/managed-by=$OPERATOR_LABEL")
class NginxDeploymentDependentResource :
    CRUDKubernetesDependentResource<Deployment, Nginx>(Deployment::class.java) {
    override fun desired(nginx: Nginx, context: Context<Nginx>): Deployment {
        val metadata = nginx.metadata

        val deployment = ReconcilerUtils.loadYaml(
            Deployment::class.java, javaClass, "/dev.hoony9x.practice/deployment.yaml"
        )

        return DeploymentBuilder(deployment)
            .editMetadata()
            .withName(metadata.name)
            .withNamespace(metadata.namespace)
            .addToLabels("app", metadata.name)
            .addToLabels("app.kubernetes.io/part-of", metadata.name)
            .addToLabels("app.kubernetes.io/managed-by", OPERATOR_LABEL)
            .endMetadata()
            .editSpec()
            .editSelector()
            .addToMatchLabels("app", metadata.name)
            .endSelector()
            .withReplicas(nginx.spec.replicas)
            .editTemplate()
            .editMetadata()
            .addToLabels("app", metadata.name)
            .endMetadata()
            .editSpec()
            .editFirstContainer()
            .withImage(nginxImage(nginx))
            .withName("nginx-container")
            .withPorts(ContainerPort(nginx.spec.port, null, null, "http", "TCP"))
            .endContainer()
            .endSpec()
            .endTemplate()
            .endSpec()
            .build()
    }

    companion object {
        private fun nginxImage(schema: Nginx): String = "nginx:${schema.spec.version}"
    }
}
