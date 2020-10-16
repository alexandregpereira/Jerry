package br.alexandregpereira.jerry.animator

import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import br.alexandregpereira.jerry.ANIMATION_STIFFNESS
import br.alexandregpereira.jerry.after
import br.alexandregpereira.jerry.dpToPx
import br.alexandregpereira.jerry.elevationSpring
import br.alexandregpereira.jerry.fadeSpring
import br.alexandregpereira.jerry.startSpringAnimation
import br.alexandregpereira.jerry.target
import br.alexandregpereira.jerry.targetFadeIn
import br.alexandregpereira.jerry.targetFadeOut
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
        holder.itemView.apply {
            val translationXTargetValue = if (deltaX != 0) translationOrigin else translationX
            val translationYTargetValue = if (deltaY != 0) translationOrigin else translationY

            translationXSpring(stiffness = translationStiffness)
                .target(translationXTargetValue)
                .translationYSpring(stiffness = translationStiffness)
                .target(translationYTargetValue)
                .startSpringAnimation { canceled ->
                    if (canceled) {
                        if (deltaX != 0) translationX = translationOrigin
                        if (deltaY != 0) translationY = translationOrigin
                    }
                    alpha = alphaFull
                    elevation = elevationFull
                    onAnimateMoveFinished(holder)
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
        this.itemView.apply {
            elevationSpring(stiffness = elevationStiffness)
                .target(elevationNone)
                .after(
                    fadeSpring(stiffness = alphaStiffness)
                        .target(alphaTargetValue)
                        .translationXSpring(stiffness = translationStiffness)
                        .target(translationXTargetValue)
                        .translationYSpring(stiffness = translationStiffness)
                        .target(translationYTargetValue)
                        .after(
                            elevationSpring(stiffness = elevationStiffness).target(elevationFull)
                        )
                ).startSpringAnimation {
                    itemView.alpha = alphaFull
                    itemView.elevation = elevationFull
                    itemView.translationX = translationOrigin
                    itemView.translationY = translationOrigin
                    onAnimateChangeFinished(this@startChangeAnimation, oldItem)
                }
        }
    }

    private fun View.startFadeElevationInAnimation(onAnimationFinished: () -> Unit) {
        fadeSpring(stiffness = alphaStiffness)
            .targetFadeIn()
            .after(
                elevationSpring(stiffness = elevationStiffness).target(elevationFull)
            )
            .startSpringAnimation {
                onAnimationFinished()
            }
    }

    private fun View.startFadeElevationOutAnimation(onAnimationFinished: () -> Unit) {
        elevationSpring(stiffness = elevationStiffness)
            .target(elevationNone)
            .after(
                fadeSpring(stiffness = alphaStiffness).targetFadeOut()
            )
            .startSpringAnimation {
                alpha = alphaFull
                elevation = elevationFull
                onAnimationFinished()
            }
    }
}
