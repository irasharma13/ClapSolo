package com.ira.clapactivity

import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.ExperimentalUnitApi

@ExperimentalMaterial3Api
@ExperimentalUnitApi
@Composable
fun MainComposable(
    context: Context,
    counter: MutableState<Int>,
    showDialog: MutableState<Boolean>
) {

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it.data?.let { data ->
                counter.value = counter.value + Integer.parseInt(data.data.toString())
            }
        }

    if (showDialog.value)
        MyAlert(showDialog)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {



        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f, true)
        ) {
            TextButton(
                onClick = { showDialog.value = true },
                colors = ButtonDefaults.buttonColors()) {
                Text("Start dialog")
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f, true)
        ) {
            Text("Thread counter: " + counter.value)
        }
    }
}

@Composable
fun MyAlert(
    showDialog: MutableState<Boolean>
) {
    AlertDialog(
        title = {
            Text("Dialog")
        },
        text = {
            Text("Hey, this is a dialog!")
        },
        confirmButton = {
            TextButton(
                onClick = { showDialog.value = false }
            ) {
                Text("Close")
            }
        },
        onDismissRequest = { showDialog.value = false }
    )
}