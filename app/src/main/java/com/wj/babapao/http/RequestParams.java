package com.wj.babapao.http;

import android.text.TextUtils;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.NetworkUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author HuXiangLiang
 * @name IsecLiveSDK
 * @class name：com.isec.livesdk.library.http
 * @class describe
 * @time 2018/6/8 0008 15:39
 * @change
 * @chang time
 * @class describe
 */

public class RequestParams {

    private String userId;

    private final int PAGE_SIZE_COUNT = 20;

    public static final String PAGE_NUMBER = "pageNumber";
    public static final String PAGE_SIZE = "pageSize";

    public RequestParams() {
    }

    public Map<String, Object> getUserIdParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", this.userId);
        return params;
    }

    public Map<String, Object> getUserIdByIdParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", this.userId);
        return params;
    }

    public Map<String, Object> getUserIdParams(int pageNum) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", this.userId);
        params.put(PAGE_NUMBER, pageNum);
        params.put(PAGE_SIZE, PAGE_SIZE_COUNT);
        return params;
    }

    public Map<String, Object> getAnchorIdParams(int pageNum) {
        Map<String, Object> params = new HashMap<>();
        params.put("anchorId", this.userId);
        params.put(PAGE_NUMBER, pageNum);
        params.put(PAGE_SIZE, PAGE_SIZE_COUNT);
        return params;
    }

    public Map<String, Object> getDefaultParams() {
        return new HashMap<>();
    }

    public Map<String, Object> getRankHiddenParams(boolean isChecked) {
        Map<String, Object> params = new HashMap<>();
        params.put("hidden", isChecked ? "1" : "0");
        return params;
    }

    public Map<String, Object> getLiveId(String liveId) {
        Map<String, Object> params = new HashMap<>();
        params.put("liveId", liveId);
        return params;
    }

    public Map<String, Object> getRecommendParams(String anonymous, String liveId) {
        Map<String, Object> params = new HashMap<>();
        params.put("anonymous", anonymous);
        params.put("liveId", liveId);
        return params;
    }



    private String getUpdateUserId() {
        return this.userId;
    }

    public Map<String, Object> getTagPageListParams(String tag, int pageNum) {
        Map<String, Object> params = new HashMap<>();
        params.put("tag", tag);
        params.put(PAGE_NUMBER, pageNum);
        params.put(PAGE_SIZE, PAGE_SIZE_COUNT);
        return params;
    }

    public Map<String, Object> getPageListByIdParams(int pageNum) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", this.userId);
        params.put(PAGE_NUMBER, pageNum);
        params.put(PAGE_SIZE, PAGE_SIZE_COUNT);
        return params;
    }

    /***
     * 分页请求参数
     * @param pageNum
     * @return
     */
    public Map<String, Object> getPageListParams(int pageNum) {
        Map<String, Object> params = new HashMap<>();
        params.put(PAGE_NUMBER, pageNum);
        params.put(PAGE_SIZE, PAGE_SIZE_COUNT);
        return params;
    }

    /***
     * 分页请求参数
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Map<String, Object> getPageListParams(int pageNum, int pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.put(PAGE_NUMBER, pageNum);
        params.put(PAGE_SIZE, pageSize);
        return params;
    }

    /***
     * 根据关键字搜索主播列表
     * @param keyword
     * @param pageNum
     * @return
     */
    public Map<String, Object> getSearchAnchorListParams(String keyword, int pageNum) {
        Map<String, Object> params = new HashMap<>();
        params.put("key", keyword);
        params.put("userId", this.userId);
        params.put(PAGE_NUMBER, pageNum);
        params.put(PAGE_SIZE, PAGE_SIZE_COUNT);
        return params;
    }

    public Map<String, Object> getPageListByKeyParams(String keyword, int pageNum) {
        Map<String, Object> params = new HashMap<>();
        params.put("key", keyword);
        params.put(PAGE_NUMBER, String.valueOf(pageNum));
        params.put(PAGE_SIZE, PAGE_SIZE_COUNT);
        return params;
    }



    /***
     * 关注、取消关注
     * @param status
     * @return
     */
    public Map<String, Object> getAttentionAnchorParams(String anchorId, int status) {
        Map<String, Object> params = new HashMap<>();
        params.put("follower", this.userId);
        params.put("userId", anchorId);
        params.put("followFlag", String.valueOf(status));
        return params;
    }

    /***
     * 排行榜请求参数
     * @param dateType
     * @return
     */
    public Map<String, Object> getHomeTopParams(String dateType) {
        Map<String, Object> params = new HashMap<>();
        params.put("dateType", dateType);
        params.put("userId", this.userId);
        return params;
    }

    public Map<String, Object> getHomeStrengthTopParams(String dateType) {
        Map<String, Object> params = new HashMap<>();
        params.put("dateType", dateType);
        return params;
    }

    /***
     * 首页轮播图
     * @param id
     * @return
     */
    public Map<String, Object> getBannerListParams(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return params;
    }






    public Map<String, Object> getUserParams(String id) {
        Map<String, Object> params = new HashMap<>();

        params.put("id", id);
        return params;
    }

    /**
     * 获取观众端直播间结束信息参数
     *
     * @param userId
     * @param liveId
     * @return
     */
    public Map<String, Object> getLiveEndInfoParams(String userId, String liveId, String liveCount) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("liveId", liveId);
        params.put("liveCount", liveCount);
        return params;
    }

    /***
     * 主播开播中---点击主播头像
     * @param id
     * @return
     */
    public Map<String, Object> getAnchorInfoParams(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return params;
    }

    /***
     * 我要开播->准备页面初始化(上次的审核数据)
     * @return
     */
    public Map<String, Object> getPreStartLiveInfoParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", this.userId);
        return params;
    }

    /**
     * 根据直播间ID查询本次直播信息
     *
     * @param liveId
     * @return
     */
    public Map<String, Object> getAnchorLiveInfoParams(String liveId, String streamName) {
        Map<String, Object> params = new HashMap<>();
        params.put("liveId", liveId);
//        params.put("streamName", streamName);
        return params;
    }

    public Map<String, Object> getUploadLiveCoverParams(String liveCoverUrl) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", this.userId);
        params.put("liveCoverUrl", liveCoverUrl);
        params.put("recomCoverUrl", "");
        return params;
    }

    public Map<String, Object> getUploadLiveCoverParams(String userId, String liveCoverUrl, String recomCoverUrl) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("liveCoverUrl", liveCoverUrl);
        params.put("recomCoverUrl", recomCoverUrl);
        return params;
    }

    /***
     *
     * @param type
     * @return
     */
    public Map<String, Object> getContributionListParams(String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("anchorId", this.userId);
        params.put("dateType", type);
        return params;
    }

    public Map<String, Object> getContributionListParams(String type, String anchorId) {
        Map<String, Object> params = new HashMap<>();
        params.put("anchorId", anchorId);
        params.put("dateType", type);
        return params;
    }

    /***
     * 房管设置
     * @param userId
     * @param action    1、设置房管，2取消房管
     * @return
     */
    public Map<String, Object> getHouseSettingParams(String userId, int action) {
        Map<String, Object> params = new HashMap<>();
        params.put("anchorId", this.userId);
        params.put("userIds", userId);
        params.put("action", String.valueOf(action));
        return params;
    }

    /***
     * 禁言和取消禁言
     * @param userId
     * @param duration 禁言截止时间的秒的时间戳(取消禁言时非必填)
     * @param action    "1"禁言， “2”取消禁言
     * @return
     */
    public Map<String, Object> getBannedSettingParams(String userId, String duration, int action) {
        Map<String, Object> params = new HashMap<>();
        params.put("anchorId", this.userId);
        params.put("userId", userId);
        params.put("duration", duration);
        params.put("action", String.valueOf(action));
        return params;
    }

    /***
     * 搜索昵称，获取用户在直播间里的信息
     * @param nickname
     * @return
     */
    public Map<String, Object> getSearchUsersParams(String nickname) {
        Map<String, Object> params = new HashMap<>();
        params.put("anchorId", this.userId);
        if (!TextUtils.isEmpty(nickname)) {
            params.put("nickname", nickname);
        }
        return params;
    }

    /***
     * 用户退出
     * @return
     */
    public Map<String, Object> getExitSDKParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", this.userId);
        return params;
    }

    /***
     * 删除单条观看记录
     * @return
     */
    public Map<String, Object> getDelWatchHistoryParams(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return params;
    }

    /***
     * 发送验证码
     * @param phone
     * @param countryCode
     * @return
     */
    public Map<String, Object> getSendPhoneCodeParams(String phone, String countryCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        params.put("countryCode", countryCode);
        params.put("methodId", "001");
        return params;
    }

    /***
     *获取用户余额
     * @return
     */
    public Map<String, Object> getUserOverParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", this.userId);
        params.put("methodId", "006");
        return params;
    }

    public Map<String, Object> getDeviceParams(int num) {
        Map<String, Object> params = new HashMap<>();
        params.put("deviceId", DeviceUtils.getAndroidID());
        params.put("deviceType", "2");
        params.put("deviceOS", DeviceUtils.getSDKVersionName());
        params.put("deviceModel", DeviceUtils.getManufacturer());
        params.put("linkType", NetworkUtils.isWifiConnected() ? "2" : "1");
        params.put("num", String.valueOf(num));
        return params;
    }

    /***
     * 进入直播间事件统计
     * @param roomId
     * @return
     */
    public Map<String, Object> getLiveStatisticsParams(String roomId) {
        Map<String, Object> params = new HashMap<>(getDeviceParams(3));
        params.put("roomId", roomId);
        return params;
    }

    /***
     * 进入直播间时间事件统计
     * @param roomId
     * @return
     */
    public Map<String, Object> getLiveTimeStatisticsParams(String roomId) {
        Map<String, Object> params = new HashMap<>(getDeviceParams(4));
        params.put("roomId", roomId);
        return params;
    }



    /***
     * 礼物赠送事件统计
     * @param giftId
     * @param giftTypeId
     * @param giftTypeName
     * @return
     */
    public Map<String, Object> getGiftStatisticsParams(String giftId, String giftTypeId, String giftTypeName) {
        Map<String, Object> params = new HashMap<>(getDeviceParams(5));
        params.put("giftId", giftId);
        params.put("giftTypeId", giftTypeId);
        params.put("giftTypeName", giftTypeName);
        return params;
    }

    /**
     * banner点击事件统计
     *
     * @param adId
     * @param adName
     * @return
     */
    public Map<String, Object> getBannerStatisticsParams(String adId, String adName) {
        Map<String, Object> params = new HashMap<>(getDeviceParams(6));
        params.put("adId", adId);
        params.put("adName", adName);
        return params;
    }


    /***
     *获取当前直播间在线用户列表
     * @return
     */
    public Map<String, Object> getCurrentOnlineUserList(String liveId) {
        Map<String, Object> params = new HashMap<>();
        params.put("liveId", liveId);
        return params;
    }


    /***
     *初始化消息
     * 是否及重连 1 是 0 否
     * 1 以主播身份进入 2 以观众身份进入
     * @return
     */
    public Map<String, Object> getLiveInitInfoParams(String liveId, String enterType, String isReconnect) {
        Map<String, Object> params = new HashMap<>();
        params.put("liveId", liveId);
        params.put("userId", this.userId);
        params.put("enterType", enterType);
        params.put("isReconnect", isReconnect);
        return params;
    }


    /***
     * 主播收到的礼物流水
     * @param pageNum
     * @return
     */
    public Map<String, Object> getLiveCountPageService(int pageNum, int liveCount) {
        liveCount = liveCount + 1;
        Map<String, Object> params = new HashMap<>();
        params.put("anchorId", this.userId);
        params.put(PAGE_NUMBER, pageNum);
        params.put("liveCount", String.valueOf(liveCount));
        params.put(PAGE_SIZE, PAGE_SIZE_COUNT);
        return params;
    }

    public Map<String, Object> getLivePreNoticeParams(String content) {
        Map<String, Object> params = new HashMap<>();
        params.put("content", content);
        params.put("userId", this.userId);
        return params;
    }


    /**
     * 上报推流错误
     *
     * @param liveId
     * @return
     */
    public Map<String, Object> getErrorReportParams(String liveId) {
        Map<String, Object> params = new HashMap<>();
        params.put("liveId", liveId);
        return params;
    }

    /**
     * 主播开播进/出直播间
     * * @param liveId
     *
     * @return
     */
    public Map<String, Object> getAnchorLiveActionParams(String liveId, String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("liveId", liveId);
        params.put("enterType", type);
        return params;
    }

    /***
     * 用户进/出直播间
     * @param type
     * @return
     */
    public Map<String, Object> getUserLiveActionParams(String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", this.userId);
        params.put("enterType", type);
        return params;
    }

    /***
     * 主播守护列表
     * @param anchorId
     * @param pageNum
     * @return
     */
    public Map<String, Object> getAnchorGuardListParams(String anchorId, int pageNum) {
        Map<String, Object> params = new HashMap<>();
        params.put("anchorId", anchorId);
        params.put(PAGE_NUMBER, pageNum);
        params.put(PAGE_SIZE, 100);
        return params;
    }


    /***
     * 直播间内通用广播点击次数更新
     * @param id
     * @return
     */
    public Map<String, Object> getBroadcastClickParams(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return params;
    }

    /***
     * 我的守护信息
     * @param anchorId
     * @return
     */
    public Map<String, Object> getPersonalGuardInfoParams(String anchorId) {
        Map<String, Object> params = new HashMap<>();
        params.put("anchorId", anchorId);
        params.put("userId", this.userId);
        return params;
    }

    /***
     * 获取websocket地址
     * @param liveId
     * @param enterType
     * @param isReconnect
     * @return
     */
    public Map<String, Object> getWebSocketAddressParams(String liveId, String enterType, String isReconnect) {
        Map<String, Object> params = new HashMap<>();
        params.put("liveId", liveId);
        params.put("userId", this.userId);
        params.put("enterType", enterType);
        params.put("isReconnect", isReconnect);
        return params;
    }


    /**
     * 收支统计参数
     *
     * @param date
     * @return
     */
    public Map<String, Object> getIncomeConsumeParams(String date) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", this.userId);
        params.put("date", date);
        return params;
    }

    /**
     * 收支详情参数
     *
     * @param pageNum
     * @param date
     * @return
     */
    public Map<String, Object> getIncomeConsumeDetailParams(int pageNum, String date) {
        Map<String, Object> params = getUserIdParams(pageNum);
        params.put("date", date);
        return params;
    }

    public Map<String, Object> getIncomeConsumeDetailParams(int pageNum, String date, boolean isFree) {
        Map<String, Object> params = getUserIdParams(pageNum);
        params.put("date", date);
        params.put("isFree", isFree ? "1" : "0");
        return params;
    }

    /***
     * 商城座驾列表(数据域，默认0仅有效的，1显示所有的)
     * @return
     */
    public Map<String, Object> getScopeParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("scope", "0");
        return params;
    }

    /***
     * 所有座驾请求参数
     * @return
     */
    public Map<String, Object> getAllCarParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("scope", "1");
        return params;
    }

    /***
     * 购买座驾请求参数
     * @param carId
     * @param type
     * @param gold
     * @return
     */
    public Map<String, Object> getBuyCarParams(String carId, String type, String gold) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("carId", carId);
        params.put("type", type);
        params.put("gold", gold);
        return params;
    }

    /***
     * 用户装备或者取消座驾
     * @param uniqueId  用户的座驾唯一标识
     * @param isUsed    使用座驾标记，0取消装备，1装备上
     * @return
     */
    public Map<String, Object> getUseCarParams(String uniqueId, String isUsed) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("uniqueId", uniqueId);
        params.put("isUsed", isUsed);
        return params;
    }

    /**
     * 查询礼物宝箱
     *
     * @param liveId
     * @return
     */
    public Map<String, Object> getGiftBoxListParams(String liveId) {
        Map<String, Object> params = new HashMap<>();
        params.put("liveId", liveId);
        return params;
    }



    /**
     * APP任务宝箱完成
     *
     * @return
     */
    public Map<String, Object> getTaskChangeParams(String taskBoxId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", this.userId);
        params.put("taskBoxId", taskBoxId);
        return params;
    }

    /***
     * 广告点击
     * @param adId
     * @return
     */
    public Map<String, Object> getClickAdParams(String adId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", adId);
        return params;
    }

    /***
     * 提交举报信息
     * @param anchorId  被举报人用户id
     * @param contentCode   举报类型编码
     * @param content   举报内容(用户输入)
     * @param image 举报截图
     * @param verifyCode    验证码
     * @return
     */
    public Map<String, Object> getReportLiveParams(String anchorId, String contentCode, String content, String image, String verifyCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("offenceUserId", this.userId);
        params.put("beOffenceUserId", anchorId);
        params.put("contentCode", contentCode);
        params.put("content", content);
        params.put("image", image);
        params.put("verifyCode", verifyCode);
        return params;
    }


    /**
     * 发送喇叭
     *
     * @param content
     * @return
     */
    public Map<String, Object> getTrumpetSendParams(String content) {
        Map<String, Object> params = new HashMap<>();
        params.put("content", content);
        return params;
    }


    /**
     * 更新喇叭飘屏被点击次数
     *
     * @param id
     * @return
     */
    public Map<String, Object> getTrumpetSendUpdateTrumpetClickCountParams(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return params;
    }

    /***
     * 贵族开通以及续费
     * @param nobilityType  贵族类型 1~7
     * @param anchorId  主播ID
     * @param openType  购买方式，1 开通 2 续费
     * @return
     */
    public Map<String, Object> getNobilityBuyParams(String nobilityType, String anchorId, String openType, String liveCount) {

//        int liveCountInt = NumberUtils.string2int(liveCount) + 1;
        Map<String, Object> params = new HashMap<>();
        params.put("nobilityType", nobilityType);
        if (!TextUtils.isEmpty(anchorId)) {
            params.put("anchorId", anchorId);
        }
        params.put("openType", openType);
        params.put("liveCount", liveCount);
        return params;
    }

    /***
     * 直播间贵宾席位列表
     * @param liveId
     * @return
     */
    public Map<String, Object> getVipSeatListParams(String liveId) {
        Map<String, Object> params = new HashMap<>();
        params.put("liveId", liveId);
        return params;
    }

    /***
     *
     * @param isChecked
     * @return
     */
    public Map<String, Object> getNobilityEnterHideParams(boolean isChecked) {
        Map<String, Object> params = new HashMap<>();
        params.put("isHide", isChecked ? "1" : "0");
        return params;
    }

    /***
     * 获取当前转盘奖品数据
     * @param liveId    直播间ID
     * @param isLuxury  是否是豪华轮盘(转盘类型 1普通，20豪华)
     * @return
     */
    public Map<String, Object> getTurntableAwardInfoParams(String liveId, boolean isLuxury) {
        Map<String, Object> params = new HashMap<>();
        params.put("liveId", liveId);
        params.put("type", isLuxury ? "20" : "1");
        return params;
    }

    /***
     * 点击转盘抽奖
     * @param liveId    直播间ID
     * @param isLuxury  是否是豪华轮盘(转盘类型 1普通，20豪华)
     * @param drawTimes 连抽次数：1，10，100
     * @param version   版本号
     * @return
     */
    public Map<String, Object> getTurntableDrawParams(String liveId, boolean isLuxury, int drawTimes, String version) {
        Map<String, Object> params = getTurntableAwardInfoParams(liveId, isLuxury);
        params.put("drawTimes", String.valueOf(drawTimes));
        params.put("version", version);
        return params;
    }

    public Map<String, Object> getTurntableLuckValueList(String liveId) {
        Map<String, Object> params = new HashMap<>();
        params.put("liveId", liveId);
        return params;
    }

    public Map<String, Object> getLotteryTicketParams(int pageNum, String date, int lotteryTicketBalanceType) {
        Map<String, Object> params = getPageListParams(pageNum);
        params.put("userId", this.userId);
        params.put("date", date);
        params.put("lotteryTicketBalanceType", String.valueOf(lotteryTicketBalanceType));
        return params;
    }

    /***
     * 用户抽奖记录
     * @param dateStr   时间字符串，“2019-10-25”
     * @return
     */
    public Map<String, Object> getTurntableDrawRecordParams(int pageNum, String dateStr) {
        Map<String, Object> params = getPageListParams(pageNum, 30);
        params.put("dateStr", dateStr);     //时间字符串，“2019-10-25”
        return params;
    }
}
