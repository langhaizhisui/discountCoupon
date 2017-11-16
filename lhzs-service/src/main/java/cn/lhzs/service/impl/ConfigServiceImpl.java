package cn.lhzs.service.impl;

import cn.lhzs.base.AbstractBaseService;
import cn.lhzs.data.bean.Article;
import cn.lhzs.data.bean.Config;
import cn.lhzs.data.bean.SlideShowPicture;
import cn.lhzs.data.common.Constants;
import cn.lhzs.data.dao.ConfigMapper;
import cn.lhzs.service.intf.ConfigService;
import cn.lhzs.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ZHX on 2017/5/7.
 */
@Service
public class ConfigServiceImpl extends AbstractBaseService<Config> implements ConfigService {

    Logger logger = Logger.getLogger(ConfigServiceImpl.class);

    @Resource
    public ConfigMapper configMapper;

    @Override
    public Config getConfigById(final Long id) {
        return findById(id);
    }

    @Override
    public void updateConfigById(Config config) {
        save(config);
    }

    @Override
    public void addConfig(Config config) {
        save(config);
    }

    @Override
    public List<SlideShowPicture> getSlideShowPictureList() {
        String value = getConfigById(Constants.SLIDESHOW_PICTURE).getValue();
        if (StringUtil.isNotEmptyString(value)) {
            return JSONObject.parseArray(value, SlideShowPicture.class).stream()
                    .sorted(Comparator.comparing(SlideShowPicture::getCreateTime).reversed())
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteSlideShowPicture(Integer id) {
        Config currentConfig = getConfigById(Constants.SLIDESHOW_PICTURE);
        List<SlideShowPicture> slideShowPictureList = JSONObject.parseArray(currentConfig.getValue(), SlideShowPicture.class);
        slideShowPictureList.remove(id - 1);
        currentConfig.setValue(JSONObject.toJSONString(slideShowPictureList));
        updateConfigById(currentConfig);
    }
}
