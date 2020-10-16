package br.alexandregpereira.jerry.animator

import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import br.alexandregpereira.jerry.ANIMATION_STIFFNESS
import br.alexandregpereira.jerry.animationSet
import br.alexandregpereira.jerry.dpToPx
import br.alexandregpereira.jerry.elevationSpring
import br.alexandregpereira.jerry.fadeSpring
import br.alexandregpereira.jerry.startFadeInSpringAnimation
import br.alexandregpereira.jerry.startFadeOutSpringAnimation
import br.alexandregpereira.jerry.startSpringAnimation
import br.alexandregpereira.jerry.target
import br.alexandregpereira.jerry.translationXSpring
import br.alexandregpereira.jerry.translationYSpring

@RequiresApi(21)
class ElevationSpringItemAnimator(
    private val elevation: Float? = null,
    private val elevationStiffness: Float = ANIMATION_STIFFNESS * 2.5f,
    private val alphaStiffness: Float = ANIMATION_STIFFNESS * 2f,
    private val translationStiffness: Float = ANIMATION_STIFFNESS * 1.2f
) : BaseItemAnimator() {

    private val translationOrigin = 0f
    private val elevationNone = 0f
    private val alphaNone = 0f
    private val alphaFull = 1f
    private val View.elevationFull: Float
        get() = this@ElevationSpringItemAnimator.elevation ?: 2f.dpToPx(resources)

    override fun preAnimateAdd(holder: RecyclerView.ViewHolder): Boolean {
        holder.itemView.alpha = alphaNone
        holder.itemView.elevation = elevationNone
        return true
    }

    override fun startAddAnimation(
        holder: RecyclerView.ViewHolder
    ): Boolean {
        holder.itemView.startFadeElevationInAnimation {
            onAnimateAddFinished(holder)
        }
        return true
    }

    override fun startRemoveAnimation(
        holder: RecyclerView.ViewHolder
    ): Boolean {
        holder.itemView.startFadeElevationOutAnimation {
            onAnimateRemoveFinished(holder)
        }
        return true
    }

    override fun startMoveAnimation(
        holder: RecyclerView.ViewHolder,
        deltaX: Int,
        deltaY: Int
    ): Boolean {
        val onAnimationEnd: RecyclerView.ViewHolder.(completed: Boolean) -> Unit = { completed ->
            if (completed) {
                itemView.alpha = alphaFull
                itemView.elevation = itemView.elevationFull
                onAnimateMoveFinished(this)
            }
        }

        holder.itemView.apply {
            val translationXTargetValue = if (deltaX != 0) translationOrigin else translationX
            val translationYTargetValue = if (deltaY != 0) translationOrigin else translationY

            translationXSpring(stiffness = translationStiffness).startSpringAnimation(
                targetValue = translationXTargetValue
            ) { canceled ->
                if (canceled) {
                    if (deltaX != 0) translationX = translationOrigin
                }
                holder.onAnimationEnd(
                    translationYSpring().isRunning.not()
                )
            }
            translationYSpring(stiffness = translationStiffness).startSpringAnimation(
                targetValue = translationYTargetValue
            ) { canceled ->
                if (canceled) {
                    if (deltaY != 0) translationY = translationOrigin
                }
                holder.onAnimationEnd(
                    translationXSpring().isRunning.not()
                )
            }
        }
        return true
    }

    override fun startOldHolderChangeAnimation(
        oldHolder: RecyclerView.ViewHolder,
        translationX: Float,
        translationY: Float
    ): Boolean {
        oldHolder.startChangeAnimation(
            alphaTargetValue = alphaNone,
            translationXTargetValue = translationX,
            translationYTargetValue = translationY,
            oldItem = true
        )
        return true
    }

    override fun startNewHolderChangeAnimation(
        newHolder: RecyclerView.ViewHolder
    ): Boolean {
        onNewViewAnimateChangeStarted(newHolder)

        newHolder.startChangeAnimation(
            alphaTargetValue = alphaFull,
            translationXTargetValue = translationOrigin,
            translationYTargetValue = translationOrigin,
            oldItem = false
        )
        return true
    }

    private fun RecyclerView.ViewHolder.startChangeAnimation(
        alphaTargetValue: Float,
        translationXTargetValue: Float,
        translationYTargetValue: Float,
        oldItem: Boolean
    ) {
        val elevationFull = itemView.elevationFull
        val onAnimationEnd: RecyclerView.ViewHolder.(canceled: Boolean) -> Unit = { canceled ->
            if (canceled) {
                itemView.alpha = alphaFull
                itemView.elevation = elevationFull
                itemView.translationX = translationOrigin
                itemView.translationY = translationOrigin
                onAnimateChangeFinished(this, oldItem)
            } else {
                itemView.elevationSpring(stiffness = elevationStiffness)
                    .startSpringAnimation(
                        targetValue = elevationFull
                    ) {
                        itemView.alpha = alphaFull
                        itemView.elevation = elevationFull
                        itemView.translationX = translationOrigin
                        itemView.translationY = translationOrigin
                        onAnimateChangeFinished(this, oldItem)
                    }
            }
        }

        this.itemView.apply {
            elevationSpring(stiffness = elevationStiffness).startSpringAnimation(
                targetValue = elevationNone
            ) { canceled ->
                if (canceled) {
                    onAnimationEnd(canceled)
                    return@startSpringAnimation
                }

                fadeSpring(stiffness = alphaStiffness)
                    .target(alphaTargetValue)
                    .animationSet()
                    .translationXSpring(stiffness = translationStiffness)
                    .target(translationXTargetValue)
                    .translationYSpring(stiffness = translationStiffness)
                    .target(translationYTargetValue)
                    .startSpringAnimation {
                        onAnimationEnd(it)
                    }
            }
        }
    }

    private fun View.startFadeElevationInAnimation(onAnimationFinished: () -> Unit) {
        fadeSpring(stiffness = alphaStiffness).startFadeInSpringAnimation { canceled ->
            if (canceled) {
                onAnimationFinished()
                return@startFadeInSpringAnimation
            }

            elevationSpring(stiffness = elevationStiffness).startSpringAnimation(
                targetValue = elevationFull
            ) {
                onAnimationFinished()
            }
        }
    }

    private fun View.startFadeElevationOutAnimation(onAnimationFinished: () -> Unit) {
        elevationSpring(stiffness = elevationStiffness).startSpringAnimation(
            targetValue = elevationNone
        ) { canceled ->

            if (canceled) {
                onAnimationFinished()
                return@startSpringAnimation
            }

            fadeSpring(stiffness = alphaStiffness).startFadeOutSpringAnimation {
                alpha = alphaFull
                elevation = elevationFull
                onAnimationFinished()
            }
        }
    }
}
