<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-list-css.inc"%>
<script src="${ctxStatic}/javascript/public/LodopFuncs.js"></script>
<style type="text/css">
	.input_info{
		width:100px;
	}
	button{
/* 		background-color: #00acec !important; */
/* 		color: white !important; */
	}
	.div_bottom{
    	width: 99%;
    	position: absolute;
    	bottom: 20px;
   		background-color: #f3f3f3;
		
	}
	.bom{
		color:red;
	}
	.yc{
		display:none!important;
	}
	#btn_search_more>span {
/* 		background-color:#00acec !important; */
/* 		color: white !important; */
	}
	ul li{
	list-style-type:none;
	
	}
	.bottom1{
	margin-top: 10px;
	}
	.bottom1 tr td{
	width: 400px !important;
	}
	tr td{
	height: 30px;
	text-align: center;
	}
	tr th{
	    border-bottom: 0px solid #ddd !important;
	}
	.spyj{
	margin-top: -100px !important;
	}
/* 	.fullscreen{ */
/* 	width:80%; */
/* 	margin:0 auto; */
/* 	} */
.table-bordered > thead > tr > td, .table-bordered > thead > tr > th {
    	border-bottom-width: 0px;
    	border-bottom:1px solid #ddd;
	}
	 table{
		border-collapse:collapse!important;
	}
   .bottom1{
  position: absolute;
   }
 #tbodyy .bottom1 div{
   float: left;list-style-type:none;margin-left: 80px;
   margin-top:500px;
   }
   #tbodyy1 .bottom1 div{
   float: left;list-style-type:none;margin-left: 80px;
   }
   .spyj{
   vertical-align: top ! important;
   }
</style>
</head>
<body>
<!-- <a href="javascript:PreviewMytable();">打印预览</a> -->
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<div class="search-simple">
				<div class="btn-group pull-right" role="group">
					<button type='button' class="btn btn-default" id="btn_print">打印预览</button>
					<button type="button" class="btn btn-default" id="btn_back">返回列表</button>
				</div>
			</div>
	</div>
<!-- 	打印内容 -->
	<div id="divprint">
	<div class="container-fluid"  style="margin-top: 20px"> 
	<div> 
	<div id="titleh">
		<center>
		<table>
			<tr style="height:25px;">
		       <td colspan="3" align="center" style="height: 40px">
                 <span id="txt_bbmc" style="font-size: 20pt"> &ensp;&ensp;&ensp; &ensp;&ensp;&ensp;安徽财贸职业学院 &ensp;&ensp;&ensp;日常报销单</span>
               </td>
	        </tr></table>
	    </center>
	    <div>
	      <center>___________________________________________________________________________________</center>
	    </div>
	    </div>
	    <div style="margin-top: 20px">
	      <center><font style="font-size: 20px">${zbMap.YEAR} &ensp;&ensp;年&ensp;&ensp;${zbMap.MONTH}&ensp;&ensp; 月&ensp;&ensp;${zbMap.DAY}&ensp;&ensp; 日</font></center>
	    </div>
	    <div class="btn-group pull-right" style="float: right;margin-top: 20px;margin-right:60px; font-size: 20px" role="group">
	         单据张数： &ensp; &ensp;${zbMap.FJZZS}
	    </div>
	     <div class="btn-group pull-center" style="float: center;margin-top: 5px;margin-left:50%;" id="tbodyy1">
	    <table class="table table-striped table-bordered" style="border-collapse:collapse;margin:0 auto">
	    <tr>
	      <th style="text-align:center;">经济科目</th>
	      <th style="text-align:center;">金额（元）</th>
	    </tr>
	     <c:forEach var="jjList" items="${jjList}">
		  <tr>
		  	<td id="" style="text-align: center;">${jjList.FYMC}</td>
		  	<td id="" style="text-align: center;">${jjList.BXJE}</td>
		  </tr>
		  </c:forEach>
	    </table>
	    </div>
	    <div style="position: absolute;margin-top: -130px;font-size: 20px">        	 
                               开支内容：&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;${zbMap.BXSY}
	    </div>
	    <div style="margin-top: -40px">        	 
                               共计报销金额
	    </div>
	    <div style="position: absolute;margin-top: 10px" id="dxje">        	 
                              人名币（大写）:
	    </div>
	    <div class="btn-group pull-right" id="xxje"  style="float: right;margin-top: 20px;margin-right:100px;" role="group">
	        ￥： ${zbMap.bxzje}
	    </div>
	     </div>
	</div>


