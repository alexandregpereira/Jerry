package br.alexandregpereira.jerry.app.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import br.alexandregpereira.jerry.animation.scaleXSpring
import br.alexandregpereira.jerry.animation.scaleYSpring
import br.alexandregpereira.jerry.app.R
import br.alexandregpereira.jerry.force
import br.alexandregpereira.jerry.start

class SecondaryButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        configMaterialShapeDrawable()
        setTextColor(ContextCompat.getColor(context, R.color.textColor))
        textAlignment = TEXT_ALIGNMENT_CENTER
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("onTouchEvent", event?.toString() ?: "")
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                this.scaleXSpring(targetValue = 0.92f)
                    .scaleYSpring(targetValue = 0.92f)
                    .start()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                this.scaleXSpring(targetValue = 1f)
                    .scaleYSpring(targetValue = 1f)
                    .force(dampingRatio = 0.3f)
                    .start()
            }
        }
        return super.onTouchEvent(event)
    }
}