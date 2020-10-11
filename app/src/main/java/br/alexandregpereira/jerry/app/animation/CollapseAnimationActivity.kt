package br.alexandregpereira.jerry.app.animation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import br.alexandregpereira.jerry.app.R
import br.alexandregpereira.jerry.collapseHeight
import br.alexandregpereira.jerry.collapseHeightSpring
import br.alexandregpereira.jerry.collapseWidth
import br.alexandregpereira.jerry.expandHeight
import br.alexandregpereira.jerry.expandHeightSpring
import br.alexandregpereira.jerry.expandWidth
import kotlinx.android.synthetic.main.activity_collapse_animation.*
import kotlinx.android.synthetic.main.container_animation_info.view.*
import kotlinx.android.synthetic.main.container_seek_bar.*

class CollapseAnimationActivity : AppCompatActivity(R.layout.activity_collapse_animation) {

    private var collapseTextViewCount = 1

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

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBarValue.text = progress.getSeekBarAnimationDuration().run {
                    "$this ms"
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

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
            collapseFixedTextView.collapseHeight(
                duration = seekBar.progress.getSeekBarAnimationDuration()
            )
        }

        collapseExpandFixedTextButton.setOnClickListener {
            collapseFixedTextView.expandHeight(
                duration = seekBar.progress.getSeekBarAnimationDuration()
            )
        }

        collapseWidthTextButton.setOnClickListener {
            collapseWidthTextView.collapseWidth()
        }

        collapseExpandWidthTextButton.setOnClickListener {
            collapseWidthTextView.expandWidth()
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
