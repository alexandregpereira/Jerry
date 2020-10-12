package br.alexandregpereira.jerry.app.animation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.alexandregpereira.jerry.app.R
import br.alexandregpereira.jerry.collapseHeightFadingSpring
import br.alexandregpereira.jerry.collapseWidthFading
import br.alexandregpereira.jerry.collapseWidthFadingSpring
import br.alexandregpereira.jerry.expandHeightFadingSpring
import br.alexandregpereira.jerry.expandWidthFading
import br.alexandregpereira.jerry.expandWidthFadingSpring
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
            expandFadingTextView.expandHeightFadingSpring()
        }

        expandCollapseFadingTextButton.setOnClickListener {
            expandFadingTextView.collapseHeightFadingSpring()
        }

        expandWidthFadingTextButton.setOnClickListener {
            expandWidthFadingTextView.expandWidthFadingSpring()
        }

        expandCollapseWidthFadingTextButton.setOnClickListener {
            expandWidthFadingTextView.collapseWidthFadingSpring()
        }

        expandFixedFadingTextButton.setOnClickListener {
            expandFixedFadingTextView.expandHeightFadingSpring()
        }

        expandCollapseFixedFadingTextButton.setOnClickListener {
            expandFixedFadingTextView.collapseHeightFadingSpring()
        }

        expandWidthFadingTextButton.setOnClickListener {
            expandWidthFadingTextView.expandWidthFadingSpring()
        }

        expandCollapseWidthFadingTextButton.setOnClickListener {
            expandWidthFadingTextView.collapseWidthFadingSpring()
        }

        expandMatchWidthFadingButton.setOnClickListener {
            expandMatchWidthFadingView.expandWidthFadingSpring()
        }

        expandCollapseMatchWidthFadingButton.setOnClickListener {
            expandMatchWidthFadingView.collapseWidthFadingSpring()
        }

        expandFixedWidthFadingButton.setOnClickListener {
            expandFixedWidthFadingView.expandWidthFadingSpring()
        }

        expandCollapseFixedWidthFadingButton.setOnClickListener {
            expandFixedWidthFadingView.collapseWidthFadingSpring()
        }
    }
}
