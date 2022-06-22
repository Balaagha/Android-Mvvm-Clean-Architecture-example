package com.example.uitoolkit.views.avatarView.withCoil

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.annotation.DrawableRes
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import okhttp3.HttpUrl
import java.io.File

/**
 * A data transfer model for transferring image data to [AvatarFetcher].
 * This model will be fetched by [AvatarFetcher] when we request image loading by using this type to Coil.
 *
 * The default supported data types are:
 * - [String] (mapped to a [Uri])
 * - [Uri] ("android.resource", "content", "file", "http", and "https" schemes only)
 * - [HttpUrl]
 * - [File]
 * - [DrawableRes]
 * - [Drawable]
 * - [Bitmap]
 *
 * @param data A list of image data.
 * @param maxSectionSize The maximum section size of the avatar when loading multiple images.
 * @param avatarBorderWidth The border width of AvatarView.
 * @param errorPlaceholder An error placeholder that should be shown when request failed.
 */
data class Avatar(

    /** A list of data to be requested. */
    val data: List<Any?>,

    /** The maximum size of the sections. */
    val maxSectionSize: Int,

    /** The border width of AvatarView. */
    val avatarBorderWidth: Int,

    /** An error placeholder that should be shown when request failed. */
    val errorPlaceholder: Drawable?,

    /** A lambda function will be executed when loading succeeds. */
    val onSuccess: (request: ImageRequest, result: SuccessResult) -> Unit,

    /** A lambda function will be executed when loading failed. */
    val onError: (request: ImageRequest, result: ErrorResult) -> Unit,
) {
    private val bagOfTags: MutableMap<String, Any> = mutableMapOf()

    /**
     * Sets a tag associated with this avatar and a key.
     * If the given [newValue] was already set for the given key, this calls do nothing,
     * the given [newValue] would be ignored.
     *
     * @param key A new key to set a tag associated with this avatar.
     * @param newValue A new value to be set on the bag.
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : Any> setTagIfAbsent(key: String, newValue: T) {
        synchronized(bagOfTags) {
            val previous = bagOfTags[key] as? T
            if (previous == null) {
                bagOfTags[key] = newValue
            }
        }
    }

    /**
     * Returns the tag associated with this avatar using the given [key].
     *
     * @param key A new key to get a tag associated with this avatar.
     *
     * @return A tag in the bag.
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getTag(key: String): T? {
        synchronized(bagOfTags) {
            return bagOfTags[key] as? T
        }
    }
}
