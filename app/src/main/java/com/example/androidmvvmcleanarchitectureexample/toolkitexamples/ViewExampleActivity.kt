package com.example.androidmvvmcleanarchitectureexample.toolkitexamples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.transform.RoundedCornersTransformation
import com.example.androidmvvmcleanarchitectureexample.R
import com.example.uitoolkit.views.avatarView.withCoil.loadImage
//import com.example.uitoolkit.views.avatarView.withGlide.loadImage
import kotlinx.android.synthetic.main.activity_view_example.*

class ViewExampleActivity : AppCompatActivity() {

    val urlsForAvatarView: List<String>
        get() = listOf(
            "https://swiftype-ss.imgix.net/https%3A%2F%2Fcdn.petcarerx.com%2FLPPE%2Fimages%2Farticlethumbs%2FFluffy-Cats-Small.jpg?ixlib=rb-1.1.0&h=320&fit=clip&dpr=2.0&s=c81a75f749ea4ed736b7607100cb52cc.png",
            "https://images.ctfassets.net/cnu0m8re1exe/1GxSYi0mQSp9xJ5svaWkVO/d151a93af61918c234c3049e0d6393e1/93347270_cat-1151519_1280.jpg?fm=jpg&fl=progressive&w=660&h=433&fit=fill",
            "https://img.webmd.com/dtmcms/live/webmd/consumer_assets/site_images/article_thumbnails/other/cat_relaxing_on_patio_other/1800x1200_cat_relaxing_on_patio_other.jpg",
            "https://post.healthline.com/wp-content/uploads/2020/08/cat-thumb2-732x415.jpg",
        ).shuffled()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_example)
        val url = urlsForAvatarView.take(1)
        avatarView1.loadImage(url)
        avatarView6.loadImage(urlsForAvatarView.take(2)){
            crossfade(true)
            crossfade(400)
            lifecycle(this@ViewExampleActivity)
            transformations(
                RoundedCornersTransformation(36f)
            )
        }
    }
}