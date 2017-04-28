package cn.lhzs.service.impl;

import cn.lhzs.data.bean.Product;
import cn.lhzs.data.bean.Shop;
import cn.lhzs.data.dao.ProductMapper;
import cn.lhzs.data.dao.ShopMapper;
import cn.lhzs.service.intf.UploadService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ZHX on 2017/4/26.
 */
@Service
public class UploadServiceImpl implements UploadService {

    @Resource
    public ProductMapper productMapper;
    @Resource
    public ShopMapper shopMapper;

    @Override
    public String getExcell(String fileName, InputStream inputStream, String type) {
        Workbook workbook = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        try {
            if (".xls".equals(fileType.trim().toLowerCase())) {
                workbook = new HSSFWorkbook(inputStream);// 创建 Excel 2003 工作簿对象
            } else if (".xlsx".equals(fileType.trim().toLowerCase())) {
                workbook = new XSSFWorkbook(inputStream);//创建 Excel 2007 工作簿对象
            }

            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();

            if("1".equals(type)){
                for (int i = 1; i < lastRowNum; i++) {
                    Row row = sheet.getRow(i);
                    Product product=new Product();
                    product.setProdId(row.getCell(0).toString());
                    product.setName(row.getCell(1).toString());
                    product.setDetail(row.getCell(2).toString());
                    product.setCategory(row.getCell(3).toString());
                    product.setPrice(Double.parseDouble(row.getCell(4).toString()));
                    product.setBanner(row.getCell(5).toString());
                    product.setBanner(row.getCell(6).toString());
//                    for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
//                        Cell  cell = row.getCell(j);
//                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
