package com.zachbartholomew.redditlurker.deserializer;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.zachbartholomew.redditlurker.model.RedditComment;
import com.zachbartholomew.redditlurker.model.RedditLink;
import com.zachbartholomew.redditlurker.model.RedditListing;

import java.lang.reflect.Type;

/**
 * Deserialize the Reddit object into a subclass based on its 'kind' field.
 */

public class RedditObjectJsonDeserializer implements JsonDeserializer {

    private static final String TAG = RedditObjectJsonDeserializer.class.getSimpleName();


    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        if (json == null || !json.isJsonObject()) {
            return null;
        }

        try {
            JsonObject jsonObject = json.getAsJsonObject();
            String kind = jsonObject.get("kind").getAsString();

            return context.deserialize(jsonObject.get("data"), getClassForKind(kind));

        } catch (JsonParseException e) {
            Log.e(TAG, "deserialize: Could not serialize Reddit element: " + json.toString());
            return null;
        }
    }

    private Class getClassForKind(String kind) {

        switch (kind) {
            case "Listing":
                return RedditListing.class;

            case "t1":
                return RedditComment.class;

            case "t3":
                return RedditLink.class;

            default:
                Log.e(TAG, "getClassForKind: Unsupported Reddit kind: " + kind);
                return null;
        }
    }
}
