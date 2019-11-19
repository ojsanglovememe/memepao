package com.wj.babapao.utils;

/**
 * @author HuXiangLiang
 * @name IsecLiveSDK
 * @class name：com.isec.livesdk.library.utils
 * @class 常量类
 * @time 2018/6/6 0006 11:20
 * @change
 * @chang time
 * @class describe
 */

public interface ConstantUtils {
    /***
     * API接口签名秘钥
     */
//    public String SIGN_API_KEY = "789";     //预发布
     String SIGN_API_KEY = "8zy8nbs9lyddx02slcz8ypmwcr2tlu72";     //测试
    /***
     * socket签名秘钥
     */
//    public String SIGN_SOCKET_KEY = "456";    //预发布
     String SIGN_SOCKET_KEY = "8zy8nbs9lyddx02slcz8ypmwcr2tlu72";     //测试

    /***
     * API接口加解密秘钥
     */
//    public String ENCRYPT_API_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIQVlM6t9IBT6OxfQPhAhTAm/lCnzjy5K9mtrlYmlEXc/uNlhIqs0bCUvBPCWZ6UqWGK23LjPxwiC2jy9+i9SdkopsenLKhZV+yf+y4m02gV/wROQ2QsLng1/0IePiSyl0iGgmXsjR+LYcdAGSBBweZ/iNyiyEsnV5FYHS0cLb0XAgMBAAECgYADpmEFURNHlIoENiGieo3zpbAzZF+zl95ZVo5RvSEtyQyWFhESj/H/ciy8UwuM8Ui49FBaHWN6EIrGLKijGs/2kRcmx4mbnK9eQmkQBuRaTfgqc03XTK7LNU+pz3PVTRlfn7GkDfsSWaeDWNtbR1zK1mgoR+JnMfqbM8C0FqOaEQJBAMEzfzqEgkpmEtx9cUfyLPIw0pGviepZtp+lFO6PHQlPszwM/Xof9ZVhIR8oIR+mCJfqqCGoeoWQbAnoiQizoD8CQQCvBHnxnsxBITaq2Wrjod/rDeM3YHRDg6HET9cVKKIIvhSlLFx8KYw+ZbhPxdz219hdFmdjM3PYy1xibucsQi0pAkBDgKypU3b6a6OXajTUQGc3z5siz8ROHz5RlSo1F8e7Yx9qkddWfigeIyuhaTH5jtddzN0ltWnplMZKx/ZpFemdAkEAot86kHWkRZQgKLyucWpKVJeW9QjpCY9tMqDOWx12NUaXNeNjqhSMM+E7tdk/uePCsVZRHotaas1NizkEHzbyiQJAfC0aRuF5AdJ81o8GJ4j0FwnRUiqWS2DPT9n2x16cmhP2v2ik14nQzp2ihML2kE1I7WUtHzFkZv6NnxBthM4Xwg==";
     String ENCRYPT_API_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ4BUmnAVmr+0JE7zL195q1QIg1NAW/zln54MjEM6qv45OpTB/jp2zRdw9i4kNPk/IjrOPYPh4dv4+YgYWQkVs0vzVnzuse9rbfoEKDFbze9pyo3EfLnrSUhe3dJp9BWwnq105/zFPkgbx7Qv5T25CMbLTfD0cFHvOAN/HEN/l0HAgMBAAECgYBnFse1dacsgfqEd3a6v5UsyNaexPpGF7C97SAaPqox798zP185OSMrBG5OTQU5KvLVRltQt4seg5M2xzOjyc3TY4/XGZIQtRA35Sovx/s+NvSy6VD+L5SEJ4r8/SXR8mXKJfxKVg8jNOcWiRR4VuQmIozzPEFqZH9qIVJcVjWt0QJBAONHlIRN4OVFzsC3hhc1yv2vYZzzjXcTw8np9wlx1Qjup3rzVhbsAd1KXqpFCTZYlqSfrudDSwQCEXaFyu32uukCQQCx+LyJOoNbnMbPSkZOoQkQfPjgHqy0yAhqiNKT7ciIVyREFmpbB1Q9ptQWDmMwEBb2ksTBNCrgUOX/IE5KsYJvAkEA3Wz6Y5+gEJ7fHGBwUiKFXnxEZG3gD/gAkrHPjLMLMwWXw7BY2kIaWua+rbJOlFTghwhPlV25MvF04/zbRNVRKQJAE5Lv6Yft+p17oEDjCrLbdFzKYpv9EsUNZ+o0fuCgNZ6f9n0gpXJg6Yb3vJVIg3jBjc0Gptk9/f3nze+XrM9pMQJAQ2EroFmPUTuv86Ghwdjnh0z4DnJkr60a6ccoNiqNZpmTAND6og99djzJqGWFHGDGS/JLfbiVdafzj6QZ5TQBzQ==";
    /***
     * socket加解密秘钥
     */
//    public String ENCRYPT_SOCKET_KEY = "c21d31be-4300-4881-a553-156ebb5df087";  //预发布
     String ENCRYPT_SOCKET_KEY = "246887c3-ee20-4fe8-a320-1fde4a8d10b6";  //测试

