package br.alexandregpereira.jerry.app.animation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.alexandregpereira.jerry.app.R
import br.alexandregpereira.jerry.expandable.collapseHeightSpring
import br.alexandregpereira.jerry.expandable.collapseWidthSpring
import br.alexandregpereira.jerry.expandable.expandHeightSpring
import br.alexandregpereira.jerry.expandable.expandWidthSpring
import kotlinx.android.synthetic.main.activity_expand_animation.*

class ExpandAnimationActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, ExpandAnimationActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expand_animation)

        expandTextButton.setOnClickListener {
            expandTextView.expandHeightSpring()
        }

        expandCollapseTextButton.setOnClickListener {
            expandTextView.collapseHeightSpring()
        }

        expandWidthTextButton.setOnClickListener {
            expandWidthTextView.expandWidthSpring()
        }

        expandCollapseWidthTextButton.setOnClickListener {
            expandWidthTextView.collapseWidthSpring()
        }

        expandFixedTextButton.setOnClickListener {
            expandFixedTextView.expandHeightSpring()
        }

        expandCollapseFixedTextButton.setOnClickListener {
            expandFixedTextView.collapseHeightSpring()
        }

        expandWidthTextButton.setOnClickListener {
            expandWidthTextView.expandWidthSpring()
        }

        expandCollapseWidthTextButton.setOnClickListener {
            expandWidthTextView.collapseWidthSpring()
        }

        expandMatchWidthButton.setOnClickListener {
            expandMatchWidthView.expandWidthSpring()
        }

        expandCollapseMatchWidthButton.setOnClickListener {
            expandMatchWidthView.collapseWidthSpring()
        }

        expand0dpWidthButton.setOnClickListener {
            expand0dpWidthView.expandWidthSpring()
        }

        expandCollapse0dpWidthButton.setOnClickListener {
            expand0dpWidthView.collapseWidthSpring()
        }

        expandHalf0dpWidthButton.setOnClickListener {
            expandHalf0dpWidthView.expandWidthSpring()
        }

        expandCollapseHalf0dpWidthButton.setOnClickListener {
            expandHalf0dpWidthView.collapseWidthSpring()
        }

        expandFixedWidthButton.setOnClickListener {
            expandFixedWidthView.expandWidthSpring()
        }

        expandCollapseFixedWidthButton.setOnClickListener {
            expandFixedWidthView.collapseWidthSpring()
        }
    }
}
