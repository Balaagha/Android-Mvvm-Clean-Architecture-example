//package com.example.uitoolkit.cell.defaultcell
//
//import abb.corporate.ui_toolkit.R
//import com.example.uitoolkit.cell.defaultcell.models.DefaultCellModel
//import abb.corporate.ui_toolkit.utils.asDp
//import android.content.Context
//import android.graphics.Typeface
//import android.util.AttributeSet
//import android.view.LayoutInflater
//import android.view.View
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.annotation.DrawableRes
//import androidx.annotation.FontRes
//import androidx.constraintlayout.widget.ConstraintLayout
//import androidx.core.content.ContextCompat
//import androidx.core.content.res.ResourcesCompat
//
//class DefaultCell@JvmOverloads constructor(
//    context: Context,
//    attrs: AttributeSet? = null,
//    defStyleAttr: Int = 0
//) : ConstraintLayout(context, attrs, defStyleAttr) {
//
//    @FontRes
//    private var _bottomTextTypeface: Int? = null
//
//    private var defaultCellModel = DefaultCellModel()
//    private var topText: TextView
//    private var bottomText: TextView
//    private var mainLayout: ConstraintLayout
//    private var image: ImageView
//    private var bottomLine: View
//    private var height = 70F
//    private var lp: LayoutParams
//
//
//    init {
//        val root = LayoutInflater.from(context).inflate(R.layout.default_cell, this, true)
//        topText = root.findViewById(R.id.topText)
//        bottomText = root.findViewById(R.id.bottomText)
//        mainLayout = root.findViewById(R.id.mainLayout)
//        image = root.findViewById(R.id.image)
//        bottomLine = root.findViewById(R.id.bottomLine)
//        lp = mainLayout.layoutParams as LayoutParams
//        loadAttr(attrs, defStyleAttr)
//    }
//
//    private fun loadAttr(attrs: AttributeSet?, defStyleAttr: Int) {
//        val arr = context.obtainStyledAttributes(
//            attrs,
//            R.styleable.DefaultCell,
//            defStyleAttr,
//            0
//        ).apply {
//            defaultCellModel.topText =
//                getString(R.styleable.DefaultCell_default_cell_top_text)
//            defaultCellModel.bottomText =
//                getString(R.styleable.DefaultCell_default_cell_bottom_text)
//
//            defaultCellModel.imageVisibility =
//                getBoolean(
//                    R.styleable.DefaultCell_default_cell_image_visibility,
//                    false
//                )
//
//            defaultCellModel.topTextVisibility =
//                getBoolean(R.styleable.DefaultCell_default_cell_top_text_visibility, false)
//
//
//            defaultCellModel.bottomLineVisibility =
//                getBoolean(R.styleable.DefaultCell_default_cell_bottom_line_visibility, false)
//
//            defaultCellModel.bottomTextTypeFace = getResourceId(R.styleable.DefaultCell_default_cell_bottom_text_typeface, R.font.inter_regular)
//
//        }
//        arr.recycle()
//        refreshViewState()
//
//    }
//
//    private fun refreshViewState() {
//        defaultCellModel.topText?.let { topText.text = it }
//        defaultCellModel.bottomText?.let { bottomText.text = it }
//        defaultCellModel.bottomTextTypeFace?.let { setBottomTextTypeFace(it) }
//        if(defaultCellModel.imageVisibility) {
//            image.visibility = View.VISIBLE
//        }else {
//            image.visibility = View.GONE
//        }
//        if (defaultCellModel.bottomLineVisibility) {
//            bottomLine.visibility = View.VISIBLE
//        } else {
//            bottomLine.visibility = View.GONE
//        }
//        if (defaultCellModel.topTextVisibility) {
//            topText.visibility = View.VISIBLE
//        } else {
//            topText.visibility = View.GONE
//        }
//
//    }
//
//    fun setViewData(data: DefaultCellModel) {
//        defaultCellModel = data
//        refreshViewState()
//    }
//
//    fun changeViewBackground(resId: Int) {
//        mainLayout.setBackgroundResource(resId)
//    }
//
//    fun setBottomTextTypeFace(font: Int?) {
//        bottomText.apply {
//            typeface = getTypeface(font)
//        }
//    }
//
//    private fun getTypeface(@FontRes font: Int?): Typeface? {
//        if (font == null || font == 0) return null
//
//        return ResourcesCompat.getFont(context, font)
//    }
//
//    fun setHeight(height: Float) {
//        lp.height = height.asDp
//        mainLayout.layoutParams = lp
//    }
//
//    fun setIcon(@DrawableRes resId: Int) {
//        image.setImageDrawable(ContextCompat.getDrawable(context,resId))
//    }
//
//}