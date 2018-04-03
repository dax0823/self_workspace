package com.panda.test.phpjson;

import java.util.ArrayList;
import java.util.List;

public class TestPHPJson {
	
	//php 格式 json 数据
	//银行列表
	private static final String PHP_JSON_BANK_NAMES = "<?php"
			+ "return array ( 'BORROW_USE' => array ( 1 => '短期周转', 2 => '生意周转', 3 => '生活周转', 4 => '购物消费', 5 => '不提现借款', 6 => '创业借款', 7 => '其它借款', ), 'BORROW_MIN' => array ( 50 => '50元', 100 => '100元', 200 => '200元', 300 => '300元', 500 => '500元', 1000 => '1000元', 2000 => '2000元', 3000 => '3000元', 4000 => '4000元', 5000 => '5000元', ), 'BORROW_MAX' => array ( 0 => '没有限制', 1500 => '1500元', 2000 => '2000元', 3000 => '3000元', 4000 => '4000元', 5000 => '5000元', 6000 => '6000元', 10000 => '10000元', ), 'BORROW_TIME' => array ( 1 => '1天', 2 => '2天', 3 => '3天', 4 => '4天', 5 => '5天', 6 => '6天', 7 => '7天', ), 'MONEY_SEARCH' => array ( 50 => '50', 1000 => '1000', 5000 => '5000', 10000 => '1万', 50000 => '5万', 100000 => '10万', 200000 => '20万', 500000 => '50万', 1000000 => '100万', '10000000000' => '100万以上', 1223 => '1亿元以上', 2323 => '10亿元以上', ), 'BANK_NAME' => array ( '招商银行' => '招商银行', '中国银行' => '中国银行', '中国工商银行' => '中国工商银行', '中国建设银行' => '中国建设银行', '中国农业银行' => '中国农业银行', '中国邮政储蓄银行' => '中国邮政储蓄银行', '交通银行' => '交通银行', '上海浦东发展银行' => '上海浦东发展银行', '深圳发展银行' => '深圳发展银行', '中国民生银行' => '中国民生银行', '兴业银行' => '兴业银行', '平安银行' => '平安银行', '北京银行' => '北京银行', '天津银行' => '天津银行', '上海银行' => '上海银行', '华夏银行' => '华夏银行', '光大银行' => '光大银行', '广发银行' => '广发银行', '中信银行' => '中信银行', '上海农商银行' => '上海农商银行', ), 'DATA_TYPE' => false, ); ?>";
//	private static final String PHP_JSON_PAY= "<?php"
//			+ "return array ( 'ecpss' => array ( 'enable' => '0', 'feerate' => '', 'MerNo' => '20318', 'MD5key' => '}JC[)UEE', ), 'ips' => array ( 'enable' => '0', 'feerate' => '', 'MerCode' => '', 'MerKey' => '', ), 'chinabank' => array ( 'enable' => '0', 'feerate' => '', 'mid' => '', 'mkey' => '', ), 'baofoo' => array ( 'enable' => '1', 'feerate' => '0', 'MemberID' => '125865', 'TerminalID' => '19617', 'pkey' => 'rpnf5s9a3m2h249d', ), 'tenpay' => array ( 'enable' => '0', 'feerate' => '', 'partner' => '', 'key' => '', ), 'guofubao' => array ( 'enable' => '0', 'feerate' => '', 'merchantID' => '', 'VerficationCode' => '', 'virCardNoIn' => '', ), 'easypay' => array ( 'enable' => '1', 'feerate' => '0', 'partner' => '100000000016968', 'key' => '294acc72b65d1c27cg498584c9ebc2915accbeg6e0g6d2d73cccgd93ggad5fcb', ), 'cmpay' => array ( 'enable' => '0', 'feerate' => '', 'merchantId' => '', 'serverCert' => '', ), ); ?>";
//	private static final String PHP_JSON_OFF_BANKS = "<?php"
//			+"return array ( 'BANK' => array ( 0 => array ( 'bank' => '招商银行', 'payee' => '石伟', 'account' => '6214860162345678', 'address' => '北京玉泉路支行', ), 1 => array ( 'bank' => '中国工商银行', 'payee' => '石伟', 'account' => '6222080200021069565', 'address' => '北京玉东支行', ), ), 'BANK_INFO' => '<span style=\"font-size:24px;\"><b><span style=\"color:#e53333;\">请大家优先打招商银行· &nbsp;到账速度相对比较快！</span></b></span>', ); ?>";
	
	
	/**
	 * 将 php json 格式的数据转换为常规的 json
	 * @param phpJson
	 * @return
	 * @throws Exception
	 */
	private static List<ArrayList> convertPHPJson2Json(String phpJson) throws Exception {
		List<ArrayList> lst = new ArrayList();
		if (!"".equals(phpJson = phpJson.trim())) {
			//1. 去掉首尾的 php 标签
//			phpJson.replace("<?php return array (", "").replace("), 'DATA_TYPE' => false, ); ?>", "");
			phpJson = phpJson.replace("<?php", "").replace("return array (", "").replace("'DATA_TYPE' => false,", "").replace(");", "").replace("?>", "");
//			System.out.println("1. " + phpJson);
			
			//2. 将每个不同的配置拆分
			if (!"".equals(phpJson = phpJson.trim())) {
				//此处有问题，不能适用所有场景，需改进
				String[] arr = phpJson.split("\\),");
				
				//3. 将 key 与 value 拆分开
				for (int i=0; i<arr.length; i++) {
					String[] kv = arr[i].replace(" ", "").replace("'", "").split("=>array");
//					System.out.println("3. key = " + kv[0] + "; value = " + kv[1]);

					ArrayList keyValue = new ArrayList();
					keyValue.add(kv[0]);	//存入 key
					
					//4. 将 value 中每个结果拼装为数组
//					(招商银行=>招商银行,中国银行=>中国银行,中国工商银行=>中国工商银行,中国建设银行=>中国建设银行,中国农业银行=>中国农业银行,中国邮政储蓄银行=>中国邮政储蓄银行,交通银行=>交通银行,上海浦东发展银行=>上海浦东发展银行,深圳发展银行=>深圳发展银行,中国民生银行=>中国民生银行,兴业银行=>兴业银行,平安银行=>平安银行,北京银行=>北京银行,天津银行=>天津银行,上海银行=>上海银行,华夏银行=>华夏银行,光大银行=>光大银行,广发银行=>广发银行,中信银行=>中信银行,上海农商银行=>上海农商银行,
					kv[1] = kv[1].replace("(", "").replace(")", "");
					String[] vArr = kv[1].split(",");
					String[] kv1 = new String[vArr.length];
					for(int j=0; j<vArr.length; j++) {
						kv1[j] = vArr[j].split("=>")[1];
//						System.out.println("4. value = " + kv1[j]);
					}
					keyValue.add(kv1) ;	//存入 value
					
					lst.add(keyValue);	//添加入返回值中
				}
			}
		}
		
		return lst;
	}
	
