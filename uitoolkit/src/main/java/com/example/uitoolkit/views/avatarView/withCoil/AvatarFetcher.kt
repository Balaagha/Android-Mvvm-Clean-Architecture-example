package com.example.uitoolkit.views.avatarView.withCoil

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.decode.DataSource
import coil.fetch.DrawableResult
import coil.fetch.Fetcher
import coil.request.Options
import coil.size.pxOrElse
import com.example.uitoolkit.views.avatarView.withCoil.Avatar
import com.example.uitoolkit.views.avatarView.withCoil.AvatarCoil

/**
 * An image request fetcher of [Avatar] data type.
 *
 * This fetcher will create a Bitmap using the [Avatar] data in the coroutines scope.
 */
internal class AvatarFetcher constructor(
    private val context: Context
) : Fetcher.Factory<Avatar> {

    override fun create(data: Avatar, options: Options, imageLoader: ImageLoader): Fetcher {
        val targetSize = options.size.width.pxOrElse { 0 }
        val resources = options.context.resources
        return Fetcher {
            DrawableResult(
                BitmapDrawable(
                    resources,
                    AvatarCoil.getAvatarBitmapFactory(context).createAvatarBitmaps(
                        data,
                        targetSize - data.avatarBorderWidth * 2
                    )
                ),
                false,
                DataSource.MEMORY
            )
        }
    }
}
