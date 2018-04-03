package com.pandadai.common.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.pandadai.finance.vo.InpourHomeVO;

/***
 * 文件导出
 * 
 * @author 仵作
 * 
 */
public class ExportFile {
	
	/**
	 * 导出数据流
	 * @param columnNames
	 * @param columnData
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static void exportStream(String columnNames,
			List<Object> columnData, HttpServletResponse response)
			throws Exception {
		ServletOutputStream outStr = response.getOutputStream();
		BufferedOutputStream buff = new BufferedOutputStream(outStr);
		final String ENCODE = "GBK";

		// 写入列名
		if (columnNames != null) {
			buff.write(columnNames.toString().getBytes(ENCODE));
			buff.write("\n".getBytes());
		}
		// 写入列内容
		if (columnData != null) {
			List<String> lst = new ArrayList<String>();
			// 先转换格式
			for (int i = 0; i < columnData.size(); i++) {
				Object obj = (Object) columnData.get(i);
				lst.add(Utils.getObjectAttrValues(obj));
			}
			// 逐个写入
			if (lst != null) {
				for (int j = 0; j < lst.size(); j++) {
					buff.write(lst.get(j).toString().getBytes(ENCODE));
					buff.write("\n".getBytes());
				}
			}
		}

		if (buff != null) {
			buff.flush();
			buff.close();
		}
		if (outStr != null) {
			outStr.flush();
			outStr.close();
		}
		response.flushBuffer();
	}

	/**
	 * 导出文件
	 * 
	 * @param outPutPath
	 * @param filename
	 * @param columnNames
	 * @param columnData
	 * @return
	 */
	public static boolean exportFile(String outputPath, String filename,
			String columnNames, List<Object> columnData) throws Exception {
		boolean flag = false;
		File file = null;
		BufferedWriter fileOutputStream = null;
		// try {
		file = new File(outputPath + filename);
		File parent = file.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.mkdirs();
		}
		file.createNewFile();

		// GB2312使正确读取分隔符","
		fileOutputStream = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file), "GB2312"), 1024);

		// 写入列名
		if (columnNames != null) {
			fileOutputStream.write(columnNames);
			fileOutputStream.newLine();
		}
		// 写入列内容
		if (columnData != null) {
			List<String> lst = new ArrayList<String>();
			// 先转换格式
			for (int i = 0; i < columnData.size(); i++) {
				Object obj = (Object) columnData.get(i);
				lst.add(Utils.getObjectAttrValues(obj));
			}
			// 逐个写入
			if (lst != null) {
				for (int j = 0; j < lst.size(); j++) {
					fileOutputStream.write(lst.get(j));
					fileOutputStream.newLine();
				}
			}
		}
		fileOutputStream.flush();
		flag = true;

		if (fileOutputStream != null)
			fileOutputStream.close();
		return flag;
	}

	public static void main(String[] args) {
		List<Object> lst = new ArrayList<Object>();
		InpourHomeVO vo1 = new InpourHomeVO();
		vo1.setDuring("1111");
		vo1.setBaofoo(0);
		vo1.setEasypay(0);
		vo1.setOff(0);
		vo1.setTotal(0);
		lst.add(vo1);
		InpourHomeVO vo2 = new InpourHomeVO();
		vo1.setDuring("2222");
		vo1.setBaofoo(2);
		vo1.setEasypay(3);
		vo1.setOff(4);
		vo1.setTotal(5);
		lst.add(vo2);
		String head = "第一列,第2列,第3列,第4列,第5列";
		try {
			ExportFile.exportFile("d:/aaaaaa/mmm/", "财务.csv", head, lst);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
