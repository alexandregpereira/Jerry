package br.alexandregpereira.jerry.app.animation

import br.alexandregpereira.jerry.collapse
import br.alexandregpereira.jerry.expand
import br.alexandregpereira.jerry.app.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_collapse_animation.*

class CollapseAnimationActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, CollapseAnimationActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collapse_animation)

        collapseTextButton.setOnClickListener {
            collapseTextView.collapse()
        }

        collapseExpandTextButton.setOnClickListener {
            collapseTextView.expand()
        }

        collapseFixedTextButton.setOnClickListener {
            collapseFixedTextView.collapse()
        }

        collapseExpandFixedTextButton.setOnClickListener {
            collapseFixedTextView.expand()
        }

        collapseWidthTextButton.setOnClickListener {
            collapseWidthTextView.collapse(isHeight = false)
        }

        collapseExpandWidthTextButton.setOnClickListener {
            collapseWidthTextView.expand(isHeight = false)
        }

        collapseMatchWidthButton.setOnClickListener {
            collapseMatchWidthView.collapse(isHeight = false)
        }

        collapseExpandMatchWidthButton.setOnClickListener {
            collapseMatchWidthView.expand(isHeight = false)
        }

        collapseFixedWidthButton.setOnClickListener {
            collapseFixedWidthView.collapse(isHeight = false)
        }

        collapseExpandFixedWidthButton.setOnClickListener {
            collapseFixedWidthView.expand(isHeight = false)
        }
    }
}