<div class="container-fluid"  style="margin-top: 10px;" id="tbodyy">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse:collapse;margin:0 auto">
		        	<thead>
				        <tr>
                            <th  style="text-align:center;width: 400px">院领导审批意见</th>
				            <th style="text-align:center;width: 400px">部门负责人审批意见</th>
				            <th  style="text-align:center;width:400px">会计审批意见</th>   
				            <th  style="text-align:center;width: 400px;">报销人</th>				            				            
				        </tr>

					</thead>
				    <tbody>
			        <tr style="height: 400px;">
			        <td style="text-align: center;"><span><img src="${fjView }" style="width: 130px;height: 90px;"></span><br>${yxMap.SHYJ }<br><fmt:formatDate value="${yxMap.jdsj }"/></td>
			        <td style="text-align: center;"><span><img src="${fjView2 }" style="width:130px;height: 90px;"></span><br>${bmMap.SHYJ }<br><fmt:formatDate value="${bmMap.jdsj }"/></td>
		            <td style="text-align: center;"><span><img src="${fjView3 }" style="width:130px;height: 90px;"></span><br>${kjMap.SHYJ }<br><fmt:formatDate value="${kjMap.jdsj }"/></td>
			        
			           <td style="text-align: center;">
				            <div>
				            ${zbMap.bxrname}
				            </div>			    
			       </td>
			        </tr>
				   </tbody>
				</table>					
		</div>
</div>
		</div>
	</div>
<input type="hidden" id="pzfh" class="form-control input-radius window" name="pzfh" value="">
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function(){
	init();
	//console.log("++++"+"${mxList}");
	$("#btn_print").click(function(){
		PreviewMytable();
		
	});
	
})

function PreviewMytable(){
	var LODOP=getLodop();  
	LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_分页打印综合表格");
	var strStyle="<style> table,td,th {border-width: 1px;border-style: solid;border-collapse: collapse;}</style>";
// 	var strStyle1="<style> table,td,th {border-width: 1px;border-style: solid;border-collapse: collapse}</style>";
	LODOP.ADD_PRINT_HTM(10,"5%","90%",1000,strStyle+document.getElementById("divprint").innerHTML);
// 	LODOP.ADD_PRINT_HTM(128,"5%","90%",344,strStyle1+document.getElementById("tbodyy1").innerHTML);
// 	LODOP.ADD_PRINT_HTM(36,"5%","90%",109,document.getElementById("titleh").innerHTML);
	
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1);	
//		LODOP.SET_PRINT_STYLEA(0,"Vorient",3);		
//		LODOP.SET_PRINT_STYLEA(0,"LinkedItem",4);
//		LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
//		LODOP.SET_PRINT_STYLEA(0,"FontColor","#FF0000");
//		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
//		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
//		LODOP.SET_PRINT_STYLEA(0,"Horient",3);	
//		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.PREVIEW();	
};	
function init(){
	var zje="${zbMap.bxzje}";
	$("#dxje").text("报销金额（大写）："+smalltoBIG(parseFloat(zje)));
	
}
//返回按钮
$("#btn_back").click(function(e){
	location.href="${ctx}/wsbx/rcbx/goRcbxListPage?mkbh=${param.mkbh}";
});


/** 数字金额大写转换(可以处理整数,小数,负数) */    
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