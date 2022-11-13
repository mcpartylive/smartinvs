plugins {
    id("maven-publish")
    id("java-library")
}

group = "live.mcparty"
version = "2.0.0"

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
    testImplementation("junit:junit:4.13.1")
    testImplementation("org.mockito:mockito-core:2.19.0")
    testImplementation("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")
    compileOnly("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")
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