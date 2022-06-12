package com.example.androidmvvmcleanarchitectureexample.toolkitexamples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.androidmvvmcleanarchitectureexample.R
import com.example.uitoolkit.popup.GenericPopUpHelper
import kotlinx.android.synthetic.main.activity_alerts.*

class AlertsImplActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alerts)

        setUpOnClickListener()

    }

    private fun setUpOnClickListener() {
        initSimpleGenericAlert()
    }

    private fun initSimpleGenericAlert() {
        gpSimple.setOnClickListener {
            GenericPopUpHelper.Builder(supportFragmentManager)
                .setStyle(GenericPopUpHelper.Style.STYLE_2_VERTICAL_BUTTONS)
                .setImage(R.drawable.ic_error_icon)
                .setImageLayoutParams(
                    imageWith = 50,
                    imageHeight = 50
                )
                .setTitle("Hello!")
                .setTitleColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .setContent("Hello to my base MVVM project from my pretty pop up helper")
                .setContentColor(ContextCompat.getColor(this, R.color.gray))
                .setPositiveButtonBackground(R.drawable.btn_accent)
                .setPositiveButtonTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .setPositiveButtonTextAppearance(R.style.MBoldWhite)
                .setPositiveButton("Okay") {
                    it.dismiss()
                }
                .setNegativeButtonBackground(R.drawable.btn_gray)
                .setNegativeButtonTextColor(ContextCompat.getColor(this, R.color.white))
                .setNegativeButton("Cancel", null)
                .create()
        }
    }


}