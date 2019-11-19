package com.wj.babapao.http.exception;

import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;
import com.wj.babapao.R;
import com.wj.babapao.utils.SystemUtils;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.ParseException;

import retrofit2.HttpException;


/**
 * 错误/异常处理工具
 */
public class ExceptionEngine {

    public static final int UN_KNOWN_ERROR = 1000;//未知错误
    public static final int ANALYTIC_SERVER_DATA_ERROR = 1001;//解析(服务器)数据错误
    public static final int CONNECT_ERROR = 1003;//网络连接错误
    public static final int TIME_OUT_ERROR = 1004;//网络连接超时

    public static final int SERVER_ERROR = 2000;//无网络

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof HttpException) {             //HTTP错误
//            HttpException httpExc = (HttpException) e;
            ex = new ApiException(e, SERVER_ERROR);
            ex.setMsg(SystemUtils.getResString(R.string.fq_net_poor_retry));  //均视为网络错误
            return ex;
        } else if (e instanceof ServerException) {    //服务器返回的错误
            ServerException serverExc = (ServerException) e;
            ex = new ApiException(serverExc, serverExc.getCode());
            ex.setMsg(serverExc.getMsg());
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException || e instanceof MalformedJsonException) {  //解析数据错误
            ex = new ApiException(e, ANALYTIC_SERVER_DATA_ERROR);
            ex.setMsg("");
            return ex;
        } else if (e instanceof ConnectException) {//连接网络错误
            ex = new ApiException(e, CONNECT_ERROR);
            ex.setMsg(SystemUtils.getResString(R.string.fq_text_no_network));
            return ex;
        } else if (e instanceof SocketTimeoutException) {//网络超时
            ex = new ApiException(e, TIME_OUT_ERROR);
                ex.setMsg(SystemUtils.getResString(R.string.fq_net_timeout_retry));
            return ex;
        } else {  //未知错误
            ex = new ApiException(e, UN_KNOWN_ERROR);
            ex.setMsg(SystemUtils.getResString(R.string.fq_net_poor_retry));
            return ex;
        }
    }

    public static boolean isExceptionErrorCode(int code) {
        return code == UN_KNOWN_ERROR || code == ANALYTIC_SERVER_DATA_ERROR || code == CONNECT_ERROR || code == TIME_OUT_ERROR || code == SERVER_ERROR;
    }

}
