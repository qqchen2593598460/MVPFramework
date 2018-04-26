package com.fif.baselib.utils;

import java.text.DecimalFormat;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils{

	public static boolean isNotBlank(String str){
		return !isBlank(str);
	}
	public static boolean isBlank(String str){
        return null == str || "".equals(str);
    }
	
	public static boolean isNotNull(String str){
        return null != str && !"null".equals(str);
    }
	
	public static String fileSize(long size){
		float f;
		DecimalFormat df = new DecimalFormat("###.##");
		if(size < 1024*1024){
			f = (float)size/(float)1024;
			return (df.format(new Float(f).doubleValue())+"KB");	
		}else{
			f = (float)size/(float)(1024*1024);
			return (df.format(new Float(f).doubleValue())+"MB");	
		}
	}
	
	public static String replaceBlank(String str){
		String dest = "";
		if(isNotBlank(str)){
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	
	public static String getUUID(){
		String str = UUID.randomUUID().toString();
		return str.replaceAll("-", "");
	}
}
