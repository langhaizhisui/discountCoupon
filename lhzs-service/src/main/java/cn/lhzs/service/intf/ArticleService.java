package cn.lhzs.service.intf;

import cn.lhzs.data.bean.Article;
import cn.lhzs.result.RequestResult;
import com.alibaba.fastjson.JSONObject;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * Created by ZHX on 2017/5/7.
 */
public interface ArticleService {

    void addArticle(Article article);

    Article getArticle(Long id);

    RequestResult generatorArticle(Article article);

//    JSONObject getArticleList(String data);

    int getArticleCount(Article article);

    void deleteArticle(Long id);

    List<Article> searchArticle(Article article);
}
