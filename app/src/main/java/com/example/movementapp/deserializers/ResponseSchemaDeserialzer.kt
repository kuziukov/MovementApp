package com.example.movementapp.deserializers

import com.example.movementapp.adapters.ResponseAPI
import com.google.gson.*
import java.lang.reflect.Type


class ResponseSchemaDeserialzer : JsonDeserializer<ResponseAPI> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): ResponseAPI {
        val response = ResponseAPI()
        val repoJsonObject = json.asJsonObject
        response.code = repoJsonObject["code"].asInt
        response.status = repoJsonObject["status"].asString
        val result = repoJsonObject["result"]
        val resultJsonObject: JsonObject = result.asJsonObject
        response.result = resultJsonObject.asJsonObject
        return response
    }
}