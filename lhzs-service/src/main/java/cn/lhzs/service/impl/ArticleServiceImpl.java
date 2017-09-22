package cn.lhzs.service.impl;


import cn.lhzs.data.bean.Article;
import cn.lhzs.data.common.ArticleTypeEnum;
import cn.lhzs.data.dao.ArticleMapper;
import cn.lhzs.result.RequestResult;
import cn.lhzs.service.intf.ArticleService;
import cn.lhzs.util.DateUtil;
import cn.lhzs.util.StringUtil;
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

    private String getTypeText(String type) {
        String typeText = "";
        String[] typeArr = type.split(",");
        for (int i = 0; i < typeArr.length; i++) {
            typeText += ArticleTypeEnum.get(Integer.parseInt(typeArr[i])).getName()+"，";
        }
        return typeText.length() > 15 ? typeText.substring(0, 15) + "..." : typeText.substring(0, typeText.length() - 1);
    }

    private Example getSearchArticleCondition(Article article) {
        Example example = new Example(Article.class);
        Criteria criteria = example.createCriteria();
        if (article == null) {
            article = new Article();
        }
        if (StringUtil.isNotEmptyString(article.getType())) {
            criteria.andLike("type", "%" + article.getType() + "%");
            criteria.andNotLike("type", "%" + article.getType() + article.getType() + "%");
        }
        return example;
    }
}
