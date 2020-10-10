package br.alexandregpereira.jerry.app.animation

import br.alexandregpereira.jerry.collapseFading
import br.alexandregpereira.jerry.expandFading
import br.alexandregpereira.jerry.app.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_expand_fading_animation.*

class ExpandFadingAnimationActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, ExpandFadingAnimationActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expand_fading_animation)

        expandFadingTextButton.setOnClickListener {
            expandFadingTextView.expandFading()
        }

        expandCollapseFadingTextButton.setOnClickListener {
            expandFadingTextView.collapseFading()
        }

        expandWidthFadingTextButton.setOnClickListener {
            expandWidthFadingTextView.expandFading(isHeight = false)
        }

        expandCollapseWidthFadingTextButton.setOnClickListener {
            expandWidthFadingTextView.collapseFading(isHeight = false)
        }

        expandFixedFadingTextButton.setOnClickListener {
            expandFixedFadingTextView.expandFading()
        }

        expandCollapseFixedFadingTextButton.setOnClickListener {
            expandFixedFadingTextView.collapseFading()
        }

        expandWidthFadingTextButton.setOnClickListener {
            expandWidthFadingTextView.expandFading(isHeight = false)
        }

        expandCollapseWidthFadingTextButton.setOnClickListener {
            expandWidthFadingTextView.collapseFading(isHeight = false)
        }

        expandMatchWidthFadingButton.setOnClickListener {
            expandMatchWidthFadingView.expandFading(isHeight = false)
        }

        expandCollapseMatchWidthFadingButton.setOnClickListener {
            expandMatchWidthFadingView.collapseFading(isHeight = false)
        }

        expandFixedWidthFadingButton.setOnClickListener {
            expandFixedWidthFadingView.expandFading(isHeight = false)
        }

        expandCollapseFixedWidthFadingButton.setOnClickListener {
            expandFixedWidthFadingView.collapseFading(isHeight = false)
        }
    }
}
