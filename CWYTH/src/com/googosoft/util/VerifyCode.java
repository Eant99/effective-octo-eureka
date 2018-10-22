package com.googosoft.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.DBHelper;
/**
 * 验证码生成类（简化版）
 * @author master
 */

@Component("verifyCode")
public class VerifyCode {
	
	@Resource(name="jdbcTemplate")
	private DBHelper db;
	
	private int w = 88;
	private int h = 30;
 	private Random r = new Random();
	private String[] fontNames  = {"微软雅黑"};
	private String codes  = "0123456789ABCDEFGHJKMNPQRSTUVWXYZ";//默认的随机内容串
	private int codesLength = 4;//随机内容串长度
	private Color bgColor  = new Color(255, 255, 255);
	private String text ;
	
	/**
	 * 生成随机的颜色
	 * @return
	 */
	private Color randomColor () {
		int red = r.nextInt(150);
		int green = r.nextInt(150);
		int blue = r.nextInt(150);
		return new Color(red, green, blue);
	}
	/**
	 * 生成随机字体
	 * @return
	 */
	private Font randomFont () {
		int index = r.nextInt(fontNames.length);
		String fontName = fontNames[0];
		int style = r.nextInt(4);
		int size = r.nextInt(5) + 16; 
		return new Font(fontName, style, size);
	}
	/**
	 * 绘制随机的干扰线
	 * @param image
	 */
	private void drawLine (BufferedImage image) {
		int num  = 3;
		Graphics2D g2 = (Graphics2D)image.getGraphics();
		for(int i = 0; i < num; i++) {
			int x1 = r.nextInt(w);
			int y1 = r.nextInt(h);
			int x2 = r.nextInt(w);
			int y2 = r.nextInt(h); 
			g2.setStroke(new BasicStroke(1.5F)); 
			g2.setColor(Color.BLUE); 
			g2.drawLine(x1, y1, x2, y2);
		}
	}
	/**
	 * 随机去随机串中的某个字符
	 * @return
	 */
	private char randomChar () {
		int index = r.nextInt(codes.length());
		return codes.charAt(index);
	}
	/**
	 * 创建图片
	 * @return
	 */
	private BufferedImage createImage () {
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB); 
		Graphics2D g2 = (Graphics2D)image.getGraphics(); 
		g2.setColor(this.bgColor);
		g2.fillRect(0, 0, w, h);
 		return image;
	}
	/**
	 * 获取图片
	 * @return
	 */
	public BufferedImage getImage () {
		BufferedImage image = createImage(); 
		Graphics2D g2 = (Graphics2D)image.getGraphics();
		StringBuilder sb = new StringBuilder();
		//0：4位数字，1：6位数字，2：4位字母，3：6位字母，4：4位数字与字母，5：6位数字与字母）
		String yzmlx = getYzmlx();
		switch (yzmlx) {
			case "1":
				codes = "0123456789";
				codesLength = 4;
				break;
			case "2":
				codes = "0123456789";
				codesLength = 6;
				break;
			case "3":
				codes = "ABCDEFGHJKLMNOPQRSTUVWXYZ";
				codesLength = 4;
				break;
			case "4":
				codes = "ABCDEFGHJKLMNOPQRSTUVWXYZ";
				codesLength = 6;
				break;
			case "5":
				codes = "0123456789ABCDEFGHJKLMNOPQRSTUVWXYZ";
				codesLength = 4;
				break;
			case "6":
				codes = "0123456789ABCDEFGHJKLMNOPQRSTUVWXYZ";
				codesLength = 6;
				break;
		}
		for(int i = 0; i < codesLength; i++)  {
			String s = randomChar() + ""; 
			sb.append(s); 
			float x = i * 1.0F * w / codesLength; 
			g2.setFont(randomFont()); 
			g2.setColor(randomColor()); 
			g2.drawString(s, x, h-5); 
		}
		this.text = sb.toString(); 
//		drawLine(image);
		return image;		
	}
	/**
	 * 获取生成的随机串内容
	 * @return
	 */
	public String getText () {
		return text;
	}
	/**
	 * 向客户端输出生成的图片
	 * @param image
	 * @param out
	 * @throws IOException
	 */
	public static void output (BufferedImage image, OutputStream out) 
				throws IOException {
		ImageIO.write(image, "JPEG", out);
	}
	/**
	 * 获取验证码类型
	 * @return
	 */
	private String getYzmlx(){
		String sql = "SELECT YZMLX FROM ZC_SYS_LOGIN";
		return db.queryForObject(sql,String.class);
	}
}