    int TYPE_NORMAL = 0;
    int TYPE_NOBILITY = 2;
    int TYPE_TRUMPET = 3;
    int TYPE_GUARD = 1;
    int MAX_ITEM_NUM = 100;

    /*常量*/
    int DEAL_MSG = 10001;
    int DEAL_ENTER_MSG = 10002;
    int DEAL_GUARD_ENTER_MSG = 10003;
    int GIFT_NOTICE = 10004;
    int SYS_NOTICE = 10005;
    int DEAL_GUARD_OPEN_MSG = 10006;
    int DEAL_LOCAL_NOBILITY_OPEN_MSG = 10007;
    int DEAL_RECEIVE_NOBILITY_OPEN_MSG = 10008;
    int DEAL_NOBILITY_ENTER_MSG = 10009;
    int SYS_LUCK_HIT = 10010;
    int RETRY_COUNT = 3;
    int COUNT_DOWN_NUMBER = 3;

    String SHORTENFACE_KEY = "SHORTENFACE_KEY";
    String WHITE_KEY = "WHITE_KEY";
    String BIGEYE_KEY = "BIGEYE_KEY";
    String LIVE_MIRROR_KEY = "LIVE_MIRROR_KEY";
    String LIVE_LABEL_MENU = "labelMenu";
    String LIVE_LAST_LABEL = "lastLabel";
    String LIVE_LAST_TOPIC = "last_topic";
    String LIVE_LAST_DANMU = "last_danmu";
    int REQUEST_CAMERA = 908;
    int REQUEST_ALBUM = 909;

    /***
     * token失效
     */
    int TOKEN_INVALID = 101001;

    int NOBILITY_RECOMMEND_STATUS_ERROR_CODE = 200111;   //推荐失败，该主播当前未开播
    int NOBILITY_RECOMMEND_USED_ERROR_CODE = 200112;   //推荐失败，推荐位使用中!


    //公屏列表显示的消息类型
    int MSG_JION = 0;//进入
    int MSG_SEND_GIFT = 1;//发礼物
    int MSG_CHAT = 3;//聊天
    int MSG_BANNED = 4;//禁言
    int MSG_BANNEDALL = 5;//全局禁言
    int MSG_CTRL = 6;//设为场控
    int MSG_SOCKET = 7;//socket连接提示
    int MSG_SOCKET_CLICK = 2;//socket连接提示
    int MSG_MODIFY = 8;//修改标题
    int MSG_KICK = 9;//被踢了
    int MSG_NOTICE = 10;//被踢了
    int MSG_SPEAK = 11;//发言等级
    int MSG_GUARD = 12;//开通守护
    int MSG_GIFT_BOX = 13;//礼物宝箱
    int MSG_NOBILITY = 14;//贵族开通


    /**
     * 悬浮窗相关
     */
    int SYS_ALERT_CODE = 100;
    String ALERT_VIEW_DIMISS_ACTION = "ALERT_VIEW_DIMISS_ACTION";
    String LIVE_KICK_OUT_ACTION = "LIVE_KICK_OUT_ACTION";

    /***
     * 昵称显示的最大长度
     */
    int USER_NAME_MAX_LENGTH = 6;

    /**
     * 图片选择
     */
    String IMGPATH = "imagePath";
    String IMGDIR = "/tomatoLive/image/";

    String RESULT_ID = "resultID";
    String RESULT_ITEM = "resultItem";
    String RESULT_FLAG = "resultFlag";
    String RESULT_COUNT = "resultCount";

    /***
     * tab标签ID
     */
    String TAB_TAG_ID = "tagId";

    /***
     *
     */
    String TAB_TAG_ALL = "allTagId";


