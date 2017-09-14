package cn.lhzs.common;

import java.io.File;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import cn.lhzs.common.util.DateUtil;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * excel导出工具类
 */
public class ExcelUtil {

	public static Workbook getWorkbook(InputStream is) {
		try {
			return Workbook.getWorkbook(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Workbook getWorkbook(File file) {
		try {
			return Workbook.getWorkbook(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static float parseFloat(Sheet sheet, int column, int row) {
		String str = getString(sheet, column, row);
		return Float.parseFloat(str);
	}

	public static boolean getBool(Sheet sheet, int column, int row) {
		String str = getString(sheet, column, row);
		if (str.equals("1") || str.equals("true") || str.equals("TRUE")) {
			return true;
		}
		return false;
	}

	public static int getInt(Sheet sheet, int column, int row) {
		String str = getString(sheet, column, row);
		return Integer.parseInt(str);
	}

	public static short getShort(Sheet sheet, int column, int row) {
		String str = getString(sheet, column, row);
		return Short.parseShort(str);
	}

	public static String getString(Sheet sheet, int column, int row) {
		return sheet.getCell(column, row).getContents();
	}

	public static double parseDouble(Sheet sheet, int column, int row) {
		String str = getString(sheet, column, row);
		return Double.parseDouble(str);
	}

	public static Date parseDate(Sheet sheet, int column, int row) {
		Cell cell = sheet.getCell(column, row);
		if (cell.getType() == CellType.DATE) {
			DateCell dateCell = (DateCell) cell;
			return convertDate4JXL(dateCell.getDate());
		}
		return null;
	}

	public static Date convertDate4JXL(Date date) {
		if (date == null)
			return null;
		TimeZone gmt = TimeZone.getTimeZone("GMT");
		DateFormat dateFormat = new SimpleDateFormat(DateUtil.DATE_PATTERN_YYYY_MM_DDHHMMSS, Locale.getDefault());
		dateFormat.setTimeZone(gmt);
		String str = dateFormat.format(date);
		TimeZone local = TimeZone.getDefault();
		dateFormat.setTimeZone(local);
		try {
			return dateFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
