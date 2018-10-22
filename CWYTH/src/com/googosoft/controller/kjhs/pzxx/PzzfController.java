package com.googosoft.controller.kjhs.pzxx;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.commons.SendHttpUtil;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.pzxx.PzcxsService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/**
*@author 杨超超
*@date   2018年2月8日---下午8:04:02
*/
@Controller
@RequestMapping(value="/pzzf")
public class PzzfController extends BaseController{

	@Resource(name = "pageService")
	private PageService pageService;
	
	@Resource(name = "pzcxsService")
	private PzcxsService pzcxsService;
	/**
	 * 凭证支付页面
	 * @return
	 */
	@RequestMapping(value="/pzzflist")
	public ModelAndView goPzlrPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String zfzt=pd.getString("zfzt");
		mv.addObject("zfzt",zfzt);
		mv.setViewName("kjhs/pzxx/pzzf/pzzf_list");
		return mv;
	}
	
	//凭证支付 dataTable 分页方法
    @RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {		
    	String zfzt=Validate.isNullToDefaultString(this.getPageData().getString("zfzt"),"0");
	    PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		String strWhere="";
		PageList pageList = new PageList();
		sqltext.append(" * ");
//		tablename.append(" ( select mx.guid,mx.zfzt as zfzt,b.pzbh as pzh,b.pzzt as pzzt,mx.kmbh as kmbh,mx.bmbh as bmbh,mx.xmbh as xmbh,to_char(b.pzrq,'yyyy-mm-dd') as pzrq, (case b.pzzt  when '00' then  '已保存' when '01' then '已复核' when '02' then '已记账'when '99' then '已结账' else '' end) pzztmc, pzlxmc,mx.zy as pzzy,lrb.jsdh as jsdh,(select yh.khyh from cw_jsyhzhb yh where yh.guid = lrb.khyh) khyh,lrb.hm as hm,lrb.zh as zh,(to_char(round(nvl(jfje,dfje),2),'fm999999999999999999990.00'))as je from cw_pzlrzb b left join cw_pzlrmxb mx on mx.pzbh=b.guid left join cw_fzlrb lrb on lrb.kmbh=mx.guid where 1=1    and b.pzzt not in ('00') and mx.zfzt in ('"+zfzt+"') ");	
		tablename.append(" (select yh.guid,yh.status as zfzt,b.pzbh as pzh,b.pzzt as pzzt,mx.kmbh as kmbh,");
		tablename.append(" mx.bmbh as bmbh,mx.xmbh as xmbh,to_char(b.pzrq, 'yyyy-mm-dd') as pzrq,");
		tablename.append(" (case b.pzzt when '00' then '已保存' when '01' then '已复核' when '02' then '已记账' when '99' then '已结账' else '' end) pzztmc,");
		tablename.append(" pzlxmc, mx.zy as pzzy, yh.yhmc khyh, yh.xm as hm,yh.yhzh as zh,to_char(yh.je,'FM999999999999999999999999999999990.00') as je ");
		tablename.append(" from cw_pzlrzb b");
		tablename.append(" left join cw_pzlrmxb mx on mx.pzbh = b.guid ");
		tablename.append(" left join cw_pzlryhmx yh on  yh.mxid=mx.guid");
		tablename.append(" where 1 = 1");
//		if("0".equals(zfzt)){
//			tablename.append(" and nvl(b.pzzt,'00') in('01','02'");
//		}else{
			tablename.append(" and nvl(b.pzzt,'00') not in ('00')");
//		}
		tablename.append(" and mx.kmbh = '"+Constant.KJKM_JH9199+"' ");
		tablename.append(" and yh.status in ('"+zfzt+"')");
		tablename.append(")A");//and lrb.jsfs in ('0002','0003')
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("A.GUID");//主键
		//设置WHERE条件
		pageList.setStrWhere(strWhere); 
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();	
	}
	
    //修改支付状态 以及修改项目基本信息表 syzje  减去支付的金额
    @RequestMapping(value = "/zfztxg")
    @ResponseBody
    public int zfztxg() {
    	String guid=this.getPageData().getString("guid");//凭证录入明细表 guid
    	String je=this.getPageData().getString("JE");//科目编号
    	String kmbh=this.getPageData().getString("KMBH");//科目编号
    	String xmbh=this.getPageData().getString("XMBH");//项目编号  
    	String bmbh=this.getPageData().getString("BMBH");//科目和部门确定 项目基本信息表 减钱
    	
    	String restime = this.getPageData().getString("restime"); //响应时间
    	String vchid = this.getPageData().getString("vchid");//凭证编号
    	
    	return pzcxsService.zfztxgupdate(guid,je,kmbh,xmbh,bmbh,restime,vchid);
    }
    //批量支付 plzf
    @RequestMapping(value="/plzf")
    @ResponseBody
    public int plzf() {
    	String guid=this.getPageData().getString("guid");//凭证录入明细表 guid
    	String je=this.getPageData().getString("JE");//科目编号
    	String kmbh=this.getPageData().getString("KMBH");//科目编号
    	String xmbh=this.getPageData().getString("XMBH");//项目编号  为空则不减
    	String bmbh=this.getPageData().getString("BMBH");//科目和部门确定 项目基本信息表 减钱
    	
    	String restime = this.getPageData().getString("restime"); //响应时间
    	String vchid = this.getPageData().getString("vchid");//凭证编号
    	
		return pzcxsService.plzfjq(guid,je,kmbh,xmbh,bmbh,restime,vchid);
    }
   
    /**
     *生成 支付信息txt    WEB-INF\\file\\txt\\
     * @return 
     */
    @RequestMapping(value="/ExpZfxxTxt",produces = "text/html;charset=UTF-8")
    @ResponseBody
	private String ExpZfxxTxt() {
    	PageData pd = this.getPageData();
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		
		M_largedata m = new M_largedata();
		m.setName("序号(必填)|");
		mlist.add(m);
		m = null;
	    m = new M_largedata();
		m.setName("账号(必填)|");
		mlist.add(m);
		m = null;
		m = new M_largedata();
		m.setName("户名(必填)|");
		mlist.add(m);
		m = null;
		m = new M_largedata();
		m.setName("金额(必填)|");
		mlist.add(m);
		m = null;
		m = new M_largedata();
		m.setName("跨行标志(必填，建行填“0”，他行填“1”)|");
		mlist.add(m);
		m = null;
		m = new M_largedata();
		m.setName("人民银行联行号（跨行业务必填）|");
		mlist.add(m);
		m = null;
		m = new M_largedata();
		m.setName("多方协议号（跨行代扣必填）|");
		mlist.add(m);
		m = null;
		m = new M_largedata();
		m.setName("	摘要（选填，显示在账户流水明细中）|");
		mlist.add(m);
		m = null;
		m = new M_largedata();
		m.setName("备注（选填，显示在处理结果中供业务发起人参考）");
		mlist.add(m);
		m = null;
		//文件绝对路径
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\txt\\";
		String bankCode = pd.getString("bankCode");// bankCode= CCB,CCB||bankCode= CCB 银行标志  逗号隔开
		String guid = pd.getString("guid");
		String str = "select je,yhzh,xm,yhlhh,guid from CW_PZLRYHMX where 1=1 and guid in ('"+CommonUtils.pointToSql(guid)+"')";
		return pzcxsService.ExpZfxxTxt(str, realfile, mlist, "1", "|", this.getRequest(),bankCode,guid);
	}

    //获取 页面数据 封装到map 转换xml 报文
