package com.example.linkgame;

import android.app.Activity;
import android.content.Context;


// 主题管理器
public class ThemeManager {

    private String[] mThemes = {"美队蓝", "铁人金", "雷神棕", "浩克绿", "毒液黑"};

    private static ThemeManager instance;

    public static ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }
        return instance;
    }

    public String[] getThemes() {
        return mThemes;
    }

    /**
     * 设置主题色
     *
     * @param context activity
     * @param theme   主题名称
     */
    public void setTheme(Activity context, String theme) {
        String curTheme = SharedPUtils.getCurrentTheme(context);
        if (curTheme != null && curTheme.equals(theme)) {
            return;
        }

        SharedPUtils.setCurrentTheme(context, theme);
    }

    /**
     * 获取当前主题名
     *
     * @param context 上下文
     * @return 如: 美队蓝
     */
    public String getCurThemeName(Context context) {
        return SharedPUtils.getCurrentTheme(context);
    }

    public void init(Context context) {
        String theme = SharedPUtils.getCurrentTheme(context);
        if (theme.equals(mThemes[0])) {
            context.setTheme(R.style.AppTheme);
        } else if (theme.equals(mThemes[1])) {
            context.setTheme(R.style.AppTheme_Gold);
        } else if (theme.equals(mThemes[2])) {
            context.setTheme(R.style.AppTheme_Brown);
        } else if (theme.equals(mThemes[3])) {
            context.setTheme(R.style.AppTheme_Green);
        } else if (theme.equals(mThemes[4])) {
            context.setTheme(R.style.AppTheme_Black);
        }
    }
}

