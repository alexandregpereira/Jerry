package br.alexandregpereira.jerry.app.animation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import br.alexandregpereira.jerry.expandable.visibleOrGoneExpandableWidth
import br.alexandregpereira.jerry.app.R
import br.alexandregpereira.jerry.expandable.collapseHeight
import br.alexandregpereira.jerry.expandable.collapseWidth
import br.alexandregpereira.jerry.expandable.expandHeight
import br.alexandregpereira.jerry.expandable.expandWidth
import kotlinx.android.synthetic.main.activity_collapse_animation.*
import kotlinx.android.synthetic.main.container_animation_info.view.*

class CollapseAnimationActivity : AppCompatActivity(R.layout.activity_collapse_animation) {

    private var collapseTextViewCount = 1
    private var collapseMatchWidthViewVisible = true

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, CollapseAnimationActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ViewCompat.setTranslationZ(
            collapseAnimationInfo,
            resources.getDimension(R.dimen.strong_elevation)
        )

        collapseTextView.expandHeight()
        collapseTextButton.setOnClickListener {
            collapseTextView.collapseHeight(
                onProgressChange = { interpolatedTime ->
                    collapseAnimationInfo.percentageTextView.text = (interpolatedTime * 100).toInt().toString()
                }
            ) {
                collapseAnimationInfo.countTextView.text = collapseTextViewCount++.toString()
            }
        }

        collapseExpandTextButton.setOnClickListener {
            collapseTextView.expandHeight(
                onProgressChange = { interpolatedTime ->
                    collapseAnimationInfo.percentageTextView.text = (interpolatedTime * 100).toInt().toString()
                }
            ) {
                collapseAnimationInfo.countTextView.text = collapseTextViewCount++.toString()
            }
        }

        collapseFixedTextButton.setOnClickListener {
            collapseFixedTextView.collapseHeight()
        }

        collapseExpandFixedTextButton.setOnClickListener {
            collapseFixedTextView.expandHeight()
        }

        collapseWidthTextButton.setOnClickListener {
            collapseWidthTextView.collapseWidth()
        }

        collapseExpandWidthTextButton.setOnClickListener {
            collapseWidthTextView.expandWidth()
        }

        collapseReverseMatchWidthButton.setOnClickListener {
            collapseMatchWidthView.visibleOrGoneExpandableWidth(
                visible = collapseMatchWidthViewVisible.not().also {
                    collapseMatchWidthViewVisible = it
                }
            )
        }

        collapseMatchWidthButton.setOnClickListener {
            collapseMatchWidthView.collapseWidth()
        }

        collapseExpandMatchWidthButton.setOnClickListener {
            collapseMatchWidthView.expandWidth()
        }

        collapseFixedWidthButton.setOnClickListener {
            collapseFixedWidthView.collapseWidth()
        }

        collapseExpandFixedWidthButton.setOnClickListener {
            collapseFixedWidthView.expandWidth()
        }
    }
}
