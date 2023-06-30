package com.wrecker.rdious.data.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.wrecker.rdious.domain.entities.Cache

val Context.dataStore by dataStore("cache.json", CacheSerializer)