    String AUTH_TYPE = "authType";
    /**
     * 未提交过认证申请
     */
    int ANCHOR_AUTH_NO_SUBMIT = -2;
    /***
     * 待审核
     */
    int ANCHOR_AUTH_CHECK = 0;
    /***
     * 驳回
     */
    int ANCHOR_AUTH_NO = -1;
    /**
     * 已认证
     */
    int ANCHOR_AUTH_YES = 1;

    /***
     * 已关注
     */
    int ATTENTION_TYPE_YES = 1;
    String ATTENTION_TYPE_YES_STR = "1";

    /***
     * 已取消关注
     */
    int ATTENTION_TYPE_NO = 0;
    String ATTENTION_TYPE_NO_STR = "0";

    int EMPTY_TYPE_SEARCH_ANCHOR = 30;
    int EMPTY_TYPE_SEARCH_LIVE = 31;
    int EMPTY_TYPE_SEARCH_INCOME = 32;
    int EMPTY_TYPE_SEARCH_TOP = 33;
    int EMPTY_TYPE_SEARCH_HEAD = 34;
    int EMPTY_TYPE_SEARCH_HOUSE = 35;
    int EMPTY_TYPE_SEARCH_BANNED = 36;
    int EMPTY_TYPE_SEARCH_CONSUME = 37;
    int EMPTY_TYPE_SEARCH_DIALOG_TOP = 38;
    int EMPTY_TYPE_SEARCH_WATCH_RECORD = 39;
    int EMPTY_TYPE_SEARCH_GIFT_RECORD = 40;
    int EMPTY_TYPE_SEARCH_CAR_RECORD = 41;
    int EMPTY_TYPE_SEARCH_RANKING_DIALOG_TOP = 42;
    int EMPTY_TYPE_SEARCH_RANKING_ANCHOR = 43;
    int EMPTY_TYPE_SEARCH_RANKING_USER = 44;
    int EMPTY_TYPE_SEARCH_LOTTERY_RECORD = 45;


    String LIVE_TYPE_STR = "liveTypeStr";
    int LIVE_TYPE_HOME = 1;
    int LIVE_TYPE_SEARCH = 2;
    int LIVE_TYPE_TOP = 3;

    /***
     * 直播标题长度限制
     */
    int MAX_TITLE_LEN = 15;


    String SEARCH_RESULT = "searchResult";
    String IS_AUTH = "isAuth";

    //角色类型-主播
    String ROLE_ANCHOR = "1";
    //角色类型-普通观众
    String ROLE_HOUSE_AUDIENCE = "2";
    //角色类型-房管
    String ROLE_HOUSE_MANAGER = "3";
    //超级管理员
    String ROLE_SUPER_MANAGER = "5";
    String FROM_ALERT_KEY = "FROM_ALERT_KEY";
    String SHOW_MOBIE_TIP = "SHOW_MOBIE_TIP";//已经提示过流量警告框了

    String LIVE_TOKEN_INVALID_ACTION = "LIVE_TOKEN_INVALID_ACTION";//token失效


    /***
     *  直播间模块时间统计
     */
    String STATISTICS_TIME_KEY_ROOM = "timeKeyRoom";

    int LIVE_IMG_ROUND_CORNERS = 6;

    int AVATAR_BORDER_WIDTH = 6;

    /***
     * 推荐列表banner广告
     */
    String AD_TYPE_HOT_LIVE_BANNER = "1";

    /***
     * 全部列表banner广告
     */
    String AD_TYPE_ALL_LIVE_BANNER = "5";

    /***
     * 直播间广告类型
     */
    String AD_TYPE_LIVE_BANNER = "2";

    /***
     * 直播间广告类型
     */
    String AD_TYPE_LIVE_IMG = "3";

    String WEB_VIEW_TITLE = "title";
    String WEB_VIEW_URL = "url";
    String LEAVE_TYPE = "leave";
    String ENTER_TYPE = "enter";

    /***
     * 日榜
     */
    String TOP_DAY = "day";
    /***
     * 周榜
     */
    String TOP_WEEK = "week";
    /***
     * 月榜
     */
    String TOP_MONTH = "month";
    /***
     * 总榜
     */
    String TOP_ALL = "all";

    /***
     * 无守护
     */
    String GUARD_NO_STR = "0";
    /***
     * 周守护
     */
    String GUARD_WEEK_STR = "1";
    /***
     * 月守护
     */
    String GUARD_MONTH_STR = "2";
    /***
     * 年守护
     */
    String GUARD_YEAR_STR = "3";

