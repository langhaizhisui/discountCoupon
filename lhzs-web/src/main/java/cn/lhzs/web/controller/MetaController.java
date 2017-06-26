package cn.lhzs.web.controller;

import cn.lhzs.data.bean.Catalog;
import cn.lhzs.data.bean.Meta;
import cn.lhzs.service.intf.CatalogService;
import cn.lhzs.service.intf.MetaService;
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
@RequestMapping("/meta")
public class MetaController {

    Logger logger = Logger.getLogger(MetaController.class);

    @Autowired
    public MetaService metaService;

    @RequestMapping("/getMeta")
    @ResponseBody
    public RequestResult getMeta(int id){
        Meta meta=metaService.getMeta(id);
        RequestResult result=new RequestResult();
        result.setCode(200);
        result.setMsg("获取分类成功");
        result.setData(meta);
        return result;
    }
}
