package br.alexandregpereira.jerry.app.widgets

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import br.alexandregpereira.jerry.app.R
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun View.setRippleDrawable(contentDrawable: Drawable): View {
    val colorState = ColorStateList(
        arrayOf(intArrayOf()), intArrayOf(ContextCompat.getColor(context, R.color.rippleColor))
    )
    return this.apply {
        ViewCompat.setBackground(
            this,
            RippleDrawable(
                colorState,
                contentDrawable,
                null
            )
        )
    }
}

fun View.setMaterialShapeDrawable(): View {
    val cornerSize = resources.getDimension(R.dimen.corner_size)
    val padding = resources.getDimensionPixelOffset(R.dimen.text_padding)

    layoutParams = ViewGroup.MarginLayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(padding / 2, padding / 2, padding / 2, padding / 2)
    }
    setPadding(padding, padding, padding, padding)

    val shapeAppearanceModel = ShapeAppearanceModel.builder()
        .setAllCornerSizes(cornerSize).build()

    val materialShapeDrawable = MaterialShapeDrawable(shapeAppearanceModel).apply {
        this.fillColor = ColorStateList.valueOf(Color.WHITE)
        this.elevation = resources.getDimension(R.dimen.elevation)
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        elevation = resources.getDimension(R.dimen.elevation)
    }

    return this.apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setRippleDrawable(materialShapeDrawable)
        } else {
            ViewCompat.setBackground(this, materialShapeDrawable)
        }
    }
}