package cn.lhzs.web.controller;

import cn.lhzs.data.bean.Article;
import cn.lhzs.service.intf.ArticleService;
import cn.lhzs.result.RequestResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/articleList")
    @ResponseBody
    public RequestResult getCatalogList(){
        return null;
    }

    @RequestMapping("/gene/article")
    @ResponseBody
    public RequestResult generatorArticle(@RequestBody Article article) {
        return articleService.generatorArticle(article);
    }
}
