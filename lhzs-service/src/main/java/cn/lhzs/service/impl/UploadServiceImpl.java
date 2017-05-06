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

            if("1".equals(type)){
                for (int i = 1; i <= lastRowNum; i++) {
                    Row row = sheet.getRow(i);
                    Product product=new Product();
                    product.setProdId(i+"");
                    product.setName(row.getCell(0)==null?"":row.getCell(0).toString());
                    product.setBanner(row.getCell(1)==null?"":row.getCell(1).toString());
                    product.setDetail(row.getCell(2)==null?"":row.getCell(2).toString());
                    product.setCategory(row.getCell(3)==null?"":row.getCell(3).toString());
                    System.out.println(row.getCell(4)==null?-1.0:Double.parseDouble(row.getCell(4).toString()));
                    product.setPrice(row.getCell(4)==null?-1.0:Double.parseDouble(row.getCell(4).toString()));
                    System.out.println(row.getCell(5)==null?-1.0:Double.parseDouble(row.getCell(5).toString()));
                    product.setDiscountPrice(row.getCell(5)==null?-1.0:Double.parseDouble(row.getCell(5).toString()));
                    System.out.println();
                    product.setDiscountDesc(row.getCell(7)==null?"":row.getCell(6).toString());
                    product.setPlatform(row.getCell(6)==null?"":row.getCell(7).toString());
                    product.setProdGeneralize(row.getCell(8)==null?"":row.getCell(8).toString());
                    product.setScanNum(0);
                    product.setCreateTime(new Date());
                    product.setUpdateTime(new Date());
                    String operation=row.getCell(9)==null?"":row.getCell(9).toString();
                    if("1".equals(operation)||"1.0".equals(operation)){
                        productMapper.insert(product);
                    }else if("2".equals(operation)||"2.0".equals(operation)){
                        productMapper.deleteByPrimaryKey(row.getCell(0)==null?"":row.getCell(0).toString());
                    }else if("3".equals(operation)||"3.0".equals(operation)){
                        productMapper.updateByPrimaryKey(product);
                    }

                }
            }else if("2".equals(type)){
                for (int i = 1; i <= lastRowNum; i++) {
                    Row row = sheet.getRow(i);
                    Shop shop=new Shop();
                    shop.setId(i+"");
                    System.out.println(row.getCell(0)==null?"":row.getCell(0).toString());
                    shop.setWebShop(row.getCell(0)==null?"":row.getCell(0).toString());
                    System.out.println(row.getCell(1)==null?"":((int)Double.parseDouble(row.getCell(1).toString()))+"");
                    shop.setSite(row.getCell(1)==null?"":((int)Double.parseDouble(row.getCell(1).toString()))+"");
                    System.out.println(row.getCell(2)==null?"":row.getCell(2).toString());
                    shop.setType(row.getCell(2)==null?"":row.getCell(2).toString());
                    System.out.println(row.getCell(3)==null?"":row.getCell(3).toString());
                    shop.setSellName(row.getCell(3)==null?"":row.getCell(3).toString());
                    System.out.println(row.getCell(4)==null?"":row.getCell(4).toString());
                    shop.setBrandName(row.getCell(4)==null?"":row.getCell(4).toString());
                    System.out.println(row.getCell(5)==null?"":row.getCell(5).toString());
                    shop.setSellProd(row.getCell(5)==null?"":row.getCell(5).toString());
                    System.out.println(row.getCell(6)==null?"":row.getCell(6).toString());
                    shop.setWebUrl(row.getCell(6)==null?"":row.getCell(6).toString());
                    System.out.println(row.getCell(7)==null?"":row.getCell(7).toString());
                    shop.setWebGeneralize(row.getCell(7)==null?"":row.getCell(7).toString());
                    System.out.println(row.getCell(8)==null?"":row.getCell(8).toString());
                    shop.setMobileUrl(row.getCell(8)==null?"":row.getCell(8).toString());
                    System.out.println(row.getCell(9)==null?"":row.getCell(9).toString());
                    shop.setMobileGeneralize(row.getCell(9)==null?"":row.getCell(9).toString());
                    System.out.println(row.getCell(10)==null?"":row.getCell(10).toString());
                    shop.setShopAddr(row.getCell(10)==null?"":row.getCell(10).toString());
                    System.out.println(row.getCell(11)==null?"":row.getCell(11).toString());
                    shop.setBanner(row.getCell(11)==null?"":row.getCell(11).toString());
                    shop.setCreatTime(new Date());
                    shop.setUpdateTime(new Date());
                    System.out.println(row.getCell(12)==null?"":row.getCell(12).toString());
                    String operation=row.getCell(12)==null?"":row.getCell(12).toString();
                    if("1".equals(operation)||"1.0".equals(operation)){
                        shopMapper.insert(shop);
                    }else if("2".equals(operation)||"2.0".equals(operation)){
                        shopMapper.deleteByPrimaryKey(row.getCell(0)==null?"":row.getCell(0).toString());
                    }else if("3".equals(operation)||"3.0".equals(operation)){
                        shopMapper.updateByPrimaryKey(shop);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
