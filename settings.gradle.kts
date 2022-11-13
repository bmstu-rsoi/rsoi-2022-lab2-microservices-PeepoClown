rootProject.name = "rsoi2-microservices"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

include("common-api")

include("cars-api")
include("cars-service")

include("gateway-service")
