package com.example.movementapp.deserializers

import com.example.movementapp.adapters.Phone
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type


class NumberSchemaDeserialzer : JsonDeserializer<Phone> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Phone {
        val phone = Phone()
        val repoJsonObject = json.asJsonObject
        phone.verify_key = repoJsonObject["verify_key"].asString
        phone.expires_in = repoJsonObject["expires_in"].asInt
        return phone
    }
}