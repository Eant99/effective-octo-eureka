<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>银行帐号管理</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style>
.btn_td{
		width:10px!important;
		height:6mm!important;
		text-align: center;
	}
.addBtn{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.addBtn:after{
/* 		2722 */
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 17px;
	    position: relative;
	    cursor: pointer;
	}
	.deleteBtn{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.deleteBtn:after{
/* 		2014 */
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 1em;
	    position: relative;
	    cursor: pointer;
	    top:3px;
	}
	.border{
		border:1px solid #a94442;
		background:url("${ctx}/webView/wsbx/rcbx/no_null.bmp") center right no-repeat;;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
		<input type="hidden" name="operateType" id="operateType" value="${operateType}">
		<input type="hidden" name="guid" value="${dwb.guid}"> 
		<input type="hidden" name="czr"	value="${loginId}">
		<center><h4><strong>基本信息表</strong></h4></center>
			<div class="search-simple">	
				<div class="form-group"> 
					<label>填报部门</label>					
 					<input type="text" readonly class="form-control input-radius" name="sbnd"  id="txt_sbnd" placeholder="请输入填报部门">
				 </div> 
			</div>
		</form>
	</div>
	<div class="container-fluid" style="margin-bottom: 150px">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			<form id="myform1">
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse: collapse">
		        	<thead id="thead">
				        <tr>
				            <th style="text-align: center;">项目</th>
				            <th style="text-align: center;">基本信息值</th>
				            <th style="text-align: center;">项目</th>
				            <th style="text-align: center;">基本信息值</th>
				            <th style="text-align: center;">项目</th>
				            <th style="text-align: center;">基本信息值</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    	 <tr>
				            <th style="color: blue;font-size:15px;vertical-align: middle;">一、基本情况</th>
				            <th></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp长休人员</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp10、编制内领导职数</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				        </tr>
				         <tr>
				            <th style="vertical-align: middle;">&nbsp&nbsp单位规格</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp提前离岗、离职留薪人员</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp11、教师学生数</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				        </tr>
				         <tr>
				            <th style="vertical-align: middle;">&nbsp&nbsp单位性质</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp3、离休人员</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp教师人数</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				        </tr>
				         <tr>
				            <th style="vertical-align: middle;">&nbsp&nbsp预算编码</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp4、退休人员</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp高中学生人数</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				        </tr>
				         <tr>
				            <th style="vertical-align: middle;">&nbsp&nbsp预算级次（一级/二级）</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp5、公益性岗位人员</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp初中学生人数</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				        </tr>
				         <tr>
				            <th style="vertical-align: middle;">&nbsp&nbsp经费供给方式（全额/差额/自收自支）</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp6、遗属人数</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp小学学生人数</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				        </tr>
				         <tr>
				            <th style="vertical-align: middle;">&nbsp&nbsp综合定额标准（元）</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp7、其他人员</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp幼儿园学生人数</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				        </tr>
				         <tr>
				            <th style="vertical-align: middle;">&nbsp&nbsp是否代发工资</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp8、在职人员职务级别</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp高等职业学校学生人数</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				        </tr>
				         <tr>
				            <th></th>
				            <th></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp正厅级</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp中等职业学校学生人数</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				        </tr>
				         <tr>
				            <th></th>
				            <th></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp副厅级</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp特殊学校学生人数</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				        </tr>
				        <tr>
				            <th style="color: blue;font-size:15px;vertical-align: middle;">二、人员基本情况</th>
				            <th></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp正处级</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th></th>
				            <th></th>
				        </tr>
				         <tr>
				            <th style="vertical-align: middle;">&nbsp&nbsp1、编制人员</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp副处级</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th></th>
				            <th></th>
				        </tr>
				         <tr>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp行政</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp正科级</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="color: blue;font-size:15px;vertical-align: middle;">三、公用设施、设备情况</th>
				            <th></th>
				        </tr>
				         <tr>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp工勤</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp副科级</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp1、公办建筑面积（平方米）</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				        </tr>
				         <tr>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp事业</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp一般干部</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp2、单位车辆</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				        </tr>
				         <tr>
				            <th style="vertical-align: middle;">&nbsp&nbsp2、实有人数</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp9、在职人员职称级别</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp一般公务用车</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				        </tr>
				         <tr>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp在岗人数</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp正高级</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp执法执勤用车</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				        </tr>
				         <tr>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp行政</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp副高级</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp其他专业用车</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				        </tr>
				         <tr>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp工勤</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp中级</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th></th>
				            <th></th>
				        </tr>
				         <tr>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp事业</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th style="vertical-align: middle;">&nbsp&nbsp&nbsp&nbsp初级</th>
				            <th><input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value=""/></th>
				            <th></th>
				            <th></th>
				        </tr>
				    </tbody>
				</table>
			</form>
			</div>
		</div>
	</div>	
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function() {
	
	//列表数据
    var columns = [
    	
//     	{"data": "GUID", orderable: false, 'render': function(data, type, full, meta){
//     		return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
//     		},"width":10,'searchable': false},
    	
//     	{"data":"_XH",orderable:false,'render': function(data,type,full,meta){
//     		return  '<input type="hidden"  class="uid"  id="' + data + '">'+data+'';
//     		},"width":41,"searchable":false,"class":"text-center"},
    	
//     	{"data": "YHDM",defaultContent:"",'render': function(data, type, full, meta){
//     		return '<input type="type" name="yhdm" style="text-align: center;width:100%;border:none;" value="' + data + '" guid = "'+full.YHDM+'">';
//     		},"width":400},
    	
//     	{"data": "YHMC",defaultContent:"",'render': function(data, type, full, meta){
//     		return '<input type="type" name="YHMC" style="text-align: center;width:100%;border:none;" value="' + data + '" guid = "'+full.YHMC+'">';
//     		},"width":400},
    	
//     	{"data": "KHYHH",defaultContent:"",'render': function(data, type, full, meta){
//     		return '<input type="type" name="KHYHH" style="text-align: center;width:100%;border:none;" value="' + data + '" guid = "'+full.KHYHH+'">';
//     		},"width":400},
    	
//     	{"data": "LHH",defaultContent:"",'render': function(data, type, full, meta){
//     		return '<input type="type" name="LHH" style="text-align: center;width:100%;border:none;" value="' + data + '" guid = "'+full.LHH+'">';
//     		},"width":400},
    	
//     	{"data": "GUID","class":"text-center",'render':function(data, type, full, meta){
// 	   		return '<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
// 	   		},orderable:false,"width":220}
    
    ];
  //  table = getDataTableByListHj("mydatatables","${ctx}/yhzhgl/getPageList",[2,'asc'],columns,0,1,setGroup);
	//table = getDataTableByListHj("mydatatables","${ctx}/json/kjhs/pzsz/yhzhgl/yhzhglList.json",[2,'asc'],columns,0,1,setGroup);
	//table = getDataTableByListHj("mydatatables","${ctx}/yhzhgl/getPageLists",[2,'asc'],columns,0,1,setGroup);
	
	
  	//新增按钮
	var num = 2;
	$(document).on("click","[class*=addBtn]",function(){
		//var wlbh = $("#txt_wlbh").val()
		var $parentTr = $(this).parents("tr").clone();
		$(this).removeClass("addBtn");
		$(this).addClass("deleteBtn");
		$parentTr.find("[a='a']").val("");
		$parentTr.find(":input").removeClass("border");
		//$parentTr.find("[id=txt_wlbh]").val(wlbh);
		$parentTr.attr("id","tr_"+num);
		
		num++;
		$(this).parents("tr").after($parentTr);
	});

	//删除按钮
	$(document).on("click","[class*=deleteBtn]",function(){
		$(this).parents("tr").remove();

	});
  	
  	
   	$("#btn_add").click(function(){
   		//doOperate("${ctx}/webView/kjhs/yhzhgl/yhzhgl_edit.jsp","C");
   		
		//alert("增加完成后，请勾选新增记录前的多选框然后单击页面右上方的‘保存’按钮。");
   		
		var index = $(".uid:last").attr("id");
		if(index==null){
		index = 0;
		}else{
			index++;
		}
		var trObj = document.createElement("tr");
		trObj.setAttribute("id",index);
		if(index%2==0 || index==1){
			trObj.setAttribute("class","odd");	
		}else{
			trObj.setAttribute("class","even");
		}
		
		trObj.setAttribute("role","row");
		
		trObj.innerHTML = "<td><input type='checkbox' name='guid' value='0'></td>"+
		"<td class='text-center'> <input type='hidden' class='uid' id='++index'></td>"+
		"<td><input type='text' name='yhdm'  style='text-align: center;width:100%;border:none;' value=''/></td>"+ 
		"<td><input type='text' name='yhmc'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
		"<td><input type='text' name='khyhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
		"<td><input type='text' name='lhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
		"<td><input type='text' name='yhdm'  style='text-align: center;width:100%;border:none;' value=''/></td>"+ 
		"<td><input type='text' name='yhmc'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
		"<td><input type='text' name='khyhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
		"<td><input type='text' name='lhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
		"<td><input type='text' name='khyhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
		"<td><input type='text' name='lhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
		"<td><input type='text' name='lhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
		"<td class='text-center'><a  class='btn btn-link btn_delxx'>删除</a></td>";
		/* trObj.innerHTML = "<td><input type='checkbox' name='guid' value='0'></td>"+
		"<td class='text-center'> <input type='hidden' class='uid' id='"+index+"'>"+index+"</td>
		<td><input type='text' name='yhdm'  style='text-align: center;width:100%;border:none;' value=''/></td>"+ 
		"<td><input type='text' name='yhmc'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
		"<td><input type='text' name='khyhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
		"<td><input type='text' name='lhh'  style='text-align: center;width:100%;border:none;' value=''/></td>"+
		"<td class='text-center'><a  class='btn btn-link btn_delxx'>删除</a></td>"; */
		document.getElementById("bod").appendChild(trObj);
   	});
  	
 
    //保存按钮
	$("#btn_save").click(function(e){
		
		
		doSave1(validate,"myform1","${ctx}/yhzhgl/addYhzhb",function(val){

		});
	});
    
	//排序
   	function px(){
   		var xh = $(".uid").parents("td").length;
   		$.each($(".uid").parents("td"),function(i,v){
   			var index = xh;
   			$(this).html("<input type='hidden' class='uid' id='"+index+"' value='"+index+"'>"+index+"");
   			xh--;
   		});
   	}
   
	
   	//修改按钮
   	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
   		doOperate("${ctx}/webView/kjhs/yhzhgl/yhzhgl_edit.jsp","C");
   	});
  
    //批量删除按钮
   	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   		$(this).parents("tr").remove();
   	     	px();
   	   		});
	   		
   		}else{
   			alert("请选择至少一条信息删除!");
   		}
   	});
	//单条删除
	$(document).on("click",".btn_delxx",function(){
// 		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
//    		var shzt = checkbox.attr("shzt");
//    		if(shzt=="已提交"||shzt=="通过"){
//    			alert("已提交或者审核通过的无法修改");
//    			return;
//    		}
// 		confirm("确定删除？","{title:提示信息}",function(){
// 			alert("删除成功");
// 		});
//alert("sss");
		$(this).parents("tr").remove();
		px();
	});
});
function remove(){
	
	
}
$(function() {	
	//列表右侧悬浮按钮
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
});
</script>
</body>
</html>