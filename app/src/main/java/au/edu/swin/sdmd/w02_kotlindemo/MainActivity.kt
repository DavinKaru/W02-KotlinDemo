package au.edu.swin.sdmd.w02_kotlindemo

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var counter: Counter
    private lateinit var counterTextView: TextView

    // A key to identify and save the counter value when the screen rotates
    private val COUNTER_VALUE_KEY = "counter_value"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Make the app take up the full screen, including the areas around the camera (like notches)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )

        // If the device has a cutout (notch), let the app's layout extend into that space
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            val params = window.attributes
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = params
        }

        // Make the status bar (top of screen) and navigation bar (bottom) icons black
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                )

        // Change the color of the status bar to white
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.white)

        // Set up the counter object (use the saved value if the screen rotated)
        counter = Counter("Main Counter")

        // If the screen was rotated or the app restarted, get the saved counter value
        if (savedInstanceState != null) {
            counter.count = savedInstanceState.getInt(COUNTER_VALUE_KEY, 0)  // Restore counter value
        }

        // Find the text view in the layout and display the counter's value
        counterTextView = findViewById(R.id.counterTextView)
        updateCounterDisplay()

        // Find the increment button and set it to increase the counter when clicked
        val incrementButton: Button = findViewById(R.id.incrementButton)
        incrementButton.setOnClickListener {
            counter.increment()  // Increase the counter
            updateCounterDisplay()  // Update the displayed counter value
        }

        // Find the reset button and set it to reset the counter when clicked
        val resetButton: Button = findViewById(R.id.resetButton)
        resetButton.setOnClickListener {
            counter.count = 0  // Reset the counter to 0
            updateCounterDisplay()  // Update the displayed counter value
        }
    }

    // Save the counter value when the screen rotates or the app is paused
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(COUNTER_VALUE_KEY, counter.count)  // Save the counter's current value
    }

    // Update the text view to show the current counter value
    private fun updateCounterDisplay() {
        counterTextView.text = "${counter.name}: ${counter.count}"
    }
}