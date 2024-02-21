plugins {
    id("maven-publish")
    id("java-library")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "dev.partyhat"
version = "1.4.0"

repositories {
    maven("https://papermc.io/repo/repository/maven-public/")
    mavenCentral()
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
}

publishing {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/partyhatgg/smartinvs")
            credentials {
                username = project.findProperty("gpr.user") as? String ?: System.getenv("REPO_USERNAME")
                password = project.findProperty("gpr.key") as? String ?: System.getenv("REPO_TOKEN")
            }
        }
    }

    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}
