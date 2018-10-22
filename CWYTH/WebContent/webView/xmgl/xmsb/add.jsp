<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<style type="text/css">
	.right{
		flaot:left;
		width:60%!important;
		height:100%!important;
	}
	.left{
		float:left;
		width:10%!important;
		height:100%!important;
	}
	[id^=div_]{
		border:none;
	}
	select{
		width:220px!important;
	}
	.inputs{
		width:220px!important;
	}
	.radiodiv{
	    border: 1px solid #ccc;
	    border-top-right-radius: 4px;
		border-bottom-right-radius:4px;
/* 	    border-radius: 4px; */
	    height: 25px;
	    line-height: 25px;
	    padding-left: 6px;
	    width:220px;
	}
	.table-bordered {
    	border: none;
	}
	table{
		 border-collapse: collapse
	}
</style>
</head>
<body>
<div id="div_${param.num}">
	<table id="mydatatables" class="table table-striped table-bordered">
				    <tbody>
				    	<tr>
				    		<td rowspan="5" style="width:50px;text-align:center;vertical-align: middle;">
								<input type="checkbox" class="ck" value="div_${param.num}"/>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td>
				    			<div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon">采购目录</span>
										<input type="text" id="txt_xmfl" name="xmfl" class="form-control input-radius inputs" value="(${param.fljb})${param.flmc}"/>
									</div>
                				</div>
				    		</td>
				    		<td>
				    			 <div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon">物品名称</span>
                       					<input type="text" id="txt_xmbh" class="form-control input-radius inputs" name="wpmc" value=""/>
									</div>
								</div>
				    		</td>
				    		<td>
				    			<div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon">计量单位</span>
										<select class="form-control input-radius select2" name="jldw">
											<option value="">个</option>
											<option value="1">升</option>
											<option value="2">其他</option>
										</select>
									</div>
								</div>	
				    		</td>
				    	</tr>
				    	<tr>
				    		<td>
				    			<div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon">数&emsp;&emsp;量</span>
										<input type="text" id="txt_xmfl" name="xmfl" class="form-control input-radius inputs" value=""/>
									</div>
                				</div>
				    		</td>
				    		<td>
				    			 <div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon">单&emsp;&emsp;价(元)</span>
                       					<input type="text" id="txt_xmbh" class="form-control input-radius number inputs" name="wpmc" value=""/>
									</div>
								</div>
				    		</td>
				    		<td>
				    			<div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon">金&emsp;&emsp;额(元)</span>
										<input type="text" id="txt_xmbh" class="form-control input-radius number inputs" name="wpmc" value=""/>
									</div>
								</div>	
				    		</td>
				    	</tr>
				    	<tr>
					    	<td colspan="3">
					    		<div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon">是否进口</span>
										<div class="radiodiv">
										<input type="radio" id="txt_xmfl" name="sfjk" class=""/>是
										<input type="radio" id="txt_xmfl" name="sfjk" class="" checked/>否
										</div>
									</div>
   								 </div>
					    	</td>
				    	</tr>
				    	<tr>
					    	<td colspan="3">
					    		<div class="col-md-12">
									<div class="input-group">
										<span class="input-group-addon">备注</span>
										<textarea style="width:100%;height:100px;" class="form-control input-radius"></textarea>
									</div>
   								 </div>
					    	</td>
				    	</tr>
				    </tbody>
				</table>
</div>
</body>
</html>