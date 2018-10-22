<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>项目信息</span>
		</div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
            	<div class="pull-right">
					<button type="button" class="btn btn-default" id="btn_back" style="background:#00acec;color: white;">返回</button>
        		</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
					<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>初审项目排名</span>
							<input type="text" id="txt_xmfl" name="xmfl" class="form-control input-radius" value=""/>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>初审人</span>
                            <input type="text" id="txt_xmbh" class="form-control input-radius" name="xmbh" value=""/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>初审日期</span>
							 <input type="text" id="txt_xmmc" class="form-control input-radius" name="xmmc" value=""/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>复审项目排名</span>
							<input type="text" id="txt_xmfl" name="xmfl" class="form-control input-radius" value=""/>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>复审人</span>
                            <input type="text" id="txt_xmbh" class="form-control input-radius" name="xmbh" value=""/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>复审日期</span>
							 <input type="text" id="txt_xmmc" class="form-control input-radius" name="xmmc" value=""/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目分类</span>
							<input type="text" id="txt_xmfl" name="xmfl" class="form-control input-radius window" value="${map.xmfl}"/>
							<span class="input-group-btn"><button type="button" id="btn_xmfl" class="btn btn-link btn-custom">选择</button></span>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目编号</span>
                            <input type="text" id="txt_xmbh" class="form-control input-radius" name="xmbh" value="${map.xmbh}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目名称</span>
							 <input type="text" id="txt_xmmc" class="form-control input-radius" name="xmmc" value="${map.xmbh}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon"><span class="required">*</span>服务专业</span>
	                         <input type="text" id="txt_fwzy" class="form-control input-radius window" name="fwzy" value="${xsxx.szyx}">
	                         <span class="input-group-btn"><button type="button" id="btn_fwzy" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>服务学科</span>
	                        <input type="text" id="txt_fwxk" class="form-control input-radius" name="fwxk" value="${xsxx.nj}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>申报年度</span>
							 <input type="text" id="txt_csrq" class="form-control date" name="csrq" value=""  data-format="yyyy-MM-dd" placeholder="申报年度">
                       		 <span class='input-group-addon'>
                            	<i class="glyphicon glyphicon-calendar"></i>
                       		 </span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>计划执行时间</span>
							 <input type="text" id="txt_csrq" class="form-control date" name="csrq" value=""  data-format="yyyy-MM-dd" placeholder="计划执行时间">
                       		 <span class='input-group-addon'>
                            	<i class="glyphicon glyphicon-calendar"></i>
                       		 </span>
						</div>
                  </div>
                  <div class="col-md-4">                 	                 	
                  	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>工&emsp;&emsp;期</span>
                            <input type="text" id="txt_fwxk" class="form-control input-radius integer" name="fwxk" value="${xsxx.nj}"/>
					</div>
                  </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>预算金额(元)</span>
                            <input type="text" id="txt_fwxk" class="form-control input-radius number" name="fwxk" value="${xsxx.nj}"/>
						</div>
					</div>
				</div>
			<div class="row">
				<div class="col-md-4">
					<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>上传附件</span>
                            <div style="height:25px;">
                            	&nbsp;&nbsp;&nbsp;
                            	<a href="">这是一个附件</a>
                            </div>
					</div>
				</div>
			</div>
                 <div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">初审意见</span>
							<textarea style="width:1560px;" readonly name="ccsy" class="form-control input-radius">通过</textarea>
						</div>
                    </div>
                 </div>
                 <div class="row">
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">复审意见</span>
							<textarea style="width:1560px;" readonly name="ccsy" class="form-control input-radius">通过</textarea>
						</div>
                    </div>
                 </div>
			</div>
			<div class="container-fluid box-content" id="group_panel">
				<table id="mydatatables" class="table table-striped table-bordered">
					<caption>采购目录明细信息</caption>
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>采购目录</th>
				            <th>物品名称</th>
				            <th>计量单位</th>
				            <th>数量</th>
				            <th>单价(元)</th>
				            <th>金额(元)</th>
				            <th>是否进口</th>
				            <th>备注</th>
				        </tr>
					</thead>
				    <tbody>
				    	 <tr>
				            <td><input type="checkbox" class="select-all"/></td>
				            <td>机械采购</td>
				            <td>汽车</td>
				            <td>辆</td>
				            <td>1</td>
				            <td>100000.00</td>
				            <td>100000.00</td>
				            <td>是</td>
				            <td>备注</td>
				        </tr>
				    </tbody>
				</table>
			</div>
			
		</div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	$("input").attr("readonly","readonly");
	
	//返回按钮
	$("#btn_back").click(function(){
		//parent.location.reload(true);
		window.location.href = "${ctx}/webView/xmgl/cxtj/xmtj/school_view.jsp";
	});
	
});

</script>
</body>
</html>