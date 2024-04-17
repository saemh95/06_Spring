package edu.kh.project.common.util;

import java.text.SimpleDateFormat;

public class Utility {

	public static int seqNum = 1;
	
	public static String fileRemane(String originalFileName) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		String date = sdf.format(new java.util.Date());
		
		String number = String.format("%05d", seqNum);
		
		seqNum++;
		if(seqNum == 100000) seqNum = 1;
		
		String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
		
		return date + "_" + number + ext;
	}
	
}
