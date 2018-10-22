<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.addBtn{
		text-align:center;
	    width: 20px;
	    height: 20px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	    margin-top:2px;
	}
	.addBtn:after{
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    position: relative;
	    cursor: pointer;
	}
	.deleteBtn{
		text-align:center;
	    width: 20px;
	    height: 20px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	    margin-top:2px;
	}
	.deleteBtn:after{
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    position: relative;
	    cursor: pointer;
	}
	.td_input{
		width:100%!important;
		border:none;
	}
	.number{
		text-align:right;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
<!-- 				<div class="form-group" style="visibility:hidden;"> -->
					
<!-- 	                <label>姓&emsp;&emsp;名</label> -->
<%-- 	             	<input name="xm" class="input_info date form-control" style="width:130px;" placeholder="请输入姓名" value="${kssj }" data-format="yyyy-MM-dd" readonly/> --%>
<!-- 					<label>银行账号</label> -->
<%-- 					<input name="yhzh" class="input_info date form-control" placeholder="请选择结束时间" style="width:130px;" value="${jssj }" data-format="yyyy-MM-dd" readonly/> --%>
<!-- 					<button type="button" class="btn btn-primary" id="btn_search"> -->
<!-- 					<i class="fa icon-chaxun"></i> -->
<!-- 					查 询 -->
<!-- 				 	</button> -->
<!-- 				</div> -->
				<div class="btn-group pull-right form-group" >
<!-- 					<button type="button" class="btn btn-default" id="btn_save">保存</button> -->
<!-- 					<button type="button" class="btn btn-default" id="btn_add">新增</button> -->
<!-- 					<button type="button" class="btn btn-default" id="btn_imp">导入Excel</button> -->
        		</div>
			</div>
		</form>
	</div>
	<form id="tableForm">
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th style="text-align:center;">姓名</th>
				            <th style="text-align:center;">银行名称</th>
				            <th style="text-align:center;">账号</th>
				            <th style="text-align:center;">金额</th>
				            <th style="text-align:center;">人民银行联行号</th>
				        </tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="list" varStatus="i">
							<tr class="tr_${i.index+1}">
					        	<input type="hidden" name="start" value="start" />
					            <td>
					            	<input type="text" name="xm" value="${list.xm}" class="td_input"/>
					            </td>
					            <td>
					            	<input type="text" name="yhmc" value="${list.yhmc}" class="td_input"/>
					            </td>
					            <td>
					            	<input type="text" name="yhzh" value="${list.yhzh}" class="td_input"/>
					            </td>
					            <td>
					            	<input type="text" name="je" value="${list.je}" class="td_input"/>
					            </td>
					            <td>
					            	<input type="text" name="yhlhh" value="${list.yhlhh}" class="td_input"/>
					            </td>
					           <input type="hidden" name="end" value="end" />
				           </tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</form>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>

</script>
</body>
</html>