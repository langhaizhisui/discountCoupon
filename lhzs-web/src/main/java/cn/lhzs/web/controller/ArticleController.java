package cn.lhzs.web.controller;

import cn.lhzs.data.bean.Article;
import cn.lhzs.data.common.Constants;
import cn.lhzs.service.impl.ConfigServiceImpl;
import cn.lhzs.service.intf.ArticleService;
import cn.lhzs.result.RequestResult;
import cn.lhzs.service.intf.ConfigService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

    @Autowired
    public ConfigService configService;

    @RequestMapping("/list")
    @ResponseBody
    public RequestResult getArticleList(@RequestBody String reqData) {
        RequestResult result = new RequestResult();
        result.setCode(200);
        result.setMsg("获取文章列表成功");
        result.setData(articleService.getArticleList(reqData));
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

    @RequestMapping("/count")
    @ResponseBody
    public RequestResult getArticleCount() {
        RequestResult result = new RequestResult();
        result.setCode(200);
        result.setMsg("获取文章列表成功");
        result.setData(articleService.getArticleCount());
        return result;
    }

    @RequestMapping("/webGeneralize")
    @ResponseBody
    public RequestResult getWebGeneralize() {
        RequestResult result = new RequestResult();
        result.setCode(200);
        result.setMsg("获取网站推广成功");
        result.setData(JSONObject.parse(configService.getConfigByConfId(Constants.WEB_GENERALIZE).getValue()));
        return result;
    }
}
