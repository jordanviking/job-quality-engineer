package intelipost.cucumber.common.utils;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.entity.StringEntity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RestApi {


    private static String path = "";
    private static List<Header> headers = new ArrayList<>();
    private static List<NameValuePair> parameters = new ArrayList<>();
    private static String jsonBodyString = null;
    private static StringEntity entity = null;

    private static HttpResponse response;
    private static String responseJson;


    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        RestApi.path = path;
    }

    public static List<Header> getHeaders() {
        return headers;
    }

    public static void setHeaders(List<Header> headers) {
        RestApi.headers = headers;
    }

    public static List<NameValuePair> getParameters() {
        return parameters;
    }

    public static void setParameters(List<NameValuePair> parameters) {
        RestApi.parameters = parameters;
    }

    public static String getJsonBodyString() {
        return jsonBodyString;
    }

    public static void setJsonBodyString(String jsonBodyString) {
        RestApi.jsonBodyString = jsonBodyString;
    }

    public static StringEntity getEntity() {
        return entity;
    }

    public static void setEntity(StringEntity entity) {
        RestApi.entity = entity;
    }

    public static HttpResponse getResponse() {
        return response;
    }

    public static void setResponse(HttpResponse response) {
        RestApi.response = response;
    }

    public static String getResponseJson() {
        return responseJson;
    }

    public static void setResponseJson(String responseJson) {
        RestApi.responseJson = responseJson;
    }

}