package com.wj.babapao.http;


import androidx.annotation.NonNull;

import com.wj.babapao.db.UserEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author HuXiangLiang
 * @class describe
 */

public interface ApiService {

    String BASE_TL_URL = "tl/";

    String BASE_MOBILE_SERVER_URL = "api/mobile/";


    /***
     *  下载文件
     * @param url
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> downLoadFile(@NonNull @Url String url);

    @POST("api/common/giftList")
    Observable<HttpResultModel<List<UserEntity>>> giftList(@Body Map<String, Object> params);


}
