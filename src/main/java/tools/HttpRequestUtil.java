package tools;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import javax.annotation.Nullable;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class HttpRequestUtil {
    public static final String POST_METHOD = "post";
    public static final String GET_METHOD = "get";
    public static final String DEL_METHOD = "delete";
    public static final String PUT_METHOD = "put";

    public static Response doPostWithBodyParams(String url,Object bodyParams, @Nullable Map<String, String> headers,Map<String, String> cookies){
        return doHttpPostRequest(url,null,bodyParams,null,headers,cookies,POST_METHOD);
    }
    public static Response doPostWithFormParams(String url,Map<String, Object> formParams,@Nullable Map<String, String> headers,Map<String, String> cookies){
        return doHttpPostRequest(url,formParams,null,null,headers,cookies,POST_METHOD);
    }
    public static Response doPostWithQueryParams(String url,Map<String, Object> queryParams,@Nullable Map<String, String> headers,Map<String, String> cookies){
        return doHttpPostRequest(url,null,null,queryParams,headers,cookies,POST_METHOD);
    }
    public static Response doGetWithNothing(String url,@Nullable Map<String, String> headers,Map<String, String> cookies){
        return doHttpPostRequest(url,null,null,null,headers,cookies,GET_METHOD);
    }
    public static Response doGetWithQueryParams(String url,Map<String, Object> queryParams,@Nullable Map<String, String> headers,Map<String, String> cookies){
        return doHttpPostRequest(url,null,null,queryParams,headers,cookies,GET_METHOD);
    }

    private  static Response doHttpPostRequest(String url,@Nullable Map<String, Object> formParams,@Nullable Object bodyParams,@Nullable Map<String, Object> queryParams,@Nullable Map<String, String> headers,@Nullable Map<String, String> cookies,String method){
        RequestSpecification requestSpecification=given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))//信任所有客户端.不用携带任何可信任证书，能够直接请求到服务端
                .log()
                .all();
        if(headers!=null){
            requestSpecification.headers(headers);
        }
        if(cookies!=null){
            requestSpecification.cookies(cookies);
        }
        if(formParams!=null){
            requestSpecification.contentType("application/x-www-form-urlencoded; charset=UTF-8").formParams(formParams);
        }
        if(bodyParams!=null){
            requestSpecification.contentType("application/json").body(bodyParams);
        }
        if(queryParams!=null){
            requestSpecification.contentType("application/x-www-form-urlencoded; charset=UTF-8").queryParams(queryParams);
        }
        Response response = requestSpecification.request(method,url);
//                .when().post(url); 两种方式都可以
        return response;
    }
}