//    @RequestMapping(value = "/zhxml")
//    @Test  
//	public void zhxml(Pzzf pzzf){  
//    	String khyh=this.getPageData().getString("KHYH");//开户银行
//    	String hm=this.getPageData().getString("HM");//户名
//    	String zh=this.getPageData().getString("ZH");//账号
//    	String je=this.getPageData().getString("JE");//
//	    try {  
//	        JAXBContext jc = JAXBContext.newInstance(Pzzf.class);  
//	        Marshaller ms = jc.createMarshaller();  
//	        Pzzf st = new Pzzf(khyh, hm,zh, je);  
//	        ms.marshal(st, System.out);  
//	    } catch (JAXBException e) {  
//	        // TODO Auto-generated catch block  
//	        e.printStackTrace();  
//	    }  
//	}  
    /** 
     *  发送请求报文  
     *  注:不可以关闭流 否则会关闭对应的socket 
     */  
//    public static void send(Socket socket, byte[] st) {  
//       DataOutputStream out=null;  
//        try {  
//            out = new DataOutputStream((socket.getOutputStream()));  
//            out.write(st);  
//            out.flush();  
//        } catch (IOException e) {  
//            e.printStackTrace();  
//        }  
//    }
    //socket 连接网银外联平台地址 
    //socket 客户端
//    public static void main(String[] args) throws IOException{
//        try{
//                Socket socket=new Socket("127.0.0.1",5200);
//                System.out.println("client start ...");
//                //向本机的52000端口发出客户请求
//                BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
//                //由系统标准输入设备构造BufferedReader对象
//                PrintWriter write=new PrintWriter(socket.getOutputStream());
//                //由Socket对象得到输出流，并构造PrintWriter对象
//                BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                //由Socket对象得到输入流，并构造相应的BufferedReader对象
//                String readline;
//                readline=br.readLine(); //从系统标准输入读入一字符串
//                while(!readline.equals("end")){
//                    //若从标准输入读入的字符串为 "end"则停止循环
//                    write.println(readline);
//                    //将从系统标准输入读入的字符串输出到Server
//                    write.flush();
//                    //刷新输出流，使Server马上收到该字符串
//                    System.out.println("Client:"+readline);
//                    //在系统标准输出上打印读入的字符串
//                    System.out.println("Server:"+in.readLine());
//                    //从Server读入一字符串，并打印到标准输出上
//                    readline=br.readLine(); //从系统标准输入读入一字符串
//                } //继续循环
//                write.close(); //关闭Socket输出流
//                in.close(); //关闭Socket输入流
//                socket.close(); //关闭Socket                       
//            }catch(Exception e) {
//                System.out.println("can not listen to:"+e);//出错，打印出错信息
//            }
//        }
   
}
