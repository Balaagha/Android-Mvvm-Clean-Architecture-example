package com.example.uitoolkit.views.avatarView.withCoil

import android.content.Context
import android.os.Build
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

/**
 * An [ImageLoader] factory to provide an instance of the [ImageLoader].
 *
 * AvatarImageLoaderFactory creates a default [ImageLoader] that has
 * caching strategy with OkHttp, image decoder (supports GIFs), and [AvatarFetcher].
 */
public class AvatarImageLoaderFactory(
    private val context: Context,
    private val builder: ImageLoader.Builder.() -> Unit = {}
) : ImageLoaderFactory {

    /** Creates a new [ImageLoader] to load [Avatar] image data. */
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(context)
            .allowHardware(false)
            .crossfade(true)
            .components {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
                add(AvatarFetcher(context))
            }
            .apply(builder)
            .build()
    }
}
