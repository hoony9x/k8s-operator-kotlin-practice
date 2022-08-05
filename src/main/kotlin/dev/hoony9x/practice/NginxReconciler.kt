package dev.hoony9x.practice

import io.fabric8.kubernetes.api.model.apps.Deployment
import io.fabric8.kubernetes.api.model.apps.DeploymentStatus
import io.javaoperatorsdk.operator.api.reconciler.Context
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration
import io.javaoperatorsdk.operator.api.reconciler.Reconciler
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@ControllerConfiguration(
    dependents = [
        Dependent(type = NginxDeploymentDependentResource::class),
        Dependent(type = NginxServiceDependentResource::class)
    ]
)
class NginxReconciler : Reconciler<Nginx> {
    private val log: Logger = LoggerFactory.getLogger(NginxReconciler::class.java)

    override fun reconcile(nginx: Nginx, context: Context<Nginx>): UpdateControl<Nginx> {
        return context.getSecondaryResource(Deployment::class.java).map { deployment ->
            val updatedNginx = updateNginxStatus(nginx, deployment)
            log.info(
                "Updating status of Nginx {} in namespace {} to {} ready replicas",
                nginx.metadata.name,
                nginx.metadata.namespace,
                nginx.status.readyReplicas
            )

            UpdateControl.patchStatus(updatedNginx)
        }.orElse(UpdateControl.noUpdate())
    }

    private fun updateNginxStatus(nginx: Nginx, deployment: Deployment): Nginx {
        val deploymentStatus = deployment.status ?: DeploymentStatus()
        val readyReplicas = deploymentStatus.readyReplicas ?: 0

        val status = NginxStatus()
        status.readyReplicas = readyReplicas

        nginx.status = status
        return nginx
    }
}
