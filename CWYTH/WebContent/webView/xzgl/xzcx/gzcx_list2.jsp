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
					<label>人员编号</label>
			        <input type="text" id="" class="form-control input-radius" name="rybh" value="" table="t" placeholder="请输入人员编号">
				</div>
			<div class="form-group">
					<label>人员姓名</label>
			        <input type="text" id="" class="form-control input-radius" name="xm" value="" table="t" placeholder="请输入人员姓名">
				</div>
				<div class="form-group">
					<label>工资月份</label>
			        <input type="text" id="" class="form-control input-radius nydate2" name="gzyf" table="t" placeholder="请输入工资月份" value="${gzyf }">
				</div>
				<div class="form-group">
					<label>人员状态</label>
					<select class="form-control input-radius" id="sel_shzt" >
						<option value="0" >在职</option>
						<option value="1" selected>退休</option>
					</select>
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div class="btn-group pull-right" role="group">
					<button type='button' class="btn btn-default" id="btn_exp2">导出银行代发表</button>
				</div>
			</div>
		</form>
	</div>

<form id="mysave" name="mysave" method="post"  >
<div class="container-fluid abc" style="overflow: auto;">
		<div class='responsive-table' >
			<div class='scrollable-area'  > 
			<table id="mydatatables" class="table table-striped table-bordered" >
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
					        <th>序号</th>
<!-- 				            <th style="text-align:center;">审核状态</th> -->
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
<script>
 $(function () {
	 $("#sel_shzt").change(function(){
		 window.location.href="${ctx}/xzcx/goGzcxPage?zt="+$(this).val();
	 });
	 var columns = [
	 	      		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
	 	      	       	return '<input type="checkbox" class="keyId" name="guid" shzt="'+full.SHZT+'" value="' + full.GUID + '"> ';
// 	 	      	       	return '<input type="checkbox" class="keyId" name="gid" shzt="'+full.SHZT+'" value="' + full.GUID + '"> <input type="hidden" class="keyId" name="guid" value="' + full.GUID + '">';
	 	      	    },"width":10,'searchable': false},
	 	      		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	 	      	   		return data;
	 	      		},"width":41,"searchable": false,"class":"text-center"},
// 	 	      		{"data": "SHZT",defaultContent:"",'render': function(data, type, full, meta){
// 	 	       		return '<input type="type"  name="shzt" style="width:100%;border:none;" readonly value = "'+full.SHZT+'">';
// 	 	        	}},
	 	      		{"data": "RYBH",defaultContent:"" },
	 	      		{"data": "XM",defaultContent:"" },
	 	      		{"data": "BM",defaultContent:"" },
	 	      		{"data": "RYLB",defaultContent:"" },
	 	        	
	 	      		{"data": "JBGZ",defaultContent:"","class":"text-right"},
	 	      		{"data": "ZJLTF",defaultContent:"","class":"text-right"},
	 	      		{"data": "YZWBT",defaultContent:"","class":"text-right"},
	 	      		{"data": "GWBT",defaultContent:"","class":"text-right"},
	 	      		{"data": "XZFT",defaultContent:"","class":"text-right"},
	 	      		{"data": "HZBT",defaultContent:"","class":"text-right"},
	 	      		{"data": "TXTGBF",defaultContent:"","class":"text-right"},
	 	      		{"data": "SHBT",defaultContent:"","class":"text-right"},
	 	      		{"data": "XZBT",defaultContent:"","class":"text-right"},
	 	      		{"data": "WJBT",defaultContent:"","class":"text-right"},
	 	      		{"data": "TXHL",defaultContent:"","class":"text-right"},
	 	      		{"data": "JHBT",defaultContent:"","class":"text-right"},
	 	      		{"data": "QTBT",defaultContent:"","class":"text-right"},
	 	      		{"data": "LTBT",defaultContent:"","class":"text-right"},
	 	      		{"data": "YZBT",defaultContent:"","class":"text-right"},
	 	      		{"data": "JCJX",defaultContent:"","class":"text-right"},
	 	      		{"data": "JTF",defaultContent:"","class":"text-right"},
	 	      		{"data": "BT",defaultContent:"","class":"text-right"},
	 	      		{"data": "ZFBT",defaultContent:"","class":"text-right"},
	 	      		{"data": "BGZ",defaultContent:"","class":"text-right"},
	 	      		{"data": "WYBT",defaultContent:"","class":"text-right"},
	 	      		{"data": "JKF",defaultContent:"","class":"text-right"},
	 	        	
	 	      		{"data": "GJF",defaultContent:"","class":"text-right"},
	 	      		{"data": "DHF",defaultContent:"","class":"text-right"},
	 	      		{"data": "YFHJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "FZ",defaultContent:"","class":"text-right"},
	 	      		{"data": "SBJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "NQF",defaultContent:"","class":"text-right"},
	 	      		{"data": "NQF1",defaultContent:"","class":"text-right"},
	 	      		{"data": "WYF",defaultContent:"","class":"text-right"},
	 	      		{"data": "JK",defaultContent:"","class":"text-right"},
	 	        	
	 	       	    {"data": "YLJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "BGJJ",defaultContent:"","class":"text-right"},
	 	         	{"data": "BS",defaultContent:"","class":"text-right"},
	 	      		{"data": "SJDCK",defaultContent:"","class":"text-right"},
	 	        	{"data": "SYJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "KKHJ",defaultContent:"","class":"text-right"},
	 	        	{"data": "SFHJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "GZYF",defaultContent:"","class":"text-right"},
	 	        	{"data": "BH",defaultContent:"","class":"text-right"},
	 	      		{"data": "JSX",defaultContent:"","class":"text-right"},
	 	        	{"data": "ZFJJ",defaultContent:"","class":"text-right"},
	 	      		{"data": "NZJ",defaultContent:"","class":"text-right"},
	 	        	{"data": "KK",defaultContent:"","class":"text-right"}
	 	      	];
	 	        table = getDataTableByListHj("mydatatables","${ctx}/xzcx/getLzPageList?shzt=2",[2,'asc'],columns,0,1,setGroup);
	 	       $("#btn_search").click();
	 	        //导出Excel
				$("#btn_exp2").click(function() {
					var json = searchJson("searchBox");
					var id = [];
					//获取人员状态离职还是在职的字段
					var zt=$('#sel_shzt').val();
			   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
			   		if(checkbox.length > 0){
						checkbox.each(function(){
							id.push($(this).val());
						});
			   		}
			   		doExp(json,"${ctx}/xzcx/expGzcxYhdf?zt="+zt,"银行代发表","${ctx}",id.join(","));
				});
	 
	});
</script>
</body>
</html>