/**
 * 
 */
package com.pandadai.common.action;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pandadai.common.handler.CaptchaHandler;
import com.pandadai.common.utils.Constants;

/**
 * @author 仵作
 * 2014-9-10 上午10:35:11
 */
@Controller
public class CaptchaAction {
	private Logger logger = Logger.getLogger(this.getClass());
	
//	private static final long serialVersionUID = 358976313252814437L;
	@Autowired
	private CaptchaHandler captchaHandler;

	@RequestMapping("/gainCaptcha.do")
	public void gainCaptcha(HttpServletRequest request, HttpServletResponse response) {
		String ip = request.getRemoteHost();
		int port = request.getServerPort();
		
		System.out.println("ip = " + ip + "; port = " + port);
		
		OutputStream os = null;
		try {
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			
			Map<String, BufferedImage> map = captchaHandler.getImage();
			String sRand = "";
			BufferedImage image = null;
			if (map.entrySet() != null && map.entrySet().size() == 1) { // 仅有一对键值时
				Iterator it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry entry = (Map.Entry) it.next();
					sRand = entry.getKey().toString();
					image = (BufferedImage) entry.getValue();
				}
			}
			
//			request.getSession().setAttribute("captchaLogin", sRand);
			request.getSession().setAttribute(Constants.CAPTCHA_LOGIN_STR, sRand);
			
			System.out.println("captchaCode:: " + request.getSession().getId());
			
			logger.debug("captchaCode = " + sRand);
//			Thread.sleep(100);
			os = response.getOutputStream();
			ImageIO.write(image, "JPEG", os);
			if (os != null) {
				os.flush();
				os.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
