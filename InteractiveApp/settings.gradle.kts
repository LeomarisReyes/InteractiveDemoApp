pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "InteractiveApp"
include(":app")
include(":core:designsystem")
include(":feature:passwordgenerator")
include(":feature:capitalizergenerator")
include(":data")
include(":core:models")
include(":feature:presidents")
include(":core:models")
include(":feature:menu")
include(":core:designsystem")
