package dev.hoony9x.practice

import dev.hoony9x.practice.K8sOperatorKotlinPractice.Companion.OPERATOR_LABEL
import io.fabric8.kubernetes.api.model.apps.Deployment
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder
import io.javaoperatorsdk.operator.ReconcilerUtils
import io.javaoperatorsdk.operator.api.reconciler.Context
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent

@KubernetesDependent(labelSelector = "app.kubernetes.io/managed-by=$OPERATOR_LABEL")
class NginxDeploymentDependentResource :
    CRUDKubernetesDependentResource<Deployment, Nginx>(Deployment::class.java) {
    override fun desired(schema: Nginx, context: Context<Nginx>): Deployment {
        val deployment = ReconcilerUtils.loadYaml(Deployment::class.java, javaClass, "deployment.yaml")
        val metadata = schema.metadata
        val name = metadata.name

        return DeploymentBuilder(deployment)
            .editMetadata()
            .withName(name)
            .withNamespace(metadata.namespace)
            .addToLabels("app", name)
            .addToLabels("app.kubernetes.io/part-of", name)
            .addToLabels("app.kubernetes.io/managed-by", OPERATOR_LABEL)
            .endMetadata()
            .editSpec()
            .editSelector().addToMatchLabels("app", name).endSelector()
            .withReplicas(schema.spec.replicas)
            .editTemplate()
            .editMetadata().addToLabels("app", name).endMetadata()
            .editSpec()
            .editFirstContainer().withImage(nginxImage(schema)).endContainer()
            .endSpec()
            .endTemplate()
            .endSpec()
            .build()
    }

    companion object {
        private fun nginxImage(schema: Nginx): String = "nginx:${schema.spec.version}"
    }
}
