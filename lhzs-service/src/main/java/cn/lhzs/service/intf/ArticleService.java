package cn.lhzs.service.intf;

import cn.lhzs.data.bean.Article;
import cn.lhzs.data.bean.WebGeneralize;
import cn.lhzs.result.RequestResult;

import java.util.List;

/**
 * Created by ZHX on 2017/5/7.
 */
public interface ArticleService {

    void addArticle(Article article);

    Article getArticle(Long id);

    RequestResult generatorArticle(Article article);

    int getArticleCount(Article article);

    void deleteArticle(Long id);

    List<Article> searchArticle(Article article);

    void addWebGeneralize(WebGeneralize webGeneralize);

    void deleteWebGeneralize(Integer id);

    void updateWebGeneralize(WebGeneralize webGeneralize);

    WebGeneralize getWebGeneralizeDetail(Integer id);

    List<WebGeneralize> getWebGeneralizeList(WebGeneralize webGeneralize);
}
