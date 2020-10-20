package br.alexandregpereira.jerry.animator

import android.view.View
import androidx.annotation.RequiresApi
import androidx.dynamicanimation.animation.SpringForce.DAMPING_RATIO_NO_BOUNCY
import androidx.recyclerview.widget.RecyclerView
import br.alexandregpereira.jerry.ANIMATION_STIFFNESS
import br.alexandregpereira.jerry.after
import br.alexandregpereira.jerry.dpToPx
import br.alexandregpereira.jerry.animation.elevationSpring
import br.alexandregpereira.jerry.animation.fadeInSpring
import br.alexandregpereira.jerry.animation.fadeOutSpring
import br.alexandregpereira.jerry.animation.fadeSpring
import br.alexandregpereira.jerry.force
import br.alexandregpereira.jerry.start
import br.alexandregpereira.jerry.animation.translationXSpring
import br.alexandregpereira.jerry.animation.translationYSpring

@RequiresApi(21)
class ElevationSpringItemAnimator(
    private val elevation: Float? = null,
    stiffness: Float = ANIMATION_STIFFNESS,
    dampingRatio: Float = DAMPING_RATIO_NO_BOUNCY
) : BaseItemAnimator() {

    var elevationStiffness: Float = stiffness * 2.5f
    var alphaStiffness: Float = stiffness * 2f
    var translationStiffness: Float = stiffness * 1.2f
    var elevationDampingRatio: Float = dampingRatio
    var alphaDampingRatio: Float = dampingRatio
    var translationDampingRatio: Float = dampingRatio

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

            translationXSpring(translationXTargetValue)
                .force(translationStiffness, translationDampingRatio)
                .translationYSpring(translationYTargetValue)
                .force(translationStiffness, translationDampingRatio)
                .start { canceled ->
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
            elevationSpring(targetValue = elevationNone)
                .force(elevationStiffness, elevationDampingRatio)
                .after(
                    fadeSpring(alphaTargetValue)
                        .force(alphaStiffness, alphaDampingRatio)
                        .translationXSpring(translationXTargetValue)
                        .force(translationStiffness, translationDampingRatio)
                        .translationYSpring(translationYTargetValue)
                        .force(translationStiffness, translationDampingRatio)
                        .after(
                            elevationSpring(targetValue = elevationFull)
                                .force(elevationStiffness, elevationDampingRatio)
                        )
                ).start {
                    itemView.alpha = alphaFull
                    itemView.elevation = elevationFull
                    itemView.translationX = translationOrigin
                    itemView.translationY = translationOrigin
                    onAnimateChangeFinished(this@startChangeAnimation, oldItem)
                }
        }
    }

    private fun View.startFadeElevationInAnimation(onAnimationFinished: () -> Unit) {
        fadeInSpring().force(stiffness = alphaStiffness, alphaDampingRatio)
            .after(
                elevationSpring(targetValue = elevationFull)
                    .force(stiffness = elevationStiffness, dampingRatio = elevationDampingRatio)
            )
            .start {
                onAnimationFinished()
            }
    }

    private fun View.startFadeElevationOutAnimation(onAnimationFinished: () -> Unit) {
        elevationSpring(targetValue = elevationNone)
            .force(elevationStiffness, elevationDampingRatio)
            .after(
                fadeOutSpring().force(stiffness = alphaStiffness, alphaDampingRatio)
            )
            .start {
                alpha = alphaFull
                elevation = elevationFull
                onAnimationFinished()
            }
    }
}
