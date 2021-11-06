package lv.id.voldemars.deviceapi.utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Extension of Google JSON lib to filter out empty attributes from JSON
 */
public class Gson implements JsonSerializer<Collection<?>> {

    public static com.google.gson.Gson getGson(){
        return new GsonBuilder().registerTypeHierarchyAdapter(
                Collection.class, new Gson()).create();
    }

    @Override
    public JsonElement serialize(Collection<?> src, Type typeOfSrc,
                                 JsonSerializationContext context) {
        if (src == null || src.isEmpty())
            return null;

        JsonArray array = new JsonArray();

        for (Object child : src) {
            JsonElement element = context.serialize(child);
            array.add(element);
        }

        return array;
    }
}
