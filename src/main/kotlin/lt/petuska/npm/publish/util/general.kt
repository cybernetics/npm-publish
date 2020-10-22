package lt.petuska.npm.publish.util

import lt.petuska.npm.publish.dsl.NpmPublishExtension
import org.gradle.api.Project

internal val Project.isDryRun: Boolean
  get() = project.hasProperty(NpmPublishExtension.DRY_RUN_PROP) && (project.properties[NpmPublishExtension.DRY_RUN_PROP] as String?)?.toBoolean() != false
