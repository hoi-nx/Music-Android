package com.sun.music_66.util

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.sun.music_66.MusicApplication
import com.sun.music_66.constant.Constants
import com.sun.music_66.data.dto.TrackDto
import java.io.*
import java.net.IDN
import java.net.URL

object SongUtils {
    @SuppressLint("StaticFieldLeak")
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var notificationManager: NotificationManager
    fun createNotification(trackDto: TrackDto){
        notificationManager = MusicApplication.applicationContext!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel("id", "an", NotificationManager.IMPORTANCE_LOW)
            notificationChannel.description = "no sound"
            notificationChannel.setSound(null, null)
            notificationChannel.enableLights(false)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationBuilder = NotificationCompat.Builder(MusicApplication.applicationContext!!, "id")
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setContentTitle("Preparing download ${trackDto.title}")
                .setDefaults(0)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)
                .setOnlyAlertOnce(true)
                .setProgress(100, 0, true)
                .setAutoCancel(true)
        notificationManager.notify(0, notificationBuilder.build())

    }
    private fun updateNotification(currentProgress: Int, nameSong: String) {
        Log.d("Downloaded: ", currentProgress.toString() + "")
        notificationBuilder.setProgress(100, currentProgress, false)
        notificationBuilder.setContentTitle("Downloading")
        notificationBuilder.setContentText("$nameSong: $currentProgress%")
        notificationManager.notify(0, notificationBuilder.build())
    }
    fun download(urlString: String, trackDto: TrackDto): Boolean {
        try {
            val songDir = songDirectory()
            if (!songDir!!.exists()) {
                songDir.mkdirs()
            }
            val url = URL(urlString)
            val connection = url.openConnection()
            val length = connection.contentLength
            val input: InputStream = BufferedInputStream(connection.getInputStream())
            val output: OutputStream = FileOutputStream(songFile(trackDto.id))
            val data = ByteArray(1024)
            var count = input.read(data)
            var total = count
            while (count != -1) {
                val persente = total * 100 / length
                Log.d("DOWNLOAD",persente.toString())
                updateNotification(currentProgress = persente,nameSong = trackDto.title)
                output.write(data, 0, count)
                count = input.read(data)
                total += count
            }
            onDownloadComplete(true,trackDto.title)
            output.flush()
            output.close()
            input.close()
            return true
        } catch (e: Exception) {
            onDownloadComplete(false,trackDto.title)
            e.printStackTrace()
            return false
        }
    }
    private fun onDownloadComplete(isDownloadAndSaveSuccess: Boolean, nameSong: String) {
        if (isDownloadAndSaveSuccess) {
            notificationBuilder.setContentText("Download Complete $nameSong")
        } else {
            notificationBuilder.setContentText("Download Error $nameSong")
        }
        notificationBuilder.setContentTitle(null)
        notificationManager.cancel(0)
        notificationBuilder.setProgress(0, 0, false)
        notificationBuilder.setOngoing(false)
        notificationManager.notify(0, notificationBuilder.build())
    }
    fun cancel(){
        if(::notificationManager.isInitialized) notificationManager.cancel(0)

    }

    private fun songDirectory() =
            MusicApplication.applicationContext?.getDir(Constants.SONGS_DIRECTORY, Context.MODE_PRIVATE)

    fun songFile(id: Int) = File(songDirectory(), "$id")
}