    /***
     * 发言字数限制
     */
    int SPEAK_WORD_COUNT = 53;
    int SPEAK_WORD_COUNT_10 = 10;
    /***
     * 弹幕发言字数限制
     */
    int SPEAK_WORD_COUNT_DANMU = 15;
    int SPEAK_WORD_TRUMPET_DANMU = 20;

    /***
     * 以主播身份进入
     */
    String LIVE_ENTER_TYPE_ANCHOR = "1";
    /***
     * 以观众身份进入
     */
    String LIVE_ENTER_TYPE_AUDIENCE = "2";

    /***
     * 是否重连 1 是
     */
    String LIVE_IS_RECONNECT_YES = "1";

    /***
     * 是否重连 0 否
     */
    String LIVE_IS_RECONNECT_NO = "0";

    /***
     * 年守护进场动画路径
     */
    String YEAR_GUARD_ENTER_ANIM_PATH = "anim/year_guard_enter.svga";

    /***
     * 月守护进场动画路径
     */
    String MONTH_GUARD_ENTER_ANIM_PATH = "anim/mouth_guard_enter.svga";

    /***
     * 座驾进场动画路径
     */
    String CAR_ENTER_ANIM_PATH = "anim/car_enter.svga";

    /***
     * 年守护开通动画路径
     */
    String YEAR_GUARD_OPEN_ANIM_PATH = "anim/NSH.svga";

    /***
     * 月守护开通动画路径
     */
    String MONTH_GUARD_OPEN_ANIM_PATH = "anim/YSH.svga";
    String GIFT_BOX_ANIM_PATH = "anim/box.svga";
    String GIFT_GIFT_DIALOG_OPEN_ANIM_PATH = "anim/gift_dialog_open_anim.svga";

    /***
     * 默认等级入场特效
     */
    String DEFAULT_ENABLE_JOIN_LEVEL = "10";

    /***
     * webSocket协议消费类型-开通守护
     */
    String SOCKET_CONSUME_TYPE_OPEN_GUARD = "openGuard";
    String SOCKET_CONSUME_TYPE_OPEN_NOBILITY = "openNobility";
    String SOCKET_CONSUME_TYPE_RENEW_NOBILITY = "renewNobility";
    String SOCKET_CONSUME_TYPE_RECOMMEND = "recommend";

    String GIFT_NEED_UPDATE = "200500";//礼物需要更新
    int KICK_OUT_CODE = 200023;//被踢了

    /***
     * 魅力偶像榜
     */
    int RANKING_ALL_CHARM_VALUE = 4;
    /***
     * 实力富豪榜
     */
    int RANKING_ALL_STRENGTH_VALUE = 5;

    /***
     * 座驾7天
     */
    String CAR_TIMES_7 = "7";
    /***
     * 座驾30天
     */
    String CAR_TIMES_30 = "30";

    /***
     * 收益类型-礼物
     */
    int INCOME_TYPE_GIFT = 1;
    /***
     * 收益类型-守护
     */
    int INCOME_TYPE_GUARD = 2;

    /***
     * 收益类型-座驾
     */
    int INCOME_TYPE_CAR = 3;

    /***
     * 收益类型-道具
     */
    int INCOME_TYPE_PROP = 4;

    /***
     * 收益类型-贵族
     */
    int INCOME_TYPE_NOBLE = 5;
    /***
     * 转盘礼物
     */
    int INCOME_TYPE_TURNTABLE_GIFT = 6;

    /**
     * 选择日期
     */
    String RESULT_DATE = "resultDate";

    String PUSH_IFRAME = "PUSH_IFRAME";

