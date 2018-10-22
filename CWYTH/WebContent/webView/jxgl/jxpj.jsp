<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>收入支出表</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	table{
		border-collapse:collapse!important;
	}
	.table-bordered>thead>tr>td, .table-bordered>thead>tr>th {
   		 border-bottom-width: 0!important;
	}
	.select2-selection{
		border-radius: 4px!important;
	}
	thead th{
		text-align:!important;
	}
	.allDiv{
		width:100%;
		height:20px;
	}
	.first{
		float:left;
		text-align:left;
		width:33%;
	}
	.second{
		float:left;
		text-align:center;
		width:34%;
	}
	.third{
		float:left;
		text-align:right;
		width:33%;
	}
	.div_bottom{
    	width: 99%;
    	margin-top: 20px;
    	bottom: 20px;
   		background-color: #f3f3f3;
	}
	.bom{
		float:left;
		text-align:left;
		width:33%;
	}
	#jjje{
	    padding: 0px;
	    border:0px;
	    
	}
	#heh{
	    border-radius: 0px !important;
	}
/* 	.table{ */
/* 		width:50%!important; */
/* 	} */
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px;margin-bottom: 30px;">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>项目负责人</label>
					<input type="text" id="txt_rygh" class="form-control" name="rygh" table="A" placeholder="请输项目负责人">
				</div>
				<div class="form-group">
					<label>项目名称</label>
					<input type="text" id="txt_xm" class="form-control" name="xm"  table="A" placeholder="请输入项目名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn1111111111"><i class="fa icon-chaxun"></i>查 询</button>
				
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_exp">保存</button>
<!-- 	               <button type="button" class="btn btn-default" id="btn_print">打印预览</button> -->
				</div>
			</div>
		</form>
	</div>
		<div class="container-fluid" style="width: 82%">

		<center>
			<tr style="height:25px;">
		       <td colspan="3" align="center" style="height: 40px">
                 <span id="txt_bbmc" style="font-size: 16pt">绩效评价</span>
               </td>
	        </tr>
	    </center>
	    
	    
	   <!--  <div class="btn-group pull-right" role="group">
	          <ul>
                <li>
                <tr>
                <td align="right" valign="bottom" style="width: 429px; height: 20px;">
                              单位：万元
                </td> 
                </tr>
                </li>
	          <li>
	    	 <td align="right" valign="middle" style="width: 400px; height: 20px;">
                              公开02表             
                </td> 
                </tr>
                </li>
                </ul>  
	    </div> -->
	  
<!-- 	    <div style="margin-top: 20px;position: absolute;">        	  -->
<!--                                 编制单位：山东国子软件 -->
<!-- 	    </div> -->
<!-- 	      <div style="margin-left: 47.5%;margin-top:20px">     -->
	    	
<!--                      2018年 -->
               
<!-- 	    </div> -->
	</div>
	
	
	<div class="container-fluid" style="margin-top: 50px">
		<div class='responsive-table'>
<!-- 			<center><h3>收入预算支出表</h3></center> -->
			<div class='scrollable-area' > 
				<table id="mydatatables" class="table table-striped table-bordered" style="width: 82%;margin:auto">
		        	<thead>
				        <tr>
				        	<th style="text-align:center;" >项目负责人</th>
				        	<th style="text-align:center;" >项目名称</th>
				        	<th style="text-align:center;" >实际金额（万元）</th>
				        	<th style="text-align:center;" >一级指标</th>
				        	<th style="text-align:center;" >二级指标</th>
				        	<th style="text-align:center;" >指标内容</th>
				        	<th style="text-align:center;" >指标值</th>
				        	<th style="text-align:center;" >实际得分</th>
<!-- 				        	<th style="text-align:center;" >其他收入</th> -->
				        </tr>
					</thead>
				    <tbody style="margin-top:20px" >
				    	<tr>
				        	<td style="text-align:center;" >王建党</th>
				        	<td style="text-align:left;" >财务一体化</th>
				        	<td id="jjje"><input  id="heh" type="text" style="width: 100% ;text-align:right;" value="10" ></input></td>
				        	<td id="jjje"><input id="heh" type="text" style="width: 100% ;text-align:right;" value="95" ></input></td>
				        	<td id="jjje"><input id="heh" type="text" style="width: 100% ;text-align:right;" value="97" ></input></td>
				        	<td id="jjje"><input id="heh" type="text" style="width: 100% ;text-align:left;" value="指示内容" ></input></td>
				        	<td id="jjje"><input id="heh" type="text" style="width: 100% ;text-align:right;" value="97" ></input></td>
				        	<td id="jjje"><input id="heh" type="text" style="width: 100% ;text-align:right;" value="96"  readonly></input></td>
<!-- 				        	<th style="text-align:right;" >8,287.66</th> -->
				        </tr>
				        <tr>
				        	<th style="text-align:center;"colspan="7"  >合计</th>
				        	<th style="text-align:right;" >96</th>
				        </tr>
				        
				        
				        <tr>
				        	<td style="text-align:center;" >项目负责人</td>
				        	<td style="text-align:center;" >项目名称</td>
				        	<td style="text-align:center;" >实际金额（万元）</td>
				        	<td style="text-align:center;" >一级指标</td>
				        	<td style="text-align:center;" >二级指标</td>
				        	<td style="text-align:center;" >指标内容</td>
				        	<td style="text-align:center;" >指标值</td>
				        	<td style="text-align:center;" >实际得分</td>
