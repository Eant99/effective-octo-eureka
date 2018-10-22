<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>科目余额明细</title>
<%@include file="/static/include/public-list-js.inc"%>
<%@include file="/static/include/public-manager-css.inc"%> 
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
.border{
		border:1px solid #a94442;
		background:url("${ctx}/webView/wsbx/rcbx/no_null.bmp") center right no-repeat;;
	}
/* #txt_grfzmc2{ */
/* margin-left: -8px; */
/* }	 */
/* #txt_wldwfzmc2{ */
/* margin-left: -8px; */
/* } */
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				
			</div>
		</form>
	</div>
	<div class="container-fluid">
	    <div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_add">增加</button>
	               <button type="button" class="btn btn-default" id="btn_imp">批量导入</button>
	               <button type="button" class="btn btn-default" id="btn_save">保存</button>
<!-- 	               <button type="button" class="btn btn-default" id="btn_del">批量删除</button>
 -->				</div>
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			<form id="myform1">
				<table id="mydatatables" class="table table-striped table-bordered">			
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>序号</th> 
				            <th>经济科目</th>
				            <th>部门名称</th>
				            <th>项目名称</th>
				            <th>个人</th>
				            <th>借款单号</th>
				            <th>往来单位</th>
				            <th>往来单号</th>
				            <th>年初余额</th>
				            <th style="text-align:center;">操作</th>
				        </tr>			      
					</thead>
					
				    <tbody id="bod">
				    </tbody>
				    	
				</table>
			</form>
			</div>
		</div>
	</div>	
