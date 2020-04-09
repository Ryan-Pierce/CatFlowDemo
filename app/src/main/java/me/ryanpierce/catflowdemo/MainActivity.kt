package me.ryanpierce.catflowdemo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Context is nullable solely to use the double-bang
        //   as exclamation in english.
        val to_me: Context? = this
        val cat = Cat()

        // Make the cat meow when the app first starts
        // Could also do this with Flow.onStart{}
        //   on the clicks() flow but I wanted to
        //   use launch.
        launch {
            cat speak to_me!!
        }

        // Also make the cat meow whenever you poke it
        findViewById<ImageView>(R.id.catImageView)
            .clicks()
            .onEach {
                cat speak to_me!!
            }
            .launchIn(this)

    }
}

fun View.clicks(): Flow<Unit> = callbackFlow {
    setOnClickListener { offer(Unit) }
    awaitClose { setOnClickListener(null) }
}.conflate()
