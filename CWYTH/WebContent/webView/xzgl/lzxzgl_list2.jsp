<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>离职薪资管理</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
.table>thead>tr>th {
    padding: 12px;
/*     padding-top: 7px; */
/*     padding-right: 7px; */
/*     padding-bottom: px; */
/*     padding-left: 7px; */
}
	.bottom {
    width: 99%;
    position: absolute;
    bottom: 27px;
    background-color: #f3f3f3;
   }
	
	.td_input{
		border:none;
		width:100%;
	}
	.text-green{
		color:green!important;
	}
	th{
		text-align:center;
	}
	.table>tbody>tr.selected:nth-of-type(odd) {
 	background-color: #f9f9f9!important;
	}
	.table>tbody>tr.selected:nth-of-type(even) {
 	background-color: #f3f3f3!important;
	}
/* 设置了浏览器高度不小于1201px时 abc 显示800px高度 */  
/*  @media screen and (min-width: 1201px) {   */
/*  .abc {height: 800px}   */
/*  }  */
   
 /* 设置了浏览器高度不大于900px时 abc 显示630px高度 */  
/*  @media screen and (max-heighe: 800px) {   */
/*  .abc {height: 630px;}   */
/*  }   */

</style>
</head>
<body>
<div class="" style="background-color: white;">
	<div class="search" id="searchBox" style="padding-top: 0px;margin-bottom: 0px;">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>人员编号或姓名</label>
 					<input type="text" id="text_rybh" class="form-control input-radius" style="width: 150px" placeholder="请输入人员编号或者姓名">
			        <button type="button" id="btn_rybh" class="btn btn-link ">选择</button>
				</div>
				<div class="form-group">
					<label>部门编号或名称</label>
			        <input type="text" id="text_bm" class="form-control input-radius" style="width:130px"  placeholder="请输入部门编号或名称">
			        <button type="button" id="btn_bm" class="btn btn-link ">选择</button>
				</div>
<!-- 				<div class="form-group"> -->
<!-- 					<label>姓名</label> -->
<!-- 			        <input type="text" id="ryxm" class="form-control input-radius" name="xm" style="width: 80px" value="" table="l" placeholder="请输入姓名"> -->
<!-- 				</div> -->
				<div class="form-group">
					<label>审核状态</label>
					<select class="form-control input-radius" id="shzt">
						<option value="">全部</option>
						<option value="0" selected="selected">未提交</option>
						<option value="1">待审核</option>
						<option value="2">审核通过</option>
						<option value="3">已退回</option>
					</select>
				</div>
				<div class="form-group">
				<label>月份</label>
<!-- 					<span class="input-group-addon">月份</span> -->
					<input type="text" id="gzyf" style="width: 70px" class="form-control input-radius nydate2" value="${yfMap.ffyf}"/>
<%-- 					<input type="text" id="gzyf" name="gzyf" class="form-control input-radius2 time" value="${yfMap.ffyf}"/> --%>
				</div>
				<button type="button" class="btn btn-primary" id="btn_search1"><i class="fa icon-chaxun"></i>查 询</button>
				<div class="btn-group pull-right" role="group">
			    <button type='button' class="btn btn-primary" id="btn_save">保存</button>
				<button type='button' class="btn btn-default" id="btn_tianjia">添加</button>
				<button type='button' class="btn btn-default" id="btn_plfz">批量赋值</button>
				<button type='button' class="btn btn-default" id="btn_shanchu">删除</button>
				<button type='button' class="btn btn-default" id="btn_fzsygz">复制上月工资</button>
			    <button type='button' class="btn btn-default" id="btn_tj">提交</button>
				<button type='button' class="btn btn-default" id="btn_imp">导入</button>
					<button type='button' class="btn btn-default" id="btn_exp">导出Excel</button>
					<button id="btn_lxz" class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">列选择</button>
				</div>
			</div>
		</form>
	</div>

<form id="mysave" name="mysave" method="post"  >
<div class="container-fluid abc" >
		<div class='responsive-table' >
			<div class='scrollable-area'  > 
			<table id="mydatatables" class="table table-striped table-bordered" >
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
					        <th>序号</th>
				            <th style="text-align:center;">审核状态</th>
				            <th style="text-align:center;">人员编号</th>
				            <th style="text-align:center;">姓名</th>
				            <th style="text-align:center;">部门</th>			            
				            <th style="text-align:center;">人员类别</th>			            
				            <th style="text-align:center;">基本工资</th>			            
				            <th style="text-align:center;">增加离退费</th>			            
				            <th style="text-align:center;">原职务补贴</th>			            
				            <th style="text-align:center;">岗位补贴</th>			            
				            <th style="text-align:center;">新住房贴</th>			            
				            <th style="text-align:center;">回族补贴</th>			            
				            <th style="text-align:center;">退休提高部分</th>			            
				            <th style="text-align:center;">生活补贴</th>			            
				            <th style="text-align:center;">新增补贴</th>			            
				            <th style="text-align:center;">物价补贴</th>			            
				            <th style="text-align:center;">特需护理</th>			            
				            <th style="text-align:center;">教护补贴</th>			            
				            <th style="text-align:center;">其他补贴</th>			            
				            <th style="text-align:center;">离退补贴</th>			            
				            <th style="text-align:center;">月增补贴</th>			            
				            <th style="text-align:center;">基础绩效</th>			            
				            <th style="text-align:center;">交通费</th>			            
				            <th style="text-align:center;">补贴</th>			            		            
				            <th style="text-align:center;">租房补贴</th>			            		            
				            <th style="text-align:center;">补工资</th>			            		            
				            <th style="text-align:center;">物业补贴</th>			            		            
				            <th style="text-align:center;">监考费</th>			            		            
				            <th style="text-align:center;">过节费</th>			            		            
				            <th style="text-align:center;">电话费</th>			            		            
				            <th style="text-align:center;">应发合计</th>			            		            
				            <th style="text-align:center;">房租</th>			            		            
				            <th style="text-align:center;">社保金</th>			            		            
				            <th style="text-align:center;">暖气费</th>			            		            
				            <th style="text-align:center;">暖气费1</th>			            		            
				            <th style="text-align:center;">物业费</th>			            		            
				            <th style="text-align:center;">借款</th>			            		            
				            <th style="text-align:center;">养老金</th>			            		            
				            <th style="text-align:center;">补公积金</th>			            		            
				            <th style="text-align:center;">补税</th>			            		            
				            <th style="text-align:center;">四季度菜款</th>			            		            
				            <th style="text-align:center;">失业金</th>			            		            
				            <th style="text-align:center;">扣款合计</th>			            		            
				            <th style="text-align:center;">实发合计</th>			            		            
				            <th style="text-align:center;">工资月份</th>			            		            
				            <th style="text-align:center;">编号</th>			            		            
				            <th style="text-align:center;">计税项</th>			            		            
				            <th style="text-align:center;">住房积金</th>			            		            
				            <th style="text-align:center;">年终奖</th>			            		            
				            <th style="text-align:center;">扣款</th>			            		            
				        </tr>
					</thead>
				    <tbody>
				   </tbody>
				</table>					
		</div>
			</div>
		</div>	
	</form>		
		</div>

