/*
 *  Copyright 2026, TeamDev
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package com.teamdev.jxbrowser.gallery.pdf

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * An enumeration of datasets available to the application.
 */
enum class Dataset(resourceName: String) {
    DIETARY_COMPOSITION_BY_REGION("/dietary-composition-by-region.info.json");

    /**
     * The parsed dataset info.
     */
    private val info: DatasetInfo

    /**
     * The dataset content.
     */
    private val data: String

    /**
     * Initializes a new enum instance for the dataset denoted by the resource
     * with the passed name.
     */
    init {
        val infoJson = resourceAsText(resourceName)!!
        this.info = Json.decodeFromString<DatasetInfo>(infoJson)
        this.data = resourceAsText(info.dataRef)!!
    }

    /**
     * Returns the dataset info.
     */
    fun info(): DatasetInfo {
        return info
    }

    /**
     * Returns the dataset content.
     */
    fun data(): String {
        return data
    }
}

@Serializable
data class DatasetInfo(
    val id: String,
    val title: String,
    val description: String,
    val rowCount: Int,
    val source: String,
    val dataRef: String,
)
