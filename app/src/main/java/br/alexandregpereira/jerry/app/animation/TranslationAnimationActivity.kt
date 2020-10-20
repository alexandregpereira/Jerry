package br.alexandregpereira.jerry.app.animation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import br.alexandregpereira.jerry.animation.translationXSpring
import br.alexandregpereira.jerry.animation.translationYSpring
import br.alexandregpereira.jerry.animationSetForce
import br.alexandregpereira.jerry.app.R
import br.alexandregpereira.jerry.cancelSpringAnimation
import br.alexandregpereira.jerry.start
import kotlinx.android.synthetic.main.activity_translation_animation.*

class TranslationAnimationActivity : AppCompatActivity(R.layout.activity_translation_animation) {

    private var isSpring = true

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, TranslationAnimationActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        container.viewTreeObserver.addOnGlobalLayoutListener {
            val buttonHeight = moveButton.height + resources.getDimension(R.dimen.small) * 2
            val point1 = 0f to (container.height - buttonHeight - moveView.height)
            val point2 = (container.width / 2f - moveView.width / 2) to (container.height / 2f - buttonHeight - moveView.height / 2)
            val point3 = (container.width - moveView.width).toFloat() to point1.second
            val point4 = point3.first to 0f
            val point6 = 0f to 0f
            val points = listOf<Pair<Float, Float>>(
                point1, point2, point3, point4, point2, point6
            ).circularIterator()

            moveButton.setOnClickListener {
                isSpring = true
                val point = points.next()
                startSpringAnimation(point)
            }

            moveNormalButton.setOnClickListener {
                isSpring = false
                val point = points.next()
                startNormalAnimation(point)
            }
        }
    }

    private fun startSpringAnimation(point: Pair<Float, Float>) {
        moveView.animate().cancel()
        moveView.translationXSpring(targetValue = point.first)
            .translationYSpring(targetValue = point.second)
            .animationSetForce(stiffness = 60f, dampingRatio = 0.8f)
            .start()
    }

    private fun startNormalAnimation(point: Pair<Float, Float>) {
        moveView.cancelSpringAnimation()
        moveView.animate()
            .translationX(point.first)
            .translationY(point.second)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(500)
            .start()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val point = event.rawX - moveView.width / 2 to event.rawY - moveView.height / 2
                if (isSpring) startSpringAnimation(point) else startNormalAnimation(point)
            }
        }
        return true
    }
}
