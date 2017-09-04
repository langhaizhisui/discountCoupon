package cn.lhzs.web.controller;

import cn.lhzs.data.bean.Article;
import cn.lhzs.service.intf.ArticleService;
import cn.lhzs.result.RequestResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/5/7.
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

    Logger logger = Logger.getLogger(ArticleController.class);

    @Autowired
    public ArticleService articleService;

    @RequestMapping("/list")
    @ResponseBody
    public RequestResult getArticleList(@RequestBody Article article) {
        RequestResult result = new RequestResult();
        result.setCode(200);
        result.setMsg("获取文章列表成功");
        result.setData(articleService.getArticleList(article.getType()));
        return result;
    }

    @RequestMapping("/detail")
    @ResponseBody
    public RequestResult getArticleDetail(@RequestBody Article article) {
        RequestResult result = new RequestResult();
        result.setCode(200);
        result.setMsg("获取文章信息成功");
        result.setData(articleService.getArticle(article.getId()));
        return result;
    }

    @RequestMapping("/gene/article")
    @ResponseBody
    public RequestResult generatorArticle(@RequestBody Article article) {
        return articleService.generatorArticle(article);
    }
}
