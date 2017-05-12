package cn.lhzs.service.impl;

import cn.lhzs.data.bean.Product;
import cn.lhzs.data.bean.Shop;
import cn.lhzs.data.bean.Upload;
import cn.lhzs.data.dao.ProductMapper;
import cn.lhzs.data.dao.ShopMapper;
import cn.lhzs.service.intf.UploadService;
import cn.lhzs.util.PoiHelper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

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

            String headColumn[] = getHeadColumn(type);
            String fieldColumn[] = getFieldColumn(type);

            //验证Excel模板
            short columnSize = sheet.getRow(0).getLastCellNum();
            String[] headRowColumn = new String[columnSize];
            for (int i = 0; i < columnSize; i++) {
                headRowColumn[i] = sheet.getRow(0).getCell(i).toString();
            }
            if (!isMatchHead(headRowColumn, headColumn)) {
                return Upload.EXCEL_TEMPLATE_ERROR;
            }

            if (Upload.PRODUCT_ADD.equals(type)) {
                for (int i = 1; i <= lastRowNum; i++) {
                    Row row = sheet.getRow(i);
                    Product product = new Product();

                    for (int j = 0; j < fieldColumn.length; j++) {
                        Cell cell = row.getCell(j);
                        if (cell.getCellType() == cell.CELL_TYPE_STRING) {
                            PoiHelper.setFieldMethod(product, fieldColumn[j], String.class, cell == null ? "" : cell.toString());
                        } else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
                            if (DateUtil.isCellDateFormatted(cell)) {
                                Date date = cell.getDateCellValue();
                                PoiHelper.setFieldMethod(product, fieldColumn[j], Date.class, date);
                            } else {
                                PoiHelper.setFieldMethod(product, fieldColumn[j], Double.class, cell == null ? -1.0 : cell.getNumericCellValue());
                            }
                        }
                    }
                    product.setScanNum(0);
                    product.setCreateTime(new Date());
                    product.setUpdateTime(new Date());

                    productMapper.insert(product);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String[] getHeadColumn(String type) {
        String[] headColumn = null;

        if (Upload.PRODUCT_ADD.equals(type)) {
            headColumn = new String[]{"商品名称", "商品主图", "商品详情页链接地址", "商品一级类目", "原价", "券后价",
                    "平台类型", "优惠券面额", "商品优惠券推广链接", "优惠券结束时间"};
        }
        return headColumn;
    }

    private String[] getFieldColumn(String type) {
        String[] fieldColumn = null;

        if (Upload.PRODUCT_ADD.equals(type)) {
            fieldColumn = new String[]{"name", "banner", "detail", "category", "price", "discountPrice",
                    "platform", "savePrice", "prodGeneralize", "expiration"};
        }
        return fieldColumn;
    }

    private boolean isMatchHead(String[] row0, String[] column) {
        if (row0 == null || column == null) {
            return false;
        }
        if (row0.length != column.length) {
            return false;
        }
        for (int i = 0; i < row0.length; i++) {
            if (!column[i].equals(row0[i])) {
                return false;
            }
        }
        return true;
    }
}
