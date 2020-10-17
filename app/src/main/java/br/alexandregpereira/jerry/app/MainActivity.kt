package br.alexandregpereira.jerry.app

import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.alexandregpereira.jerry.app.animation.CollapseAnimationActivity
import br.alexandregpereira.jerry.app.animation.CollapseFadingAnimationActivity
import br.alexandregpereira.jerry.app.animation.CollapseSpringAnimationActivity
import br.alexandregpereira.jerry.app.animation.CollapseFadingSpringAnimationActivity
import br.alexandregpereira.jerry.app.animation.ExpandAnimationActivity
import br.alexandregpereira.jerry.app.animation.ExpandFadingAnimationActivity
import br.alexandregpereira.jerry.app.animation.ExpandSpringAnimationActivity
import br.alexandregpereira.jerry.app.animation.ExpandFadingSpringAnimationActivity
import br.alexandregpereira.jerry.app.animation.FadeAnimationActivity
import br.alexandregpereira.jerry.app.animation.FadeSpringAnimationActivity
import br.alexandregpereira.jerry.app.animation.TextExpandableAnimationActivity
import br.alexandregpereira.jerry.app.animation.TextExpandableSpringAnimationActivity
import br.alexandregpereira.jerry.app.recyclerview.GridRecyclerViewActivity
import br.alexandregpereira.jerry.app.recyclerview.GridRecyclerViewSpringActivity
import br.alexandregpereira.jerry.app.recyclerview.RecyclerViewActivity
import br.alexandregpereira.jerry.app.recyclerview.RecyclerViewSpringActivity
import br.alexandregpereira.jerry.app.widgets.configMaterialShapeDrawable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appBarLayout.isLiftOnScroll = true

        componentsRecycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MainAdapter(getAnimationComponentsName()) {
                when (it) {
                    AnimationComponent.FADE.ordinal -> startActivity(
                        FadeAnimationActivity.getStartIntent(this@MainActivity)
                    )
                    AnimationComponent.FADE_SPRING.ordinal -> startActivity(
                        FadeSpringAnimationActivity.getStartIntent(this@MainActivity)
                    )
                    AnimationComponent.COLLAPSE_FADING.ordinal -> startActivity(
                        CollapseFadingAnimationActivity.getStartIntent(this@MainActivity)
                    )
                    AnimationComponent.COLLAPSE_FADING.ordinal -> startActivity(
                        CollapseFadingAnimationActivity.getStartIntent(this@MainActivity)
                    )
                    AnimationComponent.COLLAPSE_FADING_SPRING.ordinal -> startActivity(
                        CollapseFadingSpringAnimationActivity.getStartIntent(this@MainActivity)
                    )
                    AnimationComponent.EXPAND_FADING.ordinal -> startActivity(
                        ExpandFadingAnimationActivity.getStartIntent(this@MainActivity)
                    )
                    AnimationComponent.EXPAND_FADING_SPRING.ordinal -> startActivity(
                        ExpandFadingSpringAnimationActivity.getStartIntent(this@MainActivity)
                    )
                    AnimationComponent.COLLAPSE.ordinal -> startActivity(
                        CollapseAnimationActivity.getStartIntent(this@MainActivity)
                    )
                    AnimationComponent.COLLAPSE_SPRING.ordinal -> startActivity(
                        CollapseSpringAnimationActivity.getStartIntent(this@MainActivity)
                    )
                    AnimationComponent.EXPAND.ordinal -> startActivity(
                        ExpandAnimationActivity.getStartIntent(this@MainActivity)
                    )
                    AnimationComponent.EXPAND_SPRING.ordinal -> startActivity(
                        ExpandSpringAnimationActivity.getStartIntent(this@MainActivity)
                    )
                    AnimationComponent.TEXT_EXPANDABLE.ordinal -> startActivity(
                        TextExpandableAnimationActivity.getStartIntent(this@MainActivity)
                    )
                    AnimationComponent.TEXT_EXPANDABLE_SPRING.ordinal -> startActivity(
                        TextExpandableSpringAnimationActivity.getStartIntent(this@MainActivity)
                    )
                    AnimationComponent.RECYCLER.ordinal -> startActivity(
                        RecyclerViewActivity.getStartIntent(this@MainActivity)
                    )
                    AnimationComponent.RECYCLER_SPRING.ordinal -> startActivity(
                        RecyclerViewSpringActivity.getStartIntent(this@MainActivity)
                    )
                    AnimationComponent.GRID_RECYCLER.ordinal -> startActivity(
                        GridRecyclerViewActivity.getStartIntent(this@MainActivity)
                    )
                    AnimationComponent.GRID_RECYCLER_SPRING.ordinal -> startActivity(
                        GridRecyclerViewSpringActivity.getStartIntent(this@MainActivity)
                    )
                }
            }
        }
    }
}

fun getAnimationComponentsName(): List<String> {
    return AnimationComponent.values().map { it.name }
}

enum class AnimationComponent {
    COLLAPSE,
    COLLAPSE_SPRING,
    COLLAPSE_FADING,
    COLLAPSE_FADING_SPRING,
    EXPAND,
    EXPAND_SPRING,
    EXPAND_FADING,
    EXPAND_FADING_SPRING,
    FADE,
    FADE_SPRING,
    TEXT_EXPANDABLE,
    TEXT_EXPANDABLE_SPRING,
    RECYCLER,
    RECYCLER_SPRING,
    GRID_RECYCLER,
    GRID_RECYCLER_SPRING
}

class MainAdapter(
    private val texts: List<String>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>(){

    inner class MainViewHolder(
        private val textView: TextView
    ) : RecyclerView.ViewHolder(textView) {

        fun bind(text: String) {
            textView.text = text
            textView.setOnClickListener { onClick(adapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
       return MainViewHolder(TextView(parent.context).apply {
           val padding = resources.getDimensionPixelOffset(R.dimen.text_padding)
           layoutParams = ViewGroup.MarginLayoutParams(
               ViewGroup.LayoutParams.MATCH_PARENT,
               ViewGroup.LayoutParams.WRAP_CONTENT
           ).apply {
               setMargins(padding / 2, padding / 2, padding / 2, padding / 2)
           }

           setTextColor(ContextCompat.getColor(context, R.color.textSecondaryColor))
           configMaterialShapeDrawable()
       })
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(texts[position])
    }

    override fun getItemCount(): Int = texts.size
}