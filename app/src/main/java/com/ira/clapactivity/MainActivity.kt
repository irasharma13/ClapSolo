package com.ira.clapactivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    var pdf1EditText: EditText? = null
    var pdf2EditText: EditText? = null
    var pdf3EditText: EditText? = null
    var pdf4EditText: EditText? = null
    var pdf5EditText: EditText? = null
    var file1: String? = null
    var file2: String? = null
    var file3: String? = null
    var file4: String? = null
    var file5: String? = null
    var url1: String? = null
    var url2: String? = null
    var url3: String? = null
    var url4: String? = null
    var url5: String? = null
    protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pdf1EditText = findViewById(R.id.pdf1EditText) as EditText?
        pdf2EditText = findViewById(R.id.pdf2EditText) as EditText?
        pdf3EditText = findViewById(R.id.pdf3EditText) as EditText?
        pdf4EditText = findViewById(R.id.pdf4EditText) as EditText?
        pdf5EditText = findViewById(R.id.pdf5EditText) as EditText?
        pdf1EditText!!.setText("https://www.office.xerox.com/latest/SFTBR-04U.PDF")
        pdf2EditText!!.setText("https://media.amazonwebservices.com/AWS_Disaster_Recovery.pdf")
        pdf3EditText!!.setText("https://images-na.ssl-images-amazon.com/images/G/01/AdvertisingSite/pdfs/AmazonBrandUsageGuidelines.pdf")
        pdf4EditText!!.setText("https://docs.aws.amazon.com/aws-technical-content/latest/cost-optimization-storage-optimization/cost-optimization-storage-optimization.pdf")
        pdf5EditText!!.setText("https://docs.aws.amazon.com/aws-technical-content/latest/cost-management/cost-management.pdf")
        downloadPath = getExternalFilesDir(null).toString()
        val intentFilter = IntentFilter()
        intentFilter.addAction("FILE_DOWNLOADED_ACTION")
        registerReceiver(receiver, IntentFilter(MyService.NOTIFICATION))
    }

    fun startDownload(v: View?) {
        url1 = pdf1EditText!!.text.toString()
        url2 = pdf2EditText!!.text.toString()
        url3 = pdf3EditText!!.text.toString()
        url4 = pdf4EditText!!.text.toString()
        url5 = pdf5EditText!!.text.toString()
        file1 = url1!!.substring(url1!!.lastIndexOf('/') + 1)
        file2 = url2!!.substring(url2!!.lastIndexOf('/') + 1)
        file3 = url3!!.substring(url3!!.lastIndexOf('/') + 1)
        file4 = url4!!.substring(url4!!.lastIndexOf('/') + 1)
        file5 = url5!!.substring(url5!!.lastIndexOf('/') + 1)
        fileDownload()
    }

    fun fileDownload() {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show()
        downloadService(url1, file1)
        downloadService(url2, file2)
        downloadService(url3, file3)
        downloadService(url4, file4)
        downloadService(url5, file5)
    }

    private fun downloadService(url: String?, file: String?) {
        val intent = Intent(getBaseContext(), MyService::class.java)
        intent.putExtra("url", url)
        intent.putExtra("file", file)
        startService(intent)
    }

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val bundle = intent.extras
            if (bundle != null) {
                val filename = bundle.getString("file")
                Toast.makeText(
                    getBaseContext(),
                    "$filename File Downloaded Successfully",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    companion object {
        var downloadPath: String? = null
    }
}