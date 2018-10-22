<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="com.googosoft.util.Validate"%>
<%@page import="com.googosoft.util.Const"%>
<%@page import="org.apache.shiro.subject.Subject"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>条形码设置</title>
<%@include file="/static/include/public-manager-css.inc"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/plugins/ext/ext-all.css"/>
<style type="text/css">
table{
	margin:20px 0px;
}
.left {
	float: left;
	width: 330px;
	height: 100%;
	border-right: 1px solid #efefef;
	overflow:auto;
}

.right {
	float: left;
	width: 70%;
	height: 100%;
}
a {
	text-decoration: none;
}

td {
	height:20px;
	border-color: #ddd;
	padding-top:5px;
}
</style>
</head>
<body class="sm_edit_body">
	<div id="d_left" class="left">
		<table style="margin-left: 1mm; margin-top: 1mm; width: 300px; border-color: #ddd" border='1px'>
			<tr align="center">
				<td style="width: 50px; height: 20px;">行</td>
				<td style="width: 110px; height: 20px;">列名</td>
				<td style="width: 110px; height: 20px;">标签</td>
			</tr>
			<tr>
				<td style="height: 20px;">&nbsp;1</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Lyqbh')">资产编号</a>
				</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Byqbh')">资产编号</a>
				</td>
			</tr>
			<tr>
				<td style="height: 20px;">&nbsp;2</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Lyqmc')">资产名称</a>
				</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Byqmc')">资产名称</a>
				</td>
			</tr>
			<tr>
				<td style="height: 20px;">&nbsp;3</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Lflh')">分类号</a>
				</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Bflh')">分类号</a>
				</td>
			</tr>
			<tr>
				<td style="height: 20px;">&nbsp;4</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Lflmc')">分类名称</a>
				</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Bflmc')">分类名称</a>
				</td>
			</tr>
			<tr>
				<td style="height: 20px;">&nbsp;5</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Ldj')">单价</a>
				</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Bdj')">单价</a>
				</td>
			</tr>
			<tr>
				<td style="height: 20px;">&nbsp;6</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Lzj')">总价</a>
				</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Bzj')">总价</a>
				</td>
			</tr>
			<tr>
				<td style="height: 20px;">&nbsp;7</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Lsydw')">使用单位</a>
				</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Bsydw')">使用单位</a>
				</td>
			</tr>
			<tr>
				<td style="height: 20px;">&nbsp;8</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Lsyr')">使用人</a>
				</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Bsyr')">使用人</a>
				</td>
			</tr>
			<tr>
				<td style="height: 20px;">&nbsp;9</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Lgzrq')">购置日期</a>
				</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Bgzrq')">购置日期</a>
				</td>
			</tr>
			<tr>
				<td style="height: 20px;">&nbsp;10</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Ljfkm')">经费来源</a>
				</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Bjfkm')">经费来源</a>
				</td>
			</tr>
			<tr>
				<td style="height: 20px;">&nbsp;11</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Ljsh')">机身号</a>
				</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Bjsh')">机身号</a>
				</td>
			</tr>
			<tr>
				<td style="height: 20px;">&nbsp;12</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Lcch')">出厂编号</a>
				</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Bcch')">出厂编号</a>
				</td>
			</tr>
			<tr>
				<td style="height: 20px;">&nbsp;13</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Lgg')">规格</a>
				</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Bgg')">规格</a>
				</td>
			</tr>
			<tr>
				<td style="height: 20px;">&nbsp;14</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Lxh')">型号</a>
				</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Bxh')">型号</a>
				</td>
			</tr>
			<tr>
				<td style="height: 20px;">&nbsp;15</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Lcfdd')">存放地点</a>
				</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Bcfdd')">存放地点</a>
				</td>
			</tr>
			<tr>
				<td style="height: 20px;">&nbsp;17</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Lpzh')">凭证号</a>
					&nbsp;</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Bpzh')">凭证号</a>
				</td>
			</tr>
			<tr>
				<td style="height: 20px;">&nbsp;16</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" ondblclick="dbclk('Lt')">标题</a>
					&nbsp;</td>
				<td>&nbsp;&nbsp;<a href="javascript:void(0);" id="a_type" style="color: Red"
					ondblclick="dbclk('Tyqbh')">条形码</a>
				</td>
			</tr>
		</table>
		<table style="width: 300px; margin-left: 1mm; margin-top: 5px; border-color: #6666FF">
			<tr>
				<td colspan="4" style="height: 15px">
					<strong><span style="font-size: 14px; color: red">标签参数</span></strong>
				</td>
			</tr>
			<tr>
				<td align="right">条码模板：</td>
				<td colspan="3">
					<select name="drp_tmmb" onchange="changeMb()" id="drp_tmmb" style="width:100%;">
						<c:forEach var="txmlist" items="${txmlist}">
							<option value="${txmlist.designno}" <c:if test="${map.designno == txmlist.designno}">selected='selected'</c:if>>${txmlist.designname}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right">条码名称：</td>
				<td colspan="3">
					<input name="txt_tmmc" type="text" id="txt_tmmc" style="width:100%;" value="${map.designname}" />
				</td>
			</tr>
			<tr>
				<td align="right">是否默认模板：</td>
				<td colspan="3" >
					<select name="chk_isdef" id="chk_isdef" style="width:100%;">
						<c:choose>
							<c:when test="${map.isselect=='1' }">
								<option value="0">否</option>
								<option selected="selected" value="1">是</option>
							</c:when>
							<c:otherwise>
								<option selected="selected" value="0">否</option>
								<option value="1">是</option>
							</c:otherwise>
						</c:choose>
					</select>
				</td>
				
				
			</tr>
			<tr>
				<td align="right">打印机名：</td>
				<td colspan="3">
					<input name="txt_printName" type="text" id="txt_printName" style="width:100%;" value="${map.TABLETITLE }" />
				</td>
			</tr>
			<tr>
				<td align="right">打印机分辨率：</td>
				<td>
					<select name="txt_sorientation" id="txt_sorientation" style="width: 75px">
						<c:choose>
							<c:when test="${map.sorientation=='12' }">
								<option value="8">200DPI</option>
								<option selected="selected" value="12">300DPI</option>
							</c:when>
							<c:otherwise>
								<option selected="selected" value="8">200DPI</option>
								<option value="12">300DPI</option>
							</c:otherwise>
						</c:choose>
					</select>
				</td>
				<td align="right" style="width:65px;">输出方向：</td>
				<td>
					<select name="drp_scfx" id="drp_scfx" style="width:75px;">
						<c:choose>
							<c:when test="${map.titlex=='1' }">
								<option value="0">纵向</option>
								<option selected="selected" value="1">纵向180</option>
							</c:when>
							<c:otherwise>
								<option selected="selected" value="0">纵向</option>
								<option value="1">纵向180</option>
							</c:otherwise>
						</c:choose>
					</select>
				</td>
			</tr> 
			<tr>
				<td align="right">条码类别：</td>
				<td colspan="3">
					<select name="drp_type" id="drp_type" style="width:100%;">
						<c:choose>
							<c:when test="${map.tablename=='1' }">
								<option value="0">条形码</option>
								<option selected="selected" value="1">二维码</option>
							</c:when>
							<c:otherwise>
								<option selected="selected" value="0">条形码</option>
								<option value="1">二维码</option>
							</c:otherwise>
						</c:choose>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right">编码方式：</td>
				<td colspan="3" >
					<select name="sel_bmfs" id="sel_bmfs" style="width: 100%">
						<c:choose>
							<c:when test="${map.papersize=='128' }">
								<option value="39">39码</option>
								<option selected="selected" value="128">128码</option>
							</c:when>
							<c:otherwise>
								<option selected="selected" value="39">39码</option>
								<option value="128">128码</option>
							</c:otherwise>
						</c:choose>
					</select>
				</td>
			</tr> 
			
			
				<tr>
				<td align="right">比例因子窄：</td>
				<td>
				<input type="hidden" name="ppppppppppppppppp" value="${map.mxpos }">
				<select name="sel_xblyz" id="sel_xblyz" style="width: 75px">
						
						<c:forEach var="Xlist" items="${Xlist}">
							<option value="${Xlist.blyz}" <c:if test="${map.mxpos == Xlist.blyz}">selected='selected'</c:if>>${Xlist.blyz}</option>
						</c:forEach>
					</select>
				</td>
				<td align="right" style="width:65px;">宽：</td>
				<td>
					<select name="sel_yblyz" id="sel_yblyz" style="width:75px;">
						<c:forEach var="Ylist" items="${Ylist}">
							<option value="${Ylist.blyz}" <c:if test="${map.mypos == Ylist.blyz}">selected='selected'</c:if>>${Ylist.blyz}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right">条码宽度：</td>
				<td>
					<input name="txt_tmW" type="text" id="txt_tmW" style="width: 50px; text-align: right;" class="Decimal" onblur="pagesize()" value="${map.LABELWIDTH}" />mm
				</td>
				<td align="right">条码高度：</td>
				<td>
					<input name="txt_tmH" type="text" id="txt_tmH" style="width: 50px; text-align: right;" class="Decimal" onblur="pagesize()" value="${map.labelheight}" />mm
				</td>
			</tr>
			<tr>
				<td align="right">行 数 量：</td>
				<td style="width: 20px">
					<input name="txt_Hsl" type="text" id="txt_Hsl" style="width: 50px; text-align: right;" class="Int" value="${map.labelrows}" />
				</td>
				<td align="right">列 数 量：</td>
				<td>
					<input name="txt_Ssl" type="text" id="txt_Ssl" style="width: 50px; text-align: right;" class="Int" value="${map.labelacross}" />
				</td>
			</tr>
			<tr>
				<td align="right">行 间 距：</td>
				<td>
					<input name="txt_Hjj" type="text" id="txt_Hjj" style="width: 50px; text-align: right;" class="Decimal" value="${map.betweenrow}" />mm
				</td>
				<td align="right">列 间 距：</td>
				<td>
					<input name="txt_Ljj" type="text" id="txt_Ljj" style="width: 50px; text-align: right;" class="Decimal" value="${map.BETWEENCOLUMN}" />mm
				</td>
			</tr>
		</table>
	</div>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/ext/ext-base.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/ext/ext-all.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/ext/ext-lang-zh_CN.js"></script>
	<script type="text/javascript">
		$(function() {
			var name = "${map.tablename}";
			$("#drp_type").val(name);
			var _pageUrl="${pageContext.request.contextPath}/txm/getRight?type="+ $("#drp_type").val() +"&DESIGNNO="+$("[name=drp_tmmb]").val();
			new Ext.Viewport({
		        layout: "border",
		        items: [{
		            region: "west",
		            title:"",
		            border: false,
		            width:330,
		            autoScroll:true,
		            applyTo: "d_left"
		        },{
		            region: "center",
		            title:"",
		            autoScroll:true,
		            html: "<iframe frameborder='0' width='100%' height='100%' scrolling='hidden' src='" + _pageUrl + "' id='txmZh' name='txmZh'></iframe>",
		            border: false
		        }]
		    });
// 			$(".x-panel-body").css("background-color","#f3f3f3");
			$("#d_left .x-panel-body").hide();
		   $("#drp_type").bind("change",function(){
				var lb = $(this).find("option:selected").text();
			   $("#a_type").text(lb);
		        $("#txmZh").attr("src","${pageContext.request.contextPath}/txm/getRight?type="+ $("#drp_type").val() +"&DESIGNNO="+$("[name=drp_tmmb]").val());
		   });
		   $("#drp_tmmb").bind("change",function(){
		   	
		   });
		   $(".Decimal").each(function() {
	            $(this).bind("keypress", function() {
	                if (/[^0-9.]/.test(String.fromCharCode(event.keyCode)))
	                    event.keyCode = 0;
	            });
	            $(this).bind("dragenter", function() {
	                return false;
	            });
	            $(this).bind("paste", function() {
	                return false;
	            });
	        });
		    $(".Int").each(function() {
		        $(this).bind("keypress", function() {
		            if (/[^0-9]/.test(String.fromCharCode(event.keyCode)))
		                event.keyCode = 0;
		        });
		        $(this).bind("dragenter", function() {
		            return false;
		        });
		        $(this).bind("paste", function() {
		            return false;
		        });
		    });
		});

		   function dbclk(id){
		    	var obj=$('#txmZh').contents();
		        if($(obj).find("#txt_"+String(id)).css("display")=="block"){
		           $(obj).find("#txt_"+String(id)).hide();
		          // $(obj).find("#txt_"+String(id)).attr({class:"display"});
		           $(obj).find("#txt_"+String(id)).addClass("display");
		           $(obj).find("#txt_"+String(id)).removeClass("none");
		           $(obj).find("#txt_"+String(id)).css("display")=="none";
		        }else{
		           // $(obj).find("#txt_"+String(id)).attr({class:"none"});
		            $(obj).find("#txt_"+String(id)).addClass("none");
					$(obj).find("#txt_"+String(id)).css("display")=="block";
		            $(obj).find("#txt_"+String(id)).show();
		        }
		    }
		    
		    //设置页面大小(用mm计量)
		    function pagesize(){
		        var w=$("#txt_tmW").val();
		        var h=$("#txt_tmH").val();
		        if(w==""){
		            w="66";
		        }
		        if(parseFloat(w)<=0){
		            w="66";
		        }		       
		        if(h==""){
		            h="44";
		        }
		        if(parseFloat(h)<=0){
		            h="44";
		        }
		        var obj= $('#txmZh').contents();
		        $(obj).find("#txt_page").css("width",w+"mm");
		        $(obj).find("#txt_page").css("height",h+"mm");
		    }
		    var PageSet=function(){
		        if($("#drp_type").val()==0){
		            $("#a_type").text("条形码");
		        }else{
		            $("#a_type").text("二维码");
		        }
		    };
		    function changeMb(){
		    	$.ajax({
					type:"post",
					data:"DESIGNNO="+$("#drp_tmmb").val(),
					url:"${pageContext.request.contextPath}/txm/getObjectByid",
					dataType:"json",
					success:function(val){
						close(index);
						if(val!=null){
							$("#txt_tmW").val(val.LABELWIDTH);
							$("#txt_tmH").val(val.LABELHEIGHT);
							$("#txt_Ssl").val(val.LABELACROSS);
							$("#txt_Hsl").val(val.LABELROWS);
							$("#txt_Ljj").val(val.BETWEENCOLUMN);
							$("#txt_Hjj").val(val.BETWEENROW);
							$("#txt_tmmc").val(val.DESIGNNAME);
							$("#drp_type").val(val.TABLENAME);
							$("#txt_printName").val(val.TABLETITLE);//打印机名
							$("#sel_bmfs").val(val.PAPERSIZE);
							$("#chk_isdef").val(val.ISSELECT);
							$("#sel_xblyz").val(val.MXPOS);
							$("#sel_yblyz").val(val.MYPOS);
							$("#txt_sorientation").val(val.SORIENTATION);
							$("#drp_scfx").val(val.TITLEX);
							//$("#chk_isdef").attr("checked",(val.ISSELECT == "1"));
							//alert($("#chk_isdef").attr("checked") + ":" + (f[8] == "1"));
							$("#txmZh").attr("src","${pageContext.request.contextPath}/txm/getRight?type="+ $("#drp_type").val() +"&DESIGNNO="+$("[name=drp_tmmb]").val());
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
		    }
	</script>
</body>
</html>