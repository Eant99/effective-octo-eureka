<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.input-group-addon:first-child {
   		 min-width: 123px!important;
	}
	table{
		border-collapse:collapse!important;
	}
	.table-bordered>thead>tr>td, .table-bordered>thead>tr>th {
   	 	border-bottom-width: 0!important;
	}
	.radiodiv{
	    border: 1px solid #ccc;
	    border-top-right-radius: 4px;
		border-bottom-right-radius:4px;
/* 	    border-radius: 4px; */
	    height: 25px;
	    line-height: 25px;
	    padding-left: 6px;
	}
	th{
		text-align:center;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>项目申报信息查看</span>
		</div>
		<div class="pull-right">
					<button type="button" class="btn btn-default" id="btn_back" style="background:#00acec;color: white;">返回</button>
        		</div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
		<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>项目信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
					<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">初审项目排名</span>
							<input type="text" id="txt_xmfl" name="xmfl" class="form-control input-radius" value=""/>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">初&ensp;审&ensp;人</span>
                            <input type="text" id="txt_xmbh" class="form-control input-radius" name="xmbh" value=""/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">初审日期</span>
							 <input type="text" id="txt_xmmc" class="form-control input-radius" name="xmmc" value=""/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目分类</span>
							<input type="text" id="txt_xmfl" name="xmfl" class="form-control input-radius" value="${map.xmfl}"/>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目编号</span>
                            <input type="text" id="txt_xmbh" class="form-control input-radius" name="xmbh" value="${map.xmbh}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项目名称</span>
							 <input type="text" id="txt_xmmc" class="form-control input-radius" name="xmmc" value="${map.xmbh}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">服务专业</span>
	                         <input type="text" id="txt_fwzy" class="form-control input-radius" name="fwzy" value="${xsxx.szyx}">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">服务学科</span>
	                        <input type="text" id="txt_fwxk" class="form-control input-radius" name="fwxk" value="${xsxx.nj}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">申报年度</span>
							 <input type="text" id="txt_csrq" class="form-control" name="csrq" value="2017"  data-format="yyyy-MM-dd" placeholder="申报年度">
                       		 <span class='input-group-addon'>
                            	<i class="glyphicon glyphicon-calendar"></i>
                       		 </span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">计划执行时间</span>
							 <input type="text" id="txt_csrq" class="form-control" name="csrq" value=""  data-format="yyyy-MM-dd" placeholder="计划执行时间">
                       		 <span class='input-group-addon'>
                            	<i class="glyphicon glyphicon-calendar"></i>
                       		 </span>
						</div>
                  </div>
                  <div class="col-md-4">                 	                 	
                  	<div class="input-group">
							<span class="input-group-addon">工&emsp;&emsp;期</span>
                            <input type="text" id="txt_fwxk" class="form-control input-radius integer" name="fwxk" value="${xsxx.nj}"/>
					</div>
                  </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">预算金额(元)</span>
                            <input type="text" id="txt_fwxk" class="form-control input-radius number" name="fwxk" value="${xsxx.nj}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">是否上年度重新论证项目</span>
							<div class="radiodiv">
							<input type="radio" id="txt_xmfl" name="sfsndclzxm" class="" disabled/>是
							<input type="radio" id="txt_xmfl" name="sfsndclzxm" class="" disabled checked/>否
							</div>
						</div>
				    </div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
								<span class="input-group-addon">上传附件</span>
	                            <div style="height:25px;">
	                            	&nbsp;&nbsp;&nbsp;
	                            	<a href="">这是一个附件</a>
	                            </div>
						</div>
					</div>
				</div>
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
							<span class="input-group-addon">预计目标效益</span>
                            <textarea style="width:100%;height:100px;" name="yjmbxy" readonly class="form-control input-radius"></textarea>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
							<span class="input-group-addon">项目建设主要内容</span>
                            <textarea style="width:100%;height:100px;" name="xmjszynr" readonly class="form-control input-radius"></textarea>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
							<span class="input-group-addon">项目主要预算构成</span>
                            <textarea style="width:100%;height:100px;" name="xmzyysgc" readonly class="form-control input-radius"></textarea>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
							<span class="input-group-addon">项目实施主要措施</span>
                            <textarea style="width:100%;height:100px;" name="xmsszycs" readonly class="form-control input-radius"></textarea>
					</div>
				</div>
			</div>
                 <div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">初审意见</span>
							<textarea style="width:100%;height:100px;" readonly name="ccsy" class="form-control input-radius">通过</textarea>
						</div>
                    </div>
                 </div>
			</div>
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>采购目录明细信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<div class="container-fluid box-content" id="group_panel">
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
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
				            <td>机械采购</td>
				            <td>汽车</td>
				            <td>辆</td>
				            <td>1</td>
				            <td style="text-align:right;">100000.00</td>
				            <td style="text-align:right;">100000.00</td>
				            <td>是</td>
				            <td>备注</td>
				        </tr>
				    </tbody>
				</table>
			</div>
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>长期项目指标信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<div class="container-fluid box-content" id="group_panel">
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th>一级指标</th>
				            <th>二级指标</th>
				            <th>指标内容</th>
				            <th>指标值</th>
				            <th>备注</th>
				        </tr>
					</thead>
				    <tbody>
				    	 <tr>
				            <td>优秀</td>
				            <td>优秀</td>
				            <td>哈哈</td>
				            <td style="text-align:right;">100</td>
				            <td>备注啊</td>
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
	var nextBool = false;
	//保存按钮
	$("#btn_save").click(function(){
		alert("操作成功");
		nextBool = true;
	});
	//返回按钮
	$("#btn_back").click(function(){
		//parent.location.reload(true);
		window.location.href = "${ctx}/xmsb/xmsb/goXmsbPage?type=first";
	});
	//下一步
	$("#btn_next").click(function(){
		if(nextBool){
			parent.window.location.href = "${ctx}/xmsb/xmsb/after";
		}else{
			alert("请先保存该页面数据！");
		}
	});
	//删除
	$("#btn_del").click(function(){
		confirm("确定删除该项目下维护的采购明细？","{title:提示信息}",function(){
			alert("删除成功！");
		});
	});
});
$(function(){
	//项目分类弹窗 
	$("#btn_xmfl").click(function(){
		select_commonWin("${ctx}/xmsb/xmsb/window?controlId=txt_xmfl","项目分类信息","920","630");
    });
	//专业弹窗 
	$("#btn_fwzy").click(function(){
		select_commonWin("${ctx}/xmsb/xmsb/window?controlId=txt_fwzy","专业信息","920","630");
    });
});
</script>
</body>
</html>