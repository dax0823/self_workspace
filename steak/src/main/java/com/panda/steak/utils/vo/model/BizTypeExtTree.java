/** 
 * Project Name: steak 
 * File Name: BizTypeExtTree.java 
 * Package Name: com.panda.steak.utils.vo.model 
 * Date: 2016年9月6日上午11:27:26 
 * Copyright (c) 2016, dax0823@163.com All Rights Reserved. 
 * 
*/  
  
package com.panda.steak.utils.vo.model;  

import java.util.List;

/** 
 * ClassName: BizTypeExtTree
 * Function: ADD FUNCTION
 * Reason: ADD REASON
 * Date: 2016年9月6日 上午11:27:26
 * @author   dinghui 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
public class BizTypeExtTree extends BizTypeExt {
	private List<BizTypeExtTree> lst;

	public List<BizTypeExtTree> getLst() {
		return lst;
	}

	public void setLst(List<BizTypeExtTree> lst) {
		this.lst = lst;
	}
}
