package dev.hoony9x.practice

import io.fabric8.kubernetes.client.ConfigBuilder
import io.fabric8.kubernetes.client.DefaultKubernetesClient
import io.javaoperatorsdk.operator.Operator
import io.javaoperatorsdk.operator.monitoring.micrometer.MicrometerMetrics
import io.micrometer.core.instrument.logging.LoggingMeterRegistry
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.takes.facets.fork.FkRegex
import org.takes.facets.fork.TkFork
import org.takes.http.Exit
import org.takes.http.FtBasic

class K8sOperatorKotlinPractice {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(K8sOperatorKotlinPractice::class.java)

        const val OPERATOR_LABEL = "nginx-practice-operator"
        const val NGINX_VERSION = "1.23"

        @JvmStatic
        fun main(args: Array<String>) {
            log.info("Practice Operator (nginx) starting...")

            val config = ConfigBuilder().withNamespace("operator-practice").build()
            val kubernetesClient = DefaultKubernetesClient(config)
            val operator = Operator(kubernetesClient) { op ->
                op.withMetrics(MicrometerMetrics(LoggingMeterRegistry()))
            }

            val nginxReconciler = NginxReconciler()
            operator.register(nginxReconciler)

            operator.installShutdownHook()
            operator.start()

            FtBasic(TkFork(FkRegex("/health", "ALL GOOD!")), 8080).start(Exit.NEVER)
        }
    }
}
