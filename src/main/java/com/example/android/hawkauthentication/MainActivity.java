package com.example.android.hawkauthentication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
private TextView textViewResult;
private JsonPlaceHolderApi holderApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewResult=findViewById(R.id.text_view_result);
        Gson gson= new GsonBuilder().serializeNulls().create();
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request originalRequest=chain.request();
                        Request newRequest=originalRequest.newBuilder()
                          .header("Interceptor_header","xyx")
                                .addHeader("Interceptor_header","xyz")
                                .build();

                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(loggingInterceptor)
                .build();
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    holderApi=retrofit.create(JsonPlaceHolderApi.class);
        getPost();
        getComment();
        createpost();
        updatePost();
       deletePost();
    }



    private void getPost(){
        Map<String,String> parameters=new HashMap<>();
        parameters.put("userId","1");
        parameters.put("_sort","id");
        parameters.put("_order","desc");
        Call<List<Post>>call=holderApi.getPosts(parameters);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code "+response.code());
                    return;
                }
                List<Post>posts=response.body();
                for(Post post:posts){
                    String content="";
                    content +="Id :"+post.getId()+"\n";
                    content +="UserId :"+post.getUserId()+"\n";
                    content +="Title :"+post.getTitle()+"\n";
                    content +="Text :"+post.getText()+"\n\n";
                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
    private void getComment(){
        Call<List<Comment>>call=holderApi.getComments("posts/1/comments");
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                     if(!response.isSuccessful()){
                         textViewResult.setText("Code"+response.code());
                         return;
                     }
               List<Comment> comments=response.body();
                     for (Comment comment:comments){
                         String content="";
                                 content+="ID:"+comment.getId()+"\n";
                                 content+="Post ID:"+comment.getPostId()+"\n";
                                 content+="Name :"+comment.getName()+"\n";
                                 content+="Email :"+comment.getE_mail()+"\n";
                                 content+="Text :"+comment.getText()+"\n\n";
                                 textViewResult.append(content);
                     }

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                 textViewResult.setText(t.getMessage());
            }
        });

    }
    private void createpost(){
    Post post=new Post(23,"NewTitle","NewText");
    Map<String ,String> fields=new HashMap<>();
    fields.put("userId","23");
    fields.put("title","NewTitle");
    Call<Post>call=holderApi.createPost(fields);
    call.enqueue(new Callback<Post>() {
        @Override
        public void onResponse(Call<Post> call, Response<Post> response) {
            if(!response.isSuccessful()){
                textViewResult.setText("Code"+response.code());
                return;
            }
            Post postResponse=response.body();
            String content="";
            content+="Code:"+response.code()+"\n";
            content +="Id :"+postResponse.getId()+"\n";
            content +="UserId :"+postResponse.getUserId()+"\n";
            content +="Title :"+postResponse.getTitle()+"\n";
            content +="Text :"+postResponse.getText()+"\n\n";
            textViewResult.append(content);

        }

        @Override
        public void onFailure(Call<Post> call, Throwable t) {
            textViewResult.setText(t.getMessage());
        }
    });
    }
    private void updatePost() {
        Post post= new Post(12,null ,"NewText");
        Map<String,String>headers=new HashMap<>();
        headers.put("Map_Header","1");
        headers.put("Map_header","2");
        Call<Post>call=holderApi.putPatch(headers,5,post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code"+response.code());
                    return;
                }
                Post postResponse=response.body();
                String content="";
                content+="Code:"+response.code()+"\n";
                content +="Id :"+postResponse.getId()+"\n";
                content +="UserId :"+postResponse.getUserId()+"\n";
                content +="Title :"+postResponse.getTitle()+"\n";
                content +="Text :"+postResponse.getText()+"\n\n";
                textViewResult.append(content);

            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
    private void deletePost(){
        Call<Void>call=holderApi.deletePost(5);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                 textViewResult.setText("Code"+response.code());

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

}
