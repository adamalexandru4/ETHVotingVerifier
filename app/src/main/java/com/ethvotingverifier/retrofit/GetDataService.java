package com.ethvotingverifier.retrofit;

import com.ethvotingverifier.models.Transaction;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {
    @GET("/api?module=account&action=txlist&startblock=0&endblock=99999999&sort=desc&apikey=J3QBCHU9RRCMU92J88AA3HDK27NI9N9DZJ")
    Call<ResponseEtherScanTransactions> getAllTransactions(@Query("address") String address);
}
