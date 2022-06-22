@file:JvmName("AvatarViewExtension")
@file:JvmMultifileClass

package com.example.uitoolkit.views.avatarView.withCoil

import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import coil.transform.Transformation
import com.example.uitoolkit.views.avatarView.AvatarShape
import com.example.uitoolkit.views.avatarView.AvatarView
import com.example.uitoolkit.views.avatarView.internal.InternalAvatarViewApi

/**
 * Loads an image request [data] to the [AvatarView].
 *
 * @param data An image data to be loaded.
 * @param onStart A lambda function will be executed when start requesting.
 * @param onComplete A lambda function will be executed when loading finished.
 * @param onSuccess A lambda function will be executed when loading succeeds.
 * @param onError A lambda function will be executed when loading failed.
 * @param builder A receiver to be applied with the [ImageRequest.Builder].
 */
@OptIn(InternalAvatarViewApi::class)
@JvmSynthetic
fun AvatarView.loadImage(
    data: Any?,
    onStart: () -> Unit = {},
    onComplete: () -> Unit = {},
    onSuccess: (request: ImageRequest, result: SuccessResult) -> Unit = { _, _ -> },
    onError: (request: ImageRequest, result: ErrorResult) -> Unit = { _, _ -> },
    builder: ImageRequest.Builder.() -> Unit = {}
) {
    loadPlaceholder()
    AvatarImageLoaderInternal.load(
        target = this,
        data = Avatar(
            data = listOf(data),
            maxSectionSize = maxSectionSize,
            avatarBorderWidth = avatarBorderWidth,
            errorPlaceholder = errorPlaceholder,
            onSuccess = onSuccess,
            onError = onError
        ),
        transformation = transformation,
        onStart = onStart,
        onComplete = onComplete,
        builder = builder,
    )
}

/**
 * Loads a list of image request [data] to the [AvatarView].
 * Up to 4 images will be combined and loaded.
 *
 * @param data A list of image data to be loaded.
 * @param onStart A lambda function will be executed when start requesting.
 * @param onComplete A lambda function will be executed when finish loading.
 * @param onSuccess A lambda function will be executed when loading succeeds.
 * @param onError A lambda function will be executed when loading failed.
 * @param builder A receiver to be applied with the [ImageRequest.Builder].
 */
@OptIn(InternalAvatarViewApi::class)
@JvmSynthetic
fun AvatarView.loadImage(
    data: List<Any?>,
    onStart: () -> Unit = {},
    onComplete: () -> Unit = {},
    onSuccess: (request: ImageRequest, result: SuccessResult) -> Unit = { _, _ -> },
    onError: (request: ImageRequest, result: ErrorResult) -> Unit = { _, _ -> },
    builder: ImageRequest.Builder.() -> Unit = {}
) {
    loadPlaceholder()
    AvatarImageLoaderInternal.load(
        target = this,
        data = Avatar(
            data = data,
            maxSectionSize = maxSectionSize,
            avatarBorderWidth = avatarBorderWidth,
            errorPlaceholder = errorPlaceholder,
            onSuccess = onSuccess,
            onError = onError
        ),
        transformation = transformation,
        onStart = onStart,
        onComplete = onComplete,
        builder = builder,
    )
}

/**
 * Loads a vararg of image request [data] to the [AvatarView].
 * Up to 4 images will be combined and loaded.
 *
 * @param data A vararg of image data to be loaded.
 * @param onStart A lambda function will be executed when start requesting.
 * @param onComplete A lambda function will be executed when finish loading.
 * @param onSuccess A lambda function will be executed when loading succeeds.
 * @param onError A lambda function will be executed when loading failed.
 * @param builder A receiver to be applied with the [ImageRequest.Builder].
 */
@JvmSynthetic
fun AvatarView.loadImage(
    vararg data: Any?,
    onStart: () -> Unit = {},
    onComplete: () -> Unit = {},
    onSuccess: (request: ImageRequest, result: SuccessResult) -> Unit = { _, _ -> },
    onError: (request: ImageRequest, result: ErrorResult) -> Unit = { _, _ -> },
    builder: ImageRequest.Builder.() -> Unit = {}
) {
    loadPlaceholder()
    loadImage(
        data = data.toList(),
        onStart = onStart,
        onComplete = onComplete,
        onSuccess = onSuccess,
        onError = onError,
        builder = builder
    )
}

/** Loads a placeholder with the [AvatarView.placeholder] property. */
@OptIn(InternalAvatarViewApi::class)
@PublishedApi
internal fun AvatarView.loadPlaceholder() {
    placeholder?.let {
        AvatarImageLoaderInternal.load(
            target = this,
            data = it,
            transformation = transformation,
            onStart = {},
            onComplete = {},
            builder = {}
        )
    }
}

/** Returns a [Transformation] from a [AvatarView]. */
@PublishedApi
internal val AvatarView.transformation: Transformation
    @JvmSynthetic inline get() = when (avatarShape) {
        AvatarShape.CIRCLE -> CircleCropTransformation()
        AvatarShape.ROUNDED_RECT -> RoundedCornersTransformation(avatarBorderRadius)
    }
