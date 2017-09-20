package cn.lhzs.service.impl;


import cn.lhzs.data.bean.Article;
import cn.lhzs.data.bean.Catalog;
import cn.lhzs.data.bean.Product;
import cn.lhzs.data.common.ArticleCatalogEnum;
import cn.lhzs.data.dao.ArticleMapper;
import cn.lhzs.data.dao.CatalogMapper;
import cn.lhzs.result.RequestResult;
import cn.lhzs.service.intf.ArticleService;
import cn.lhzs.service.intf.CatalogService;
import cn.lhzs.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

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

//    @Override
//    public JSONObject getArticleList(String data) {
//        JSONObject jsonObject = JSONObject.parseObject(data).getJSONObject("reqData");
//        Integer page = jsonObject.getInteger("page");
//        Integer size = jsonObject.getInteger("pageSize");
//        String type = jsonObject.getString("type");
//        Article article = new Article();
//        article.setPage(page);
//        article.setSize(size);
//        if (type != null) {
//            if (type.length() == 1) {
//                article.setSearchType("%" + type + "%");
//                article.setNotSearchType("%" + type + type + "%");
//            } else {
//                article.setSearchType("%" + type + "%");
//            }
//        }
//
//        List<Article> articleList = getArticleList(article);
//        List newList = new ArrayList();
//        for (int i = 0; i < articleList.size(); i++) {
//            Article article1 = articleList.get(i);
//            String typeText = getTypeText(article1.getType());
//            String time = new SimpleDateFormat("yyyy-MM-dd").format(article1.getCreateTime());
//            if (typeText.length() > 10) {
//                typeText = typeText.substring(0, 10) + "...";
//            }
//
//            article1.setType(typeText);
//            article1.setTime(time);
//            newList.add(article1);
//        }
//
//        Map countMap = new HashMap();
//        if (type != null && !"".equals(type)) {
//            countMap.put("searchType", type);
//        }
////        Integer count = articleMapper.selectCount(countMap);
//        Integer count = 20;
//        Integer totalPage = count / size;
//        if (count % size != 0) {
//            totalPage = totalPage + 1;
//        }
//
//        JSONObject prodJson = new JSONObject();
//        prodJson.put("articleList", newList);
//        prodJson.put("totalPage", totalPage);
//        prodJson.put("page", page);
//
//        return prodJson;
//    }

//    public List<Article> getArticleList(Article article) {
//        Integer page = article.getPage();
//        Integer size = article.getSize();
//        if (page == null || size == null) {
//            article.setIndex(0);
//            article.setSize(20);
//        } else {
//            article.setIndex((page - 1) * size);
//            article.setSize(size);
//        }
////        return articleMapper.selectListByType(article);
//        return null;
//    }


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

    private String getTypeText(String type) {
        String typeText = "";
        String[] typeArr = type.split(",");
        for (int i = 0; i < typeArr.length; i++) {
            typeText += ArticleCatalogEnum.get(Integer.parseInt(typeArr[i])).getName();
        }
        if (!typeText.equals("")) {
            typeText = typeText.substring(0, typeText.length() - 1);
            if (typeText.length() > 15) {
                typeText = typeText.substring(0, 15) + "...";
            }
        }
        return typeText;
    }

    @Override
    public int getArticleCount(Article article) {
//        return articleMapper.selectCountByCondition(getSearchArticleCondition(article));
        return 0;
    }

    @Override
    public void deleteArticle(Long id) {
        articleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Article> searchArticle(Article article) {
//        startPage(article.getPage(), article.getSize());
//        return articleMapper.selectByCondition(getSearchArticleCondition(article));
//        return articleMapper.selectAll();
        return null;
    }

    private Condition getSearchArticleCondition(Article article) {
        Condition condition = new Condition(Article.class);
        Example.Criteria criteria = condition.createCriteria();
        if (article == null) {
            article = new Article();
        }
        if (StringUtil.isNotEmptyString(article.getType())) {
            criteria.andLike("type", "%" + article.getType() + "%");
            criteria.andNotLike("type", "%" + article.getType() + article.getType() + "%");
        }
        return condition;
    }
}