<%@include file="/static/include/public-list-js.inc"%>
 <script type="text/javascript">
//     $(".time").datetimepicker({
//       format: "yyyy.mm",
//       autoclose: true,
//       todayBtn: true,
//       language:'zh-CN',
//       pickerPosition:"bottom-left"
//     });
  </script>

<script>
// height();
// $(window).resize(function(){
// 	height();
// });
// function height(){
// 	$(".abc").height($(window).height()-$("#searchBox").outerHeight(true)-50);
// }
//添加功能   弹出人员信息树，进行人员信息的添加，如果当前发放月份存在当前人员则不能添加；
$("#btn_tianjia").click(function(){
	var gzyf=$("#gzyf").val();
	select_commonWin("${ctx}/xzgl/rypage?controlId=txry&gzyf="+gzyf,"人员信息","920","630");
});
	//查询人员编号
	$(document).on("click","#btn_rybh",function(){			
		select_commonWin("${ctx}/window/rypagexm?controlId=text_rybh","人员信息","920","630");
	});
	//查询人员编号或者姓名
	$(document).on("click","[id=btn_bm]",function(){			
		select_commonWin("${ctx}/window/dwpage?controlId=text_bm","部门信息","920","630");
	});
	//查询人员编号
	$("#text_rybh").bindChange("${ctx}/suggest/getXx","R");
	//查询单位编号或者名称
	$("#text_bm").bindChange("${ctx}/suggest/getXx","D");
	//人员弹窗 查找上级方法