</div>
<script>
removeTr();
$(function () {
	//列表数据
	var winId = getTopFrame().layer.getFrameIndex(window.name);
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
           },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
     	   return  '<input type="hidden"  class="uid"  id="' + data + '">'+data+'';
           	},"width":41,"searchable":false,"class":"text-center"},  
       {"data": "JJKMMC",orderable:false,defaultContent:"",'render': function (data, type, full, meta){
    	   if(data==null){
    		   data="";
    	   }
         return '<c:if test="${map.sfjjflkm==1}"><input class="null" type="text" name="jjkm1" id="txt_jjkmmc'+full.GUID+'" style="width:80%;border:none;" value="' + data + '" ><input type="hidden" name="jjkm" id="txt_jjkmid'+full.GUID+'" style="text-align: center;width:100%;border:none;" value="' + full.JJKM + '" ><button type="button" id="btn_jjkm'+full.GUID+'" class="btn btn-link btn-custom">选择</button></c:if><c:if test="${map.sfjjflkm==0}"><input readonly type="text" name="jjkm1" id="txt_jjkmmc'+full.GUID+'" style="width:80%;border:none;" value="' + data + '" ><input type="hidden" name="jjkm" id="txt_jjkmid'+full.GUID+'" style="text-align: center;width:100%;border:none;" value="' + full.JJKM + '" ></c:if>';
       
       
       },"width":400},  
       {"data": "BMMC",orderable:false,defaultContent:"",'render': function (data, type, full, meta){
    	   if(data==null){
    		   data="";
    	   }
         return '<c:if test="${map.bmhs==1}"><input class="null" type="text" id="txt_bmmc'+full.GUID+'" name="bmbh1" style="width:80%;border:none;" value="' + data + '" ><input type="hidden" name="bmbh" id="txt_bmid'+full.GUID+'" style="text-align: center;width:20%;border:none;" value="' + full.BMBH + '" ><button type="button" id="btn_bmbh"  class="btn btn-link btn-custom">选择</button></c:if><c:if test="${map.bmhs==0}"><input readonly  type="text" id="txt_bmmc'+full.GUID+'" name="bmbh1" style="width:80%;border:none;" value="' + data + '" ><input type="hidden" name="bmbh" id="txt_bmid'+full.GUID+'" style="text-align: center;width:20%;border:none;" value="' + full.BMBH + '" ></c:if>';
       },"width":400},
       {"data": "XMMC",orderable:false,defaultContent:"",'render': function (data, type, full, meta){
    	   if(data==null){
    		   data="";
    	   }
         return '<c:if test="${map.xmhs==1}"><input class="null" type="text" id="txt_xmmc'+full.GUID+'" name="xmmc1" style="width:80%;border:none;" value="' + data + '" ><input type="hidden" name="xmmc" id="txt_xmbh'+full.GUID+'" style="text-align: center;width:100%;border:none;" value="' + full.XMBH + '" ><button type="button" id="btn_xmbh'+full.GUID+'"  class="btn btn-link btn-custom">选择</button></c:if><c:if test="${map.xmhs==0}"><input readonly  type="text" id="txt_xmmc'+full.GUID+'" name="xmmc1" style="width:80%;border:none;" value="' + data + '" ><input type="hidden" name="xmmc" id="txt_xmbh'+full.GUID+'" style="text-align: center;width:100%;border:none;" value="' + full.XMBH + '" ></c:if>';
       },"width":400},
       {"data": "GRFZS",orderable:false,defaultContent:"",'render': function (data, type, full, meta){
    	   if(data==null){
    		   data="";
    	   }
         return '<c:if test="${map.grfz==1}"><input class="null" type="text" id="txt_grfzmc'+full.GUID+'" name="grfz1" style="width:80%;border:none;" value="' + data + '" ><input type="hidden" name="grfz" id="txt_grfzid'+full.GUID+'" style="text-align: center;width:100%;border:none;" value="' + full.GRFZ + '" ><button type="button" id="btn_grfz"  class="btn btn-link btn-custom">选择</button></c:if><c:if test="${map.grfz==0}"><input readonly  type="hidden" id="txt_grfzmc'+full.GUID+'" name="grfz1" style="width:80%;border:none;" value="' + data + '" ><input type="hidden" name="grfzmc" id="txt_grfzid'+full.GUID+'" style="text-align: center;width:100%;border:none;" value="' + full.GRFZ + '" ></c:if>';
       },"width":400},
       {"data": "GRDH",defaultContent:"",'render': function (data, type, full, meta){
         return '<c:if test="${map.grfz==1}"><input class="null " type="text" id="txt_grfzcx"  name="grfzcx" style="width:100%;border:none;" value="' + data + '" ></c:if><c:if test="${map.grfz==0}"><input class="null " type="hidden" id="txt_grfzcx"  name="grfzcx" style="width:100%;border:none;" value="' + data + '" ></c:if>';
       }},  
       {"data": "DWFZS",orderable:false,defaultContent:"",'render': function (data, type, full, meta){
    	   if(data==null){
    		   data="";
    	   }
         return '<c:if test="${map.wldwfz==1}"><input class="null" type="text" id="txt_wldwfzmc'+full.GUID+'" name="wldwfz1" style="width:80%;border:none;" value="' + data + '" ><input type="hidden" name="wldwfz" id="txt_wldwfzid'+full.GUID+'" style="text-align: center;width:100%;border:none;" value="' + full.DWFZ + '" ><button type="button" id="btn_wldwfz"  class="btn btn-link btn-custom">选择</button></c:if><c:if test="${map.wldwfz==0}"><input readonly  type="hidden" id="txt_wldwfzmc'+full.GUID+'" name="grfz1" style="width:80%;border:none;" value="' + data + '" ><input type="hidden" name="wldwfzmc" id="txt_wldwfzid'+full.GUID+'" style="text-align: center;width:100%;border:none;" value="' + full.DWFZS + '" ></c:if>';
       },"width":400},
       {"data": "DWBH",defaultContent:"",'render': function (data, type, full, meta){
         return '<c:if test="${map.wldwfz==1}"><input class="null " type="text" id="txt_wldwfzdh"  name="wldwfzdh" style="width:100%;border:none;" value="' + data + '" > </c:if>   <c:if test="${map.wldwfz==0}"><input class="null " type="hidden" id="txt_wldwfzdh"  name="wldwfzdh" style="width:100%;border:none;" value="' + data + '" > </c:if>';
       }},  
       {"data": "NCYE",defaultContent:"",'render': function (data, type, full, meta){
         return '<input class="null number" type="text" id="txt_ncye"  name="ncye" style="text-align: right;width:100%;border:none;" value="' + data + '" >';
       }},  
       {"data": "GUID","class":"text-center",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
      },orderable:false,"width":120}
     ];
	table = getDataTableByListHj("mydatatables","${ctx}/kmsz/getkmyemxList?kmyeguid=${param.kmyeguid}&nd=${nd}",[5,'asc'],columns,0,1,setGroup);
	//经济科目弹窗
	$(document).on("click","[id^=btn_jjkm]",function(){
		var txt_jjkmmc = $(this).parents("tr").find("[id^=txt_jjkmmc]").attr("id");
		var txt_jjkmid = $(this).parents("tr").find("[id^=txt_jjkmid]").attr("id");

        select_commonWin("${ctx}/kmsz/jjkmTree?controlId="+txt_jjkmmc+"&controlId1="+txt_jjkmid,"经济科目信息","1500","630");	

	});
	/* $("[id^=btn_jjkm]").click(function(){
	//select_commonWin("${ctx}/bmysbz/jjkmTree","经济科目信息","920","630");
	    		var txt_jjkmmc = $(this).parents("tr").find("[id^=txt_jjkmmc]").attr("id");
	    		var txt_jjkmid = $(this).parents("tr").find("[id^=txt_jjkmid]").attr("id");

		select_commonWin("${ctx}/kmsz/jjkmTree?controlId="+txt_jjkmmc+"&controlId1="+txt_jjkmid,"经济科目信息","920","630");	
    }); */
	//项目弹窗
	$(document).on("click","[id^=btn_xmbh]",function(){
		var bmbh = $(this).parents("tr").find("[name=bmbh]").val();
		var txt_xmmc = $(this).parents("tr").find("[id^=txt_xmmc]").attr("id");
		var txt_xmbh = $(this).parents("tr").find("[id^=txt_xmbh]").attr("id");
		select_commonWin("${ctx}/kmsz/getxmbh?controlId="+txt_xmmc+"&controlId1="+txt_xmbh+"&bmbh="+bmbh,"项目信息","920","630");	
		
	});
    
    
    
    
	$(document).on("click","[id^=btn_bmbh]",function(){
		var txt_bmmc = $(this).parents("tr").find("[id^=txt_bmmc]").attr("id");
		var txt_bmid = $(this).parents("tr").find("[id^=txt_bmid]").attr("id");
		select_commonWin("${ctx}/kmsz/dwpage?controlId="+txt_bmmc+"&controlId1="+txt_bmid,"单位信息","920","630");	
		
	});
	
	
	$(document).on("click","[id^=btn_grfz]",function(){
// 		var ss="${param.grfz}";
// 		alert("hhehehe"+ss);
		var txt_bmmc = $(this).parents("tr").find("[id^=txt_grfzmc]").attr("id");
		var txt_bmid = $(this).parents("tr").find("[id^=txt_grfzid]").attr("id");
		select_commonWin("${ctx}/kmsz/grpage?controlId="+txt_bmmc+"&controlId1="+txt_bmid,"单位信息","920","630");	
		
	});
	
	
	
	/* $(document).on("click","[id^=btn_wldwfz]",function(){
		var txt_bmmc = $(this).parents("tr").find("[id^=txt_wldwfzmc]").attr("id");
		var txt_bmid = $(this).parents("tr").find("[id^=txt_wldwfzid]").attr("id");
		select_commonWin("${ctx}/kmsz/dwpage?controlId="+txt_bmmc+"&controlId1="+txt_bmid,"单位信息","920","630");	
		
	}); */
	$(document).on("click","[id^=btn_wldwfz]",function(){
		//var id = $(this).parents("td").find("[name=dfdw1]").attr("id");
		//var gid = $(this).parents("td").find("[name=dfdwdg]").attr("id");
		var id = $(this).parents("tr").find("[id^=txt_wldwfzmc]").attr("id");
		var gid = $(this).parents("tr").find("[id^=txt_wldwfzid]").attr("id");
		select_commonWin("${ctx}/wsbx/rcbx/window?flag=2&controlId=xnzz&id="+id+"&gid="+gid,"往来单位","920","630");
	});
	
	$(document).on("click","#btn_wldwfz3",function(){
// 		var ss="${param.grfz}";
// 		alert("hhehehe"+ss);
		var txt_bmmc = $(this).parents("tr").find("[id^=wldwfzmc]").attr("id");
		var txt_bmid = $(this).parents("tr").find("[id^=wldwfzid]").attr("id");
		select_commonWin("${ctx}/kmsz/dwpage?controlId="+txt_bmmc+"&controlId1="+txt_bmid,"单位信息","920","630");	
		
	});


	
	$(document).on("click","#btn_grfz3",function(){
// 		var ss="${param.grfz}";
// 		alert("hhehehe"+ss);
		var txt_bmmc = $(this).parents("tr").find("[id^=grmc]").attr("id");
		var txt_bmid = $(this).parents("tr").find("[id^=grid]").attr("id");
		select_commonWin("${ctx}/kmsz/grpage?controlId="+txt_bmmc+"&controlId1="+txt_bmid,"单位信息","920","630");	
		
	});
	
	
	
  	//添加按钮
  	var a=2;
   	$("#btn_add").click(function(){
   		
		var index = $(".uid:last").attr("id");
		if(index==null){
		index = 1;
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
		trObj.innerHTML = "<td><input type='checkbox' ></td>"+"<td class='text-center'> <input type='hidden' name='guid' class='uid' id='"+index+"'>"+index+"</td>"+
		"<c:if test="${map.sfjjflkm==1}"><td><input type='text' class='null' name='jjkm1' id='txt_jjkmmc"+a+"'  style='text-align: left;width:80%;border:none;' value=''/><input id='txt_jjkmid"+a+"' type='hidden' name='jjkm'  style='text-align: left;width:30%;border:none;' value=''/><button type='button' id='btn_jjkm"+a+"' class='btn btn-link btn-custom'>选择</button></td></c:if><c:if test="${map.sfjjflkm==0}"><td><input type='text' readonly name='jjkm1' id='txt_jjkmmc"+a+"'  style='text-align: center;width:80%;border:none;' value=''/><input id='txt_jjkmid"+a+"' type='hidden' name='jjkm'  style='text-align: center;width:30%;border:none;' value=''/></c:if>"+ 
		"<c:if test="${map.bmhs==1}"><td><input type='text' class='null' name='bmbh1' id='txt_bmmc"+a+"'  style='text-align: left;width:80%;border:none;' value=''/><input id='txt_bmid"+a+"' type='hidden' name='bmbh'  style='text-align: left;width:30%;border:none;' value=''/><button type='button' id='btn_bmbh"+a+"'  class='btn btn-link btn-custom'>选择</button></td></c:if><c:if test="${map.bmhs==0}"><td><input readonly type='text'  name='bmbh1' id='txt_bmmc"+a+"'  style='text-align: center;width:80%;border:none;' value=''/><input id='txt_bmid"+a+"' type='hidden' name='bmbh'  style='text-align: center;width:30%;border:none;' value=''/></td></c:if>"+
		"<c:if test="${map.xmhs==1}"><td><input type='text' class='null' name='xmmc1' id='txt_xmmc"+a+"'  style='text-align: left;width:80%;border:none;' value=''/><input id='txt_xmbh"+a+"' type='hidden' name='xmmc'  style='text-align: left;width:30%;border:none;' value=''/><button type='button' id='btn_xmbh"+a+"'  class='btn btn-link btn-custom'>选择</button></td></c:if><c:if test="${map.xmhs==0}"><td><input readonly type='text'  name='xmmc1' id='txt_xmmc"+a+"'  style='text-align: center;width:80%;border:none;' value=''/><input id='txt_xmbh"+a+"' type='hidden' name='xmmc'  style='text-align: center;width:30%;border:none;' value=''/></td></c:if>"+
		"<c:if test="${map.GRFZ==1}"><td><input type='text' class='null' name='grfz1' id='txt_grfzmc"+a+"'  style='text-align: left;width:80%;border:none;' value=''/><input id='txt_grfzid"+a+"' type='hidden' name='grfz'  style='text-align: left;width:30%;border:none;' value=''/><button type='button' id='btn_grfz"+a+"'  class='btn btn-link btn-custom'>选择</button></td></c:if><c:if test="${map.GRFZ==0}"><td><input readonly type='hidden'  name='grfz1' id='txt_grfzmc"+a+"'  style='text-align: center;width:80%;border:none;' value='0'/><input id='txt_grfzid"+a+"' type='hidden' name='grfz'  style='text-align: center;width:30%;border:none;' value='0'/></td></c:if>"+
		"<c:if test="${map.GRFZ==1}"><td><input type='text' class='null ' name='grfzcx' id='txt_grfzcx'  style='width:100%;border:none;' value=''/></td></c:if><c:if test="${map.GRFZ==0}"><td><input type='hidden' name='grfzcx' id='txt_grfzcx'  style='width:100%;border:none;' value='0'/></td></c:if>"+
		"<c:if test="${map.WLDWFZ==1}"><td><input type='text' class='null' name='wldwfz1' id='txt_wldwfzmc"+a+"'  style='text-align: left;width:80%;border:none;' value=''/><input id='txt_wldwfzid"+a+"' type='hidden' name='wldwfz'  style='text-align: left;width:30%;border:none;' value=''/><button type='button' id='btn_wldwfz"+a+"'  class='btn btn-link btn-custom'>选择</button></td></c:if><c:if test="${map.WLDWFZ==0}"><td><input readonly type='hidden'  name='wldwfz1' id='txt_wldwfzmc"+a+"'  style='text-align: center;width:80%;border:none;' value='0'/><input id='txt_wldwfzid"+a+"' type='hidden' name='gwldwfz'  style='text-align: center;width:30%;border:none;' value='0'/></td></c:if>"+
		"<c:if test="${map.WLDWFZ==1}"><td><input type='text' class='null ' name='wldwfzdh' id='txt_wldwfzdh'  style='width:100%;border:none;' value=''/></td></c:if><c:if test="${map.WLDWFZ==0}"><td><input type='hidden'  name='wldwfzdh' id='txt_wldwfzdh'  style='width:100%;border:none;' value=''/></td></c:if>"+
		"<td><input type='text' class='null number' name='ncye' id='txt_ncye'  style='text-align: right;width:100%;border:none;' value=''/></td>"+
		"<td class='text-center'><a  class='btn btn-link btn_delxx'>删除</a></td>";
		document.getElementById("bod").appendChild(trObj);
        a++;
   	});
	//数据导入
	$("#btn_imp").click(function(){
		var mblx="1";//模板类型,三个字段
		
		if(${map.sfjjflkm==1} && ${map.bmhs==1}){//经济和部门
			mblx="2";
		}else if(${map.sfjjflkm==1} && ${map.xmhs==1}){//经济和项目
			mblx="3";
		}else if(${map.bmhs==1} && ${map.xmhs==1}){//部门加项目
			mblx="4";
		}else if(${map.sfjjflkm==1}){//经济
			mblx="5";
		}else if(${map.bmhs==1}){//部门
			mblx="6";
		}else if(${map.xmhs==1}){//项目
			mblx="7";
		}
		var  kmyebh = "${param.kmyeguid}";
        var kmmc = "${param.kmmc}";
		select_commonWin("${ctx}/kmsz/getImpPage?mblx="+mblx+"&kmyebh="+kmyebh+"&kmmc="+kmmc, "导入科目余额明细信息", 550,450);
		return false;
	});
   	$("#btn_save").click(function(e){
   		$(":checkbox").attr("checked",true);
   		var  kmyebh = "${param.kmyeguid}";
        var kmmc = "${param.kmmc}";
		doSave1("myform1","${ctx}/kmsz/editkmyemx?kmyebh="+kmyebh+"&kmmc="+kmmc+"&nd=${nd}",function(val){
			
		});
	});	
   	function computerBn(){
   		var nczye = 0.00;
   		$.each($("[id=txt_ncye]"),function(){
   			var val = $(this).val();
   			if(val==""||val==0||isNaN(val)){
   				val = 0.00;
   			}
   			nczye = parseFloat(nczye)+parseFloat(val);
   		});
   		return nczye;
   		//$("[name='dynzchz']").val(dynzchz.toFixed(4));
   	} 
	function doSave1( _formId, _url, _success, _fail){
		checkAutoInput();
		var valid=true;
        var id = "${param.id}";
        var nczye=computerBn();
		var json = $('#myform1').serializeObject1("guid","ncye");  //json对象				
		var jsonStr = JSON.stringify(json);  //json字符串
		if(!tag){
			valid = false;
		}
		if(valid){
			$.ajax({
				type:"post",
				url:_url,
				dataType:"json",
				data:"json="+jsonStr,
				success:function(val){	
					var winId = getTopFrame().layer.getFrameIndex(window.name);
					getIframWindow("${param.pname}").addnczye(id,nczye);
					getIframWindow("${param.pname}").sumje("${param.kmyeguid}".substring(0,"${param.kmyeguid}".length-2),"${param.kmyeguid}");
					close(winId);
					if(val.success=="true"){
						
					}else{
						alert(val.msg);
					}
				},
				error:function(val){
					alert("失败");

				}	
			});
		}
			
	}
	function checkAutoInput(){
		tag = true;
		var ys = $(".null");
		var value = "";
		$.each(ys,function(){
			value = $(this).val();
			if(value==""){
				$(this).addClass("border");
				tag = false;
			}
		});
	}
	$(document).on("focus","[class*=border]",function(){
		$(this).removeClass("border");
	});
	$.fn.serializeObject1 = function(start,end){
		var f = {"list":[]};
	    var a = this.serializeArray();
	    var o = {};
	    $.each(a, function() {
	    	if (this.name == start) {
	        	o = {};
	        	o[this.name] = this.value;
	        } else if(this.name == end){
	        	o[this.name] = this.value;
	        	f["list"].push(o);
	        }else{
	        	o[this.name] = this.value;
	        }
	    });
	    return f;
	};
	//单删
	
	$(document).on("click",".btn_delxx",function(){
		$(this).parents("tr").remove();
		
	});
	//批量删
   /*  $(".btn_del").click(function(){
    	var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
    	$.each(checkbox,function(){
    		$(this).parents("tr").remove();
    		
    	})
    	
    }); */
	
	
});

$(function() {	
	//列表右侧悬浮按钮
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
});
removeTr();
$(document).ready(function(){
	removeTr();
	
	
});
function removeTr(){
	$(".dataTables_empty").parents("tr").remove();
}
</script>
</body>
</html>