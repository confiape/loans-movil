package org.confiape.loan.core.repositories

import org.confiape.loan.apis.TagApi
import org.confiape.loan.models.TagDto
import javax.inject.Inject

class TagRepository @Inject constructor(
    private val tagApi: TagApi,
) {
    private var tagCache: List<TagDto>? = null

    suspend fun getTags(): List<TagDto> {
        return if (tagCache == null) {

            updateTags()
            tagCache!!
        } else {
            tagCache!!
        }
    }

    suspend fun updateTags() {
        tagCache = tagApi.apiTagGet(100, 1).body()!!.result
    }

}