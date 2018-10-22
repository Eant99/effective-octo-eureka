package com.googosoft.controller.kjhs.pzxx;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.zf.Pzzf;
import com.googosoft.service.base.PageService;

/**
*@author 杨超超
*@date   2018年2月9日---下午2:07:03
*/
@Controller
@RequestMapping(value="/javatoxml")
public class JavaToXml extends BaseController{
	
	@Resource(name = "pageService")
	private PageService pageService;
	 //获取 页面数据 封装到map 转换xml 报文
	
    @RequestMapping(value = "/zhxml")
    @Test  
	public void zhxml(Pzzf pzzf){  
    	String khyh=this.getPageData().getString("KHYH");//开户银行
    	String hm=this.getPageData().getString("HM");//户名
    	String zh=this.getPageData().getString("ZH");//账号
    	int je=(int) this.getPageData().get("JE");//金额
	    try {  
	        JAXBContext jc = JAXBContext.newInstance(Pzzf.class);  
	        Marshaller ms = jc.createMarshaller();  
	        Pzzf st = new Pzzf(khyh, hm,zh, je);  
	        ms.marshal(st, System.out);  
	    } catch (JAXBException e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    }  
	}  
    /** 
     *  发送请求报文  
     *  注:不可以关闭流 否则会关闭对应的socket 
     */  
    public static void send(Socket socket, byte[] st) {  
       DataOutputStream out=null;  
        try {  
            out = new DataOutputStream((socket.getOutputStream()));  
            out.write(st);  
            out.flush();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
    //socket 连接网银外联平台地址 
    //socket 客户端
    public static void main(String[] args) throws IOException{
        try{
                Socket socket=new Socket("192.168.10.81",5200);
                System.out.println("client start ...");
                //向本机的5200端口发出客户请求
                BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
                //由系统标准输入设备构造BufferedReader对象
                PrintWriter write=new PrintWriter(socket.getOutputStream());
                //由Socket对象得到输出流，并构造PrintWriter对象
                BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //由Socket对象得到输入流，并构造相应的BufferedReader对象
                String readline;
                readline=br.readLine(); //从系统标准输入读入一字符串
                while(!readline.equals("end")){
                    //若从标准输入读入的字符串为 "end"则停止循环
                    write.println(readline);
                    //将从系统标准输入读入的字符串输出到Server
                    write.flush();
                    //刷新输出流，使Server马上收到该字符串
                    System.out.println("Client:"+readline);
                    //在系统标准输出上打印读入的字符串
                    System.out.println("Server:"+in.readLine());
                    //从Server读入一字符串，并打印到标准输出上
                    readline=br.readLine(); //从系统标准输入读入一字符串
                } //继续循环
                write.close(); //关闭Socket输出流
                in.close(); //关闭Socket输入流
                socket.close(); //关闭Socket                       
            }catch(Exception e) {
                System.out.println("can not listen to:"+e);//出错，打印出错信息
            }
        }
}
