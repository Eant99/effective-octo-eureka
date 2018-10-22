package com.googosoft.controller.base;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.googosoft.util.PageData;
import com.googosoft.util.PsImage;
import com.googosoft.util.Validate;

@Controller
@RequestMapping("/convert")
public class ConvertController extends BaseController {
	static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
    static BASE64Decoder decoder = new sun.misc.BASE64Decoder();

    /**
     * 
     * @param url
     * @return
     */
    public static String getImageBinary(String url){      
        File f = new File(url);
        BufferedImage bi;      
        try {
            bi = ImageIO.read(f);      
            ByteArrayOutputStream baos = new ByteArrayOutputStream();      
            ImageIO.write(bi, "jpg", baos);
            byte[] bytes = baos.toByteArray();      
            
            return encoder.encodeBuffer(bytes).trim();      
        } catch (IOException e) {
            e.printStackTrace();     
        }
        return null;      
    }
    /**
     * 
     * @param base64Str 
     * @param newurl  绝对路径，需要有图片名称及扩展名
     */
    @RequestMapping(value = "/base64toimg", produces = "text/json;charset=UTF-8")
	@ResponseBody
    public boolean base64StringToImage(){      
        try {
        	PageData pd = this.getPageData();
        	String base64Str = pd.getString("base64Str");
        	if(Validate.noNull(base64Str)){
        		base64Str = base64Str.split(",")[1];
        	}
        	String newurl = pd.getString("newurl");
        	newurl = this.getRequest().getServletContext().getRealPath("/")+"imgFile/123.jpg";
        	byte[] bytes1 = decoder.decodeBuffer(base64Str);      
            
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            BufferedImage bi1 = ImageIO.read(bais);
            File w2 = new File(newurl);//可以是jpg,png,gif格式      
            return ImageIO.write(bi1, "jpg", w2);//不管输出什么格式图片，此处不需改动（这里的jpg跟newurl中图片的格式没关系）
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 切割图片
     * @return
     */
    @RequestMapping(value = "/cuttingimg", produces = "text/json;charset=UTF-8")
	@ResponseBody
    public String cuttingImage(){
    	PageData pd = this.getPageData();
    	String path = this.getRequest().getServletContext().getRealPath("/");
    	String oldurl = path + pd.getString("oldurl");
    	oldurl = oldurl.replace("\\", "/");
    	String fold = pd.getString("fold");
    	String rybh = pd.getString("rybh");
    	float width = 0;
    	float height = 0;
    	float x = 0;
    	float y = 0;
    	float rotate = 0;
    	try{
    		width = Float.parseFloat(pd.getString("width"));
        	height = Float.parseFloat(pd.getString("height"));
        	x = Float.parseFloat(pd.getString("x"));
        	y = Float.parseFloat(pd.getString("y"));
        	rotate = Float.parseFloat(pd.getString("rotate"));
    	}
    	catch(Exception e){
    		return "{\"success\":false,\"mgs\":\"参数错误\",\"url\":\"" + oldurl + "\"}";
    	}
    	String kzm = oldurl.substring(oldurl.lastIndexOf("."));//带.
    	String lstp = path + fold + rybh + kzm;
    	lstp = lstp.replace("\\", "/");
    	String newurl = path + fold + oldurl.substring(oldurl.lastIndexOf("/") + 1);
    	newurl = newurl.replace("\\", "/");
    	
    	try {
    		/**
    		 * 注释掉的这一款可以获取网络图片
    		 */
    		//oldurl = "http://192.168.10.63:8010/GXGDZC7/imgFile/ryphoto/3C49EFF4D3E9436E98642110282B0903.jpg";
//        	File uploadPic = new File(lstp);
//        	URL url = new URL(oldurl);
//        	HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
//        	httpUrl.connect();
//        	BufferedInputStream bis = new BufferedInputStream(httpUrl.getInputStream());
//        	FileOutputStream fos = new FileOutputStream(uploadPic);  
//        	int size = 0; 
//        	int BUFFER_SIZE = 1024;  
//    	    byte[] buf = new byte[BUFFER_SIZE];  
//        	while ((size = bis.read(buf)) != -1) {  
//        		fos.write(buf, 0, size);  
//    	    }
//        	fos.flush();
//        	fos.close();
//        	bis.close();
//        	httpUrl.disconnect();
        	
        	PsImage pi = new PsImage(new File(oldurl));
        	pi.rotate(rotate);
        	pi.clip((int)x, (int)y, (int)width, (int)height);
        	
        	File file = new File(newurl);
        	pi.createPic(file);
        	
        	
        	
        	return "{\"success\":true,\"msg\":\"图片处理成功!\",\"url\":\"" + fold + oldurl.substring(oldurl.lastIndexOf("/") + 1) + "\"}";
        } catch (Exception e) {
        	e.printStackTrace();
            return "{\"success\":false,\"msg\":\"图片处理失败!\",\"url\":\"" + fold + oldurl.substring(oldurl.lastIndexOf("/") + 1) + "\"}";
        }
    }
}
