package com.wj.babapao.http.function;


import com.wj.babapao.http.HttpResultModel;
import com.wj.babapao.http.exception.ServerException;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 服务器结果处理函数
 *
 */
public class ServerResultFunction<T> implements Function<HttpResultModel<T>, T> {

    @Override
    public T apply(@NonNull HttpResultModel<T> tResultModel)  {

        if(!tResultModel.isSuccess()){
            throw new ServerException(tResultModel.getCode(), tResultModel.getMessage());
        }

        return tResultModel.getData();
    }
}
