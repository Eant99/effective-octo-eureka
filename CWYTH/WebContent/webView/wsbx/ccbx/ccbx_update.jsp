<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>差旅报销</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.addBtn{
		width:13mm!important;
		height:5mm!important;
		background-image: url("${ctx}/webView/wsbx/rcbx/add.gif");
	}
	.deleteBtn{
		width:13mm!important;
		height:5mm!important;
		background-image: url("${ctx}/webView/wsbx/rcbx/del.gif");
	}
	.add{
		width:13mm!important;
		height:5mm!important;
		background-image: url("${ctx}/webView/wsbx/rcbx/add.gif");
	}
	.delete{
		width:13mm!important;
		height:5mm!important;
		background-image: url("${ctx}/webView/wsbx/rcbx/del.gif");
	}
	.btn_td{
		width:14mm!important;
		height:6mm!important;
	}
	.selWindow{
		width:280px!important;
	}
	
	.number,.sz{
		text-align:right;
	}
	.bm_input{
		width:400px!important;
		border:none;
	}
	#tbody input{
		border:none;
		width:80px;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div class='page-header'>
        <div class="pull-left">
         	  编辑差旅报销
		</div>
        <div class="pull-right">
			   <button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			   <button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
    </div>
	<div class="box">
		<div class="box-panel">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>日常差旅报销
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
							<span class="input-group-addon">报&ensp;销&ensp;人</span>
                            <input type="text" id="txt_bxr" class="form-control input-radius window" name="bxr" value="超级管理员">
							<span class="input-group-btn"><button type="button" id="btn_bxr" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">部&emsp;&emsp;门</span>
							<input type="text" id="txt_bmmc" class="form-control input-radius window" name="bmmc" value="教务处"/>
							<span class="input-group-btn"><button type="button" id="btn_bmmc" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">保存日期</span>
							<input type="text" id="txt_czrq" class="form-control input-radius date" name="czrq" 
							value="${sysDate}" data-format="yyyy-MM-dd" readonly/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">票据张数</span>
							<input type="text" id="txt_pjzzs" class="form-control input-radius sz" name="pjzzs" value="0"
							onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">冲借款金额</span>
							<input type="text" id="txt_cjkje" class="form-control input-radius number sz" name="cjkje" value="100.00"
							readonly/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">还款金额</span>
							<input type="text" id="txt_hkje" class="form-control input-radius number sz" name="hkje" value="100.00"
							onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">领款金额</span>
							<input type="text" id="txt_lkje" class="form-control input-radius number sz" name="lkje" value="100.00"
							readonly onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">合计金额</span>
							<input type="text" id="txt_hjje" class="form-control input-radius number sz" name="hjje" value="100.00" 
							readonly onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">支付方式</span>
                            <select id="drp_zffs" class="form-control input-radius" name="zffs">
	                                <c:forEach var="zffsList" items="${zffsList}"> 
	                                   <option value="${zffsList.DM}">${zffsList.MC}</option>
									</c:forEach>
	                        </select>
						</div>
					</div>
			  </div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">收&ensp;款&ensp;人</span>
							<input type="text" id="txt_skr" class="form-control input-radius" name="skr" value="超级管理员"/>
						</div>
					</div>				
					
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">银行卡账号</span>
							<input type="text" id="txt_yhzh" class="form-control input-radius" name="yhzh" value="62225458522"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">提交日期</span>
							<input type="text" id="txt_tjrq" class="form-control input-radius date" name="tjrq" 
							value="${sysDate}" data-format="yyyy-MM-dd" readonly/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">开户银行</span>
							<input type="text" id="txt_khyh" class="form-control input-radius" name="khyh" value="济南分行"/>
						</div>
					</div>				
					
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">收款单位名称</span>
							<input type="text" id="txt_skdwmc" class="form-control input-radius" name="skdwmc" value="山东国子软件按"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">收款单位所在省</span>
							<input type="text" id="txt_skdwszsh" class="form-control input-radius" name="skdwszsh" value="山东省"/>
						</div>
					</div>	
				</div>	
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">收款单位所在市</span>
							<input type="text" id="txt_skdwszsi" class="form-control input-radius" name="skdwszsi" value="济南市"/>
						</div>
					</div>				
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">收款单位所在(区)县</span>
							<input type="text" id="txt_skdwszqx" class="form-control input-radius" name="skdwszqx" value="高新区"/>
						</div>
					</div>
					
				</div>	
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">出差事由</span>
							<textarea style="width:1560px;" name="ccsy" class="form-control input-radius" >招标</textarea>
						</div>
					</div>
				</div>				
			</div>
		</div>
		<div class="box-panel" id="operate">
			<div class="container-fluid box-content">
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead id="thead">
				        <tr>
				        	<th>操作</th>
				            <th>费用项目</th>
				            <th>出发时间</th>
				            <th>出发地点</th>
				            <th>到达时间</th>
				            <th>到达地点</th>
				            <th>出差天数</th>
				            <th>车船/机票费</th>
				            <th>交通工具</th>
				            <th>票据张数</th>
				            <th>住宿费</th>
				            <th>补助费</th>
				            <th>发票号码</th>
				            <th>其他费用</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    	<tr id="tr_1">
				    		<td class="btn_td">
				    			<input class="addBtn" type="button">
				    		</td>
				            <td>
				            	<input type="text" name="fyxm_1" value="出差" />
				            </td>
				            <td>
				            	<input type="text" name="cfsj_1" class="date" value="2017-10-16" />
				            </td>
				            <td>
				            	<input type="text" name="cfdd_1" value="济南" />
				            </td>
				            <td>
				            	<input type="text" name="ddsj_1" class="date" value="2017-10-18" />
				            </td>
				            <td>
				            	<input type="text" name="dddd_1" value="江苏" />
				            </td>
				            <td>
				            	<input type="text" name="ccts_1" value="2" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
				            </td>
				            <td>
				            	<input type="text" name="ccjp_1" value="20.00" />
				            </td>
				            <td>
				            	<input type="text" name="jtgj_1" value="汽车" />
				            </td>
				            <td>
				            	<input type="text" name="pjzs_1" class="integer" value="1" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
				            </td>
				            <td>
				            	<input type="text" name="zsf_1" class="num" value="50.00" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
				            </td>
				            <td>
				            	<input type="text" name="bzf_1" class="num" value="30.00" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
				            </td>
				            <td>
				            	<input type="text" name="fphm_1" value="125225222" />
				            </td>
				            <td>
				            	<input type="text" name="qtfy_1" class="num" value="0.00" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
				            </td>
				    	</tr>
				    </tbody>
				</table>
			</div>
		</div>
			<div class="box-panel" id="">
			<div class="container-fluid box-content">
				<table id="mydatatables" class="table table-striped table-bordered">
					<caption style="text-align:center;">借款列表</caption>
		        	<thead id="thead">
				        <tr>
				        	<th>借款人姓名</th>
				            <th>部门名称</th>
				            <th>合计金额</th>
				            <th>申请日期</th>
				            <th>报销日期</th>
				            <th>支付方式</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    	<tr id="tr_1">
				    		<td>超级管理员</td>
				            <td>教务处</td>
				            <td style="text-align:right;">100.00</td>
				            <td style="text-align:center;">2017-10-16</td>
				            <td style="text-align:center;">2017-10-18</td>
				            <td>转账</td>
				    	</tr>
				    </tbody>
				</table>
			</div>
		</div>
		<div class="box-panel" id="">
			<div class="container-fluid box-content">
				<table id="mydatatables" class="table table-striped table-bordered">
					<caption style="text-align:center;">借款列表</caption>
		        	<thead id="thead">
				        <tr>
				        	<th>操作</th>
				        	<th>姓名</th>
				            <th>卡号</th>
				            <th>金额</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    	<tr id="trr_1">
				    		<td class="btn_add">
				    			<input class="add" type="button">
				    		</td>
				        	<td>
				        		<input type="text" name="xm_1" class="bm_input" value="超级管理员" />
				        	</td>
				            <td>
				            	<input type="text" name="kh_1" class="bm_input" value="625525522" />
				            </td>
				            <td>
				            	<input type="text" name="je_1" class="num bm_input" value="50.00" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
				            </td>
				    	</tr>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
