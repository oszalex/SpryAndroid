package com.getbro.meetmeandroid.remote;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * Created by rich on 10.11.14.
 */
public enum HttpMethod {
    GET,
    POST,
    DELETE,
    PUT;

    public HttpRequestBase freshRequest(String url) {
        switch (this) {
            case GET: return new HttpGet(url);
            case POST: return new HttpPost(url);
            case PUT: return new HttpPut(url);
            case DELETE: return new HttpDelete(url);
            default: throw new IllegalArgumentException("you forgot to implement me");
        }
    }

}
