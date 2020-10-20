package br.alexandregpereira.jerry.app.animation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.alexandregpereira.jerry.app.R
import br.alexandregpereira.jerry.expandable.goneCollapseHeight
import br.alexandregpereira.jerry.expandable.goneCollapseWidth
import br.alexandregpereira.jerry.expandable.visibleExpandHeight
import br.alexandregpereira.jerry.expandable.visibleExpandWidth
import kotlinx.android.synthetic.main.activity_expand_animation.*

class ExpandSpringAnimationActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, ExpandSpringAnimationActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expand_animation)

        expandLabel.setText(R.string.expand_spring)

        expandTextButton.setOnClickListener {
            expandTextView.visibleExpandHeight()
        }

        expandCollapseTextButton.setOnClickListener {
            expandTextView.goneCollapseHeight()
        }

        expandWidthTextButton.setOnClickListener {
            expandWidthTextView.visibleExpandWidth()
        }

        expandCollapseWidthTextButton.setOnClickListener {
            expandWidthTextView.goneCollapseWidth()
        }

        expandFixedTextButton.setOnClickListener {
            expandFixedTextView.visibleExpandHeight()
        }

        expandCollapseFixedTextButton.setOnClickListener {
            expandFixedTextView.goneCollapseHeight()
        }

        expandWidthTextButton.setOnClickListener {
            expandWidthTextView.visibleExpandWidth()
        }

        expandCollapseWidthTextButton.setOnClickListener {
            expandWidthTextView.goneCollapseWidth()
        }

        expandMatchWidthButton.setOnClickListener {
            expandMatchWidthView.visibleExpandWidth()
        }

        expandCollapseMatchWidthButton.setOnClickListener {
            expandMatchWidthView.goneCollapseWidth()
        }

        expand0dpWidthButton.setOnClickListener {
            expand0dpWidthView.visibleExpandWidth()
        }

        expandCollapse0dpWidthButton.setOnClickListener {
            expand0dpWidthView.goneCollapseWidth()
        }

        expandHalf0dpWidthButton.setOnClickListener {
            expandHalf0dpWidthView.visibleExpandWidth()
        }

        expandCollapseHalf0dpWidthButton.setOnClickListener {
            expandHalf0dpWidthView.goneCollapseWidth()
        }

        expandFixedWidthButton.setOnClickListener {
            expandFixedWidthView.visibleExpandWidth()
        }

        expandCollapseFixedWidthButton.setOnClickListener {
            expandFixedWidthView.goneCollapseWidth()
        }
    }
}
