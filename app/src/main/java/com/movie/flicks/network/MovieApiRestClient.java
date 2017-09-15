package com.movie.flicks.network;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author tejalpar
 */
public class MovieApiRestClient {

    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), getRequestParams(params), responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    private static RequestParams getRequestParams(RequestParams params) {
        if (params == null) {
            params = new RequestParams();
        }
        params.put("api_key", API_KEY);
        return params;
    }
}
