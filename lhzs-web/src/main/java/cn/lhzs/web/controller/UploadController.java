package cn.lhzs.web.controller;

import cn.lhzs.service.impl.UploadServiceImpl;
import cn.lhzs.web.result.RequestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IBA-EDV on 2017/4/26.
 */
@Controller
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    public UploadServiceImpl uploadService;

    @RequestMapping("/excel")
    @ResponseBody
    public RequestResult getExcel(@RequestParam(value = "file") MultipartFile file,@RequestParam(value = "type") String type) {
        try {
            String fileName = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            String flag = uploadService.getExcell(fileName, inputStream, type);

            RequestResult result = new RequestResult();
            result.setCode(200);
            result.setMsg("上传成功");
            result.setData(flag);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
