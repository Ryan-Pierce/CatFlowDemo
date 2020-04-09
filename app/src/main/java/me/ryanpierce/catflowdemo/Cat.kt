package me.ryanpierce.catflowdemo

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.flow.AbstractFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class Cat : AbstractFlow<String>() {

    // Because a cat meows...
    override suspend fun collectSafely(collector: FlowCollector<String>)
        = collector.emit("meow")
}

// A terminal operator to command your cat to speak
@UseExperimental(kotlinx.coroutines.InternalCoroutinesApi::class)
suspend inline infix fun Flow<String>.speak(context: Context)
    = collect(object : FlowCollector<String> {
        override suspend fun emit(value: String)
            = Toast.makeText(context, value, Toast.LENGTH_SHORT).show()
    })