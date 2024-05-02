/*
 *  Copyright 2024, TeamDev. All rights reserved.
 *
 *  Redistribution and use in source and/or binary forms, with or without
 *  modification, must retain the above copyright notice and the following
 *  disclaimer.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *  LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 *  A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 *  OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 *  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 *  OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.teamdev.jxbrowser.gallery.pdf

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

enum class Dataset(resourceName: String) {
    DIETARY_COMPOSITION_BY_COUNTRY("dietary-composition-by-country.info.json");

    private val info: DatasetInfo
    private val data: String

    init {
        val infoJson = resourceAsText(resourceName)!!
        this.info = Json.decodeFromString<DatasetInfo>(infoJson)
        this.data = resourceAsText(info.dataRef)!!
    }

    fun info(): DatasetInfo {
        return info
    }

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
