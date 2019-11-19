package com.wj.babapao.utils.language;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import com.blankj.utilcode.util.SPUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;

/**
 * 多语言切换的帮助类
 * http://blog.csdn.net/finddreams
 */
public class MultiLanguageUtil {

    private static final String TAG = "MultiLanguageUtil";
    public static final String SAVE_LANGUAGE = "MultiLanguageSave";

    private MultiLanguageUtil() {
    }

    private static class SingletonHolder {
        private static final MultiLanguageUtil INSTANCE = new MultiLanguageUtil();
    }

    public static MultiLanguageUtil getInstance() {
        return MultiLanguageUtil.SingletonHolder.INSTANCE;
    }

    /**
     * 设置语言
     */
    public void setConfiguration(Context context) {
        Locale targetLocale = getLanguageLocale();
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(targetLocale);
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(configuration, dm);//语言更换生效的代码!
    }

    /**
     * 如果不是英文、简体中文、繁体中文，默认返回简体中文
     *
     * @return
     */
    private Locale getLanguageLocale() {
        int languageType = SPUtils.getInstance().getInt(MultiLanguageUtil.SAVE_LANGUAGE, 0);
        if (languageType == LanguageType.LANGUAGE_FOLLOW_SYSTEM) {
            return getSysLocale();
        } else if (languageType == LanguageType.LANGUAGE_EN) {
            return Locale.ENGLISH;
        } else if (languageType == LanguageType.LANGUAGE_CHINESE_SIMPLIFIED) {
            return Locale.SIMPLIFIED_CHINESE;
        } else if (languageType == LanguageType.LANGUAGE_CHINESE_TRADITIONAL) {
            return Locale.TAIWAN;
        } else if (languageType == LanguageType.LANGUAGE_JAPAN) {
            return Locale.JAPAN;
        } else if (languageType == LanguageType.LANGUAGE_KOREA) {
            return Locale.KOREA;
        }
        getSystemLanguage(getSysLocale());
        return Locale.SIMPLIFIED_CHINESE;
    }

    private String getSystemLanguage(Locale locale) {
        return locale.getLanguage() + "_" + locale.getCountry();

    }

    //以上获取方式需要特殊处理一下
    public Locale getSysLocale() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = LocaleList.getDefault();
            int spType = SPUtils.getInstance().getInt(MultiLanguageUtil.SAVE_LANGUAGE, LanguageType.LANGUAGE_FOLLOW_SYSTEM);
            // 如果app已选择不跟随系统语言，则取第二个数据为系统默认语言
            if (spType != 0 && localeList.size() > 1) {
                locale = localeList.get(1);
            } else {
                locale = localeList.get(0);
            }
        } else {
            locale = Locale.getDefault();
        }
        return locale;
    }

    /**
     * 更新语言
     *
     * @param languageType
     */
    public void updateLanguage(Context context, int languageType) {
        SPUtils.getInstance().put(MultiLanguageUtil.SAVE_LANGUAGE, languageType);
        setConfiguration(context);
        EventBus.getDefault().post(new OnChangeLanguageEvent(languageType));
    }

    /**
     * 获取到用户保存的语言类型
     *
     * @return
     */
    public int getLanguageType() {
        int languageType = SPUtils.getInstance().getInt(MultiLanguageUtil.SAVE_LANGUAGE, LanguageType.LANGUAGE_FOLLOW_SYSTEM);
        if (languageType == LanguageType.LANGUAGE_CHINESE_SIMPLIFIED) {
            return LanguageType.LANGUAGE_CHINESE_SIMPLIFIED;
        } else if (languageType == LanguageType.LANGUAGE_CHINESE_TRADITIONAL) {
            return LanguageType.LANGUAGE_CHINESE_TRADITIONAL;
        } else if (languageType == LanguageType.LANGUAGE_FOLLOW_SYSTEM) {
            return LanguageType.LANGUAGE_FOLLOW_SYSTEM;
        }
//        Log.e(TAG, "getLanguageType" + languageType);
        return languageType;
    }

    public Context attachBaseContext(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return createConfigurationResources(context);
        } else {
            setConfiguration(context);
            return context;
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context createConfigurationResources(Context context) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = getInstance().getLanguageLocale();
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }
}
