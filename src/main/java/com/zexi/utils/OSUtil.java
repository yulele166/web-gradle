/**
 * 
 */
package com.zexi.utils;

import java.util.Properties;

/**
 * @author yulele
 *
 */
public class OSUtil {

	public static String getOSName(){
	    Properties prop = System.getProperties();
	    String os = prop.getProperty("os.name").toLowerCase();
	    if(os.contains("windows")){
	        os = "windows";
	    }else{
	        os = "linux";
	    }
	    return os;
   }
}
