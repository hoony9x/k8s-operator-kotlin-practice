package dev.hoony9x.practice

data class NginxSchemaSpec(
    var port: Int = 8080,
    var replicas: Int = 1,
)