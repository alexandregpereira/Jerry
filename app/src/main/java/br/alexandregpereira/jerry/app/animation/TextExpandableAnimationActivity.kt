package br.alexandregpereira.jerry.app.animation

import br.alexandregpereira.jerry.setTextExpandableAnimation
import br.alexandregpereira.jerry.app.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_text_expandable_animation.*

class TextExpandableAnimationActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, TextExpandableAnimationActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_expandable_animation)

        expandCancelTextButton.setOnClickListener {
            expandCancelTextView.setTextExpandableAnimation(
                getString(R.string.expand)
            )
            Handler().postDelayed({
                expandCancelTextView.setTextExpandableAnimation(null)
            }, 100)
        }

        collapseCancelTextButton.setOnClickListener {
            collapseCancelTextView.setTextExpandableAnimation(null)
            Handler().postDelayed({
                collapseCancelTextView.setTextExpandableAnimation(
                    getString(R.string.collapse)
                )
            }, 100)
        }

        expandFadingTextButton.setOnClickListener {
            expandFadingTextView.setTextExpandableAnimation(
                getString(R.string.expand_collapse_animation)
            )
        }

        expandCollapseFadingTextButton.setOnClickListener {
            expandFadingTextView.setTextExpandableAnimation(null)
        }

        collapseFadingTextButton.setOnClickListener {
            collapseFadingTextView.setTextExpandableAnimation(null)
        }

        collapseExpandFadingTextButton.setOnClickListener {
            collapseFadingTextView.setTextExpandableAnimation(
                getString(R.string.collapse_expand_animation)
            )
        }

        textFadeTextButton.setOnClickListener {
            textFadeTextView.setTextExpandableAnimation("${System.currentTimeMillis()}")
        }
    }
}
