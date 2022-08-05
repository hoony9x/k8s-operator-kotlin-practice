package dev.hoony9x.practice

import io.javaoperatorsdk.operator.api.reconciler.*

@ControllerConfiguration
class NginxSchemaReconciler : Reconciler<NginxSchema>, ErrorStatusHandler<NginxSchema> {
    override fun reconcile(resource: NginxSchema, context: Context<NginxSchema>): UpdateControl<NginxSchema> {
        TODO("Not yet implemented")
    }

    override fun updateErrorStatus(
        resource: NginxSchema,
        context: Context<NginxSchema>,
        e: Exception
    ): ErrorStatusUpdateControl<NginxSchema> {
        TODO("Not yet implemented")
    }
}