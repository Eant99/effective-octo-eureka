<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>银行帐号管理</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
 .a{
   width:100%;border:none;
 }
</style> 	
</head>
<body>
<div class="fullscreen">
<div class="search" id="searchBox">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
		  <div class="search-simple">
				<div class="form-group">
					<label>科目编号</label>
					<input type="text" id="txt_kmbh" class="form-control" name="kmbh"  table="A" placeholder="请输入科目编号">
				</div>
				<div class="form-group">
					<label>银行名称</label>
					<input type="text" id="txt_yhmc" class="form-control" name="yhmc1"  table="A" placeholder="请输入银行名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_save">保存</button>
				</div>
			</div>
		</form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			<form id="myform1" class="add">
			<input type="hidden" name="start">
				<table id="mydatatables" class="table table-striped table-bordered">			
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>序号</th> 
				            <th>科目编号</th>
				            <th>科目名称</th>
				            <th><span class="required" style="color:red;">*</span>银行名称</th>
				            <th><span class="required" style="color:red;">*</span>开户行账号</th>
				            <th><span class="required" style="color:red;">*</span>联行号</th>
				        </tr>			      
					</thead>
				    <tbody>
				    </tbody>
				</table>
				<input type="hidden" name="end">
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
    	{"data": "GUID", orderable: false, 'render': function(data, type, full, meta){
    		return '<input type="checkbox" name="guid" class="keyId" value="'+ data +'">';
    	},"width":10,'searchable': false},   	
    	{"data":"_XH",orderable:false,'render': function(data,type,full,meta){
    		return  data;
    	},"width":41,"searchable":false,"class":"text-center"},
    	{"data": "KMBH",defaultContent:"",'render': function(data, type, full, meta){
    		return '<input type="type"  class="a" name="kmbh"  readonly value = "'+full.KMBH+'">'
    	}},	
    	{"data": "KMMC",defaultContent:"",},
    	{"data": "YHMC",defaultContent:"",'render': function(data, type, full, meta){
    		if( typeof(data)=="undefined"){
    			return '<input type="type" class="a" name="yhmc" >';	
    		}else{
    			return '<input type="type" class="a" name="yhmc" value="' + data + '">';
    		}   		
    	},"width":400},    	
    	{"data": "KHYHH",defaultContent:"",'render': function(data, type, full, meta){
    		if( typeof(data)=="undefined"){
    			return '<input type="type" class="a" name="khyhh">';	
    		}else{
    			return '<input type="type" class="a" name="khyhh" value="' + data + '">';
    		}
    	},"width":400},    	
    	{"data": "LHH",defaultContent:"",'render': function(data, type, full, meta){
    		if( typeof(data)=="undefined"){
    		    return '<input type="type" class="a" name="lhh">';
    		}else{
    			return '<input type="type" class="a" name="lhh" value="' + data + '" >';
    		}
    	},"width":400},
    ];
    table = getDataTableByListHj("mydatatables","${ctx}/yhzhgl/getPageList",[2,'asc'],columns,0,1,setGroup);
	
  	//验证银行号是否重复
  	$(document).on("blur","[name=khyhh]",function(){
  		var khyhh = $(this).val();
  		var khyhhcd = khyhh.length;
  		var cd = [0,12,14,16,18,19,22];
  		var flag = true;
  		for(var i=0,k=cd.length;i<k;i++){
  			if(khyhhcd==cd[i]){
  				flag = false;
  	  			break ;
  	  		}
  		}
  		if(flag){
	  		alert("请输入有效银行账号");
	  		$(this).val("");
	  	}
  	});
    //保存按钮
    $(document).on("click","#btn_save",function(){
    	debugger
    	var flag = true;
    	//return false;跳出整个循环  return true;进入下一个
    	$.each($("tbody tr"),function(i){
    		var yhmc = $(this).find("[name='yhmc']").val();
    		var khyhh = $(this).find("[name='khyhh']").val();
    		var lhh = $(this).find("[name='lhh']").val();
    		if(yhmc!="" || khyhh!=""){
    			if(isNull(yhmc)){
    				alert("请输入第"+(i+1)+"行的银行名称");
    				flag = false;
    				return false;
    			}else if(isNull(khyhh)){
    				alert("请输入第"+(i+1)+"行的开户行账号");
    				flag = false;
    				return false;
    			}else{
    				//bankCardAttribution(khyhh) --> ["CCB"] or [undefined]
    				if("CCB"!=bankCardAttribution(khyhh) && lhh.length != 12 ){
    					alert("请输入第"+(i+1)+"行正确的联行号");
    					flag = false;
    					return false;
    				}
    			}
    		}else{
    			return true;
    		}
    	});
    	if(flag){
    		save();
    	}
	});

   function save(){
	   debugger
		var json = $("#myform1").serializeObject1("kmbh","lhh");  //json对象
		var jsonStr = JSON.stringify(json);  //json字符串
		$.ajax({
 			url:"${ctx}/yhzhgl/addYhzhb",
 			type:"post",
 			data:"json="+jsonStr,
 			async:"false",
 			dataType:"json",
 			success:function(data){	
 				if(data.success){
 					alert(data.msg);  	  
 				    window.location.href = "${ctx}/yhzhgl/goyhzhglListPage";
 				}else{
 					alert(data.msg); 
 				}
	   	   }
   	   });	
   }
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
	    	console.log(JSON.stringify(f));
	    return f;
	};
});


</script>
</body>
</html>