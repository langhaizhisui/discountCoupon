package cn.lhzs.web.controller;

import cn.lhzs.data.bean.SlideShowPicture;
import cn.lhzs.data.bean.Upload;
import cn.lhzs.result.ResponseResult;
import cn.lhzs.service.impl.UploadServiceImpl;
import cn.lhzs.result.RequestResult;
import cn.lhzs.util.DateUtil;
import org.apache.commons.io.input.ReaderInputStream;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static cn.lhzs.result.ResponseResultGenerator.generatorFailResult;
import static cn.lhzs.result.ResponseResultGenerator.generatorSuccessResult;

/**
 * Created by IBA-EDV on 2017/4/26.
 */
@Controller
@RequestMapping("/upload")
public class UploadController {

    Logger logger = Logger.getLogger(UploadController.class);

    @Autowired
    public UploadServiceImpl uploadService;

    @RequestMapping("/excel")
    @ResponseBody
    public RequestResult getExcel(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "type") String type) {
        try {
            String fileName = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            String flag = uploadService.getExcell(fileName, inputStream, type);

            RequestResult result = new RequestResult();
            if (flag == Upload.EXCEL_TEMPLATE_ERROR) {
                result.setCode(1001);
                result.setMsg("模板格式错误");
            } else {
                result.setCode(200);
                result.setMsg("上传成功");
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/slideShowPicture")
    @ResponseBody
    public ResponseResult saveSlideShowPicture(@RequestParam(value = "file") MultipartFile file,
                                               @RequestParam(value = "toUrl") String toUrl,
                                               HttpServletRequest request) {
        try {
            String slideShowPicturePath = request.getSession().getServletContext().getRealPath("/slideShowPicture");
            String fileName = file.getOriginalFilename();
            String pictureName = DateUtil.getNowTimeStampStr() + fileName.substring(fileName.indexOf('.'));
            file.transferTo(new File(slideShowPicturePath, pictureName));

            SlideShowPicture slideShowPicture = new SlideShowPicture();
            slideShowPicture.setUrl("/slideShowPicture/" + pictureName);
            slideShowPicture.setToUrl(toUrl);
            slideShowPicture.setWeight(1);
            uploadService.saveImageInfo(slideShowPicture);
            return generatorSuccessResult();
        } catch (IOException e) {
            return generatorFailResult("上传失败");
        }
    }
}