<!-- 				        	<th style="text-align:center;" >其他收入</th> -->
				        </tr>
				        <tr>
				        	<td style="text-align:center;" >温娜娜</th>
				        	<td style="text-align:left;" >采购中心系统</th>
				        	<td id="jjje"><input id="heh" type="text" style="width: 100% ;text-align:right;" value="20.5" ></input></td>
				        	<td id="jjje"><input id="heh" type="text" style="width: 100% ;text-align:right;" value="98" ></input></td>
				        	<td id="jjje"><input id="heh" type="text" style="width: 100% ;text-align:right;" value="96" ></input></td>
				        	<td id="jjje"><input id="heh" type="text" style="width: 100% ;text-align:left;" value="指示内容" ></input></td>
				        	<td id="jjje"><input id="heh" type="text" style="width: 100% ;text-align:right;" value="96" ></input></td>
				        	<td id="jjje"><input id="heh" type="text" style="width: 100% ;text-align:right;" value="97"  readonly></input></td>
<!-- 				        	<th style="text-align:right;" >8,287.66</th> -->
				        </tr>
				        <tr>
				        	<th style="text-align:center;"colspan="7"  >合计</th>
				        	<th style="text-align:right;" >97</th>
				        </tr>
				        
				        <tr>
				        	<td style="text-align:center;" >项目负责人</td>
				        	<td style="text-align:center;" >项目名称</td>
				        	<td style="text-align:center;" >实际金额（万元）</td>
				        	<td style="text-align:center;" >一级指标</td>
				        	<td style="text-align:center;" >二级指标</td>
				        	<td style="text-align:center;" >指标内容</td>
				        	<td style="text-align:center;" >指标值</td>
				        	<td style="text-align:center;" >实际得分</td>
<!-- 				        	<th style="text-align:center;" >其他收入</th> -->
				        </tr>
				        <tr>
				        	<td style="text-align:center;" >孙海强</th>
				        	<td style="text-align:left;" >数据中心</th>
				        	<td id="jjje"><input id="heh" type="text" style="width: 100% ;text-align:right;" value="15" ></input></td>
				        	<td id="jjje"><input id="heh" type="text" style="width: 100% ;text-align:right;" value="94" ></input></td>
				        	<td id="jjje"><input id="heh" type="text" style="width: 100% ;text-align:right;" value="94" ></input></td>
				        	<td id="jjje"><input id="heh" type="text" style="width: 100% ;text-align:left;" value="指示内容" ></input></td>
				        	<td id="jjje"><input id="heh" type="text" style="width: 100% ;text-align:right;" value="93" ></input></td>
				        	<td id="jjje"><input id="heh" type="text" style="width: 100% ;text-align:right;" value="93"  readonly></input></td>
<!-- 				        	<th style="text-align:right;" >8,287.66</th> -->
				        </tr>
				        <tr>
				        	<th style="text-align:center;"colspan="7"  >合计</th>
				        	<th style="text-align:right;" >93</th>
				        </tr>
				        
				        <tr>
				        	<td style="text-align:center;" >项目负责人</td>
				        	<td style="text-align:center;" >项目名称</td>
				        	<td style="text-align:center;" >实际金额（万元）</td>
				        	<td style="text-align:center;" >一级指标</td>
				        	<td style="text-align:center;" >二级指标</td>
				        	<td style="text-align:center;" >指标内容</td>
				        	<td style="text-align:center;" >指标值</td>
				        	<td style="text-align:center;" >实际得分</td>
<!-- 				        	<th style="text-align:center;" >其他收入</th> -->
				        </tr>
				        <tr>
				        	<td style="text-align:center;" >李海燕</td>
				        	<td style="text-align:left;" >认证平台</td>
				        	<td id="jjje"><input id="heh" type="text" style="width: 100% ;text-align:right;" value="43" ></input></td>
				        	<td id="jjje"><input id="heh" type="text" style="width: 100% ;text-align:right;" value="98" ></input></td>
				        	<td id="jjje"><input id="heh" type="text" style="width: 100% ;text-align:right;" value="99" ></input></td>
				        	<td id="jjje"><input id="heh" type="text" style="width: 100% ;text-align:left;" value="指示内容" ></input></td>
				        	<td id="jjje"><input id="heh" type="text" style="width: 100% ;text-align:right;" value="98" ></input></td>
				        	<td id="jjje"><input id="heh" type="text" style="width: 100% ;text-align:right;" value="98"  readonly></input></td>
<!-- 				        	<th style="text-align:right;" >8,287.66</th> -->
				        </tr>
				        <tr>
				        	<th style="text-align:center;"colspan="7"  >合计</th>
				        	<th style="text-align:right;" >98</th>
				        </tr>
				        
				        
				    </tbody>
				</table>
			<!-- <h5>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：本表反映部门本年度取得的各项收入情况。</h5> -->
				
			</div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function() {	
	//列表右侧悬浮按钮
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
	//导出按钮
	$("#btn_exp").click(function(e) {
		alert("保存成功！");
	});
});
</script>
</body>
</html>