package cn.lhzs.common.config;

import cn.lhzs.result.ResponseCode;

import java.util.Optional;
import java.util.ResourceBundle;

public final class Resources {

    public static final ResourceBundle EMAIL = ResourceBundle.getBundle("jest");

    public static String getMessage(Integer key) {
        return Optional.ofNullable(ResponseCode.get(key).getDescp()).orElse("");
    }

}
