package cn.lhzs.service.impl;

import cn.lhzs.base.AbstractBaseService;
import cn.lhzs.data.bean.Article;
import cn.lhzs.data.bean.Config;
import cn.lhzs.data.bean.WebGeneralize;
import cn.lhzs.data.common.ArticleTypeEnum;
import cn.lhzs.data.common.Constants;
import cn.lhzs.data.dao.ArticleMapper;
import cn.lhzs.result.ResponseResult;
import cn.lhzs.service.intf.ArticleService;
import cn.lhzs.service.intf.ConfigService;
import cn.lhzs.util.DateUtil;
import cn.lhzs.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

import static cn.lhzs.result.ResponseResultGenerator.generatorFailResult;
import static cn.lhzs.result.ResponseResultGenerator.generatorSuccessResult;
import static com.github.pagehelper.PageHelper.startPage;
import static java.util.stream.Collectors.toList;

/**
 * Created by ZHX on 2017/11/10.
 */
@CacheConfig(cacheNames = "Article")
@Service
@Transactional
public class ArticleServiceImpl extends AbstractBaseService<Article> implements ArticleService {

    Logger logger = Logger.getLogger(ArticleServiceImpl.class);

    @Resource
    public ArticleMapper articleMapper;

    @Resource
    public ConfigService configService;

    @Override
    public ResponseResult addArticle(Article article) {
        article.setWeight(1);
        article.setState(1);
        if (save(article) != null) {
            return generatorSuccessResult();
        }
        return generatorFailResult("添加文章失败");
    }

    @Override
    public Article getArticle(Long id) {
        Article article = findById(id);
        article.setType(getTypeText(article.getType()));
        article.setcTime(DateUtil.formatDate(article.getCreateTime(), DateUtil.DEFAULT_NO_TIME_FROMAT));
        return article;
    }

    @Override
    public int getArticleCount(Article article) {
        return articleMapper.getArticleCount(article);
    }

    @Override
    public void deleteArticle(Long id) {
        deleteById(id);
    }

    @Override
    public List<Article> searchArticle(Article article) {
        startPage(article.getPage(), article.getSize());
        return articleMapper.getArticleList(article).stream().map(item -> {
            item.setcTime(DateUtil.formatDate(item.getCreateTime(), "yyyy-MM-dd"));
            item.setType(getTypeText(item.getType()));
            return item;
        }).collect(toList());
    }

    @Override
    public void addWebGeneralize(WebGeneralize webGeneralize) {
        Config webGeneralizeConfig = configService.getConfigById(Constants.WEB_GENERALIZE);
        List<WebGeneralize> webGeneralizeList = JSONObject.parseArray(webGeneralizeConfig.getValue(), WebGeneralize.class);
        webGeneralizeList.add(webGeneralize);
        webGeneralizeConfig.setValue(JSONObject.toJSONString(webGeneralizeList));
        configService.updateConfigById(webGeneralizeConfig);
    }

    @Override
    public void deleteWebGeneralize(Integer id) {
        Config webGeneralizeConfig = configService.getConfigById(Constants.WEB_GENERALIZE);
        List<WebGeneralize> webGeneralizeList = JSONObject.parseArray(webGeneralizeConfig.getValue(), WebGeneralize.class);
        webGeneralizeList.remove(id - 1);
        webGeneralizeConfig.setValue(JSONObject.toJSONString(webGeneralizeList));
        configService.updateConfigById(webGeneralizeConfig);
    }

    @Override
    public void updateWebGeneralize(WebGeneralize webGeneralize) {
        Config webGeneralizeConfig = configService.getConfigById(Constants.WEB_GENERALIZE);
        List<WebGeneralize> webGeneralizeList = JSONObject.parseArray(webGeneralizeConfig.getValue(), WebGeneralize.class);
        webGeneralizeList.remove(webGeneralize.getId() - 1);
        webGeneralizeList.add(webGeneralize.getId() - 1, webGeneralize);
        webGeneralizeConfig.setValue(JSONObject.toJSONString(webGeneralizeList));
        configService.updateConfigById(webGeneralizeConfig);
    }

    @Override
    public WebGeneralize getWebGeneralizeDetail(Integer id) {
        Config webGeneralizeConfig = configService.getConfigById(Constants.WEB_GENERALIZE);
        return JSONObject.parseArray(webGeneralizeConfig.getValue(), WebGeneralize.class).get(id - 1);
    }

    @Override
    public List<WebGeneralize> getWebGeneralizeList(WebGeneralize webGeneralize) {
        return JSONObject.parseArray(configService.getConfigById(Constants.WEB_GENERALIZE).getValue(), WebGeneralize.class).stream()
                .filter(item->StringUtil.isNotEmptyString(webGeneralize.getWebName()) && webGeneralize.getWebName().equals(item.getWebName()))
                .collect(toList());
    }

    private String getTypeText(String type) {
        StringBuilder builder = new StringBuilder();
        Arrays.asList(type.split(",")).stream().forEach(e -> {
            builder.append(ArticleTypeEnum.get(Integer.parseInt(e)).getName() + "，");
        });
        return builder.toString().isEmpty() ? "" : builder.toString().substring(0, builder.toString().length() - 1);
    }

}
