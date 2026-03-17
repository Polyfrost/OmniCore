plugins {
    id("dev.deftu.gradle.multiversion-root")
}

preprocess {
    strictExtraMappings.set(true)

    "26.1-fabric"(26_01_00, "srg") {
        "1.21.11-fabric"(1_21_11, "srg") {
            "1.21.11-neoforge"(1_21_11, "srg") {
                "1.21.10-neoforge"(1_21_10, "srg") {
                    "1.21.10-fabric"(1_21_10, "srg") {
                        "1.21.8-neoforge"(
                            1_21_08,
                            "srg",
                            file("versions/mappings/1.21.9-neoforge+1.21.8-neoforge.txt")
                        ) {
                            "1.21.8-fabric"(1_21_08, "srg") {
                                "1.21.5-fabric"(
                                    1_21_05,
                                    "srg",
                                    file("versions/mappings/1.21.6-fabric+1.21.5-fabric.txt")
                                ) {
                                    "1.21.5-neoforge"(1_21_05, "srg") {
                                        "1.21.4-neoforge"(
                                            1_21_04,
                                            "srg",
                                            file("versions/mappings/1.21.5-neoforge+1.21.4-neoforge.txt")
                                        ) {
                                            "1.21.4-fabric"(1_21_04, "srg") {
                                                "1.21.1-fabric"(
                                                    1_21_01,
                                                    "srg",
                                                    file("versions/mappings/1.21.2-fabric+1.21.1-fabric.txt")
                                                ) {
                                                    "1.21.1-neoforge"(1_21_01, "srg")
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

val versions = listOf(
    "1.21.1-neoforge",
    "1.21.1-fabric",

    "1.21.4-neoforge",
    "1.21.4-fabric",

    "1.21.5-neoforge",
    "1.21.5-fabric",

    "1.21.8-neoforge",
    "1.21.8-fabric",

    "1.21.10-neoforge",
    "1.21.10-fabric",

    "1.21.11-neoforge",
    "1.21.11-fabric",

    "26.1-fabric",
)

listOf(
    "DeftuReleasesRepository",
    "DeftuSnapshotsRepository",
    "MavenLocalRepository"
).forEach { repository ->
    versions.forEach { version ->
        project(":$version").tasks.register("fullReleaseWith$repository") {
            group = "deftu"

            dependsOn(":$version:publishAllPublicationsTo$repository")
            dependsOn(":$version:publishModToAllPlatforms")
        }
    }

    project.tasks.register("fullReleaseWith$repository") {
        group = "deftu"

        dependsOn(versions.map { ":$it:fullReleaseWith$repository" })
    }
}
