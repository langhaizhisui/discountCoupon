package cn.lhzs.service.impl;

import cn.lhzs.data.bean.Article;
import cn.lhzs.data.bean.Catalog;
import cn.lhzs.data.dao.ArticleMapper;
import cn.lhzs.data.dao.CatalogMapper;
import cn.lhzs.result.RequestResult;
import cn.lhzs.service.intf.ArticleService;
import cn.lhzs.service.intf.CatalogService;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZHX on 2017/5/7.
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    Logger logger = Logger.getLogger(ArticleServiceImpl.class);

    @Resource
    public ArticleMapper articleMapper;

    @Override
    public List<Article> getArticleList() {
        return null;
    }

    @Override
    public void addAticle(Article article) {
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
        if (articleMapper.insert(article) != 0) {
            generatorHtml(article);

            result.setCode(200);
            result.setMsg("生成文章成功");
        }

        return result;
    }

    @Override
    public List<Article> getArticleList(String type) {
        List<Article> articleList = articleMapper.selectListByType("%"+type+"%");
//        for(int i =0;i<articleList.size();i++){
//            Article article = articleList.get(i);
//            String typeText = getTypeText(article.getType());
//            article.setType(typeText);
//        }
        return articleList;
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
            paramMap.put("title",article.getTitle());
            paramMap.put("typeText",getTypeText(article.getType()));
            paramMap.put("author",article.getAuthor());
            paramMap.put("createTime",article.getCreateTime());
            paramMap.put("content", article.getContent());

            Writer writer = new OutputStreamWriter(new FileOutputStream(getClass().getResource("../../../../../../").getPath() + article.getSrc()), "UTF-8");
            template.process(paramMap, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTypeText(String type) {
        String[] typeArr = type.split(",");
        String typeText = "";
        for (int i = 0; i < typeArr.length; i++) {
            String typeValue = typeArr[i];
            if ("1".equals(typeValue)) {
                typeText += "家居装修,";
            } else if ("2".equals(typeValue)) {
                typeText += "选购导购,";
            } else if ("3".equals(typeValue)) {
                typeText += "旅游景点,";
            } else if ("4".equals(typeValue)) {
                typeText += "娱乐趣闻,";
            } else if ("5".equals(typeValue)) {
                typeText += "健康养生,";
            } else if ("6".equals(typeValue)) {
                typeText += "日常生活,";
            } else if ("7".equals(typeValue)) {
                typeText += "母婴育儿,";
            } else if ("8".equals(typeValue)) {
                typeText += "数码科技,";
            } else if ("9".equals(typeValue)) {
                typeText += "游戏资讯,";
            } else if ("10".equals(typeValue)) {
                typeText += "服装鞋包,";
            } else if ("11".equals(typeValue)) {
                typeText += "美妆护肤,";
            } else if ("12".equals(typeValue)) {
                typeText += "城市房产,";
            } else if ("13".equals(typeValue)) {
                typeText += "金融理财,";
            } else if ("14".equals(typeValue)) {
                typeText += "汽车出行,";
            } else if ("15".equals(typeValue)) {
                typeText += "品牌热点,";
            } else if ("16".equals(typeValue)) {
                typeText += "人生指南,";
            } else if ("17".equals(typeValue)) {
                typeText += "美食菜谱,";
            } else if ("18".equals(typeValue)) {
                typeText += "奢侈时尚,";
            }
        }
        if (!typeText.equals("")) {
            typeText = typeText.substring(0, typeText.length() - 1);
            if (typeText.length() > 10) {
                typeText = typeText.substring(0, 9);
            }
        }
        return typeText;
    }
}