</form>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//返回按钮
	$("#btn_back").click(function(e){
		location.href="${ctx}/wsbx/ccbx/goCcbxListPage";
	});
	//保存按钮
	$("#btn_save").click(function(e){
		alert("保存成功");
		location.href="${ctx}/wsbx/ccbx/goCcbxListPage";
	});	
	//联想输入
	$("#txt_bxr").bindChange("${ctx}/suggest/getXx","R");//报销人
	$("#txt_bmmc").bindChange("${ctx}/suggest/getXx","D");//部门
	//弹窗
	$("#btn_bxr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_bxr","人员信息","920","630");
    });
	$("#btn_bmmc").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_bmmc","部门信息","920","630");
    });
	$("#btn_xmbh").click(function(){
		select_commonWin("${ctx}/wsbx/rcbx/window?type=xm&controlId=txt_xmbh","信息","920","630");
    });
});
//明细js操作
$(function(){
	//费用名称
	$(document).on("click","[id^=btn_fymc]",function(){
		var controlId = $(this).attr("sj");
		select_commonWin("${ctx}/wsbx/rcbx/window?type=fy&controlId="+controlId,"费用信息","920","630");
    });
	//新增按钮
	var num = 2;
	$(document).on("click","[class=addBtn]",function(){
		var $parentTr = $(this).parents("tr").clone();
		$(this).removeClass("addBtn");
		$(this).addClass("deleteBtn");
		$parentTr.find(":input").val("");
		$parentTr.attr("id","tr_"+num);
		num++;
		$(this).parents("tr").after($parentTr);
	});
	//删除按钮
	$(document).on("click","[class=deleteBtn]",function(){
		$(this).parents("tr").remove();
		//删除后重新计算金额
		//computer();
	});
	//输入还款金额
	$(document).on("keyup","[name=hkje]",function(){
		//computer();
	});
	//计算金额
	function computer(){
		var lkje = 0;
		var je = 0;
		var hjje = 0;
		//领款金额
		$.each($("[id^=txt_je]"),function(){
			var $this = $(this);
			je = $this.val();
			if(isNaN(je)||je==""){
				je = 0;
			}
			lkje = parseFloat(lkje) + parseFloat(je);
		});
		if(lkje<0){
			lkje = 0.00;
		}
		$("[name='lkje']").val(lkje.toFixed(2));
		//还款金额
		var hkje = $("[name='hkje']").val();
		if(hkje==""||isNaN(hkje)){
			hkje = 0.00;
		}
		//冲借款金额
		var jkje = $("[name='cjkje']").val();
		if(jkje==""||isNaN(jkje)){
			jkje = 0.00;
		}
		//合计金额 = 领款金额+还款金额-冲借款金额
		hjje = parseFloat(lkje)+parseFloat(hkje)-parseFloat(jkje);
		if(hjje==""||isNaN(hjje)){
			hjje = 0.00;
		}
		$("[name='hjje']").val(hjje.toFixed(2));
	}
});
$(function(){
	//新增按钮
	var num = 2;
	$(document).on("click","[class=add]",function(){
		var $parentTr = $(this).parents("tr").clone();
		$(this).removeClass("add");
		$(this).addClass("delete");
		$parentTr.find(":input").val("");
		$parentTr.attr("id","tr_"+num);
		num++;
		$(this).parents("tr").after($parentTr);
	});
	//删除按钮
	$(document).on("click","[class=delete]",function(){
		$(this).parents("tr").remove();
		//删除后重新计算金额
		//computer();
	});
});
</script>

</html>