<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>成本中心管理</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.box {
		padding:0px;
		margin-left:0px;
		margin-right:0px;
		margin-top: 15px;
	}
	.zhxxcx .icon-chaxun {
	top:10px;
	}
</style>
</head>
<body style="background:white">
<!-- 成本中心信息 -->
<div class="box-content col-md-6" style="padding-right:5px;">
	<div class="box">
		<div class=" box-content">
	   		<!-- 列表操作按钮组-->
	   		
			<div class="clearfix">
				<div class="pull-left sub-title text-primary">成本中心</div>
				
				<div class="btn-group pull-right" role="group">
					<button type='button' class="btn btn-default" id="btn_addl">增加</button>
					<button type='button' class="btn btn-default" id="btn_exp">导出Excel</button>
				</div>
			</div>
			<div class="search" id="searchBox">
    	<form id="myform" class="form-inline" role="form" action="">
    	<div class="search-simple">
				<div class="form-group">
					<label>成本中心名称</label>
					<input type="text" id="txt_cbzxmc" class="form-control input-radius" name="cbzxmc" table="D" placeholder="请输入成本中心名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_sea">查 询</button>
    	</div>
    	</form>
    	</div>
			<div class="responsive-table">
	          	<div class="scrollable-area">
		            <table id="mydatatables" class="table table-striped table-bordered">
					    <thead>
						    <tr style="background-color: #f3f3f3">
								<th style="text-align:center"><input type="checkbox" class="select-all"/></th>
								<th style="text-align:center">序号</th>
								<th style="text-align:center">成本中心名称</th>
								<th style="text-align:center">操作</th>
							</tr>
						</thead>
						<tbody>
						<!-- 测试数据 -->
						<tr>
								<td style="text-align:center"><input type="checkbox" value="" class="select-all" /></td>
								<td>1</td>
								<td name="cbzxmc">教学成本中心</td>
								<td>
								<div><a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxxl">删除</a>
								</div>
								</td>
							</tr>
							<tr>
								<td style="text-align:center"><input type="checkbox" value="" class="select-all" /></td>
								<td>2</td>
								<td name="cbzxmc">行政成本中心</td>
								<td>
								<div><a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxxl">删除</a>
								</div>
								</td>
							</tr>
							<!-- 测试数据 -->
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 成本中心类型详情 -->
<div class=" box-content col-md-6" >
	<input type="hidden" id="txt_jsbh">
	<div class="box">
		<div class='box-content'>
			<div class="clearfix">
				<div class="pull-left sub-title text-primary" id="">成本中心所用单位&emsp;当前成本中心名称：
					<input type="text" readonly style ="border:0;color:red;font-weight:bolder" class="cbzxmc"  value="未选择">
				</div>
				<div class="btn-group pull-right" role="group">
					<button type='button' class="btn btn-default" id="btn_addr">增加</button>
					<button type='button' class="btn btn-default" id="btn_delr">批量删除</button>
					<button type="button" class="btn btn-default" id="btn_expr">导出Excel</button>
				</div>
			</div>
			<hr class="hr-normal" />
			<div class='responsive-table'>
	        	<div class='scrollable-area'>
	                <table id="jsyh" class="table table-striped table-bordered">
					    <thead>
						    <tr style="background-color: #f3f3f3">
								<th style="text-align:center"><input type="checkbox" value="" class="select-all" /></th>
								<th style="text-align:center">序号</th>
								<th style="text-align:center">单位编号</th>
								<th style="text-align:center">单位名称</th>
								<th style="text-align:center">操作</th>
							</tr>
						</thead>
						<tbody>
						<tr>
						<td style="text-align:center"><input type="checkbox" value="" class="select-all" /></td>
								<td>1</td>
								<td>1</td>
								<td>1</td>
								<td>
								<div><a href="javascript:void(0);" class="btn btn-link btn_delxxl">删除</a>
								</div>
								</td>
								</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function() {
	//点击成本中心信息的行事件
	$(document).on('click','#mydatatables tr',function(){
		var cbzxmc=$(this).find('td[name="cbzxmc"]').text();
		$('.cbzxmc').val(cbzxmc);
		
    });
	//导出Excel（成本中心信息）
	$(document).on("click","#btn_exp",function(){
   		alert("暂时未实现");
   	});
  //导出Excel
  $(document).on("click","#btn_expr",function(){
	  alert("暂时未实现");
			});
  	
	//添加按钮：成本中心信息
	$(document).on("click","#btn_addl",function(){
		select_commonWin("${ctx}/cbgl/cbzx/getCbzxxx?operateType=C","成本中心信息", "400", "450");
	});
	//添加按钮：单位
	$(document).on("click","#btn_addr",function(){
		select_commonWin("${ctx}/jsxx/doAdd","人员信息", "920", "630");
	});
	//查看
   	$(document).on("click",".btn-lookl",function(){
   		select_commonWin("${ctx}/cbgl/cbzx/getCbzxxx?operateType=C","成本中心信息", "400", "450");
   	});
	//单条删除操作：成本中心信息
	$(document).on("click",".btn_delxxl",function() {
		alert("测试数据暂时不能删除！");
		});  
	});
	//单条删除操作：成本中心类型
	$(document).on("click",".btn_delxxr",function() {
		alert("测试数据暂时不能删除！");
	});
	//批量删除按钮：成本中心类型
	$(document).on("click","#btn_delr",function() {
		alert("测试数据暂时不能删除！");
	});
	//修改操作
	$(document).on("click",".btn_upd",function() {
		select_commonWin("${ctx}/cbgl/cbzx/getCbzxxx?operateType=C","成本中心信息", "400", "450");
	});
function doDelQxsq(_data,_url,_success,_fail,_length,xx){
	var index;
	if(xx == undefined || xx == ""){
		xx = "确认要删除该成本中心类型吗？";
		if(_length!="1"&&_length!="0"){
			xx = "确认要删除这"+_length+"个成本中心类型吗？";
		}
	}
	confirm(xx,{title:"提示"},function(){
		 $.ajax({
			type:"post",
			data:_data,
			url:_url,
			success:function(val){
				close(index);
				var data = JSON.getJson(val);
				if(data.success){
					alert(data.msg);
					if(_success!=""&&_success!=undefined&&_success!=null){
						_success(data);
					}
				}else
				{
					alert(data.msg);
					if(_fail!=""&&_fail!=""&&_fail!=""){
						_fail(data);
					}
				}
			},
			error:function(){
				close(index);
				alert(getPubErrorMsg());
			},
			beforeSend:function(){
				index = loading(2);
			}
		});
	}); 
}
</script>
</body>
</html>