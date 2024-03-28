/*
 *  Copyright (c) 2000-2023 TeamDev Ltd. All rights reserved.
 *  TeamDev PROPRIETARY and CONFIDENTIAL.
 *  Use is subject to license terms.
 */

package gradle.task

import com.teamdev.jxbrowser.license.internal.LicenseProvider
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.TestDescriptor
import org.gradle.api.tasks.testing.TestResult
import org.gradle.kotlin.dsl.KotlinClosure2

/**
 * Specifies a JVM option with a JxBrowser license.
 */
fun Test.attachJxBrowserLicense() {
    val licenseKey = LicenseProvider.key
    val licenseArg = "-Djxbrowser.license.key=$licenseKey"
    jvmArgs(licenseArg)
}

/**
 * Configures this [Test] task to log a summary report of test results
 * at the end of the task execution.
 *
 * Below is an example of the printed summary:
 *
 * ```
 * > Task :my-lib:test
 *
 *         Test summary:
 *         >> 12 tests
 *         >> 12 succeeded
 *         >> 0 failed
 *         >> 0 skipped
 * ```
 *
 * This logging protects from an accidental omission of `test` task execution.
 * Now, it is clearly seen if (and how many) tests were executed.
 */
internal fun Test.logSummaryReport() = afterSuite(

        // Groovy interop is used because `afterSuite()` has no equivalent in Kotlin DSL.
        // See issue: https://github.com/gradle/gradle/issues/5431.
        KotlinClosure2<TestDescriptor, TestResult, Unit>({ descriptor, result ->

            // If the descriptor has no parent, then it is the root test suite.
            // The root suite has test summary info.
            if (descriptor.parent == null) {
                logger.lifecycle(result.summary())
            }
        })
)

private fun TestResult.summary(): String =
        """
        Test summary:
        >> $testCount tests
        >> $successfulTestCount succeeded
        >> $failedTestCount failed
        >> $skippedTestCount skipped
        """
