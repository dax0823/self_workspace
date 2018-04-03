package com.panda.test;

public class TestCopyFile {
	
	/**
	 * 将某个文件夹下某个源文件放到指定文件夹下
	 * @throws Exception
	 */
	private static String copyFile2Folder(String fileName, String srouceFolder,
			String targetFolder) throws Exception {
		if (!srouceFolder.endsWith("\\")) {
			srouceFolder += "\\";
		}
		if (!targetFolder.endsWith("\\")) {
			targetFolder += "\\";
		}

		try {
			Runtime run = Runtime.getRuntime();
			Process pro = run.exec("cmd /c copy " + srouceFolder + fileName + " " + targetFolder + fileName);
			pro.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return targetFolder + fileName;
	}
	
	/**
	 * main
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("begin.");
		
		String fileName = "DUMP20c8.tmp";
		String srouceFolder = "D:\\";
		String targetFolder = "e:\\";
		
		String ret = copyFile2Folder(fileName, srouceFolder, targetFolder);
		System.out.println("return targetFolder.filePath = " + ret);
		
		System.out.println("end.");
	}
}
