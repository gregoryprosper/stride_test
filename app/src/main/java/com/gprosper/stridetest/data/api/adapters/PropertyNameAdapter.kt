package com.gprosper.stridetest.data.api.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

/**
 * Created by gregprosper on 23,May,2021
 */
class PropertyNameAdapter {
    @FromJson
    @PropertyName
    fun fromJson(json: Map<String, Any?>): String {
        return json.getValue("name") as String
    }

    @ToJson
    fun toJson(@PropertyName name: String): String {
        return name
    }
}

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class PropertyName