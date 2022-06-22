package com.example.uitoolkit.views.avatarView.withCoil

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.example.uitoolkit.views.avatarView.withCoil.AvatarCoil.imageLoader
import com.example.uitoolkit.views.avatarView.withCoil.AvatarCoil.setImageLoader

/**
 * AvatarCoil provides a [ImageLoader], [AvatarBitmapFactory], and [ImageHeadersProvider] that can be
 * fully customized for loading avatar image:
 *
 * - [imageLoader] be used to load [Avatar] payload internally. You can customize with your
 * own [ImageLoaderFactory] or [AvatarImageLoaderFactory] by using [setImageLoader] function.
 *
 * - [AvatarBitmapFactory] will creates avatar bitmaps when [AvatarFetcher] fetches
 * the [Avatar] payload successfully. The loaded bitmaps will be operated by the factory and they will be
 * loaded as [BitmapDrawable] to the [io.getstream.avatarview.AvatarView].
 *
 * - [ImageHeadersProvider] be used to provide image header. If you're using your own CDN,
 * you can set the [AvatarCoil.imageHeadersProvider] to load image data with your own header.
 */
object AvatarCoil {

    private var imageLoader: ImageLoader? = null
    private var imageLoaderFactory: ImageLoaderFactory? = null

    /**
     * Sets a [ImageLoaderFactory] to provide your own [ImageLoader].
     *
     * @param factory An [ImageLoader] factory.
     */
    @Synchronized
    fun setImageLoader(factory: ImageLoaderFactory) {
        imageLoaderFactory = factory
        imageLoader = null
    }

    /** Returns an [imageLoader] or [newImageLoader]. */
    @PublishedApi
    internal fun imageLoader(context: Context): ImageLoader = imageLoader ?: newImageLoader(context)

    /** Returns an [imageLoader] or a new [imageLoader] from the [imageLoaderFactory]. */
    @Synchronized
    private fun newImageLoader(context: Context): ImageLoader {
        imageLoader?.let { return it }

        val imageLoaderFactory = imageLoaderFactory ?: newImageLoaderFactory(context)
        return imageLoaderFactory.newImageLoader().apply {
            imageLoader = this
        }
    }

    /** Creates a new default instance of the [ImageLoaderFactory]. */
    private fun newImageLoaderFactory(context: Context): ImageLoaderFactory {
        return AvatarImageLoaderFactory(context).apply {
            imageLoaderFactory = this
        }
    }

    /** Returns an [imageLoader] to load avatar images. */
    @PublishedApi
    internal inline val Context.avatarImageLoader: ImageLoader
        get() = imageLoader(this)

    /** An avatar bitmap factory to create custom avatar bitmaps. */
    @SuppressLint("StaticFieldLeak")
    private var avatarBitmapFactory: AvatarBitmapFactory? = null

    /** Returns an [AvatarBitmapFactory]. */
    fun getAvatarBitmapFactory(context: Context): AvatarBitmapFactory {
        return avatarBitmapFactory ?: synchronized(this) {
            avatarBitmapFactory ?: AvatarBitmapFactory(context.applicationContext).also {
                avatarBitmapFactory = it
            }
        }
    }

    /** Sets a custom [AvatarBitmapFactory]. */
    @Synchronized
    fun setAvatarBitmapFactory(avatarBitmapFactory: AvatarBitmapFactory?) {
        AvatarCoil.avatarBitmapFactory = avatarBitmapFactory
    }

    /** Provides a custom image header. */
    var imageHeadersProvider: ImageHeadersProvider = DefaultImageHeadersProvider
}
