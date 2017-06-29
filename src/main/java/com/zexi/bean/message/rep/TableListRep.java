package com.zexi.bean.message.rep;

import java.util.List;

import com.zexi.bean.ResponseMessage;
import com.zexi.bean.TableElement;


public class TableListRep extends ResponseMessage{
	private int total;
	private List<? extends TableElement> rows;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<? extends TableElement> getRows() {
		return rows;
	}
	public void setRows(List<? extends TableElement> rows) {
		this.rows = rows;
	}
	
}
