package com.europa.sightup.platformspecific.videoplayer

import android.content.Context
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import java.io.File

@UnstableApi
internal object CacheManager {
    private var cache: SimpleCache? = null

    fun getCache(context: Context): SimpleCache {
        if (cache == null) {
            val cacheSize = 100 * 1024 * 1024 // 100 MB
            val cacheDir = File(context.cacheDir, "video_cache")
            cache = SimpleCache(cacheDir, LeastRecentlyUsedCacheEvictor(cacheSize.toLong()))
        }
        return cache!!
    }

    fun release() {
        cache?.release()
        cache = null // Allow garbage collection
    }
}