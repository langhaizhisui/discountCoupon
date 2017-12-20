package cn.lhzs.config;

import java.util.ResourceBundle;

/**
 * Created by ZHX on 2017/12/20.
 */
public final class Resources {
    /**
     * es引擎搜索配置
     */
    public static final ResourceBundle JEST = ResourceBundle.getBundle("config/jest");

    public static void main(String[] args) {
        System.out.println(Resources.JEST.getString("jest.ip"));
    }
}
