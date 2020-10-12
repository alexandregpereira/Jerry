package br.alexandregpereira.jerry.app.animation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.alexandregpereira.jerry.app.R
import br.alexandregpereira.jerry.collapseHeightFadingSpring
import br.alexandregpereira.jerry.collapseWidthFadingSpring
import br.alexandregpereira.jerry.expandHeightFadingSpring
import br.alexandregpereira.jerry.expandWidthFadingSpring
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
            collapseFadingTextView.collapseHeightFadingSpring()
        }

        collapseExpandFadingTextButton.setOnClickListener {
            collapseFadingTextView.expandHeightFadingSpring()
        }

        collapseFixedFadingTextButton.setOnClickListener {
            collapseFixedFadingTextView.collapseHeightFadingSpring()
        }

        collapseExpandFixedFadingTextButton.setOnClickListener {
            collapseFixedFadingTextView.expandHeightFadingSpring()
        }

        collapseWidthFadingTextButton.setOnClickListener {
            collapseWidthFadingTextView.collapseWidthFadingSpring()
        }

        collapseExpandWidthFadingTextButton.setOnClickListener {
            collapseWidthFadingTextView.expandWidthFadingSpring()
        }

        collapseMatchWidthFadingButton.setOnClickListener {
            collapseMatchWidthFadingView.collapseWidthFadingSpring()
        }

        collapseExpandMatchWidthFadingButton.setOnClickListener {
            collapseMatchWidthFadingView.expandWidthFadingSpring()
        }

        collapseFixedWidthFadingButton.setOnClickListener {
            collapseFixedWidthFadingView.collapseWidthFadingSpring()
        }

        collapseExpandFixedWidthFadingButton.setOnClickListener {
            collapseFixedWidthFadingView.expandWidthFadingSpring()
        }
    }
}
