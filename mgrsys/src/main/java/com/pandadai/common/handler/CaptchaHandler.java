/**
 * 
 */
package com.pandadai.common.handler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author 仵作
 * 2014-9-10 上午10:48:50
 */
public class CaptchaHandler {
	private static final String CHARS = "abcdefghjklmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ";
	
	/**
	 * 颜色
	 * @param fc
	 * @param bc
	 * @return
	 */
	private static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	
	/**
	 * 图片生成
	 * @return
	 */
	public Map<String, BufferedImage> getImage() {
		int charsLength = CHARS.length();
		// 图片大小
		int width = 79, height = 21;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Random random = new Random();
		// 背景颜色
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.ITALIC, height - 10));
		// 底色乱线
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 50; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		// 缓存字符串
		StringBuilder sRand = new StringBuilder();
		// 随意字体
		String[] fontNames = { "Times New Roman", "Gungsuh", "Book antiqua", "" };
		for (int i = 0; i < 5; i++) {
			g.setFont(new Font(fontNames[random.nextInt(3)], Font.CENTER_BASELINE, height - 5));
			char rand = CHARS.charAt(random.nextInt(charsLength));
			sRand.append(rand);
			g.setColor(new Color(20 + random.nextInt(110), 20 
					+ random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(String.valueOf(rand), 15 * i + random.nextInt(6) + 3, 17);
		}
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 5; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(width);
			int yl = random.nextInt(width);
			g.drawLine(x, y, x + xl, y + yl);
		}
		g.dispose();
		
		Map map = new HashMap();
		map.put(sRand.toString(), image);
		return map;
	}
}
