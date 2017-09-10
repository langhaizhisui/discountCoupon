package cn.lhzs.web.controller;

import cn.lhzs.data.bean.Article;
import cn.lhzs.data.common.Constants;
import cn.lhzs.result.RequestResult;
import cn.lhzs.service.intf.ConfigService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/5/7.
 */
@Controller
@RequestMapping("/config")
public class ConfigController {

    Logger logger = Logger.getLogger(ConfigController.class);

    @Autowired
    public ConfigService configService;

    @RequestMapping("/slideshowPicture")
    @ResponseBody
    public RequestResult getSlideshowPicture(@RequestBody Article article) {
        RequestResult result = new RequestResult();
        result.setCode(200);
        result.setMsg("获取轮播图成功");
        result.setData(configService.getConfigByConfId(Constants.SLIDESHOW_PICTURE));
        return result;
    }
}
