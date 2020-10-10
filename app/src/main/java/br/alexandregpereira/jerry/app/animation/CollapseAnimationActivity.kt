package br.alexandregpereira.jerry.app.animation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import br.alexandregpereira.jerry.app.R
import br.alexandregpereira.jerry.collapseHeight
import br.alexandregpereira.jerry.collapseWidth
import br.alexandregpereira.jerry.expandHeight
import br.alexandregpereira.jerry.expandWidth
import kotlinx.android.synthetic.main.activity_collapse_animation.*
import kotlinx.android.synthetic.main.container_seek_bar.*

class CollapseAnimationActivity : AppCompatActivity(R.layout.activity_collapse_animation) {

    var collapseTextViewCount = 1

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, CollapseAnimationActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            collapseTextView.collapseHeight(
                duration = seekBar.progress.getSeekBarAnimationDuration(),
                onProgressChange = { interpolatedTime ->
                    collapsePercentageTextView.text = (interpolatedTime * 100).toInt().toString()
                }
            ) {
                collapseCountTextView.text = collapseTextViewCount++.toString()
            }
        }

        collapseExpandTextButton.setOnClickListener {
            collapseTextView.expandHeight(
                duration = seekBar.progress.getSeekBarAnimationDuration(),
                onProgressChange = { interpolatedTime ->
                    collapsePercentageTextView.text = (interpolatedTime * 100).toInt().toString()
                }
            ) {
                collapseCountTextView.text = collapseTextViewCount++.toString()
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
