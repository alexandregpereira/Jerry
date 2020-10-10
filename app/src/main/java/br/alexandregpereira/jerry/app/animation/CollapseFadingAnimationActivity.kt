package br.alexandregpereira.jerry.app.animation

import br.alexandregpereira.jerry.collapseFading
import br.alexandregpereira.jerry.expandFading
import br.alexandregpereira.jerry.app.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_collapse_fading_animation.*

class CollapseFadingAnimationActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, CollapseFadingAnimationActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collapse_fading_animation)

        collapseFadingTextButton.setOnClickListener {
            collapseFadingTextView.collapseFading()
        }

        collapseExpandFadingTextButton.setOnClickListener {
            collapseFadingTextView.expandFading()
        }

        collapseFixedFadingTextButton.setOnClickListener {
            collapseFixedFadingTextView.collapseFading()
        }

        collapseExpandFixedFadingTextButton.setOnClickListener {
            collapseFixedFadingTextView.expandFading()
        }

        collapseWidthFadingTextButton.setOnClickListener {
            collapseWidthFadingTextView.collapseFading(isHeight = false)
        }

        collapseExpandWidthFadingTextButton.setOnClickListener {
            collapseWidthFadingTextView.expandFading(isHeight = false)
        }

        collapseMatchWidthFadingButton.setOnClickListener {
            collapseMatchWidthFadingView.collapseFading(isHeight = false)
        }

        collapseExpandMatchWidthFadingButton.setOnClickListener {
            collapseMatchWidthFadingView.expandFading(isHeight = false)
        }

        collapseFixedWidthFadingButton.setOnClickListener {
            collapseFixedWidthFadingView.collapseFading(isHeight = false)
        }

        collapseExpandFixedWidthFadingButton.setOnClickListener {
            collapseFixedWidthFadingView.expandFading(isHeight = false)
        }
    }
}
