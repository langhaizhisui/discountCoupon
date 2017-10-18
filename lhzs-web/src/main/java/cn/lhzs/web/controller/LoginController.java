package cn.lhzs.web.controller;

import cn.lhzs.result.ResponseResult;
import cn.lhzs.service.intf.ArticleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static cn.lhzs.result.ResponseResultGenerator.generatorSuccessResult;

/**
 * Created by ZHX on 2017/5/7.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    Logger logger = Logger.getLogger(LoginController.class);

    @Autowired
    public ArticleService articleService;

    @RequestMapping("/login")
    @ResponseBody
    public ResponseResult login() {
        return generatorSuccessResult("login");
    }

    @RequestMapping("/aaa")
    @ResponseBody
    public ResponseResult deleteArticle() {
        return generatorSuccessResult("aaaaa");
    }
}
