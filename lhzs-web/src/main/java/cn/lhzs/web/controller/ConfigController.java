package cn.lhzs.web.controller;

import cn.lhzs.result.ResponseResult;
import cn.lhzs.service.intf.ConfigService;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static cn.lhzs.result.ResponseResultGenerator.generatorSuccessResult;

/**
 * Created by Administrator on 2017/5/7.
 */
@Controller
@RequestMapping("/config")
public class ConfigController {

    Logger logger = Logger.getLogger(ConfigController.class);

    @Autowired
    public ConfigService configService;

    @RequestMapping("/slideshowPicture/list")
    @ResponseBody
    public ResponseResult getSlideshowPicture() {
        return generatorSuccessResult(new PageInfo(configService.getSlideShowPictureList()));
    }

    @RequestMapping("/slideshowPicture/delete")
    @ResponseBody
    public ResponseResult deleteSlideshowPicture(Integer id) {
        configService.deleteSlideShowPicture(id);
        return generatorSuccessResult();
    }
}
