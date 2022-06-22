package com.example.uitoolkit.views.avatarView.withGlide

import android.graphics.Bitmap
import android.graphics.drawable.Drawable

/**
 * A result class that encapsulates outcome with a Bitmap from the target request by a [CustomTarget].
 */
@PublishedApi
internal sealed class AvatarResult {

    /** A result class that encapsulates the successful result using Bitmap data. */
    class Success(val bitmap: Bitmap) : AvatarResult()

    /** A result class that encapsulates the failure result using Drawable data. */
    class Failure(val placeholder: Drawable?) : AvatarResult()
}
