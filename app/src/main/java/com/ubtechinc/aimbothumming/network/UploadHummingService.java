package com.ubtechinc.aimbothumming.network;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadHummingService {
    @Multipart
    @POST("/aimbot-api/asrBuzzDetect/upload")
    Call<ResponseBody> postHummingBytes(@Part MultipartBody.Part requestBody,
                                        @Header("sn") String sn,
                                        @Header("timestamp") long timestamp,
                                        @Header("locationFlag") int locationFlag,
                                        @Header("x") double x,
                                        @Header("y") double y,
                                        @Header("detectType") int detectType);
}
