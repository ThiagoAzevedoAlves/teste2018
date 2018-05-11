package br.hight.teste2018.network;

import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Thiago Azevedo on 28/04/18.
 */

public class RestClient {
    private static final String URL = "https://api.themoviedb.org/";
    private static final String imageURL = "https://image.tmdb.org/t/p/w500/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .followRedirects(false)
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header("Content-Type", "application/json")
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                }
            })
            .addInterceptor(
                    new HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY)
            );

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(
                    new GsonBuilder()
                            .create()
            ));

    private static Retrofit.Builder imageBuilder = new Retrofit.Builder()
            .baseUrl(imageURL)
            .addConverterFactory(GsonConverterFactory.create(
                    new GsonBuilder()
                            .create()
            ));

    private static Retrofit retrofit;

    public static <S> S createService(Class<S> serviceClass) {
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .removeHeader("Authorization")
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });
        builder.client(httpClient.build());
        retrofit = builder.build();

        return getRetrofit().create(serviceClass);
    }

    public static <S> S createImageService(Class<S> serviceClass) {
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .removeHeader("Authorization")
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });
        imageBuilder.client(httpClient.build());
        retrofit = imageBuilder.build();

        return getRetrofit().create(serviceClass);
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = builder
                    .client(httpClient.build()).build();
        }
        return retrofit;
    }
}
