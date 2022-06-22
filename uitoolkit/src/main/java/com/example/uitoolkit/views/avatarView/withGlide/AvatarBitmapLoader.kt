package com.example.uitoolkit.views.avatarView.withGlide

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.uitoolkit.views.avatarView.AvatarBitmapCombiner
import com.example.uitoolkit.views.avatarView.internal.InternalAvatarViewApi
import com.example.uitoolkit.views.avatarView.withGlide.AvatarResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext

/**
 * A Bitmap loader to emit loaded avatar bitmaps.
 */
@PublishedApi
internal object AvatarBitmapLoader {

    /**
     * Load Bitmaps and emit them on callback flow.
     *
     * @param requestManager A class for managing and starting requests for Glide.
     * @param data A list of model to load images.
     * @param errorPlaceholder An error placeholder that should be shown when request failed.
     *
     * @return A flow of [AvatarResult].
     */
    @OptIn(InternalAvatarViewApi::class)
    @PublishedApi
    internal fun loadBitmaps(
        requestManager: RequestManager,
        data: List<Any?>,
        errorPlaceholder: Drawable? = null
    ): Flow<AvatarResult> = callbackFlow {

        var avatarViewTarget: CustomTarget<Bitmap>? = null

        withContext(Dispatchers.IO) {
            data.forEach { endPoint ->
                avatarViewTarget = object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        trySend(AvatarResult.Success(resource))
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)

                        val error = errorPlaceholder ?: errorDrawable ?: let {
                            trySend(AvatarResult.Failure(null))
                            return
                        }

                        AvatarBitmapCombiner.drawableToBitmap(error, error.intrinsicWidth)?.let {
                            trySend(AvatarResult.Success(it))
                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) = Unit
                }.also { target ->
                    requestManager
                        .asBitmap()
                        .load(endPoint)
                        .into(target)
                }
            }
        }

        awaitClose {
            requestManager.clear(avatarViewTarget)
        }
    }
}
