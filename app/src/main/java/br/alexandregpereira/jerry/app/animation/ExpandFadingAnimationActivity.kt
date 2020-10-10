package br.alexandregpereira.jerry.app.animation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.alexandregpereira.jerry.app.R
import br.alexandregpereira.jerry.collapseHeightFading
import br.alexandregpereira.jerry.collapseWidthFading
import br.alexandregpereira.jerry.expandHeightFading
import br.alexandregpereira.jerry.expandWidthFading
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
            expandFadingTextView.expandHeightFading()
        }

        expandCollapseFadingTextButton.setOnClickListener {
            expandFadingTextView.collapseHeightFading()
        }

        expandWidthFadingTextButton.setOnClickListener {
            expandWidthFadingTextView.expandWidthFading()
        }

        expandCollapseWidthFadingTextButton.setOnClickListener {
            expandWidthFadingTextView.collapseWidthFading()
        }

        expandFixedFadingTextButton.setOnClickListener {
            expandFixedFadingTextView.expandHeightFading()
        }

        expandCollapseFixedFadingTextButton.setOnClickListener {
            expandFixedFadingTextView.collapseHeightFading()
        }

        expandWidthFadingTextButton.setOnClickListener {
            expandWidthFadingTextView.expandWidthFading()
        }

        expandCollapseWidthFadingTextButton.setOnClickListener {
            expandWidthFadingTextView.collapseWidthFading()
        }

        expandMatchWidthFadingButton.setOnClickListener {
            expandMatchWidthFadingView.expandWidthFading()
        }

        expandCollapseMatchWidthFadingButton.setOnClickListener {
            expandMatchWidthFadingView.collapseWidthFading()
        }

        expandFixedWidthFadingButton.setOnClickListener {
            expandFixedWidthFadingView.expandWidthFading()
        }

        expandCollapseFixedWidthFadingButton.setOnClickListener {
            expandFixedWidthFadingView.collapseWidthFading()
        }
    }
}
