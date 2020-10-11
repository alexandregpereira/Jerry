package br.alexandregpereira.jerry

import android.view.View
import android.view.ViewGroup
import androidx.dynamicanimation.animation.FloatPropertyCompat

internal fun View.widthHeightViewProperty(
    isHeight: Boolean = true,
    onProgressChange: ((progress: Float) -> Unit)? = null
) = object : FloatPropertyCompat<View>("viewProperty") {

    private val originalValue: Int
        get() = getOriginalValue(isHeight)

    private val initialValue: Int
        get() = if (isExpandingRunning()) {
            (getLayoutParamSize(isHeight)).let {
                if (it == originalValue || it < 0) 0 else it
            }
        } else {
            getCollapsingInitialValue(isHeight)
        }

    private val targetValue: Int?
        get() = if (isExpandingRunning()) {
            getTargetValue(originalValue, isHeight)
        } else {
            null
        }

    private val progressFunction = createProgressFunction()

    override fun getValue(view: View?): Float {
        return initialValue.toFloat()
    }

    override fun setValue(view: View?, value: Float) {
        val targetValue = targetValue
        if (targetValue != null) {
            setExpandingValue(value, targetValue)
        } else {
            setCollapsingValue(value)
        }

        onProgressChange?.invoke(progressFunction(value))
        requestLayout()
    }

    private fun setCollapsingValue(value: Float) {
        val finalValue = value.toInt().let {
            if (it == 0) 1 else it
        }
        setLayoutParamSize(finalValue, isHeight)
    }

    private fun setExpandingValue(value: Float, targetValue: Int) {
        val finalValue = if (value == 0f) 1f else {
            if (value == targetValue.toFloat() &&
                originalValue == ViewGroup.LayoutParams.WRAP_CONTENT
            ) {
                ViewGroup.LayoutParams.WRAP_CONTENT
            } else {
                value
            }
        }

        setLayoutParamSize(finalValue.toInt(), isHeight)
    }

    private fun createProgressFunction(): (Float) -> Float {
        val fullValue = targetValue ?: initialValue
        return if (isExpandingRunning()) {
            { value ->
                (fullValue - (fullValue - value)) / fullValue
            }
        } else {
            { value ->
                (fullValue - value) / fullValue
            }
        }
    }
}