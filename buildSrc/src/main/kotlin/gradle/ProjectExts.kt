/*
 *  Copyright (c) 2000-2024 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

package gradle

import org.gradle.api.Project

/**
 * If the version has been passed to CLI with `-Pversion=value`,
 * returns the passed value.
 *
 * Otherwise, returns `null`.
 */
fun Project.versionFromCli(): Any? = properties["version"]
    .takeIf { it != "unspecified" }
