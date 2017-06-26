package cn.lhzs.web.controller;

import cn.lhzs.data.bean.Catalog;
import cn.lhzs.service.intf.CatalogService;
import cn.lhzs.web.result.RequestResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2017/5/7.
 */
@Controller
@RequestMapping("/catalog")
public class CatalogController {

    Logger logger = Logger.getLogger(CatalogController.class);

    @Autowired
    public CatalogService catalogService;

    @RequestMapping("/cataList")
    @ResponseBody
    public RequestResult getCatalogList(){
        List<Catalog> catalogList=catalogService.getCatalogList();
        RequestResult result=new RequestResult();
        result.setCode(200);
        result.setMsg("获取分类成功");
        result.setData(catalogList);
        return result;
    }
}
