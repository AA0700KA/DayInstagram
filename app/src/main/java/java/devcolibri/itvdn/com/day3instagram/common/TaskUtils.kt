package java.devcolibri.itvdn.com.day3instagram.common

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.android.gms.tasks.Tasks


fun Task<Void>.toUnit(): Task<Unit> =
    onSuccessTask { Tasks.forResult(Unit) }

fun <T> task(block: (TaskCompletionSource<T>) -> Unit): Task<T> {
    val taskSource = TaskCompletionSource<T>()
    block(taskSource)
    return taskSource.task
}