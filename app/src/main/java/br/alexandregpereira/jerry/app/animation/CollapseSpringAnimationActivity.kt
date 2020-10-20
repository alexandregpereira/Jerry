package br.alexandregpereira.jerry.app.animation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.dynamicanimation.animation.DynamicAnimation
import br.alexandregpereira.jerry.after
import br.alexandregpereira.jerry.animation.expandable.visibleOrGoneExpandableWidth
import br.alexandregpereira.jerry.app.R
import br.alexandregpereira.jerry.animation.expandable.goneCollapseHeight
import br.alexandregpereira.jerry.animation.expandable.goneCollapseWidth
import br.alexandregpereira.jerry.animation.expandable.visibleExpandHeight
import br.alexandregpereira.jerry.animation.expandable.visibleExpandWidth
import br.alexandregpereira.jerry.animation.scaleXSpring
import br.alexandregpereira.jerry.animation.scaleYSpring
import br.alexandregpereira.jerry.force
import br.alexandregpereira.jerry.cancelSpringAnimation
import br.alexandregpereira.jerry.spring
import br.alexandregpereira.jerry.start
import kotlinx.android.synthetic.main.activity_collapse_animation.*
import kotlinx.android.synthetic.main.container_animation_info.view.*

class CollapseSpringAnimationActivity : AppCompatActivity(R.layout.activity_collapse_animation) {

    private var collapseTextViewCount = 1
    private var collapseMatchWidthViewVisible = true

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, CollapseSpringAnimationActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        collapseLabel.setText(R.string.collapse_spring)

        ViewCompat.setTranslationZ(
            collapseAnimationInfo,
            resources.getDimension(R.dimen.strong_elevation)
        )

        collapseTextView.visibleExpandHeight()
        collapseTextButton.setOnClickListener {
            collapseTextView.goneCollapseHeight(
                onProgressChange = { interpolatedTime ->
                    collapseAnimationInfo.percentageTextView.text = (interpolatedTime * 100).toInt().toString()
                }
            ) {
                startScaleAnimation()
            }
        }

        collapseExpandTextButton.setOnClickListener {
            collapseTextView.visibleExpandHeight(
                onProgressChange = { interpolatedTime ->
                    collapseAnimationInfo.percentageTextView.text = (interpolatedTime * 100).toInt().toString()
                }
            ) {
                startScaleAnimation()
            }
        }

        collapseFixedTextButton.setOnClickListener {
            collapseFixedTextView.goneCollapseHeight()
        }

        collapseExpandFixedTextButton.setOnClickListener {
            collapseFixedTextView.visibleExpandHeight()
        }

        collapseWidthTextButton.setOnClickListener {
            collapseWidthTextView.goneCollapseWidth()
        }

        collapseExpandWidthTextButton.setOnClickListener {
            collapseWidthTextView.visibleExpandWidth()
        }

        collapseReverseMatchWidthButton.setOnClickListener {
            collapseMatchWidthView.visibleOrGoneExpandableWidth(
                visible = collapseMatchWidthViewVisible.not().also {
                    collapseMatchWidthViewVisible = it
                }
            )
        }

        collapseMatchWidthButton.setOnClickListener {
            collapseMatchWidthView.goneCollapseWidth()
        }

        collapseExpandMatchWidthButton.setOnClickListener {
            collapseMatchWidthView.visibleExpandWidth()
        }

        collapseFixedWidthButton.setOnClickListener {
            collapseFixedWidthView.goneCollapseWidth()
        }

        collapseExpandFixedWidthButton.setOnClickListener {
            collapseFixedWidthView.visibleExpandWidth()
        }
    }

    private fun startScaleAnimation() {
        collapseAnimationInfo.countTextView.apply {
            text = collapseTextViewCount++.toString()
            cancelSpringAnimation()
                .scaleXSpring(targetValue = 0.8f)
                .scaleYSpring(targetValue = 0.8f)
                .after(
                    spring(key = id, targetValue = 1f, property = DynamicAnimation.SCALE_X)
                        .spring(
                            key = collapseAnimationInfo.id,
                            targetValue = 1f,
                            property = DynamicAnimation.SCALE_Y
                        )
                        .force(dampingRatio = 0.15f)
                )
                .start()
        }
    }
}
