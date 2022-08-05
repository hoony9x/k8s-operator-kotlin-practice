package dev.hoony9x.practice

data class NginxSpec(
    var port: Int = 8080,
    var exposePort: Int = 80,
    var replicas: Int = 1,
    var version: String = "latest",
)
