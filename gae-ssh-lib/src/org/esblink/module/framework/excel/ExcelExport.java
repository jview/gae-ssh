package org.esblink.module.framework.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 */
public class ExcelExport {

	// Excel导出表格式定义
	private TableDefine tableDefine;

	public ExcelExport(TableDefine tableDefine) {
		this.tableDefine = tableDefine;
	}

	/**
	 * 导出数据到InputStream
	 * 
	 * @param data
	 * @return
	 */
	public InputStream export(Collection<?> data) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(tableDefine.getSheetName());
		writeSheet(sheet, data);
		return save(workbook);
	}

	/**
	 * 导出Excel
	 * 
	 * @param sheet
	 * @param data
	 */
	private void writeSheet(HSSFSheet sheet, Collection<?> data) {
		try {
			writeSheetHeader(sheet);
			writeSheetContent(sheet, data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 生成表头
	 * 
	 * @param sheet
	 */
	private void writeSheetHeader(HSSFSheet sheet) {
		HSSFRow row = sheet.createRow(0);
		for (ColumnDefine column : tableDefine.getColumns()) {
			short columnIndex = (short) column.getIndex();
			HSSFCell cell = row.createCell(columnIndex);
			if (column.getWidth() > 0) {
				sheet.setColumnWidth(columnIndex, (short) column.getWidth());
			}
			Object value = column.getTitle();
			if (column.getExportCellEvent() != null) {
				value = column.getExportCellEvent().onExportCell(column, 0, cell, value);
			}
			HSSFRichTextString richText = new HSSFRichTextString((value == null) ? "" : value.toString());
			cell.setCellValue(richText);
		}
	}

	/**
	 * 生成Excel内容
	 * 
	 * @param sheet
	 * @param data
	 * @throws Exception
	 */
	private void writeSheetContent(HSSFSheet sheet, Collection<?> data) throws Exception {
		if (data.size() > 0) {
			Map<String, Field> fields = getClazzFields(data.iterator().next().getClass());
			int rowIndex = 0;
			for (Object obj : data) {
				HSSFRow row = sheet.createRow(++rowIndex);
				for (ColumnDefine column : tableDefine.getColumns()) {
					short columnIndex = (short) column.getIndex();
					HSSFCell cell = row.createCell(columnIndex);
					Object value = fields.get(column.getPropName()).get(obj);
					if (column.getExportCellEvent() != null) {
						value = column.getExportCellEvent().onExportCell(column, rowIndex, cell, value);
					}
					HSSFRichTextString richText = new HSSFRichTextString((value == null) ? "" : value.toString());
					cell.setCellValue(richText);
				}
			}
		}
	}

	/**
	 * 获取对象的全部属性
	 * 
	 * @param clazz
	 * @return
	 */
	private Map<String, Field> getClazzFields(Class<?> clazz) {
		Map<String, Field> fieldMap = new HashMap<String, Field>();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				fieldMap.put(field.getName(), field);
			}
		}
		return fieldMap;
	}

	/**
	 * 保存Excel到InputStream
	 * 
	 * @param workbook
	 * @return
	 */
	private InputStream save(HSSFWorkbook workbook) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			workbook.write(bos);
			InputStream bis = new ByteArrayInputStream(bos.toByteArray());
			return bis;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
