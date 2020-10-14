package br.alexandregpereira.jerry.app

import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import br.alexandregpereira.jerry.ANIMATION_STIFFNESS
import br.alexandregpereira.jerry.animator.BaseItemAnimator
import br.alexandregpereira.jerry.dpToPx
import br.alexandregpereira.jerry.fadeSpring
import br.alexandregpereira.jerry.startElevationSpringAnimation
import br.alexandregpereira.jerry.startFadeSpringAnimation
import br.alexandregpereira.jerry.startTranslationXSpringAnimation
import br.alexandregpereira.jerry.startTranslationYSpringAnimation
import br.alexandregpereira.jerry.translationXSpring
import br.alexandregpereira.jerry.translationYSpring

@RequiresApi(21)
class SpringItemAnimator : BaseItemAnimator() {

    private val elevationStiffness = ANIMATION_STIFFNESS * 2.5f
    private val alphaStiffness = ANIMATION_STIFFNESS * 2f
    private val elevationInitialValue = 0f
    private val alphaInitialValue = 0f
    private val elevationFinalValue = 4f
    private val alphaFinalValue = 1f

    override fun preAnimateAdd(holder: RecyclerView.ViewHolder): Boolean {
        holder.itemView.alpha = 0f
        holder.itemView.elevation = 0f
        return true
    }

    override fun startAddAnimation(
        holder: RecyclerView.ViewHolder
    ): Boolean {
        holder.itemView.startFadeSpringAnimation(
            stiffness = alphaStiffness,
            targetValue = alphaFinalValue
        ) { canceled ->

            if (canceled) {
                onAnimateAddFinished(holder)
                return@startFadeSpringAnimation
            }

            holder.itemView.startElevationSpringAnimation(
                stiffness = elevationStiffness,
                targetValue = elevationFinalValue.dpToPx(holder.itemView.resources)
            ) {
                onAnimateAddFinished(holder)
            }
        }
        return true
    }

    override fun startRemoveAnimation(
        holder: RecyclerView.ViewHolder
    ): Boolean {
        holder.itemView.startElevationSpringAnimation(
            stiffness = elevationStiffness,
            targetValue = elevationInitialValue.dpToPx(holder.itemView.resources)
        ) { canceled ->

            if (canceled) {
                onAnimateRemoveFinished(holder)
                return@startElevationSpringAnimation
            }

            holder.itemView.startFadeSpringAnimation(
                stiffness = alphaStiffness,
                targetValue = alphaInitialValue
            ) {
                holder.itemView.alpha = alphaFinalValue
                holder.itemView.elevation = elevationFinalValue
                onAnimateRemoveFinished(holder)
            }
        }
        return true
    }

    override fun startMoveAnimation(
        holder: RecyclerView.ViewHolder,
        deltaX: Int,
        deltaY: Int
    ): Boolean {
        val onAnimationEnd: RecyclerView.ViewHolder.(completed: Boolean) -> Unit =  { completed ->
            if (completed) {
                onAnimateMoveFinished(this)
            }
        }

        holder.itemView.apply {
            val translationXTargetValue = if (deltaX != 0) 0f else translationX
            val translationYTargetValue = if (deltaY != 0) 0f else translationY

            startTranslationXSpringAnimation(targetValue = translationXTargetValue) { canceled ->
                if (canceled) {
                    if (deltaX != 0) translationX = 0f
                }
                holder.onAnimationEnd(
                    translationYSpring().isRunning.not()
                )
            }
            startTranslationYSpringAnimation(targetValue = translationYTargetValue) { canceled ->
                if (canceled) {
                    if (deltaY != 0) translationY = 0f
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
            alphaTargetValue = alphaInitialValue,
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
            alphaTargetValue = alphaFinalValue,
            translationXTargetValue = 0f,
            translationYTargetValue = 0f,
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
        val onAnimationEnd: RecyclerView.ViewHolder.(completed: Boolean) -> Unit =  { completed ->
            if (completed) {
                itemView.alpha = alphaFinalValue
                itemView.elevation = elevationFinalValue
                itemView.translationX = 0f
                itemView.translationY = 0f
                onAnimateChangeFinished(this, oldItem)
            }
        }

        this.itemView.apply {
            elevation = elevationInitialValue
            startFadeSpringAnimation(
                stiffness = alphaStiffness,
                targetValue = alphaTargetValue
            ) {
                this@startChangeAnimation.onAnimationEnd(
                    translationYSpring().isRunning.not()
                            && translationXSpring().isRunning.not()
                )
            }

            startTranslationXSpringAnimation(targetValue = translationXTargetValue) {
                this@startChangeAnimation.onAnimationEnd(
                    translationYSpring().isRunning.not()
                            && fadeSpring().isRunning.not()
                )
            }
            startTranslationYSpringAnimation(targetValue = translationYTargetValue) {
                this@startChangeAnimation.onAnimationEnd(
                    fadeSpring().isRunning.not()
                            && translationXSpring().isRunning.not()
                )
            }
        }
    }
}