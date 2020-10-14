package br.alexandregpereira.jerry.app.animation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.alexandregpereira.jerry.app.R
import br.alexandregpereira.jerry.expandable.collapseHeightFadingSpring
import br.alexandregpereira.jerry.expandable.collapseWidthFadingSpring
import br.alexandregpereira.jerry.expandable.expandHeightFadingSpring
import br.alexandregpereira.jerry.expandable.expandWidthFadingSpring
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
