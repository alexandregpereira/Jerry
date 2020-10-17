package br.alexandregpereira.jerry.app.widgets

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import br.alexandregpereira.jerry.dpToPx

class RoundTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        setRoundShape()
        setTextColor(Color.WHITE)
        gravity = Gravity.CENTER
        textSize = 12f
        height = 40.dpToPx(resources)
        width = 40.dpToPx(resources)
    }
}