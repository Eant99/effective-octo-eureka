<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>薪资申报打印</title>
<%@include file="/static/include/public-list-js.inc"%>
</head>
<body>

<a href="javascript:PreviewMytable();">打印预览</a>
<a id="btn_back" href="javascript:void(0)">返回</a>


<!-- 打印区域---------------------------------------------------------------------------------------------------- -->
<div id="div1" style="width: 90%;height: 540px">
  <h1 style="text-align: center">人员其他工薪收入发放表</h1>
  <table width="52%" border="0"  style="font-size: 12px">
    <tbody>
      <tr>
        <td width="30%">发放时间：2013年9月7号</td>
        <td width="39%">摘要：   民远大神打撒大实打实大沙发上</td>
        <td width="31%">&nbsp;</td>
      </tr>
      <tr>
        <td>部门编号：</td>
        <td>部门名称：</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>项目编号：</td>
        <td>项目名称：</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>经办人：</td>
        <td>联系电话：</td>
        <td>Email：wangchengxin1995@qq.com</td>
      </tr>
    </tbody>
  </table>
  <table width="100%" border="1" cellSpacing=0 cellPadding=1 style="border-collapse:collapse;">
    <tbody style="text-align: center">
      <tr>
        <th width="5%" scope="col">序号</th>
        <th width="9%" scope="col">工号</th>
        <th width="12%" scope="col">姓名</th>
        <th width="19%" scope="col">银行卡号</th>
        <th width="20%" scope="col">身份证号</th>
        <th width="15%" scope="col">发放类别</th>
        <th width="11%" scope="col">应发金额</th>
        <th width="9%" scope="col">备注</th>
      </tr>
      <tr>
        <td>1</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>6223190236541021</td>
        <td>370511125365893</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </tbody>
  </table>
  <table width="100%" border="0" style="font-size: 12px">
    <tbody>
      <tr>
        <td width="36%" height="74"><strong>大写金额：</strong></td>
        <td width="45%"><strong>发放方式：</strong></td>
        <td width="19%"><strong>小写金额：</strong></td>
      </tr>
      <tr>
        <td height="56">经手人：</td>
        <td>证明人：</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="93"><p>一支笔：</p>
        <p>本纸质数据与申报系统系统电子数据一致</p></td>
        <td><p>联签人：</p>
        <p>&nbsp;</p></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="93">&nbsp;</td>
        <td>&nbsp;</td>
        <td style="text-align: right">&emsp;&emsp;&emsp;年&emsp;&emsp;&emsp;月&emsp;&emsp;&emsp;日</td>
      </tr>
    </tbody>
  </table>
</div>
<!-- 打印区域---------------------------------------------------------------------------------------------------- -->
<script src="${ctx }/static/javascript/public/LodopFuncs.js"></script>
<script> 
	$(function(){
		init();
	})
	
	//返回
$("#btn_back").click(function(){
	window.history.back(-1);
// 	location.href="${ctx}/kylbx/gowdbxListPage";
});
	function PreviewMytable(){
		var LODOP=getLodop();  
		LODOP.PRINT_INIT("打印");
		LODOP.ADD_PRINT_HTM("20%","5%","90%",1000,document.getElementById("div1").innerHTML);
		LODOP.ADD_PRINT_BARCODE("25%","70%",70,70,"QRCode","21452632144564512154");
		LODOP.ADD_PRINT_BARCODE("25%","77%",200,70,"Code39","21452632144564512154");
		LODOP.SET_PRINT_PAGESIZE(2, 2100, 2970,"A4");
		LODOP.PREVIEW();	
	};	
	function init(){
		var zje= "${organizationname1.BXZJE}";
		$("#456").text((""+smalltoBIG(parseFloat(zje))).replace("undefined", "零").replace("undefined", "零"));
// 		$("#4561").text((""+smalltoBIG(parseFloat(zje))).replace("undefined", "零"));
	}
	function smalltoBIG(n)     
	{    
	    var fraction = ['角', '分'];    
	    var digit = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];    
	    var unit = [ ['元', '万', '亿'], ['', '拾', '佰', '仟']  ];    
	    var head = n < 0? '欠': '';    
	    n = Math.abs(n);    
	  
	    var s = '';    
	  
	    for (var i = 0; i < fraction.length; i++)     
	    {    
	        s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');    
	    }    
	    s = s || '整';    
	    n = Math.floor(n);    
	  
	    for (var i = 0; i < unit[0].length && n > 0; i++)     
	    {    
	        var p = '';    
	        for (var j = 0; j < unit[1].length && n > 0; j++)     
	        {    
	            p = digit[n % 10] + unit[1][j] + p;    
	            n = Math.floor(n / 10);    
	        }    
	        s = p.replace(/(零.)*零$/, '').replace(/^$/, '零')  + unit[0][i] + s;    
	    }    
	    return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整');    
	}  
</script>
		

</body>
</html>
