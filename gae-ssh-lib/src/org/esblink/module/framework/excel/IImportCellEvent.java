package org.esblink.module.framework.excel;

/**
 *
 */
public interface IImportCellEvent {

	public Object onImportCell(ColumnDefine column, String value);

}
