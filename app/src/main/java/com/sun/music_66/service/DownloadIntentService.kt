package com.sun.music_66.service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.sun.music_66.data.dto.TrackDto
import com.sun.music_66.util.SongUtils
import com.sun.music_66.util.StringUtils

class DownloadIntentService : IntentService("DownloadIntentService") {

    companion object {

        private const val TAG: String = "DownloadIntentService"

        private const val ACTION_DOWNLOAD = "ACTION_DOWNLOAD"

         const val EXTRA_TRACK = "EXTRA_TRACK"

        const val DOWNLOAD_COMPLETE = "DOWNLOAD_COMPLETE"

        fun startActionDownload(context: Context, trackDto: TrackDto) {
            val intent = Intent(context, DownloadIntentService::class.java).apply {
                action = ACTION_DOWNLOAD
                putExtra(EXTRA_TRACK, trackDto)
            }
            context.startService(intent)
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_DOWNLOAD -> {
                val trackDto=intent.getParcelableExtra(EXTRA_TRACK) as TrackDto
                Log.d("onHandleIntent",trackDto.title)
                SongUtils.createNotification(trackDto)
                handleActionDownload(trackDto)
            }
        }
    }

    private fun handleActionDownload(trackDto: TrackDto) {
        if(SongUtils.download(StringUtils.generateStreamUrl(trackDto.id),trackDto)){
            broadcastDownloadComplete(trackDto)
        }
    }

    private fun broadcastDownloadComplete(trackDto: TrackDto) {
        val intent = Intent(DOWNLOAD_COMPLETE)
        intent.putExtra(EXTRA_TRACK, trackDto)
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        SongUtils.cancel()

    }
}
