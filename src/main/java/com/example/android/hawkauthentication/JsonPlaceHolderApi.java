package com.example.android.hawkauthentication;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {
    @GET("posts")
    Call<List<Post>> getPosts(@Query( "userId") Integer[]userId,
                              @Query("_sort") String sort,
                              @Query("_order") String order
    );
    @GET("posts")
    Call<List<Post>> getPosts(
            @QueryMap Map<String ,String> parameter
    );
    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments( @Path("id") int postId);
    @GET()
    Call<List<Comment>> getComments(@Url String url);
    @POST("Posts")
    Call<Post>createPost(@Body Post post);
    @FormUrlEncoded
    @POST("Posts")
    Call<Post>createPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String text

    );
    @FormUrlEncoded
    @POST("Posts")
    Call<Post> createPost(@FieldMap Map<String,String>fields);
    @Headers({"static_header1:123","static_header2: 456"})
    @PUT("Posts/{id}")
Call<Post>putPost(@Header("Dynamic_header")String header,
                  @Path("id") int id ,
                  @Body Post post);
    @PATCH("Posts/{id}")
    Call<Post>putPatch(@HeaderMap Map<String,String> headers,
            @Path("id") int id ,
                         @Body Post post);
@DELETE("Posts/{id}")
    Call<Void>deletePost(@Path("id")int id);
}
