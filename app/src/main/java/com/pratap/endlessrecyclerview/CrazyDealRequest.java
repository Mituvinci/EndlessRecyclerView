package com.pratap.endlessrecyclerview;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import Libraries.Connectivity;
import Libraries.GsonRequest;
import Libraries.NetworkRetryPolicy;
import Libraries.RequestCallback;
import Libraries.Volley;

/**
 * Created by has9 on 3/28/16.
 */
public class CrazyDealRequest {

    private static final String TAG = "DealCrazyDealsRequest";



    public void getCrazyDeals(final Context context,
                              final RequestCallback<CrazyData[]> requestCallback,
                              final LimitModel limit) {
        String url = "";
        Gson gson = new Gson();
        String requestBody = gson.toJson(limit);
        if(!Connectivity.isInternetConnected(context)) {
            Log.e(TAG, "getCrazyDeals: No Internet");
            return;
        }

        GsonRequest<CrazyData[]> serviceCall = new GsonRequest<>(Request.Method.POST, url,
                CrazyData[].class, null, requestBody, new Response.Listener<CrazyData[]>() {
            @Override
            public void onResponse(CrazyData[] response) {
                requestCallback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ", error);
            }
        });
        serviceCall.setRetryPolicy(NetworkRetryPolicy.getRetryPolicyForFifteenSecond());
        Volley.getInstance(context).addToRequestQueue(serviceCall);
    }

    public void getCrazyDealsCount(final Context context,
                                   final RequestCallback<CountModel> requestCallback) {
        String url = "http://192.168.0.139/CrazyDeals/GetCrazyDealsCount";
        if(!Connectivity.isInternetConnected(context)) {
            Log.e(TAG, "getCrazyDeals: No Internet");
            return;
        }

        GsonRequest<CountModel> serviceCall = new GsonRequest<>(Request.Method.GET, url,
                CountModel.class, null, null,
                new Response.Listener<CountModel>() {
                    @Override
                    public void onResponse(CountModel response) {
                        requestCallback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: ", error);
                    }
                }
        );
    }
}
