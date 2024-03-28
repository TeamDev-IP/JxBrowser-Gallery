/*
 *  Copyright (c) 2000-2023 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

package dependency

import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.jvm.toolchain.JvmVendorSpec

/**
 * Java language.
 *
 * When changing the version or vendor, please, also update values in
 * the following locations:
 *
 * 1. Build files of `buildSrc` and `jxbrowser-license` modules.
 * 2. GitHub's workflows for Ubuntu and Windows.
 * 3. `Prerequisites` section of README.
 *
 * Please make sure JDK specified in `$JAVA_HOME` corresponds to specification
 * of this dependency object. Otherwise, Gradle and Compose plugin will use
 * the wrong JDK, most likely failing the build and/or app's packaging.
 */
object Java {

    /**
     * Language version.
     */
    val version: JavaLanguageVersion = JavaLanguageVersion.of(17)

    /**
     * A preferred JVM vendor.
     */
    val vendor: JvmVendorSpec = JvmVendorSpec.AZUL
}
