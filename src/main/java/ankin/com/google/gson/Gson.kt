package ankin.com.google.gson

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.json.JSONObject

/**
 * 帝鲮于 2018/6/15创建.
 */
fun Gson.fromJson(json: String, obj: Any) {
    val proxy = fromJson(json, obj.javaClass)
    val clazz = obj.javaClass
    try {
        clazz.fields.forEach {
            it.isAccessible = true
            it.set(obj, it.get(proxy))
        }
    } catch (e: Exception) {

    }
}

fun Gson.singleInstanceToJson(obj: Any): String {
    val jsonObject = JsonObject()
    val gson = Gson()
    obj.javaClass.declaredFields.forEach {
        if (it.name != "INSTANCE") {
            it.isAccessible = true
            jsonObject.add(it.name, gson.toJsonTree(it.get(obj)))
        }
    }
    return gson.toJson(jsonObject)?:"{}"
}

fun Gson.singleInstanceFromJson(json: String, obj: Any) {
    val gson = Gson()
    val jsonObject = JsonParser().parse(json).asJsonObject
    obj.javaClass.declaredFields.forEach {
        if (it.name != "INSTANCE") {
            it.isAccessible = true
            it.set(obj, gson.fromJson(jsonObject[it.name], it.type))
        }
    }
}