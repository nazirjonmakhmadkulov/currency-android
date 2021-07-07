package com.developer.valyutaapp.data.remote_data_source;


import com.developer.valyutaapp.model.ValCurs;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkInterfaces {

    @GET("export_xml.php")
    Observable<ValCurs> getValutes(@Query("date") String date, @Query("export") String exp);

}
