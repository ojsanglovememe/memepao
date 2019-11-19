package com.wj.babapao.http.utils;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.wj.babapao.BuildConfig;
import com.wj.babapao.http.exception.ServerException;
import com.wj.babapao.utils.ConstantUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;


/**
 * @author HuXiangLiang
 * @name TomatoLive
 * @class name：com.tomatolive.library.http.utils
 * @class describe
 * @time 2018/11/20 0020 14:13
 * @change
 * @chang time
 * @class describe
 */

public class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CustomGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(@NonNull ResponseBody value) throws IOException {

        String response = value.string();
        JsonParser jsonParser = new JsonParser();
        if (isEncrypt(jsonParser, response)) {
            EncryptHttpResultModel resultModel2 = gson.fromJson(response, EncryptHttpResultModel.class);
            ResultMode resultMode = new ResultMode();
            resultMode.code = resultModel2.getCode();
            resultMode.msg = resultModel2.getMessage();
            String jsonData = resultModel2.getJsonData();
            if (jsonParser.parse(jsonData).isJsonArray()) {
                resultMode.data = new JsonParser().parse(jsonData).getAsJsonArray();
            } else {
                resultMode.data = new JsonParser().parse(jsonData).getAsJsonObject();
            }
            response = gson.toJson(resultMode);
        }

        if (BuildConfig.DEBUG) {
            LogUtils.json(response);
        }

        try {

            ResultMode resultMode = gson.fromJson(response, ResultMode.class);
            if (resultMode.getCode() == ConstantUtils.TOKEN_INVALID) {
                value.close();
                throw new ServerException(resultMode.getCode(), resultMode.getMsg());
            }

            return adapter.fromJson(response);
        } finally {
            value.close();
        }
    }

    private boolean isEncrypt(JsonParser jsonParser, String response) {
        try {
            ResultMode resultMode = gson.fromJson(response, ResultMode.class);
            String jsonData = gson.toJson(resultMode.data);

            if (jsonParser.parse(jsonData).isJsonArray()) {
                return false;
            }
            return jsonParser.parse(jsonData).getAsJsonObject().size() == 2 && jsonParser.parse(jsonData).getAsJsonObject().has("key") && jsonParser.parse(jsonData).getAsJsonObject().has("data");

        } catch (Exception e) {
            return false;
        }

    }

    private class ResultMode {
        private int code = 0;
        private String msg;
        private Object data;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}