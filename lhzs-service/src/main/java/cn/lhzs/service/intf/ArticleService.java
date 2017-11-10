package cn.lhzs.service.intf;
import cn.lhzs.data.bean.Article;
import cn.lhzs.base.IBaseService;
import cn.lhzs.data.bean.WebGeneralize;
import cn.lhzs.result.ResponseResult;

import java.util.List;

/**
 * Created by ZHX on 2017/11/10.
 */
public interface ArticleService extends IBaseService<Article> {

    ResponseResult addArticle(Article article);

    Article getArticle(Long id);

    int getArticleCount(Article article);

    void deleteArticle(Long id);

    List<Article> searchArticle(Article article);

    void addWebGeneralize(WebGeneralize webGeneralize);

    void deleteWebGeneralize(Integer id);

    void updateWebGeneralize(WebGeneralize webGeneralize);

    WebGeneralize getWebGeneralizeDetail(Integer id);

    List<WebGeneralize> getWebGeneralizeList(WebGeneralize webGeneralize);

}
