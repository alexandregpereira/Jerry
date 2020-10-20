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
import kotlinx.android.synthetic.main.activity_expand_fading_animation.*

class ExpandFadingSpringAnimationActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, ExpandFadingSpringAnimationActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expand_fading_animation)

        expandFadingLabel.setText(R.string.expand_fading_spring)

        expandFadingTextButton.setOnClickListener {
            expandFadingTextView.visibleExpandHeightFadeIn()
        }

        expandCollapseFadingTextButton.setOnClickListener {
            expandFadingTextView.goneCollapseHeightFadeOut()
        }

        expandWidthFadingTextButton.setOnClickListener {
            expandWidthFadingTextView.visibleExpandWidthFadeIn()
        }

        expandCollapseWidthFadingTextButton.setOnClickListener {
            expandWidthFadingTextView.goneCollapseWidthFadeOut()
        }

        expandFixedFadingTextButton.setOnClickListener {
            expandFixedFadingTextView.visibleExpandHeightFadeIn()
        }

        expandCollapseFixedFadingTextButton.setOnClickListener {
            expandFixedFadingTextView.goneCollapseHeightFadeOut()
        }

        expandWidthFadingTextButton.setOnClickListener {
            expandWidthFadingTextView.visibleExpandWidthFadeIn()
        }

        expandCollapseWidthFadingTextButton.setOnClickListener {
            expandWidthFadingTextView.goneCollapseWidthFadeOut()
        }

        expandMatchWidthFadingButton.setOnClickListener {
            expandMatchWidthFadingView.visibleExpandWidthFadeIn()
        }

        expandCollapseMatchWidthFadingButton.setOnClickListener {
            expandMatchWidthFadingView.goneCollapseWidthFadeOut()
        }

        expandFixedWidthFadingButton.setOnClickListener {
            expandFixedWidthFadingView.visibleExpandWidthFadeIn()
        }

        expandCollapseFixedWidthFadingButton.setOnClickListener {
            expandFixedWidthFadingView.goneCollapseWidthFadeOut()
        }
    }
}
