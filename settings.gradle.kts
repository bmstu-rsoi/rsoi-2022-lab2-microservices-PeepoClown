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

include("payment-api")
include("payment-service")

include("rental-api")
include("rental-service")

include("gateway-service")
