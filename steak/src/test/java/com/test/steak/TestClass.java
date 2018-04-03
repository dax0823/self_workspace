package com.test.steak;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.panda.steak.generator.model.GoodsTransfer;
import com.panda.steak.goods.service.IGoodsTransfer;

@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestClass {
	private static Logger logger = Logger.getLogger(TestClass.class);
    // private ApplicationContext ac = null;
	
    @Resource
    private IGoodsTransfer goodsTransfer = null;
    
    @Test
    public void testGetInfoById(int id) {
    	System.out.println("Here is testGetInfoById().");
//    	GoodsTransfer gt = goodsTransfer.getInfoById(id);
//        logger.info(JSONObject.fromObject(gt).toString());
//    	System.out.println("TestClass.testGetInfoById: " + JSONObject.fromObject(gt).toString() + ".");
//    	System.out.println("TestClass.testGetInfoById: " + JSON.toJSONString(gt) + ".");
    }
	
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "begin.");
		
		final int id = 1;
		TestClass clz = new TestClass();
		clz.testGetInfoById(id);
		
		System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "end.");
	}

}
