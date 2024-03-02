import com.example.ice_cream.enums.Status
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class StatusDeserializer : JsonDeserializer<Status> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?) =
        when (val statusString = json?.asString) {
            "available" -> Status.AVAILABLE
            "melted" -> Status.MELTED
            "unavailable" -> Status.UNAVAILABLE
            else -> throw IllegalArgumentException("Unknown status: $statusString")
        }
}
