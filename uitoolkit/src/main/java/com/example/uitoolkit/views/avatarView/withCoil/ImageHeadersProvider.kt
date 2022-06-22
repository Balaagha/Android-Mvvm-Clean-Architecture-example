package com.example.uitoolkit.views.avatarView.withCoil

/** Provides HTTP headers for image loading requests. */
interface ImageHeadersProvider {
    fun getImageRequestHeaders(): Map<String, String>
}

/** Provides a default HTTP headers for image loading requests. */
internal object DefaultImageHeadersProvider : ImageHeadersProvider {
    override fun getImageRequestHeaders(): Map<String, String> = emptyMap()
}
