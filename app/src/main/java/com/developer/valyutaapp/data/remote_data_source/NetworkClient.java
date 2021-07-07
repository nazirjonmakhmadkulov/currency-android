package com.developer.valyutaapp.data.remote_data_source;

import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class NetworkClient {

    public static Retrofit retrofit;

    public static Retrofit getRetrofit(){

        if(retrofit==null) {
            HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
            OkHttpClient.Builder okClient = new OkHttpClient.Builder();

            logger.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            okClient.addInterceptor(logger);
            okClient.cache(null);

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://nbt.tj/ru/kurs/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            SimpleXmlConverterFactory.createNonStrict(
                                  new Persister(new AnnotationStrategy())
                            )
                    )
                    .client(okClient.build())
                    .build();
        }

        return retrofit;
    }
}
