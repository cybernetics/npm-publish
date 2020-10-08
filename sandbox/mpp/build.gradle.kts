plugins {
  id("lt.petuska.npm.publish")
  kotlin("multiplatform")
  `maven-publish`
}

repositories {
  jcenter()
  mavenCentral()
}

kotlin {
  js { browser() }

  sourceSets {
    val jsMain by getting {
      dependencies {
        implementation(devNpm("axios", "*"))
        api(npm("snabbdom", "*"))
      }
    }
  }
}

npmPublishing {
  organization = group as String
  repositories {
    repository("GitLab") {
      registry = uri("https://gitlab.com/api/v4/projects/${System.getenv("CI_PROJECT_ID")?.trim()}/packages/npm")
      authToken = System.getenv("PRIVATE_TOKEN")?.trim() ?: ""
    }
  }
}
