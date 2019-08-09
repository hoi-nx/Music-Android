package com.sun.music_66.constant

/**
 * Created by nguyenxuanhoi on 2019-07-15.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
interface Constants {
    companion object {
        const val ARGUMENT_TRACK="ARGUMENT_TRACK"
        const val TIME_DELAY = 2000L
        const val BASE_GENRE_URL =
            "https://api-v2.soundcloud.com/charts?kind=%s&genre=%s&client_id=%s&limit=%d&offset=%d"
        const val BASE_TRENDING_URL = "https://api-v2.soundcloud.com/charts?kind=%s&genre=%s&client_id=%s"
        const val BASE_SEARCH_URL = "http://api.soundcloud.com/tracks?q=%s&client_id=%s&limit=%d&offset=%d"
        const val BASE_DOWNLOAD_URL = "https://api.soundcloud.com/tracks/%d/download?client_id=%s"
        const val BASE_STREAM_URL = "https://api.soundcloud.com/tracks/%d/stream?client_id=%s"
        const val KIND_TOP = "top"
        const val KIND_TREND = "trending"
        const val GENRES_COUNTRY = "soundcloud:genres:country"
        const val ARGUMENT_GENRES = "ARGUMENT_GENRES"
        const val GENRES_ALL_MUSIC = "soundcloud:genres:all-music"
        const val GENRES_ALL_AUDIO = "soundcloud:genres:all-audio"
        const val GENRES_ROCK = "soundcloud:genres:alternativerock"
        const val GENRES_AMBIENT = "soundcloud:genres:ambient"
        const val GENRES_CLASSICAL = "soundcloud:genres:classical"
        const val SONGS_DIRECTORY = "songs"
        const val SONG_FILENAME = "song.mp3"
        const val IMAGE_LARGE = "large"
        const val IMAGE_FULL = "t500x500"
        const val DATABASE_NAME = "music.db"
        const val DATA_VERSION = 1

    }
}
