package cn.lhzs.service.impl;

import cn.lhzs.data.bean.Product;
import cn.lhzs.data.bean.Shop;
import cn.lhzs.data.bean.Upload;
import cn.lhzs.data.dao.ProductMapper;
import cn.lhzs.data.dao.ShopMapper;
import cn.lhzs.service.intf.UploadService;
import cn.lhzs.util.PoiHelper;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by ZHX on 2017/4/26.
 */
@Service
public class UploadServiceImpl implements UploadService {

    Logger logger = Logger.getLogger(UploadServiceImpl.class);

    @Resource
    public ProductMapper productMapper;
    @Resource
    public ShopMapper shopMapper;

    @Override
    public String getExcell(String fileName, InputStream inputStream, String type) {
        try {
            Sheet sheet = PoiHelper.initSheet(inputStream, fileName);

            String headColumn[] = getHeadColumn(type);
            String fieldColumn[] = getFieldColumn(type);
            String fieldClassColumn[] = getFieldClassColumn(type);

            //验证Excel模板
            short columnSize = sheet.getRow(0).getLastCellNum();
            String[] headRowColumn = new String[columnSize];
            for (int i = 0; i < columnSize; i++) {
                headRowColumn[i] = sheet.getRow(0).getCell(i).toString();
            }
            if (!isMatchHead(headRowColumn, headColumn)) {
                return Upload.EXCEL_TEMPLATE_ERROR;
            }

            //添加数据
            int lastRowNum = sheet.getLastRowNum();
            if (Upload.PRODUCT_ADD.equals(type)) {
                Product product = new Product();
                for (int i = 1; i <= lastRowNum; i++) {
                    Row row = sheet.getRow(i);
                    product.setScanNum(0);
                    product.setCreateTime(new Date());
                    product.setUpdateTime(new Date());
                    setField(row, product, fieldColumn, fieldClassColumn);

                    productMapper.insert(product);
                }
            } else if (Upload.SHOP_ADD.equals(type)) {
                Shop shop = new Shop();
                for (int i = 1; i <= lastRowNum; i++) {
                    Row row = sheet.getRow(i);
                    for (int j = 0; j < fieldColumn.length; j++) {
                        shop.setCreatTime(new Date());
                        shop.setUpdateTime(new Date());
                        setField(row, shop, fieldColumn, fieldClassColumn);

                        shopMapper.insert(shop);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void setField(Row row, Object clazz, String[] fieldColumn, String[] FieldClassType) throws Exception {
        for (int j = 0; j < fieldColumn.length; j++) {
            Cell cell = row.getCell(j);
            if ("String".equals(FieldClassType[j])) {
                PoiHelper.setFieldMethod(clazz, fieldColumn[j], String.class, cell == null ? "" : cell.toString());
            } else if ("Double".equals(FieldClassType[j])) {
                PoiHelper.setFieldMethod(clazz, fieldColumn[j], Double.class, cell == null ? 0.0 : Double.parseDouble(cell.toString()));
            } else if ("Integer".equals(FieldClassType[j])) {
                PoiHelper.setFieldMethod(clazz, fieldColumn[j], Integer.class, cell == null ? 0 : Integer.parseInt(cell.toString()));
            } else if ("Boolean".equals(FieldClassType[j])) {
                PoiHelper.setFieldMethod(clazz, fieldColumn[j], Boolean.class, cell == null ? false : Boolean.parseBoolean(cell.toString()));
            } else if ("Float".equals(FieldClassType[j])) {
                PoiHelper.setFieldMethod(clazz, fieldColumn[j], Float.class, cell == null ? 0f : Float.parseFloat(cell.toString()));
            } else if ("Date".equals(FieldClassType[j])) {
                PoiHelper.setFieldMethod(clazz, fieldColumn[j], Date.class, cell == null ? new Date() : cell.getDateCellValue());
            }
        }
    }

    private String[] getHeadColumn(String type) {
        String[] headColumn = null;

        if (Upload.PRODUCT_ADD.equals(type)) {
            headColumn = new String[]{"商品名称", "商品主图", "商品详情页链接地址", "商品一级类目", "原价", "券后价",
                    "平台类型", "优惠券面额", "商品优惠券推广链接", "优惠券结束时间"};
        } else if (Upload.SHOP_ADD.equals(type)) {
            headColumn = new String[]{"网店名称", "所属商城", "网店类型", " 网店经营商", "品牌名称", "主要经营产品",
                    "网店网址", "推广链接", "手机版推广网址", "店铺所在地"};
        }
        return headColumn;
    }

    private String[] getFieldColumn(String type) {
        String[] fieldColumn = null;

        if (Upload.PRODUCT_ADD.equals(type)) {
            fieldColumn = new String[]{"name", "banner", "detail", "category", "price", "discountPrice",
                    "platform", "savePrice", "prodGeneralize", "expiration"};
        } else if (Upload.SHOP_ADD.equals(type)) {
            fieldColumn = new String[]{"webShop", "site", "type", "sellName", "brandName", "sellProd",
                    "webUrl", "webGeneralize", "mobileGeneralize", "shopAddr"};
        }
        return fieldColumn;
    }

    private String[] getFieldClassColumn(String type) {
        String[] fieldColumn = null;

        if (Upload.PRODUCT_ADD.equals(type)) {
            fieldColumn = new String[]{"String", "String", "String", "String", "Double", "Double",
                    "String", "Double", "String", "String"};
        } else if (Upload.SHOP_ADD.equals(type)) {
            fieldColumn = new String[]{"String", "String", "String", "String", "String", "String",
                    "String", "String", "String", "String"};
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
