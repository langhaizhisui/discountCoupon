package cn.lhzs.service.impl;


import cn.lhzs.data.bean.Article;
import cn.lhzs.data.bean.Config;
import cn.lhzs.data.bean.WebGeneralize;
import cn.lhzs.data.common.ArticleTypeEnum;
import cn.lhzs.data.common.Constants;
import cn.lhzs.data.dao.ArticleMapper;
import cn.lhzs.result.RequestResult;
import cn.lhzs.service.intf.ArticleService;
import cn.lhzs.service.intf.ConfigService;
import cn.lhzs.util.DateUtil;
import cn.lhzs.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.github.pagehelper.page.PageMethod.startPage;

/**
 * Created by ZHX on 2017/5/7.
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    Logger logger = Logger.getLogger(ArticleServiceImpl.class);

    @Resource
    public ArticleMapper articleMapper;

    @Resource
    public ConfigService configService;

    @Override
    public void addArticle(Article article) {
        articleMapper.insert(article);
    }

    @Override
    public Article getArticle(Long id) {
        return articleMapper.selectByPrimaryKey(id);
    }

    @Override
    public RequestResult generatorArticle(Article article) {
        RequestResult result = new RequestResult();
        result.setCode(4000);
        result.setMsg("生成文章失败");

        Date date = new Date();
        article.setWeight(1);
        article.setState(1);
        article.setCreateTime(date);
        article.setUpdateTime(date);
        article.setSrc("article/" + date.getTime() + ".html");
        if (article.getContent().length() > 100) {
            article.setContent(article.getContent().substring(0, 100));
        }

        if (articleMapper.insert(article) != 0) {
            generatorHtml(article);

            result.setCode(200);
            result.setMsg("生成文章成功");
        }

        return result;
    }

    private void generatorHtml(Article article) {
        try {
            //创建一个合适的Configration对象
            Configuration configuration = new Configuration();
            configuration.setDirectoryForTemplateLoading(new File(getClass().getResource("../../../../../../").getPath()));
            System.out.println(getClass().getResource("../../../../../../").getPath());
            configuration.setObjectWrapper(new DefaultObjectWrapper());
            configuration.setDefaultEncoding("UTF-8");   //这个一定要设置，不然在生成的页面中 会乱码
            //获取或创建一个模版。
            Template template = configuration.getTemplate("article/articleGenerator.html");
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("title", article.getTitle());
            paramMap.put("typeText", getTypeText(article.getType()));
            paramMap.put("author", article.getAuthor());
            paramMap.put("createTime", new SimpleDateFormat("yyyy/MM/dd").format(article.getCreateTime()));
            paramMap.put("articleContent", "10");

            Writer writer = new OutputStreamWriter(new FileOutputStream(getClass().getResource("../../../../../../").getPath() + article.getSrc()), "UTF-8");
            template.process(paramMap, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        List<Article> articleList = articleMapper.selectByExample(getSearchArticleCondition(article));
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
        webGeneralizeList.remove(id);
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
        return JSONObject.parseArray(configService.getConfigById(Constants.WEB_GENERALIZE).getValue(), WebGeneralize.class);
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
        Criteria criteria = example.createCriteria();
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
