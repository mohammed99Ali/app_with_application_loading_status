package com.udacity



import android.animation.ValueAnimator
import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.udacity.databinding.ActivityMainBinding

open class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var button: LoadingButton
    private var downloadID: Long = 0
    lateinit var radioButton: RadioGroup
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action
    private var url: String = ""
    private var radioName : String = "nothing"
    private var statueOfDownload : String = "nothing"
    private val savedInstanceState = Bundle()
    private var isDownload = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        button = findViewById(R.id.custom_button)
        radioButton = findViewById(R.id.group)

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))



        createChannel(CHANNEL_ID, getString(R.string.notification_channel_name))

        button.setOnClickListener {

            when(radioButton.checkedRadioButtonId){
                R.id.radio_glide -> {
                    url = glide
                    radioName ="glide"
                }
                R.id.radio_load_app -> {
                    url = loadApp
                    radioName ="load app"
                }
                R.id.radio_retrofit -> {
                    url = retrofit
                    radioName ="retrofit"
                }
            }

            if(url != "") {
                download()
                sharedViewModel.saveValues(radioName,statueOfDownload)
            } else {
                val toast = Toast.makeText(applicationContext,getString(R.string.please_select_the_button),Toast.LENGTH_LONG).show()

            }     }


    }

       private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

            val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager

            val query = DownloadManager.Query()
            query.setFilterById(id!!)

            val cursor = downloadManager.query(query)
            if (cursor.moveToFirst()) {

                    val notificationManager = ContextCompat.getSystemService(applicationContext,NotificationManager::class.java) as NotificationManager
                    notificationManager.sendNotification(applicationContext.getString(R.string.notification_description), applicationContext,"channelId", file = radioName, statue = statueOfDownload)
                    valueAnimator.repeatCount = 0
                //valueAnimator.pause()
                valueAnimator.cancel()
                }}
    }


    private fun download() {
        val request =
            DownloadManager.Request(Uri.parse(url))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request)// enqueue puts the download request in the queue.


        val downloadStatus = downloadManager.getUriForDownloadedFile(downloadID)

        if (downloadStatus != null) {
            statueOfDownload="Successful"
                    }
        else if(downloadStatus == null) {
            statueOfDownload="failed"
                    } else {
            statueOfDownload="wrong"

        }

        if (intent.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE){
            val notificationManager = ContextCompat.getSystemService(applicationContext,NotificationManager::class.java) as NotificationManager
            notificationManager.sendNotification(applicationContext.getString(R.string.notification_description), applicationContext,"channelId", file = radioName, statue = statueOfDownload)
            }

        }


    private fun createChannel(channelId: String, channelName: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.notification_channel_name_description)

            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }}

    companion object {
        private const val glide =
            "https://github.com/bumptech/glide/archive/refs/heads/master.zip"
        private const val loadApp =
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/masterXXX.zip"
        private const val retrofit =
            "https://github.com/square/retrofit/archive/refs/heads/master.zip"

        private const val CHANNEL_ID = "channelId"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putAll(savedInstanceState)
    }

}