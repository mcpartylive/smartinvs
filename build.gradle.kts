plugins {
    id("java-library")
    id("maven-publish")
    id("io.freefair.lombok") version "6.2.0" apply false
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "io.freefair.lombok")
    apply(plugin = "com.github.johnrengelman.shadow")

    group = "live.mcparty"
    version = (project.findProperty("pubVersion") as? String) ?: "2.0.0"
    base.archivesName.set("${rootProject.name}-${project.name}")

    configurations["api"].isCanBeResolved = true
    configurations["api"].extendsFrom(configurations["shadow"])
    tasks["build"].dependsOn(tasks["shadowJar"])

    repositories {
        mavenLocal()
        mavenCentral()

        maven("https://jitpack.io")
        maven("https://repo.aikar.co/content/groups/aikar")
        maven("https://repo.codemc.io/repository/maven-public/")
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

    java {
        withJavadocJar()
        withSourcesJar()
    }

    tasks {
        withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
            configurations = listOf(project.configurations["shadow"], project.configurations["api"])
        }

        withType<JavaCompile> {
            options.encoding = "UTF-8"
        }
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
                artifactId = base.archivesName.get()
                from(components["java"])
            }
        }
    }
}