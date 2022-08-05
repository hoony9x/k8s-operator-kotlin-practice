package dev.hoony9x.practice

data class NginxStatus(
    var readyReplicas: Int = 0,
    var status: String = "UNKNOWN"
)
