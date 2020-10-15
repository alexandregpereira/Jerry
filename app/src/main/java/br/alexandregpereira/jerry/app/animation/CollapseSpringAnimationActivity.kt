package br.alexandregpereira.jerry.app.animation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import br.alexandregpereira.jerry.expandable.animateWidthVisibility
import br.alexandregpereira.jerry.app.R
import br.alexandregpereira.jerry.expandable.collapseHeightSpring
import br.alexandregpereira.jerry.expandable.collapseWidthSpring
import br.alexandregpereira.jerry.expandable.expandHeightSpring
import br.alexandregpereira.jerry.expandable.expandWidthSpring
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

        collapseTextView.expandHeightSpring()
        collapseTextButton.setOnClickListener {
            collapseTextView.collapseHeightSpring(
                onProgressChange = { interpolatedTime ->
                    collapseAnimationInfo.percentageTextView.text = (interpolatedTime * 100).toInt().toString()
                }
            ) {
                collapseAnimationInfo.countTextView.text = collapseTextViewCount++.toString()
            }
        }

        collapseExpandTextButton.setOnClickListener {
            collapseTextView.expandHeightSpring(
                onProgressChange = { interpolatedTime ->
                    collapseAnimationInfo.percentageTextView.text = (interpolatedTime * 100).toInt().toString()
                }
            ) {
                collapseAnimationInfo.countTextView.text = collapseTextViewCount++.toString()
            }
        }

        collapseFixedTextButton.setOnClickListener {
            collapseFixedTextView.collapseHeightSpring()
        }

        collapseExpandFixedTextButton.setOnClickListener {
            collapseFixedTextView.expandHeightSpring()
        }

        collapseWidthTextButton.setOnClickListener {
            collapseWidthTextView.collapseWidthSpring()
        }

        collapseExpandWidthTextButton.setOnClickListener {
            collapseWidthTextView.expandWidthSpring()
        }

        collapseReverseMatchWidthButton.setOnClickListener {
            collapseMatchWidthView.animateWidthVisibility(
                visible = collapseMatchWidthViewVisible.not().also {
                    collapseMatchWidthViewVisible = it
                }
            )
        }

        collapseMatchWidthButton.setOnClickListener {
            collapseMatchWidthView.collapseWidthSpring()
        }

        collapseExpandMatchWidthButton.setOnClickListener {
            collapseMatchWidthView.expandWidthSpring()
        }

        collapseFixedWidthButton.setOnClickListener {
            collapseFixedWidthView.collapseWidthSpring()
        }

        collapseExpandFixedWidthButton.setOnClickListener {
            collapseFixedWidthView.expandWidthSpring()
        }
    }
}