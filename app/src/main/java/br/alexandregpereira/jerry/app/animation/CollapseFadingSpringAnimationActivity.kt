package br.alexandregpereira.jerry.app.animation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.alexandregpereira.jerry.app.R
import br.alexandregpereira.jerry.expandable.goneCollapseHeightFadeOut
import br.alexandregpereira.jerry.expandable.goneCollapseWidthFadeOut
import br.alexandregpereira.jerry.expandable.visibleExpandHeightFadeIn
import br.alexandregpereira.jerry.expandable.visibleExpandWidthFadeIn
import kotlinx.android.synthetic.main.activity_collapse_fading_animation.*

class CollapseFadingSpringAnimationActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, CollapseFadingSpringAnimationActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collapse_fading_animation)

        collapseFadingLabel.setText(R.string.collapse_fading_spring)

        collapseFadingTextButton.setOnClickListener {
            collapseFadingTextView.goneCollapseHeightFadeOut()
        }

        collapseExpandFadingTextButton.setOnClickListener {
            collapseFadingTextView.visibleExpandHeightFadeIn()
        }

        collapseFixedFadingTextButton.setOnClickListener {
            collapseFixedFadingTextView.goneCollapseHeightFadeOut()
        }

        collapseExpandFixedFadingTextButton.setOnClickListener {
            collapseFixedFadingTextView.visibleExpandHeightFadeIn()
        }

        collapseWidthFadingTextButton.setOnClickListener {
            collapseWidthFadingTextView.goneCollapseWidthFadeOut()
        }

        collapseExpandWidthFadingTextButton.setOnClickListener {
            collapseWidthFadingTextView.visibleExpandWidthFadeIn()
        }

        collapseMatchWidthFadingButton.setOnClickListener {
            collapseMatchWidthFadingView.goneCollapseWidthFadeOut()
        }

        collapseExpandMatchWidthFadingButton.setOnClickListener {
            collapseMatchWidthFadingView.visibleExpandWidthFadeIn()
        }

        collapseFixedWidthFadingButton.setOnClickListener {
            collapseFixedWidthFadingView.goneCollapseWidthFadeOut()
        }

        collapseExpandFixedWidthFadingButton.setOnClickListener {
            collapseFixedWidthFadingView.visibleExpandWidthFadeIn()
        }
    }
}