	/**
	 * 根据属性名获取 json 数据中的数组
	 * @param targetName
	 * @return
	 */
	private static String[] getTargetList(String phpJson, String targetName) throws Exception {
		//无目标属性不操作
		if ("".equals(targetName.trim())) {
			return null;
		}
		
		List<ArrayList> tlst = convertPHPJson2Json(phpJson);
		for (int i = 0; i < tlst.size(); i++) {
			ArrayList tl = (ArrayList) tlst.get(i);
			if (targetName.trim().equals(tl.get(0))) {
				return  (String[]) tl.get(1);
			}
		}
		
		return null;
	}
	
	/**
	 * 获取银行列表
	 * @return
	 * @throws Exception
	 */
	private static String[] getBankNames() throws Exception {
		final String KEY_WORD = "BANK_NAME";
		
//		String[] ret = getTargetList(PHP_JSON_BANK_NAMES, KEY_WORD);
		return getTargetList(PHP_JSON_BANK_NAMES, KEY_WORD);
	}
	
	/**
	 * 获取线下充值平台账户列表
	 * @return
	 * @throws Exception
	 */
	private static String[] getOffBanks() throws Exception {
		final String KEY_WORD = "BANK";
			
//		return getTargetList(PHP_JSON_OFF_BANKS, KEY_WORD);
		return null;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " begin.");
		
		try {
			String[] names = TestPHPJson.getBankNames();
			for (String name : names) {
				System.out.println("银行名称：" + name);
			}
//			String[] names = TestPHPJson.getOffBanks();
//			for (String name : names) {
//				System.out.println("银行名称：" + name);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " end.");
	}

}