    /***
     * 本地资源文件加解密秘钥
     */
    String ENCRYPT_FILE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ4BUmnAVmr+0JE7zL195q1QIg1NAW/zln54MjEM6qv45OpTB/jp2zRdw9i4kNPk/IjrOPYPh4dv4+YgYWQkVs0vzVnzuse9rbfoEKDFbze9pyo3EfLnrSUhe3dJp9BWwnq105/zFPkgbx7Qv5T25CMbLTfD0cFHvOAN/HEN/l0HAgMBAAECgYBnFse1dacsgfqEd3a6v5UsyNaexPpGF7C97SAaPqox798zP185OSMrBG5OTQU5KvLVRltQt4seg5M2xzOjyc3TY4/XGZIQtRA35Sovx/s+NvSy6VD+L5SEJ4r8/SXR8mXKJfxKVg8jNOcWiRR4VuQmIozzPEFqZH9qIVJcVjWt0QJBAONHlIRN4OVFzsC3hhc1yv2vYZzzjXcTw8np9wlx1Qjup3rzVhbsAd1KXqpFCTZYlqSfrudDSwQCEXaFyu32uukCQQCx+LyJOoNbnMbPSkZOoQkQfPjgHqy0yAhqiNKT7ciIVyREFmpbB1Q9ptQWDmMwEBb2ksTBNCrgUOX/IE5KsYJvAkEA3Wz6Y5+gEJ7fHGBwUiKFXnxEZG3gD/gAkrHPjLMLMwWXw7BY2kIaWua+rbJOlFTghwhPlV25MvF04/zbRNVRKQJAE5Lv6Yft+p17oEDjCrLbdFzKYpv9EsUNZ+o0fuCgNZ6f9n0gpXJg6Yb3vJVIg3jBjc0Gptk9/f3nze+XrM9pMQJAQ2EroFmPUTuv86Ghwdjnh0z4DnJkr60a6ccoNiqNZpmTAND6og99djzJqGWFHGDGS/JLfbiVdafzj6QZ5TQBzQ==";

    /***
     * 第三方美颜SDK证书加解密秘钥
     */
    String ENCRYPT_FU_AUTH_KEY = "246887c3-ee20-4fe8-a320-1fde4a8d10b6";

    /***
     * 贵族资源下载加解密秘钥
     */
    String ENCRYPT_NOBILITY_DOWN_KEY = "246887c3-ee20-4fe8-a320-1fde4a8d10b6";

    /***
     * 用户等级区间（1到12）
     */
    int USER_GRADE_INTERVAL_1 = 1;
    int USER_GRADE_INTERVAL_2 = 2;
    int USER_GRADE_INTERVAL_3 = 3;
    int USER_GRADE_INTERVAL_4 = 4;
    int USER_GRADE_INTERVAL_5 = 5;
    int USER_GRADE_INTERVAL_6 = 6;
    int USER_GRADE_INTERVAL_7 = 7;
    int USER_GRADE_INTERVAL_8 = 8;
    int USER_GRADE_INTERVAL_9 = 9;
    int USER_GRADE_INTERVAL_10 = 10;
    int USER_GRADE_INTERVAL_11 = 11;
    int USER_GRADE_INTERVAL_12 = 12;

    /***
     * 用户等级最大值
     */
    int USER_GRADE_MAX_60 = 60;
    int USER_GRADE_MAX_120 = 120;

    /***
     * 首页全部标签banner位置
     */
    int LIVE_BANNER_SPANPOSITION = 6;

    /***
     * 贵族徽章等级
     */
    int USER_NOBLE_GRADE_1 = 1;
    int USER_NOBLE_GRADE_2 = 2;
    int USER_NOBLE_GRADE_3 = 3;
    int USER_NOBLE_GRADE_4 = 4;
    int USER_NOBLE_GRADE_5 = 5;
    int USER_NOBLE_GRADE_6 = 6;
    int USER_NOBLE_GRADE_7 = 7;

    String INPUT_TYPE = "INPUT_TYPE";
    int BIG_ANIM_GIFT_TYPE = 0x900;//播放的礼物动画
    int BIG_ANIM_PROP_TYPE = 0x901;//播放的道具动画
    int BIG_ANIM_OPEN_NOBILITY_TYPE = 0x902;//播放的贵族开通大动画
    int BIG_ANIM_OPEN_GUARD_TYPE = 0x903;//播放的守护开通大动画
    int LEFT_ANIM_OPEN_NOBILITY_TYPE = 0x904;//贵族开通 横幅
    int LEFT_ANIM_OPEN_GUARD_TYPE = 0x905;//守护开通 横幅
    int LEFT_ANIM_ENTER_NOBILITY_TYPE = 0x906;//贵族入场 横幅
    int NO_BAN_TYPE = 0x907;//未禁言状态
    int NORMAL_BAN_TYPE = 0x908;//单独禁言
    int ALL_BAN_TYPE = 0x909;//全局禁言
    int SUPER_BAN_TYPE = 0x910;//超管禁言
    String SHOW_LUCKY_GIFT_TIP = "SHOW_LUCKY_GIFT_TIP";
    String LOAD_LUXURY_BOOM_ANIM = "LOAD_LUXURY_BOOM_ANIM";
    String LOAD_GENERAL_BOOM_ANIM = "LOAD_GENERAL_BOOM_ANIM";

    /***
     * 用户（主播）进出直播间算经验值延迟时间
     */
    long LIVE_EXP_ACTION_DELAY = 10;
}
