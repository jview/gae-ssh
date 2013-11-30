package org.esblink.module.loginmgmt.util;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.cache.Cache;
import javax.cache.CacheManager;
import com.google.appengine.api.images.*;

public class ImageServlet extends HttpServlet 
{    
	private static final long serialVersionUID = 1L;    
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
	{        
		response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片        
		response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容        
		response.setHeader("Cache-Control", "no-cache");        
		response.setDateHeader("Expire", 0);        
//		RandomValidateCode randomValidateCode = new RandomValidateCode();        
		try 
		{            
//			randomValidateCode.getRandcode(request, response);//输出图片方法
//			 response.setHeader("Pragma", "No-cache");
//			    response.setHeader("Cache-Control", "no-cache");
//			    response.setDateHeader("Expires", 0);

			    ImagesService imagesService = ImagesServiceFactory.getImagesService();
			    try {
			        byte[] imgData;
			        String imgName = "WEB-INF/pic.png";
			        Cache cache = CacheManager.getInstance().getCacheFactory().createCache(Collections.emptyMap());
			        if (cache.containsKey(imgName)) {
			            imgData = (byte[]) cache.get(imgName);
			        } else {
			            FileInputStream fileInputStream = new FileInputStream(imgName);
			            imgData = new byte[fileInputStream.available()];
			            fileInputStream.read(imgData);
			            fileInputStream.close();
			            cache.put(imgName, imgData);
			        }
			        Image bigImg = ImagesServiceFactory.makeImage(imgData);

			        Random random = new Random();
			        Collection<Composite> compositeArray = new ArrayList();
			        int row = random.nextInt(10);
			        String captcha = "";

			        for (int i = 0; i < 4; i++) {
			            int column = random.nextInt(10);
			            captcha += String.valueOf(column);
			            Transform crop = ImagesServiceFactory.makeCrop(0.1f*column, 0.1f*row, 0.1f*(column+1), 0.1f*(row+1));
			            Image unitImg = ImagesServiceFactory.makeImage(imagesService.applyTransform(crop, bigImg).getImageData());
			            bigImg.setImageData(imgData);
			            Composite composite = ImagesServiceFactory.makeComposite(unitImg, i * 12 + 1, 0, 1.0f, Composite.Anchor.TOP_LEFT);
			            compositeArray.add(composite);
			        }
			        request.getSession().setAttribute("randomCode", captcha);

			        Image finalImg = imagesService.composite(compositeArray, 50, 20, 0);
			        OutputStream output = response.getOutputStream();
			        output.write(finalImg.getImageData());
			        output.flush();
//			        out.clear();
//			        out = pageContext.pushBody();
			    }
			    catch (Exception e) {
			        e.printStackTrace();
			    }
		} catch (Exception e) 
		{            
			e.printStackTrace();        
		}    
	}    
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{        
		doGet(request, response);    
		}
	}


