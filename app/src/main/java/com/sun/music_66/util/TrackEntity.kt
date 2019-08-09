package com.sun.music_66.util

import androidx.annotation.StringDef

@StringDef(
        TrackEntity.ARTWORK_URL,
        TrackEntity.DESCRIPTION,
        TrackEntity.DOWNLOADABLE,
        TrackEntity.STREAMABLE,
        TrackEntity.DURATION,
        TrackEntity.GENRE,
        TrackEntity.ID,
        TrackEntity.PUBLISHER_METADATA,
        TrackEntity.PLAYBACK_COUNT,
        TrackEntity.LIKES_COUNT,
        TrackEntity.DOWNLOAD_COUNT,
        TrackEntity.TITLE,
        TrackEntity.PUBLIS,
        TrackEntity.DOWNLOAD_URL,
        TrackEntity.PUBLISHER
)
annotation class TrackEntity {
    companion object {
        const val ARTWORK_URL = "artwork_url"
        const val DESCRIPTION = "description"
        const val DOWNLOADABLE = "downloadable"
        const val DURATION = "duration"
        const val GENRE = "genre"
        const val ID = "id"
        const val TITLE = "title"
        const val PUBLISHER_METADATA = "publisher_metadata"
        const val STREAMABLE = "streamable"
        const val PLAYBACK_COUNT = "playback_count"
        const val LIKES_COUNT = "likes_count"
        const val DOWNLOAD_COUNT = "download_count"
        const val PUBLIS = "public"
        const val DOWNLOAD_URL = "download_url"
        const val PUBLISHER = "publisher_metadata"
    }
}
