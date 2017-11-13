package cn.lhzs.service.impl;

import cn.lhzs.data.bean.Config;
import cn.lhzs.data.bean.WebGeneralize;
import cn.lhzs.data.common.ArticleTypeEnum;
import cn.lhzs.data.common.Constants;
import cn.lhzs.data.dao.ArticleMapper;
import cn.lhzs.data.bean.Article;
import cn.lhzs.result.ResponseResult;
import cn.lhzs.service.intf.ArticleService;
import cn.lhzs.base.AbstractBaseService;
import cn.lhzs.service.intf.ConfigService;
import cn.lhzs.util.DateUtil;
import cn.lhzs.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.CacheConfig;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static cn.lhzs.result.ResponseResultGenerator.generatorFailResult;
import static cn.lhzs.result.ResponseResultGenerator.generatorSuccessResult;
import static com.github.pagehelper.PageHelper.startPage;

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
        return articleMapper.selectCountByExample(getSearchArticleCondition(article));
    }

    @Override
    public void deleteArticle(Long id) {
        articleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Article> searchArticle(Article article) {
        startPage(article.getPage(), article.getSize());
        List<Article> articleList = articleMapper.getArticleList(article);
        for (Article item : articleList) {
            item.setcTime(DateUtil.formatDate(item.getCreateTime(), "yyyy-MM-dd"));
            item.setType(getTypeText(item.getType()));
        }
        return articleList;
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
        List<WebGeneralize> webGeneralizeList = JSONObject.parseArray(webGeneralizeConfig.getValue(), WebGeneralize.class);
        return webGeneralizeList.get(id - 1);
    }

    @Override
    public List<WebGeneralize> getWebGeneralizeList(WebGeneralize webGeneralize) {
        List<WebGeneralize> webGeneralizeList = JSONObject.parseArray(configService.getConfigById(Constants.WEB_GENERALIZE).getValue(), WebGeneralize.class);
        if (getSearchWebGeneralizeList(webGeneralize, webGeneralizeList) != null) {
            return getSearchWebGeneralizeList(webGeneralize, webGeneralizeList);
        }
        return webGeneralizeList;
    }

    private List<WebGeneralize> getSearchWebGeneralizeList(WebGeneralize webGeneralize, List<WebGeneralize> webGeneralizeList) {
        List<WebGeneralize> currWebGeneralizeList = new ArrayList<WebGeneralize>();
        if (StringUtil.isNotEmptyString(webGeneralize.getWebName())) {
            for (int i = 0; i < webGeneralizeList.size(); i++) {
                WebGeneralize currentWebGeneralize = webGeneralizeList.get(i);
                if (webGeneralize.getWebName().equals(currentWebGeneralize.getWebName())) {
                    currWebGeneralizeList.add(currentWebGeneralize);
                }
            }
            return currWebGeneralizeList;
        }
        return null;
    }

    private String getTypeText(String type) {
        String typeText = "";
        String[] typeArr = type.split(",");
        for (int i = 0; i < typeArr.length; i++) {
            typeText += ArticleTypeEnum.get(Integer.parseInt(typeArr[i])).getName() + "，";
        }
        return typeText.length() > 15 ? typeText.substring(0, 15) + "..." : typeText.substring(0, typeText.length() - 1);
    }

    private Example getSearchArticleCondition(Article article) {
        Example example = new Example(Article.class);
        example.orderBy("createTime").desc();
        Example.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmptyString(article.getTitle())) {
            criteria.andEqualTo("title", article.getTitle());
        }
        String typeCode = ArticleTypeEnum.getTypeCode(article.getType());
        if (StringUtil.isNotEmptyString(typeCode)) {
            criteria.andLike("type", "%" + typeCode + "%");
            criteria.andNotLike("type", "%" + typeCode + "0%");
            criteria.andNotLike("type", "%" + typeCode + "1%");
            criteria.andNotLike("type", "%" + typeCode + "2%");
            criteria.andNotLike("type", "%" + typeCode + "3%");
            criteria.andNotLike("type", "%" + typeCode + "4%");
            criteria.andNotLike("type", "%" + typeCode + "5%");
            criteria.andNotLike("type", "%" + typeCode + "6%");
            criteria.andNotLike("type", "%" + typeCode + "7%");
            criteria.andNotLike("type", "%" + typeCode + "8%");
            criteria.andNotLike("type", "%" + typeCode + "9%");
        }
        return example;
    }

}
