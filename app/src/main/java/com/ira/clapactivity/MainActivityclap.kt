package com.ira.clapactivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun BlueButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(16.dp)
            .height(60.dp)
            .fillMaxWidth()
            .background(Color(0xFF4A90E2)), // Set the background color here
        content = { Text(text, fontSize = 20.sp, color = Color.White) }
    )
}

@ExperimentalMaterial3Api
@ExperimentalUnitApi
class MainActivityclap: ComponentActivity() {

    private var refreshKey = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Welcome to Clap Activity",
                    fontSize = 36.sp,
                    color = Color(0xFF4A90E2), // Change this color to blue
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(32.dp))

                BlueButton(
                    onClick = {
                        val intent = Intent(applicationContext, clap::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }
                        applicationContext.startActivity(intent)
                    },
                    text = "Start Clapping"
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Make some noise!",
                    fontSize = 18.sp,
                    color = Color(0xFF4A90E2) // Change this color to blue
                )
            }
        }




    }
}
