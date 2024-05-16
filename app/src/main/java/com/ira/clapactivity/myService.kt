package com.ira.clapactivity

import android.app.Service
import android.content.Intent
import android.os.AsyncTask
import android.os.Binder
import android.os.IBinder
import android.widget.Toast
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URL

class MyService : Service() {
    private var success = false

    inner class MyBinder : Binder() {
        val service: MyService
            get() = this@MyService
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val url = intent.getStringExtra("url")
        val file = intent.getStringExtra("file")
        com.android.servicesapp.MyService.DoBackgroundTask().execute(url, file)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show()
    }

    private inner class DoBackgroundTask :
        AsyncTask<String?, String?, String?>() {
        protected override fun doInBackground(vararg params: String): String? {
            var count = 0
            val output = File(getExternalFilesDir(null), params[1])
            try {
                val url = URL(params[0])
                val connection = url.openConnection()
                connection.connect()
                val inputStream: InputStream = BufferedInputStream(url.openStream(), 8192)
                val outputStream: OutputStream = FileOutputStream(output.path)
                val data = ByteArray(1024)
                var total: Long = 0
                while (inputStream.read(data).also { count = it } != -1) {
                    total += count.toLong()
                    outputStream.write(data, 0, count)
                }
                outputStream.flush()
                outputStream.close()
                inputStream.close()
                success = true
                onProgressUpdate(params[1])
            } catch (e: Exception) {
            }
            return null
        }

        protected fun onProgressUpdate(file: String?) {
            val intent = Intent(NOTIFICATION)
            intent.putExtra("file", file)
            sendBroadcast(intent)
        }

        override fun onPostExecute(file: String?) {
            stopSelf()
        }
    }

    companion object {
        const val NOTIFICATION = "receiver"
    }
}