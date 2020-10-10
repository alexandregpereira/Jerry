package br.alexandregpereira.jerry.app.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import br.alexandregpereira.jerry.app.R

class SecondaryButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        setMaterialShapeDrawable()
        setTextColor(ContextCompat.getColor(context, R.color.textColor))
        textAlignment = TEXT_ALIGNMENT_CENTER
    }
}