function dsqtr(rybh,xl,dwbh){
	var gzyf=$("#gzyf").val();
	$.ajax({
			url:"${ctx}/xzgl/txryCheck",//判断该人员是否为退休人员
			data:"rybh="+rybh,
			type:"post",
			async:"false",
			success:function(val){
				if(val=="true"){
					$.ajax({
			   			url:"${ctx}/xzgl/addryxx",
			   			data:"rybh="+rybh+"&txry=txry&gzyf="+gzyf+"&xl="+xl+"&dwbh="+dwbh,
			   			type:"post",
			   			async:"false",
			   			success:function(val){
			   				if(val==1){
			   				alert("添加成功！");
			   				table.ajax.reload();
			   				}else{
			   					alert("添加失败,请联系管理员!");
			   				}
			   			}
					});
				}else{
					alert("该人员不是退休人员,请重新选择!");
				}
			}
	});
}
 $(function () {
	 
	 var columns = [
	 	      		{"data": "XH",orderable:false, "render": function (data, type, full, meta){
	 	      	       	return '<input type="hidden" name="gid" value="' + full.GUID + '" ><input type="checkbox" class="keyId" name="guid" shzt="'+full.SHZT+'" value="' + full.GUID + '"> ';
	 	      	    },"width":10,'searchable': false,"class":"text-center"},
	 	      		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	 	      	   		return data;
	 	      		},"width":41,"searchable": false,"class":"text-center"},
	 	      		{"data": "SHZT","zhpxname":"审核状态",defaultContent:"",'render': function(data, type, full, meta){
	 	       		return '<input type="type"  name="shzt" style="width:100%;border:none;" readonly value = "'+full.SHZT+'">';
	 	        	}},
	 	      		{"data": "RYBH","zhpxname":"人员编号",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a =full.RYBH;
		 	        	if(full.RYBH==null || full.RYBH=='' || full.RYBH=='undefined'){
		 	      			a='';
		 	       		}
	 	      			return '<input type="type"  name="rybh" style="width:100%;border:none;" readonly value = "'+a+'">';
	 	        	}},
	 	      		{"data": "XM","zhpxname":"姓名",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a =full.XM;
		 	        	if(full.XM==null || full.XM=='' || full.XM=='undefined'){
		 	      			a='';
		 	       		}
	 	      			return '<input type="type"  name="xm" style="width:100%;border:none;" readonly value = "'+a+'">';
	 	        	}},
	 	      		{"data": "BM","zhpxname":"部门",defaultContent:"",'render': function(data, type, full, meta){
	 	      			var a =full.BM;
		 	        	if(full.BM==null || full.BM=='' || full.BM=='undefined'){
		 	      			a='';
		 	       		}
	 	      			return '<input type="type"  name="bm" style="width:100%;border:none;" readonly value = "'+a+'">';
	 	        	}},
	 	      		{"data": "RYLB","zhpxname":"人员类别",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.RYLB==null || full.RYLB=='' || full.RYLB=='undefined'){
	 	      				return '<input type="type"  name="rylb" style="width:100%;border:none;" readonly value = "">';
	 	       			}else{
	 	       				return '<input type="type"  name="rylb" style="width:100%;border:none;" readonly value = "'+full.RYLB+'">';
	 	       			}
	 	        	}},
	 	      		{"data": "JBGZ","zhpxname":"基本工资",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.JBGZ==null || full.JBGZ=='' || full.JBGZ=='undefined'){
	 	      				if(full.SHZT=='未提交'){
	 	      					return '<input type="type"  name="jbgz" style="width:100%;border:none;text-align:right;"  value = "">';
	 	      				}else{
	 	      					return '<input type="type"  name="jbgz" style="width:100%;border:none;text-align:right;" readonly  value = "">';
	 	      				}
	 	       			}else{
	 	       			if(full.SHZT=='未提交'){
	 	      					return '<input type="type"  name="jbgz" style="width:100%;border:none;text-align:right;" value = "'+full.JBGZ+'">';
	 	      				}else{
	 	      					return '<input type="type"  name="jbgz" style="width:100%;border:none;text-align:right;" readonly  value = "'+full.JBGZ+'">';
	 	      				}
	 	       			}
	 	        	}},
	 	      		{"data": "ZJLTF","zhpxname":"增加离退费",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.ZJLTF==null || full.ZJLTF=='' || full.ZJLTF=='undefined'){
	 	      				if(full.SHZT=='未提交'){
	 	      					return '<input type="type"  name="zjltf" style="width:100%;border:none;text-align:right;"  value = "">';
		 	      			}else{
	 	      					return '<input type="type"  name="zjltf" style="width:100%;border:none;text-align:right;" readonly value = "">';
		 	      			}
	 	       			}else{
	 	      				if(full.SHZT=='未提交'){
	 	       					return '<input type="type"  name="zjltf" style="width:100%;border:none;text-align:right;"  value = "'+full.ZJLTF+'">';
		 	      			}else{
	 	       					return '<input type="type"  name="zjltf" style="width:100%;border:none;text-align:right;" readonly value = "'+full.ZJLTF+'">';
		 	      			}
	 	       			}
	 	        	}},
	 	      		{"data": "YZWBT","zhpxname":"原职务补贴",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.YZWBT==null || full.YZWBT=='' || full.YZWBT=='undefined'){
	 	      				if(full.SHZT=='未提交'){
	 	      					return '<input type="type"  name="yzwbt" style="width:100%;border:none;text-align:right;"  value = "">';
			 	      		}else{
	 	      					return '<input type="type"  name="yzwbt" style="width:100%;border:none;text-align:right;" readonly value = "">';
			 	      		}
	 	       			}else{
	 	       			if(full.SHZT=='未提交'){
	 	      					return '<input type="type"  name="yzwbt" style="width:100%;border:none;text-align:right;"  value = "'+full.YZWBT+'">';
			 	      		}else{
	 	      					return '<input type="type"  name="yzwbt" style="width:100%;border:none;text-align:right;" readonly value = "'+full.YZWBT+'">';
			 	      		}
	 	       			}
	 	        	}},
	 	      		{"data": "GWBT","zhpxname":"岗位补贴",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.GWBT==null || full.GWBT=='' || full.GWBT=='undefined'){
	 	      				if(full.SHZT=='未提交'){
	 	      					return '<input type="type"  name="gwbt" style="width:100%;border:none;text-align:right;"  value = "">';
				 	      	}else{
	 	      					return '<input type="type"  name="gwbt" style="width:100%;border:none;text-align:right;" readonly value = "">';
				 	      	}
	 	       			}else{
	 	       			if(full.SHZT=='未提交'){
	 	      					return '<input type="type"  name="gwbt" style="width:100%;border:none;text-align:right;"  value = "'+full.GWBT+'">';
				 	      	}else{
	 	      					return '<input type="type"  name="gwbt" style="width:100%;border:none;text-align:right;" readonly value = "'+full.GWBT+'">';
				 	      	}
	 	       			}
	 	        	}},
	 	      		{"data": "XZFT","zhpxname":"新住房贴",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.JBGZ==null || full.JBGZ=='' || full.JBGZ=='undefined'){
	 	      				if(full.SHZT=='未提交'){
	 	      					return '<input type="type"  name="xzft" style="width:100%;border:none;text-align:right;"  value = "">';
					 	    }else{
	 	      					return '<input type="type"  name="xzft" style="width:100%;border:none;text-align:right;" readonly value = "">';
					 	    }
	 	       			}else{
	 	       			if(full.SHZT=='未提交'){
	 	      					return '<input type="type"  name="xzft" style="width:100%;border:none;text-align:right;"  value = "'+full.XZFT+'">';
					 	    }else{
	 	      					return '<input type="type"  name="xzft" style="width:100%;border:none;text-align:right;" readonly value = "'+full.XZFT+'">';
					 	    }
	 	       			}
	 	        	}},
	 	      		{"data": "HZBT","zhpxname":"回族补贴",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.HZBT==null || full.HZBT=='' || full.HZBT=='undefined'){
	 	      				if(full.SHZT=='未提交'){
	 	      					return '<input type="type"  name="hzbt" style="width:100%;border:none;text-align:right;"  value = "">';
						 	}else{
	 	      					return '<input type="type"  name="hzbt" style="width:100%;border:none;text-align:right;" readonly value = "">';
						 	}
	 	       			}else{
	 	       			if(full.SHZT=='未提交'){
	 	      					return '<input type="type"  name="hzbt" style="width:100%;border:none;text-align:right;"  value = "'+full.HZBT+'">';
						 	}else{
	 	      					return '<input type="type"  name="hzbt" style="width:100%;border:none;text-align:right;" readonly value =  "'+full.HZBT+'">';
						 	}
	 	       			}
	 	        	}},
	 	      		{"data": "TXTGBF","zhpxname":"退休提高部分",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.TXTGBF==null || full.TXTGBF=='' || full.TXTGBF=='undefined'){
	 	      				if(full.SHZT=='未提交'){
	 	      					return '<input type="type"  name="txtgbf" style="width:100%;border:none;text-align:right;"  value = "">';
							 	}else{
	 	      					return '<input type="type"  name="txtgbf" style="width:100%;border:none;text-align:right;" readonly  value = "">';
								}
	 	       			}else{
	 	       			if(full.SHZT=='未提交'){
	 	      						return '<input type="type"  name="txtgbf" style="width:100%;border:none;text-align:right;"  value = "'+full.TXTGBF+'">';
							 	}else{
	 	      						return '<input type="type"  name="txtgbf" style="width:100%;border:none;text-align:right;" readonly  value = "'+full.TXTGBF+'">';
							 	}
	 	       			}
	 	        	}},
	 	      		{"data": "SHBT","zhpxname":"生活补贴",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.SHBT==null || full.SHBT=='' || full.SHBT=='undefined'){
	 	      				if(full.SHZT=='未提交'){
	 	      						return '<input type="type"  name="shbt" style="width:100%;border:none;text-align:right;"  value = "">';
								}else{
	 	      						return '<input type="type"  name="shbt" style="width:100%;border:none;text-align:right;" readonly value = "">';
								}
	 	       			}else{
	 	       			if(full.SHZT=='未提交'){
	 	      					return '<input type="type"  name="shbt" style="width:100%;border:none;text-align:right;"  value = "'+full.SHBT+'">';
							}else{
	 	      					return '<input type="type"  name="shbt" style="width:100%;border:none;text-align:right;" readonly value = "'+full.SHBT+'">';
							}
	 	       			}
	 	        	}},
	 	      		{"data": "XZBT","zhpxname":"新增补贴",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.XZBT==null || full.XZBT=='' || full.XZBT=='undefined'){
	 	      				if(full.SHZT=='未提交'){
	 	      					return '<input type="type"  name="xzbt" style="width:100%;border:none;text-align:right;"  value = "">';
							}else{
	 	      					return '<input type="type"  name="xzbt" style="width:100%;border:none;text-align:right;" readonly value = "">';
							}
	 	       			}else{
	 	       			if(full.SHZT=='未提交'){
 	      					return '<input type="type"  name="xzbt" style="width:100%;border:none;text-align:right;"  value = "'+full.XZBT+'">';
						 }else{
 	      					return '<input type="type"  name="xzbt" style="width:100%;border:none;text-align:right;" readonly value = "'+full.XZBT+'">';
						 }
	 	       			}
	 	        	}},
	 	      		{"data": "WJBT","zhpxname":"物价补贴",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.WJBT==null || full.WJBT=='' || full.WJBT=='undefined'){
	 	      				if(full.SHZT=='未提交'){
	 	      					return '<input type="type"  name="wjbt" style="width:100%;border:none;text-align:right;"  value = "">';
							 }else{
		 	      				return '<input type="type"  name="wjbt" style="width:100%;border:none;text-align:right;" readonly value = "">';
							 }
	 	       			}else{
	 	       			if(full.SHZT=='未提交'){
 	      					return '<input type="type"  name="wjbt" style="width:100%;border:none;text-align:right;"  value = "'+full.WJBT+'">';
						 }else{
	 	      				return '<input type="type"  name="wjbt" style="width:100%;border:none;text-align:right;" readonly value = "'+full.WJBT+'">';
						 }
	 	       			}
	 	        	}},
	 	      		{"data": "TXHL","zhpxname":"特需护理",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.TXHL==null || full.TXHL=='' || full.TXHL=='undefined'){
	 	      				if(full.SHZT=='未提交'){
	 	      					return '<input type="type"  name="txhl" style="width:100%;border:none;text-align:right;"  value = "">';
							 }else{
	 	      					return '<input type="type"  name="txhl" style="width:100%;border:none;text-align:right;" readonly value = "">';
							 }
	 	       			}else{
	 	       			if(full.SHZT=='未提交'){
 	      					return '<input type="type"  name="txhl" style="width:100%;border:none;text-align:right;"  value = "'+full.TXHL+'">';
						 }else{
 	      					return '<input type="type"  name="txhl" style="width:100%;border:none;text-align:right;" readonly value = "'+full.TXHL+'">';
						 }
	 	       			}
	 	        	}},
	 	      		{"data": "JHBT","zhpxname":"教护补贴",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.JHBT==null || full.JHBT=='' || full.JHBT=='undefined'){
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="jhbt" style="width:100%;border:none;text-align:right;"  value = "">';
							 }else{
		 	      				return '<input type="type"  name="jhbt" style="width:100%;border:none;text-align:right;" readonly value = "">';
							 }
	 	       			}else{
	 	       			if(full.SHZT=='未提交'){
	 	      				return '<input type="type"  name="jhbt" style="width:100%;border:none;text-align:right;" value = "'+full.JHBT+'">';
						 }else{
	 	      				return '<input type="type"  name="jhbt" style="width:100%;border:none;text-align:right;" readonly value = "'+full.JHBT+'">';
						 }
	 	       			}
	 	        	}},
	 	      		{"data": "QTBT","zhpxname":"其他补贴",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.QTBT==null || full.QTBT=='' || full.QTBT=='undefined'){
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="qtbt" style="width:100%;border:none;text-align:right;"  value = "">';
							 }else{
		 	      				return '<input type="type"  name="qtbt" style="width:100%;border:none;text-align:right;" readonly value = "">';
							 }
	 	       			}else{
	 	       			if(full.SHZT=='未提交'){
	 	      				return '<input type="type"  name="qtbt" style="width:100%;border:none;text-align:right;"  value = "'+full.QTBT+'">';
						 }else{
	 	      				return '<input type="type"  name="qtbt" style="width:100%;border:none;text-align:right;" readonly value = "'+full.QTBT+'">';
						 }
	 	       			}
	 	        	}},
	 	      		{"data": "LTBT","zhpxname":"离退补贴",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.LTBT==null || full.LTBT=='' || full.LTBT=='undefined'){
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="ltbt" style="width:100%;border:none;text-align:right;"  value = "">';
							 }else{
		 	      				return '<input type="type"  name="ltbt" style="width:100%;border:none;text-align:right;" readonly value = "">';
							 }
	 	       			}else{
	 	       			if(full.SHZT=='未提交'){
	 	      				return '<input type="type"  name="ltbt" style="width:100%;border:none;text-align:right;"  value = "'+full.LTBT+'">';
						 }else{
	 	      				return '<input type="type"  name="ltbt" style="width:100%;border:none;text-align:right;" readonly value = "'+full.LTBT+'">';
						 }
	 	       			}
	 	        	}},
	 	      		{"data": "YZBT","zhpxname":"月增补贴",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.YZBT==null || full.YZBT=='' || full.YZBT=='undefined'){
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="yzbt" style="width:100%;border:none;text-align:right;"  value = "">';
							 }else{
		 	      				return '<input type="type"  name="yzbt" style="width:100%;border:none;text-align:right;" readonly value = "">';
							 }
	 	       			}else{
	 	       			if(full.SHZT=='未提交'){
	 	      				return '<input type="type"  name="yzbt" style="width:100%;border:none;text-align:right;"  value = "'+full.YZBT+'">';
						 }else{
	 	      				return '<input type="type"  name="yzbt" style="width:100%;border:none;text-align:right;" readonly value = "'+full.YZBT+'">';
						 }
	 	       			}
	 	        	}},
	 	      		{"data": "JCJX","zhpxname":"基础绩效",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.JCJX==null || full.JCJX=='' || full.JCJX=='undefined'){
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="jcjx" style="width:100%;border:none;text-align:right;"  value = "">';
							 }else{
		 	      				return '<input type="type"  name="jcjx" style="width:100%;border:none;text-align:right;" readonly value = "">';
							 }
	 	       			}else{
	 	       			if(full.SHZT=='未提交'){
	 	      				return '<input type="type"  name="jcjx" style="width:100%;border:none;text-align:right;"  value ="'+full.JCJX+'">';
						 }else{
	 	      				return '<input type="type"  name="jcjx" style="width:100%;border:none;text-align:right;" readonly value ="'+full.JCJX+'">';
						 }
	 	       			}
	 	        	}},
	 	      		{"data": "JTF","zhpxname":"交通费",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.JTF==null || full.JTF=='' || full.JTF=='undefined'){
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="jtf" style="width:100%;border:none;text-align:right;"  value = "">';
							 }else{
		 	      				return '<input type="type"  name="jtf" style="width:100%;border:none;text-align:right;" readonly value = "">';
							 }
	 	       			}else{
	 	       			if(full.SHZT=='未提交'){
	 	      				return '<input type="type"  name="jtf" style="width:100%;border:none;text-align:right;"  value = "'+full.JTF+'">';
						 }else{
	 	      				return '<input type="type"  name="jtf" style="width:100%;border:none;text-align:right;" readonly value = "'+full.JTF+'">';
						 }
	 	       			}
	 	        	}},
	 	      		{"data": "BT","zhpxname":"补贴",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.BT==null || full.BT=='' || full.BT=='undefined'){
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="bt" style="width:100%;border:none;text-align:right;"  value = "">';
							 }else{
		 	      				return '<input type="type"  name="bt" style="width:100%;border:none;text-align:right;" readonly value = "">';
							 }
	 	      				
	 	       			}else{
	 	       			if(full.SHZT=='未提交'){
	 	      				return '<input type="type"  name="bt" style="width:100%;border:none;text-align:right;"  value = "'+full.BT+'">';
						 }else{
	 	      				return '<input type="type"  name="bt" style="width:100%;border:none;text-align:right;" readonly value = "'+full.BT+'">';
						 }
	 	       			}
	 	      			
	 	        	}},
	 	      		{"data": "ZFBT","zhpxname":"租房补贴",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.ZFBT==null || full.ZFBT=='' || full.ZFBT=='undefined'){
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="zfbt" style="width:100%;border:none;text-align:right;"  value = "">';
							 }else{
		 	      				return '<input type="type"  name="zfbt" style="width:100%;border:none;text-align:right;" readonly value = "">';
							 }
	 	       			}else{
	 	       			if(full.SHZT=='未提交'){
	 	      				return '<input type="type"  name="zfbt" style="width:100%;border:none;text-align:right;"  value = "'+full.ZFBT+'">';
						 }else{
	 	      				return '<input type="type"  name="zfbt" style="width:100%;border:none;text-align:right;" readonly  value = "'+full.ZFBT+'">';
						 }
	 	       			}
	 	        	}},
	 	      		{"data": "BGZ","zhpxname":"补工资",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.BGZ==null || full.BGZ=='' || full.BGZ=='undefined'){
	 	      				if(full.SHZT=='未提交'){
	 	      					return '<input type="type"  name="bgz" style="width:100%;border:none;text-align:right;"  value = "">';
							 }else{
	 	      					return '<input type="type"  name="bgz" style="width:100%;border:none;text-align:right;" readonly value = "">';
							 }
	 	       			}else{
	 	      				if(full.SHZT=='未提交'){
	 	      					return '<input type="type"  name="bgz" style="width:100%;border:none;text-align:right;"  value = "'+full.BGZ+'">';
							 }else{
	 	      					return '<input type="type"  name="bgz" style="width:100%;border:none;text-align:right;" readonly value = "'+full.BGZ+'">';
							 }
	 	       			}
	 	        	}},
	 	      		{"data": "WYBT","zhpxname":"物业补贴",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.WYBT==null || full.WYBT=='' || full.WYBT=='undefined'){
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="wybt" style="width:100%;border:none;text-align:right;"  value = "">';
							 }else{
		 	      				return '<input type="type"  name="wybt" style="width:100%;border:none;text-align:right;" readonly value = "">';
							 }
	 	       			}else{
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="wybt" style="width:100%;border:none;text-align:right;"  value = "'+full.WYBT+'">';
							 }else{
		 	      				return '<input type="type"  name="wybt" style="width:100%;border:none;text-align:right;" readonly value = "'+full.WYBT+'">';
							 }
	 	       			}
	 	        	}},
	 	      		{"data": "JKF","zhpxname":"监考费",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.JKF==null || full.JKF=='' || full.JKF=='undefined'){
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="jkf" style="width:100%;border:none;text-align:right;"  value = "">';
							 }else{
		 	      				return '<input type="type"  name="jkf" style="width:100%;border:none;text-align:right;" readonly value = "">';
							 }
	 	       			}else{
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="jkf" style="width:100%;border:none;text-align:right;"  value = "'+full.JKF+'">';
							 }else{
		 	      				return '<input type="type"  name="jkf" style="width:100%;border:none;text-align:right;" readonly value = "'+full.JKF+'">';
							 }
	 	       			}
	 	        	}},
	 	        	
	 	      		{"data": "GJF","zhpxname":"过节费",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.GJF==null || full.GJF=='' || full.GJF=='undefined'){
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="gjf" style="width:100%;border:none;text-align:right;"  value = "">';
							 }else{
		 	      				return '<input type="type"  name="gjf" style="width:100%;border:none;text-align:right;" readonly value = "">';
							 }
	 	       			}else{
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="gjf" style="width:100%;border:none;text-align:right;"  value = "'+full.GJF+'">';
							 }else{
		 	      				return '<input type="type"  name="gjf" style="width:100%;border:none;text-align:right;" readonly value = "'+full.GJF+'">';
							 }
	 	       			}
	 	        	}},
	 	      		{"data": "DHF","zhpxname":"电话费",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.DHF==null || full.DHF=='' || full.DHF=='undefined'){
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="dhf" style="width:100%;border:none;text-align:right;"  value = "">';
							 }else{
		 	      				return '<input type="type"  name="dhf" style="width:100%;border:none;text-align:right;" readonly value = "">';
							 }
	 	       			}else{
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="dhf" style="width:100%;border:none;text-align:right;"  value = "'+full.DHF+'">';
							 }else{
		 	      				return '<input type="type"  name="dhf" style="width:100%;border:none;text-align:right;" readonly value = "'+full.DHF+'">';
							 }
	 	       			}
	 	        	}},
	 	      		{"data": "YFHJ","zhpxname":"应发合计",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.YFHJ==null || full.YFHJ=='' || full.YFHJ=='undefined'){
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="yfhj" style="width:100%;border:none;text-align:right;"  value = "">';
							 }else{
		 	      				return '<input type="type"  name="yfhj" style="width:100%;border:none;text-align:right;" readonly value = "">';
							 }
	 	       			}else{
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="yfhj" style="width:100%;border:none;text-align:right;"  value = "'+full.YFHJ+'">';
							 }else{
		 	      				return '<input type="type"  name="yfhj" style="width:100%;border:none;text-align:right;" readonly value = "'+full.YFHJ+'">';
							 }
	 	       			}
	 	        	}},
	 	      		{"data": "FZ","zhpxname":"房租",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.FZ==null || full.FZ=='' || full.FZ=='undefined'){
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="fz" style="width:100%;border:none;text-align:right;"  value = "">';
							 }else{
		 	      				return '<input type="type"  name="fz" style="width:100%;border:none;text-align:right;" readonly value = "">';
							 }
	 	       			}else{
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="fz" style="width:100%;border:none;text-align:right;"  value = "'+full.FZ+'">';
							 }else{
		 	      				return '<input type="type"  name="fz" style="width:100%;border:none;text-align:right;" readonly  value = "'+full.FZ+'">';
							 }
	 	       			}
	 	        	}},
	 	      		{"data": "SBJ","zhpxname":"社保金",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.SBJ==null || full.SBJ=='' || full.SBJ=='undefined'){
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="sbj" style="width:100%;border:none;text-align:right;"  value = "">';
							 }else{
		 	      				return '<input type="type"  name="sbj" style="width:100%;border:none;text-align:right;" readonly value = "">';
							 }
	 	       			}else{
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="sbj" style="width:100%;border:none;text-align:right;"  value = "'+full.SBJ+'">';
							 }else{
		 	      				return '<input type="type"  name="sbj" style="width:100%;border:none;text-align:right;" readonly value = "'+full.SBJ+'">';
							 }
	 	       			}
	 	        	}},
	 	      		{"data": "NQF","zhpxname":"暖气费",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.NQF==null || full.NQF=='' || full.NQF=='undefined'){
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="nqf" style="width:100%;border:none;text-align:right;"  value = "">';
							 }else{
		 	      				return '<input type="type"  name="nqf" style="width:100%;border:none;text-align:right;" readonly value = "">';
							 }
	 	       			}else{
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="nqf" style="width:100%;border:none;text-align:right;"  value = "'+full.NQF+'">';
							 }else{
		 	      				return '<input type="type"  name="nqf" style="width:100%;border:none;text-align:right;" readonly value ="'+full.NQF+'">';
							 }
	 	       			}
	 	        	}},
	 	      		{"data": "NQF1","zhpxname":"暖气费1",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.NQF1==null || full.NQF1=='' || full.NQF1=='undefined'){
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="nqf1" style="width:100%;border:none;text-align:right;"  value = "">';
							 }else{
		 	      				return '<input type="type"  name="nqf1" style="width:100%;border:none;text-align:right;" readonly value = "">';
							 }
	 	       			}else{
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="nqf1" style="width:100%;border:none;text-align:right;"  value = "'+full.NQF1+'">';
							 }else{
		 	      				return '<input type="type"  name="nqf1" style="width:100%;border:none;text-align:right;" readonly value = "'+full.NQF1+'">';
							 }
	 	       			}
	 	        	}},
	 	      		{"data": "WYF","zhpxname":"物业费",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.WYF==null || full.WYF=='' || full.WYF=='undefined'){
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="wyf" style="width:100%;border:none;text-align:right;"  value = "">';
							 }else{
		 	      				return '<input type="type"  name="wyf" style="width:100%;border:none;text-align:right;" readonly value = "">';
							 }
	 	       			}else{
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="wyf" style="width:100%;border:none;text-align:right;"  value = "'+full.WYF+'">';
							 }else{
		 	      				return '<input type="type"  name="wyf" style="width:100%;border:none;text-align:right;" readonly value = "'+full.WYF+'">';
							 }
	 	       			}
	 	        	}},
	 	      		{"data": "JK","zhpxname":"借款",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.JK==null || full.JK=='' || full.JK=='undefined'){
	 	      				if(full.SHZT=='未提交'){
	 	      					return '<input type="type"  name="jk" style="width:100%;border:none;text-align:right;"  value = "">';
							 }else{
	 	      					return '<input type="type"  name="jk" style="width:100%;border:none;text-align:right;" readonly value = "">';
							 }
	 	       			}else{
	 	      				if(full.SHZT=='未提交'){
	 	      					return '<input type="type"  name="jk" style="width:100%;border:none;text-align:right;"  value ="'+full.JK+'">';
							 }else{
	 	      					return '<input type="type"  name="jk" style="width:100%;border:none;text-align:right;" readonly value = "'+full.JK+'">';
							 }
	 	       			}
	 	        	}},
	 	        	
	 	       	    {"data": "YLJ","zhpxname":"养老金",defaultContent:"",'render': function(data, type, full, meta){
		 	       	    if(full.YLJ==null || full.YLJ=='' || full.YLJ=='undefined'){
		 	       	    if(full.SHZT=='未提交'){
		 	       	    	return '<input type="type"  name="ylj" style="width:100%;border:none;text-align:right;"  value = "">';
						 }else{
		 	       	    	return '<input type="type"  name="ylj" style="width:100%;border:none;text-align:right;" readonly value = "">';
						 }
	 	       			}else{
	 	       			 if(full.SHZT=='未提交'){
			 	       	    	return '<input type="type"  name="ylj" style="width:100%;border:none;text-align:right;"  value = "'+full.YLJ+'">';
							 }else{
			 	       	    	return '<input type="type"  name="ylj" style="width:100%;border:none;text-align:right;" readonly value = "'+full.YLJ+'">';
							 }
	 	       			}
	 	        	}},
	 	      		{"data": "BGJJ","zhpxname":"补公积金",defaultContent:"",'render': function(data, type, full, meta){
		 	      		 if(full.BGJJ==null || full.BGJJ=='' || full.BGJJ=='undefined'){
		 	      			 if(full.SHZT=='未提交'){
			 	      			 return '<input type="type"  name="bgjj" style="width:100%;border:none;text-align:right;"  value = "">';
							 }else{
			 	      			 return '<input type="type"  name="bgjj" style="width:100%;border:none;text-align:right;" readonly value = "">';
							 }
	 	       			}else{
		 	      			if(full.SHZT=='未提交'){
			 	      			return '<input type="type"  name="bgjj" style="width:100%;border:none;text-align:right;"  value = "'+full.BGJJ+'">';
							}else{
			 	      			return '<input type="type"  name="bgjj" style="width:100%;border:none;text-align:right;" readonly value = "'+full.BGJJ+'">';
							}
	 	       			}
	 	        	}},
	 	         	{"data": "BS","zhpxname":"补税",defaultContent:"",'render': function(data, type, full, meta){
	 	         		 if(full.BS==null || full.BS=='' || full.BS=='undefined'){
	 	         			if(full.SHZT=='未提交'){
		 	         			 return '<input type="type"  name="bs" style="width:100%;border:none;text-align:right;"  value = "">';
							}else{
		 	         			 return '<input type="type"  name="bs" style="width:100%;border:none;text-align:right;" readonly value = "">';
							}
	 	       			}else{
	 	       			if(full.SHZT=='未提交'){
	 	         			 return '<input type="type"  name="bs" style="width:100%;border:none;text-align:right;"  value = "'+full.BS+'">';
						}else{
	 	         			 return '<input type="type"  name="bs" style="width:100%;border:none;text-align:right;" readonly value = "'+full.BS+'">';
						}
	 	       			}
	 	        	}},
	 	      		{"data": "SJDCK","zhpxname":"四季度菜款",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.SJDCK==null || full.SJDCK=='' || full.SJDCK=='undefined'){
	 	      				if(full.SHZT=='未提交'){
	 	      				return '<input type="type"  name="sjdck" style="width:100%;border:none;text-align:right;"  value = "">';
							}else{
	 	      				return '<input type="type"  name="sjdck" style="width:100%;border:none;text-align:right;" readonly value = "">';
							}
	 	       			}else{
	 	       			if(full.SHZT=='未提交'){
	 	      				return '<input type="type"  name="sjdck" style="width:100%;border:none;text-align:right;"  value = "'+full.SJDCK+'">';
						}else{
	 	      				return '<input type="type"  name="sjdck" style="width:100%;border:none;text-align:right;" readonly value = "'+full.SJDCK+'">';
						}
	 	       			}
	 	        	}},
	 	        	{"data": "SYJ","zhpxname":"失业金",defaultContent:"",'render': function(data, type, full, meta){
	 	        		 if(full.SYJ==null || full.SYJ=='' || full.SYJ=='undefined'){
	 	        			if(full.SHZT=='未提交'){
	 	        			   return '<input type="type"  name="syj" style="width:100%;border:none;text-align:right;"  value = "">';
							}else{
	 	        			   return '<input type="type"  name="syj" style="width:100%;border:none;text-align:right;" readonly value = "">';
							}
	 	       			}else{
	 	        			if(full.SHZT=='未提交'){
	 	        			   return '<input type="type"  name="syj" style="width:100%;border:none;text-align:right;"  value = "'+full.SYJ+'">';
							}else{
	 	        			   return '<input type="type"  name="syj" style="width:100%;border:none;text-align:right;" readonly value = "'+full.SYJ+'">';
							}
	 	       			}
	 	        	}},
	 	      		{"data": "KKHJ","zhpxname":"扣款合计",defaultContent:"",'render': function(data, type, full, meta){
	 	      		 	if(full.KKHJ==null || full.KKHJ=='' || full.KKHJ=='undefined'){
	 	      		 	if(full.SHZT=='未提交'){
		 	      		 		return '<input type="type"  name="kkhj" style="width:100%;border:none;text-align:right;"  value = "">';
							}else{
		 	      		 		return '<input type="type"  name="kkhj" style="width:100%;border:none;text-align:right;" readonly value = "">';
							}
	 	       			}else{
	 	      		 	if(full.SHZT=='未提交'){
		 	      		 		return '<input type="type"  name="kkhj" style="width:100%;border:none;text-align:right;"  value = "'+full.KKHJ+'">';
							}else{
		 	      		 		return '<input type="type"  name="kkhj" style="width:100%;border:none;text-align:right;" readonly value = "'+full.KKHJ+'">';
							}
	 	       			}
	 	        	}},
	 	        	{"data": "SFHJ","zhpxname":"实发合计",defaultContent:"",'render': function(data, type, full, meta){
	 	        		if(full.SFHJ==null || full.SFHJ=='' || full.SFHJ=='undefined'){
	 	        			if(full.SHZT=='未提交'){
		 	        			return '<input type="type"  name="sfhj" style="width:100%;border:none;text-align:right;"  value = "">';
							}else{
		 	        			return '<input type="type"  name="sfhj" style="width:100%;border:none;text-align:right;" readonly value = "">';
							}
	 	       			}else{
	 	        			if(full.SHZT=='未提交'){
		 	        			return '<input type="type"  name="sfhj" style="width:100%;border:none;text-align:right;"  value = "'+full.SFHJ+'">';
							}else{
		 	        			return '<input type="type"  name="sfhj" style="width:100%;border:none;text-align:right;" readonly value = "'+full.SFHJ+'">';
							}
	 	       			}
	 	        	}},
	 	      		{"data": "GZYF","zhpxname":"工资月份",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.GZYF==null || full.GZYF=='' || full.GZYF=='undefined'){
	 	      				return '<input type="type"  name="gzyf" style="width:100%;border:none;"  value = "">';
	 	       			}else{
	 	       				return '<input type="type"  name="gzyf" style="width:100%;border:none;"  value = "'+full.GZYF+'">';
	 	       			}
	 	        	}},
	 	        	{"data": "BH","zhpxname":"编号",defaultContent:"",'render': function(data, type, full, meta){
	 	        		if(full.BH==null || full.BH=='' || full.BH=='undefined'){
	 	        			return '<input type="type"  name="bh" style="width:100%;border:none;"  value = "">';
	 	       			}else{
	 	       				return '<input type="type"  name="bh" style="width:100%;border:none;"  value = "'+full.BH+'">';
	 	       			}
	 	        	}},
	 	      		{"data": "JSX","zhpxname":"计税项",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.JSX==null || full.JSX=='' || full.JSX=='undefined'){
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="jsx" style="width:100%;border:none;text-align:right;"  value = "">';
							}else{
		 	      				return '<input type="type"  name="jsx" style="width:100%;border:none;text-align:right;" readonly value = "">';
							}
	 	       			}else{
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="jsx" style="width:100%;border:none;text-align:right;"  value = "'+full.JSX+'">';
							}else{
		 	      				return '<input type="type"  name="jsx" style="width:100%;border:none;text-align:right;" readonly value = "'+full.JSX+'">';
							}
	 	       			}
	 	        	}},
	 	        	{"data": "ZFJJ","zhpxname":"住房积金",defaultContent:"",'render': function(data, type, full, meta){
	 	        		if(full.ZFJJ==null || full.ZFJJ=='' || full.ZFJJ=='undefined'){
	 	        			if(full.SHZT=='未提交'){
		 	        			return '<input type="type"  name="zfjj" style="width:100%;border:none;text-align:right;"  value = "">';
							}else{
		 	        			return '<input type="type"  name="zfjj" style="width:100%;border:none;text-align:right;" readonly value = "">';
							}
	 	       			}else{
	 	        			if(full.SHZT=='未提交'){
		 	        			return '<input type="type"  name="zfjj" style="width:100%;border:none;text-align:right;"  value ="'+full.ZFJJ+'">';
							}else{
		 	        			return '<input type="type"  name="zfjj" style="width:100%;border:none;text-align:right;" readonly value ="'+full.ZFJJ+'">';
							}
	 	       			}
	 	        	}},
	 	      		{"data": "NZJ","zhpxname":"年终奖",defaultContent:"",'render': function(data, type, full, meta){
	 	      			if(full.NZJ==null || full.NZJ=='' || full.NZJ=='undefined'){
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="nzj" style="width:100%;border:none;text-align:right;"  value = "">';
							}else{
		 	      				return '<input type="type"  name="nzj" style="width:100%;border:none;text-align:right;" readonly value = "">';
							}
	 	       			}else{
	 	      				if(full.SHZT=='未提交'){
		 	      				return '<input type="type"  name="nzj" style="width:100%;border:none;text-align:right;"  value ="'+full.NZJ+'">';
							}else{
		 	      				return '<input type="type"  name="nzj" style="width:100%;border:none;text-align:right;" readonly value ="'+full.NZJ+'">';
							}
	 	       			}
	 	        	}},
	 	        	{"data": "KK","zhpxname":"扣款",efaultContent:"",'render': function(data, type, full, meta){
	 	        		if(full.KK==null || full.KK=='' || full.KK=='undefined'){
	 	        			if(full.SHZT=='未提交'){
	 	        				return '<input type="type"  name="kk" style="width:100%;border:none;text-align:right;"  value = "">';
							}else{
		 	        			return '<input type="type"  name="kk" style="width:100%;border:none;text-align:right;" readonly value = "">';
							}
	 	       			}else{
	 	        			if(full.SHZT=='未提交'){
	 	        				return '<input type="type"  name="kk" style="width:100%;border:none;text-align:right;"  value = "'+full.KK+'">';
							}else{
		 	        			return '<input type="type"  name="kk" style="width:100%;border:none;text-align:right;" readonly value = "'+full.KK+'">';
							}
	 	       			}
	 	        	}}
	 	      	];
	 	        table = getDataTableByListHj("mydatatables","${ctx}/xzgl/getLzPageList?bmbh="+$("#text_bm").val()+"&rybh="+$("#text_rybh").val()+"&shzt="+$("#shzt").val()+"&gzyf="+$("#gzyf").val(),[0,'asc'],columns,0,1,setGroup);
	 	       lxzExp(columns,"#mydatatables","#searchBox",table,"${pageContext.request.contextPath}/xzgl/doExp","管理员建帐","${pageContext.request.contextPath}");
	$("#btn_search").click();
	//把所有undefined改为空值
		$('input[value=undefined]').val('');
	window.onresize=function(){
		$('input[value=undefined]').val('');
	}
	 //导入数据
     $("#btn_imp").click(function(){
    	 	var texts = [];
	  		var name = $("table").find("th:gt(1)");
	  		$.each(name,function(){
	  			var name = $(this).text();
	  			texts.push(name);
	  		});
          select_commonWin("${pageContext.request.contextPath}/webView/xzgl/lzxzgl_imp.jsp?texts="+texts.join(","),"导入离职工资信息","550","450");
           return false;
     });
     //复制上月工资
	 $("#btn_fzsygz").click(function(){
		 var gzyf = $("#gzyf").val();
		if(gzyf=="" || gzyf == null ){
			alert("请选择月份！")
		}else{
			confirm("确定复制？","",function(){
			$.ajax({
				url:"${ctx}/xzgl/checkGzyf",
				data:"gzyf="+gzyf+"&type=txry",
				dataType:"json",
				type:"post",
				success:function(date){
					alert(date.success);
					if(date.success=='true'){
						$.ajax({
							url:"${ctx}/xzgl/doFzsygzLz",
							data:"gzyf="+gzyf,
							dataType:"json",
							type:"post",
							success:function(date){
								location.reload();
								alert("复制成功！");
							}
						});
					}else{
						alert("当月数据已存在!");
					}
				}
			});
			});
		}
	 });

 	//点击保存按钮
		$("#btn_save").click(function(){
			var end = $("table").last("tr").find("input:last").attr("name");
			if(end==""||end=="undefined"||end==null){
				alert("列选择错误无法保存，请重新选择！");
				return;
			}
			var json = $("#mysave").serializeObject("gid",end);  //json对象	
// 			var json = $("#mysave").serializeObject("gid","kk");  //json对象				
			var jsonStr = JSON.stringify(json);
			console.log(jsonStr);
			confirm("确定保存？","",function(){
			$.ajax({
				url:"${ctx}/xzgl/doSaveLz",
				data:"jsonStr="+jsonStr,
				dataType:"json",
				type:"post",
				success:function(msg){
					if($.trim(msg)=="true"){
// 						window.location.href="${ctx}/webView/xzgl/lzxzgl_show.jsp";
						table.ajax.reload();
						alert("保存成功!");
					}else{
						alert("保存失败!");
					}
				}
			});
				
			});
		});
		$(document).on("click","[id=btn_plfz]",function(){
			var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
			var msg = '';
			if(checkbox.length>0){
				var dwbh = [];
				checkbox.each(function(){
					dwbh.push($(this).val());
					if("1"==$(this).val()){
// 						alert("合计不允许赋值！")
						msg = '1';
						return;
					}else if("未提交"!=$(this).attr("shzt")){
						msg = '2';
						return;
					}
				});
				if(msg=="1"){
					alert("合计不允许赋值！");
					return false;
				}else if(msg=="2"){
					alert("只能选择未提交的信息进行赋值！");
					return false;
				}else{
					var checkbox = $("#mydatatables").find("[name='guid']")
					confirm("确认批量赋值"+dwbh.length+"条信息？",{title:"提示"},function(index){
						select_commonWin("${ctx}/xzgl/goPlfuzhitPage?dwbh="+dwbh.join(","),"批量赋值","400","450");
						close(index);
					}); 
					
				}
			}else{
				var rybh = $("#text_rybh").val();
				var shzt = $("#shzt").val();
				var gzyf = $("#gzyf").val();
// 				if("0"==shzt){
				confirm("点击确认将会确认批量赋值所有未提交信息！",{title:"注意！"},function(index){
					select_commonWin("${ctx}/xzgl/goPlfuzhitPage?dwbh=nonono&rybh="+rybh+"&shzt="+shzt+"&gzyf="+gzyf,"批量赋值","400","450");
					close(index);
				});
// 				}else{
// 					alert("只能选择未提交的信息进行赋值！");
// 					return false;
// 				}
			}
//		 	select_commonWin("${ctx}/window/dwpage?controlId=text_bm","部门信息","920","630");
		});
		//删除
		$("#btn_shanchu").click(function(){
			var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
			if(checkbox.length>0){
			   		var guid = [];
			   		flag=true;
			   		checkbox.each(function(){
			   			guid.push($(this).val());
				   		 if($(this).attr("shzt") != "未提交"){
				   			 flag=false;
				   		 }
			   		});
			   		if(flag){
			   	       confirm("确定删除？","",function(){
			   			$.ajax({
			   	   			url:"${ctx}/xzgl/doDelete_txry",
			   	   			data:"guid="+guid.join(","),
			   	   			type:"post",
			   	   			async:"false",
			   	   			success:function(val){
			   	   				alert("操作成功！");
			   	   				table.ajax.reload();
			   	   			}
			   	   		});
			   		});
			   		}else{
			   			alert("请选择未提交数据删除！");
			   		}
				}else{
					alert("请选择一条信息进行删除！");
				}
		});
		
		//导出Excel
		$("#btn_exp").click(function() {
		   var id = [];
	  		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
	  		if(checkbox.length > 0){
				checkbox.each(function(){
					id.push($(this).val());
				});
				id = id.join("','");
	  		}else{
	  			id = "";
	  		}
	  		var texts = [];
	  		var name = $("table").find("th:gt(1)");
	  		$.each(name,function(){
	  			var name = $(this).text();
	  			texts.push(name);
	  		});
	  		var rybh=$("#text_rybh").val();
	  		var ryxm=$("#ryxm").val();
	  		var shzt=$("#shzt").val();
	  		var gzyf=$("#gzyf").val();
	  		doExp("","${ctx}/xzgl/LzryexpExcel?rybh="+rybh+"&ryxm="+ryxm+"&shzt="+shzt+"&gzyf="+gzyf+"&texts="+texts.join("','"),"退休人员薪资","${pageContext.request.contextPath}",id);
		});
		//查询
		 $("#cx").change(function(){
			var rybh = $(this).val();
		 	location.href="${ctx}/xzgl/lzxz_list?rybh="+rybh;
		 });
		
		    //提交
		   	$("#btn_tj").click(function(){
		   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		   		var flag = true;
		   		if(checkbox.length>0){
		   	   		var guid = [];
		   	   		checkbox.each(function(){
		   	   			guid.push($(this).val());
		   	   		 if($(this).attr("shzt") != "未提交"){
		   	   			 flag=false;
		   	   		 }
		   	   		});
		   	   		if(flag){
		   	   	       confirm("确定提交？","",function(){
			   			$.ajax({
			   	   			url:"${ctx}/xzgl/doLzTj",
			   	   			data:"guid="+guid.join(","),
			   	   			type:"post",
			   	   			async:"false",
			   	   			success:function(val){
			   	   				alert("操作成功！");
			   	   				table.ajax.reload();
			   	   			}
			   	   		});
			   		});
		   	   		}else{
		   	   			alert("信息不可重复提交！");
		   	   		}
		   		}else{
		   			var ck = $("#mydatatables").find("[name='guid']");
		   			var gzyf = $("#gzyf").val();
		   			var guid = [];
		   	   		ck.each(function(){
		   	   			guid.push($(this).val());
		   	   		 if($(this).attr("shzt") != "未提交"){
		   	   			 flag=false;
		   	   		 }
		   	   		})
		   	   		
		   			if(flag){
// 		   			guid="";
		   		  	confirm("确定全部提交？","",function(){
			   			$.ajax({
			   	   			url:"${ctx}/xzgl/doLzTj",
			   	   			data:"gzyf="+gzyf,
			   	   			type:"post",
			   	   			async:"false",
			   	   			success:function(val){
			   	   				alert("操作成功！");
			   	   				table.ajax.reload();
			   	   			}
			   	   		});
			   		});
		   			}else{
		   				alert("请选择未提交的信息提交");
		   			}
		   		}
		   	});

			//列表右侧悬浮按钮
			$(window).resize(function(){
		    	$("div.dataTables_wrapper").width($("#searchBox").width());
		    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
		    	$(".dataTables_scrollBody").height(heights);
		    	table.draw();
			});

	});
//查询按钮
$(document).on("click","#btn_search1",function(){
	var rybh = $("#text_rybh").val();
	var shzt = $("#shzt").val();
	var gzyf = $("#gzyf").val();
	table.ajax.url("${ctx}/xzgl/getLzPageList?rybh="+rybh+"&shzt="+shzt+"&gzyf="+gzyf+"&bmbh="+$("#text_bm").val());
	table.ajax.reload();
});
</script>
</body>
</html>