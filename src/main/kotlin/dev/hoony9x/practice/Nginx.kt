package dev.hoony9x.practice

import io.fabric8.kubernetes.api.model.Namespaced
import io.fabric8.kubernetes.client.CustomResource
import io.fabric8.kubernetes.model.annotation.Group
import io.fabric8.kubernetes.model.annotation.Version

@Group("dev.hoony9x.practice")
@Version("v1")
class Nginx : CustomResource<NginxSpec, NginxStatus>(), Namespaced