package com.movie.flicks.utils;

import android.util.Log;

import com.movie.flicks.models.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author tejalpar
 */
public class VideoResponseParser {
    public static String TAG = VideoResponseParser.class.getSimpleName();

    public static Video parseVideoDetailsFromJson(JSONObject response) {
        Video video = new Video();
        try {
            JSONArray resultsJson = response.getJSONArray("results");

            if (resultsJson.length() > 0) {
                JSONObject item = resultsJson.getJSONObject(0);

                video.videoId = item.optInt("id");
                video.size = item.optInt("size");
                video.key = item.optString("key");
                video.site = item.optString("site");
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing json: " + e.getMessage());
        }
        return video;
    }
}
