package com.ubtechinc.aimbothumming.network;

import android.content.Context;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class UploadHummingRespository {

    public static Call<ResponseBody> uploadHumming(Context context, byte[] bytes, String sn, long timestamp, int locationFlag, double x, double y, int detectType) {
        RequestBody body = MultipartBody.create(MultipartBody.FORM, bytes);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", "filename", body);
        return ApiClient.get().getService(context, UploadHummingService.class).postHummingBytes(part, sn, timestamp, locationFlag, x, y, detectType);
    }

}
