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
        article.setSrc("article/"+ date.getTime() +".html");
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
            paramMap.put("article", article.getContent());

            Writer writer = new OutputStreamWriter(new FileOutputStream(getClass().getResource("../../../../../../").getPath() + article.getSrc()), "UTF-8");
            template.process(paramMap, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
