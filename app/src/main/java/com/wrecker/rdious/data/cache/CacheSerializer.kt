package com.wrecker.rdious.data.cache

import androidx.datastore.core.Serializer
import com.wrecker.rdious.domain.entities.Cache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object CacheSerializer : Serializer<Cache> {

    override val defaultValue: Cache
        get() = Cache()

    override suspend fun readFrom(input: InputStream): Cache {
        return try {
            Json.decodeFromString(deserializer = Cache.serializer(),
                string = input.readBytes().decodeToString())
        } catch (exception: Exception) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: Cache, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(Json.encodeToString(Cache.serializer(), value = t).encodeToByteArray())
        }
    }
}