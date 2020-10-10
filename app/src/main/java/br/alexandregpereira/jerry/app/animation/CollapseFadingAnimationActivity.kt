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
            collapseFadingTextView.collapseHeightFading()
        }

        collapseExpandFadingTextButton.setOnClickListener {
            collapseFadingTextView.expandHeightFading()
        }

        collapseFixedFadingTextButton.setOnClickListener {
            collapseFixedFadingTextView.collapseHeightFading()
        }

        collapseExpandFixedFadingTextButton.setOnClickListener {
            collapseFixedFadingTextView.expandHeightFading()
        }

        collapseWidthFadingTextButton.setOnClickListener {
            collapseWidthFadingTextView.collapseWidthFading()
        }

        collapseExpandWidthFadingTextButton.setOnClickListener {
            collapseWidthFadingTextView.expandWidthFading()
        }

        collapseMatchWidthFadingButton.setOnClickListener {
            collapseMatchWidthFadingView.collapseWidthFading()
        }

        collapseExpandMatchWidthFadingButton.setOnClickListener {
            collapseMatchWidthFadingView.expandWidthFading()
        }

        collapseFixedWidthFadingButton.setOnClickListener {
            collapseFixedWidthFadingView.collapseWidthFading()
        }

        collapseExpandFixedWidthFadingButton.setOnClickListener {
            collapseFixedWidthFadingView.expandWidthFading()
        }
    }
}
