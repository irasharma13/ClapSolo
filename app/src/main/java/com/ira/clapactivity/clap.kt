package com.ira.clapactivity

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class clap : ComponentActivity(), SensorEventListener {

    lateinit var sensorManager: SensorManager
    lateinit var sensorProximity: Sensor
    lateinit var mediaPlayer: MediaPlayer

    val valueProximity = mutableStateOf(0f)

    @OptIn(ExperimentalUnitApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xFF66CCFF)) // Background color
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(color = Color(0xFF3399CC)) // Background color for the header
                ) {
                    Text(
                        text = "Simulate Clapping with Your Phone",
                        fontSize = 30.sp,
                        modifier = Modifier.padding(16.dp),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Proximity Value:",
                    fontSize = 24.sp,
                    color = Color(0xFF666666), // Text color
                    modifier = Modifier.padding(8.dp)
                )

                Text(
                    text = valueProximity.value.toString(),
                    fontSize = 36.sp,
                    color = Color(0xFF333333), // Proximity value color
                )
            }
        }





        // sensor stuff
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val sensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        sensorManager.registerListener(
            this,
            sensorProximity,
            SensorManager.SENSOR_DELAY_NORMAL
        )

        // media player stuff
        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.clap)
    }

    override fun onPause() {
        super.onPause()

        sensorManager.unregisterListener(this)
        mediaPlayer.release()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        valueProximity.value = event!!.values[0]

        // "clap!"
        if (event.values[0] == 0f)
            mediaPlayer.start()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}