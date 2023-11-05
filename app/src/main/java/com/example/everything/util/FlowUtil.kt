package com.example.everything.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Flow<T>.collectInLifecycle(
    lifeCycleOwner: LifecycleOwner,
    lifeCycleState: Lifecycle.State = Lifecycle.State.CREATED,
    block: suspend (T) -> Unit
) {
    lifeCycleOwner.lifecycleScope.launch {
        lifeCycleOwner.repeatOnLifecycle(lifeCycleState) {
            collect { value ->
                block(value)
            }
        }
    }
}