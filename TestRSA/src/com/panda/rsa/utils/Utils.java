package com.panda.rsa.utils;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Utils {
	/**
     * 将url中参数（升序）排序后重新拼接
     * @param paramStr
     * @param isAsc
     * @return
     * @throws Exception
     */
    public static String sortParamStr(String paramStr) throws Exception {
    	if (paramStr.trim().length() == 0) {
    		return null;
    	}
    	
		Map<String, String> map = new TreeMap<String, String>(
				new Comparator<String>() {
					public int compare(String obj1, String obj2) {
						// return obj2.compareTo(obj1); // 降序排序
						return obj1.compareTo(obj2);
					}
				});
//		map.put("month", "The month");
//		map.put("bread", "The bread");
//		map.put("attack", "The attack");
		String[] keyValue = paramStr.split("&");
		for (int i=0; i<keyValue.length; i++) {
			if (keyValue.toString().trim().length() > 0) {
				String[] kv = keyValue[i].split("=");
				map.put(kv[0], kv[1]);
			}
		}

		StringBuilder bu = new StringBuilder();
		Set<String> keySet = map.keySet();
		Iterator<String> iter = keySet.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
//			System.out.println(key + ":" + map.get(key));
			bu.append("&").append(key).append("=").append(map.get(key));
		}
		
		String newUrl = bu.toString();
		if (newUrl.startsWith("&")) {
			newUrl = newUrl.substring(1);
		}
		
    	return newUrl;
    }
}
