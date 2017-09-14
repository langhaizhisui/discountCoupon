package cn.lhzs.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class CommonUtils {

    public static String gerStatusTime(String orgStatus, int status, String time) {
        // 获取员状态数组信息
        JSONArray orgStatusArray = null;
        if (orgStatus == null) {
            orgStatusArray = new JSONArray();
        } else {
            orgStatusArray = JSON.parseArray(orgStatus);
        }
        // 原状态信息格式不正确
        if (null == orgStatusArray) {
            return null;
        }
        // 需要增加的状态信息
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("sts", status);
        jsonObj.put("time", time);
        orgStatusArray.add(jsonObj);
        return orgStatusArray.toJSONString();
    }
}
