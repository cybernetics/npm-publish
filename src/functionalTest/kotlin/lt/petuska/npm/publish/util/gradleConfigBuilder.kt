package lt.petuska.npm.publish.util

import lt.petuska.npm.publish.dsl.NpmPublishExtension
import lt.petuska.npm.publish.kotlinVersion
import lt.petuska.npm.publish.pluginGroup
import lt.petuska.npm.publish.pluginName
import lt.petuska.npm.publish.pluginVersion
import org.gradle.internal.impldep.org.junit.rules.TemporaryFolder
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import java.io.File

private fun KotlinBuilder.buildProject(kotlinPlugin: String? = null, config: KotlinBuilder.() -> Unit = {}) = apply {
  "plugins" {
    "id"(pluginName.raw)
    kotlinPlugin?.let {
      "kotlin"(it.raw) infix "version"(kotlinVersion.raw) infix "apply" infix true
    }
  }

  "version" to pluginVersion.raw
  "group" to pluginGroup.raw chain "toUpperCase()"

  "repositories" {
    "jcenter"()
    "mavenCentral"()
  }
  config()
}

fun KotlinBuilder.kotlinMpp(config: KotlinBuilder.() -> Unit = {}) = buildProject("multiplatform") {
  "kotlin" {
    config()
  }
}

fun KotlinBuilder.kotlinJs(config: KotlinBuilder.() -> Unit = {}) = buildProject("js") {
  "kotlin" {
    config()
  }
}

fun KotlinBuilder.noKotlin(config: KotlinBuilder.() -> Unit = {}) = buildProject {
  config()
}

fun KotlinBuilder.npmPublishing(config: KotlinBuilder.() -> Unit = {}) = apply {
  NpmPublishExtension.EXTENSION_NAME {
    config()
  }
}

fun KotlinBuilder.npmRepository(name: String = defaultRepoName, config: KotlinBuilder.() -> Unit = {}) = apply {
  "npmPublishing" {
    "repositories" {
      "repository(\"$name\")" {
        // +"registry = uri(\"https://registry.$name.org\")"
        "registry" to "uri"("https://registry.$name.org".raw)
        "authToken" to "asdhkjsdfjvhnsdrishdl".raw
        "otp" to "gfahsdjglknamsdkpjnmasdl".raw
        config()
      }
    }
  }
}

fun gradleExec(buildFile: KotlinBuilder.() -> Unit, vararg args: String): BuildResult = File("build/functionalTest").let {
  it.mkdirs()
  TemporaryFolder(it)
}.run {
  create()
  root.run {
    deleteRecursively()
    mkdirs()
    resolve("settings.gradle.kts").writeText(
      """
    rootProject.name = "test-project"
    pluginManagement {
      repositories {
        mavenCentral()
        jcenter()
        gradlePluginPortal()
      }
    }
      """.trimIndent()
    )
    resolve("build.gradle.kts").apply {
      println(absolutePath)
      writeText(KotlinBuilder(buildFile).toString())
    }

    try {
      GradleRunner.create()
        .forwardOutput()
        .withPluginClasspath()
        .withProjectDir(this)
        .withArguments(*args)
        .build()
    } catch (e: Exception) {
      // delete()
      throw e
    }
  } // .also { delete() }
}
