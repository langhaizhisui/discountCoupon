package cn.lhzs.web.controller;

import cn.lhzs.data.bean.Article;
import cn.lhzs.data.bean.WebGeneralize;
import cn.lhzs.result.ResponseResult;
import cn.lhzs.service.intf.ArticleService;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import static cn.lhzs.result.ResponseResultGenerator.generatorSuccessResult;

/**
* Created by ZHX on 2017/11/10.
*/
@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    public ArticleService articleService;

    @RequestMapping("/delete")
    @ResponseBody
    public ResponseResult deleteArticle(Long id) {
        articleService.deleteArticle(id);
        return generatorSuccessResult();
    }

    @RequestMapping("/detail")
    @ResponseBody
    public ResponseResult getArticleDetail(@RequestBody Article article) {
        return generatorSuccessResult(articleService.getArticle(article.getId()));
    }

    @RequestMapping("/search")
    @ResponseBody
    public ResponseResult searchArticle(@RequestBody Article article) {
        return generatorSuccessResult(new PageInfo(articleService.searchArticle(article)));
    }

    @RequestMapping("/gene/article")
    @ResponseBody
    public ResponseResult addArticle(@RequestBody Article article) {
        Assert.notNull(article);
        return articleService.addArticle(article);
    }

    @RequestMapping("/count")
    @ResponseBody
    public ResponseResult getArticleCount(@RequestBody Article article) {
        return generatorSuccessResult(articleService.getArticleCount(article));
    }

    @RequestMapping("/webGeneralize/search")
    @ResponseBody
    public ResponseResult getWebGeneralize(@RequestBody WebGeneralize webGeneralize) {
        return generatorSuccessResult(new PageInfo(articleService.getWebGeneralizeList(webGeneralize)));
    }

    @RequestMapping("/webGeneralize/add")
    @ResponseBody
    public ResponseResult addWebGeneralize(@RequestBody WebGeneralize webGeneralize) {
        articleService.addWebGeneralize(webGeneralize);
        return generatorSuccessResult();
    }

    @RequestMapping("/webGeneralize/delete")
    @ResponseBody
    public ResponseResult deleteWebGeneralize(Integer id) {
        articleService.deleteWebGeneralize(id);
        return generatorSuccessResult();
    }

    @RequestMapping("/webGeneralize/update")
    @ResponseBody
    public ResponseResult updateWebGeneralize(@RequestBody WebGeneralize webGeneralize) {
        articleService.updateWebGeneralize(webGeneralize);
        return generatorSuccessResult();
    }

    @RequestMapping("/webGeneralize/detail")
    @ResponseBody
    public ResponseResult getWebGeneralizeDetail(@RequestParam Integer id) {
        return generatorSuccessResult(articleService.getWebGeneralizeDetail(id));
    }

}
