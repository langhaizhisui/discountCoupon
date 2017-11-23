package cn.lhzs.web.controller;

import cn.lhzs.common.aop.log.OpLog;
import cn.lhzs.data.bean.Article;
import cn.lhzs.data.bean.WebGeneralize;
import cn.lhzs.result.ResponseResult;
import cn.lhzs.service.intf.ArticleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static cn.lhzs.common.aop.log.OpEnum.ADD;
import static cn.lhzs.common.aop.log.OpEnum.DEL;
import static cn.lhzs.common.aop.log.OpEnum.EDIT;
import static cn.lhzs.result.ResponseResultGenerator.generatorSuccessResult;

/**
* Created by ZHX on 2017/11/10.
*/
@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    public ArticleService articleService;

    @OpLog(type = DEL, descp = "删除文章")
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

    @OpLog(type = ADD, descp = "删除文章")
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

    @OpLog(type = ADD, descp = "增加推荐商城")
    @RequestMapping("/webGeneralize/add")
    @ResponseBody
    public ResponseResult addWebGeneralize(@RequestBody WebGeneralize webGeneralize) {
        articleService.addWebGeneralize(webGeneralize);
        return generatorSuccessResult();
    }

    @OpLog(type = DEL, descp = "删除推荐商城")
    @RequestMapping("/webGeneralize/delete")
    @ResponseBody
    public ResponseResult deleteWebGeneralize(Integer id) {
        articleService.deleteWebGeneralize(id);
        return generatorSuccessResult();
    }

    @OpLog(type = ADD, descp = "增加推荐商城")
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
