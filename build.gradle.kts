plugins {
    id("maven-publish")
    id("java-library")
}

group = "live.mcparty"
version = "1.3.0"

repositories {
    maven("https://papermc.io/repo/repository/maven-public/")

    maven {
        name = "MCPartyPackages"
        url = uri("https://repo.mcparty.live/packages/")
        credentials {
            username = project.findProperty("mcp.user") as? String ?: System.getenv("REPO_USERNAME")
            password = project.findProperty("mcp.key") as? String ?: System.getenv("REPO_TOKEN")
        }
    }
}

dependencies {
    api("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
}

publishing {
    repositories {
        maven {
            url = uri("https://repo.mcparty.live/packages/")
            credentials {
                username = project.findProperty("mcp.user") as? String ?: System.getenv("REPO_USERNAME")
                password = project.findProperty("mcp.key") as? String ?: System.getenv("REPO_TOKEN")
            }
        }
    }

    publications {
        create<MavenPublication>("mcp") {
            from(components["java"])
        }
    }
}