<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>条形码设置</title>
<style type="text/css">
table td{
	border: 0px;
	border-bottom: 0px;
	padding:5px 0px;
}
/* .btn{ */
/* 	width: 70px;  */
/* 	height: 32px; */
/* } */
/* .btn-default { */
/*   background-color: #00acec; */
/*   color: white; */
/*   } */
</style>
</head>
<body>
	<input type="hidden" id="DESIGNNO" value="${DESIGNNO }">
	<div>
		<table style="width: 100%">
			<tr>
				<td align="center"></td>
			</tr>
			<tr>
				<td align="center">
					<table>
						<tr>
							<td>X轴：</td>
							<td style="text-align: left">
								<input id="txt_X" style="width: 60px" type="text" onblur="X()" />
							</td>
							<td>Y轴：</td>
							<td>
								<input id="txt_Y" style="width: 60px" type="text" onblur="Y()" />
							</td>
							<td>宽度：</td>
							<td>
								<input id="txt_W" style="width: 60px" type="text" onblur="W()" />
							</td>
							<td>高度：</td>
							<td>
								<input id="txt_H" style="width: 60px" type="text" onblur="H()" />
							</td>
							<td>间距：</td>
							<td>
								<input id="txt_zjj" style="width: 60px" type="text" onblur="zjj()" />
							</td>
						</tr>
						<tr>
							<td>名称：</td>
							<td colspan="3">
								<input id="txt_mc" style="width: 185px" type="text" onblur="mc()" />
							</td>
							<td>字体：</td>
							<td style="text-align: left" colspan="3">
                            	<select name="drp_zt" id="drp_zt" onchange="setZt()">
                            		<c:forEach var="fontName" items="${fontName}">
                            			<option value="${fontName }">${fontName }</option>
                            		</c:forEach>
								</select>
							</td>
							<td style="text-align: left">字号：</td>
							<td style="text-align: left">
								<input id="txt_zh" style="width: 59px" type="text" onblur="zh()" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="center">&nbsp; &nbsp; &nbsp; &nbsp; <br /> 
					<button class="btn btn-default" type="button" onclick="goTotop();">
						整体上移
					</button>
					<button class="btn btn-default" type="button" onclick="goTobottom();">
						整体下移
					</button>
					<button class="btn btn-default" type="button" onclick="goToleft();">
						整体左移
					</button>
					<button class="btn btn-default" type="button" onclick="goToright();">
						整体右移
					</button>
					<button class="btn btn-default" type="button" onclick="saveTxm();">
						保存
					</button>
					<button class="btn btn-default" type="button" id="btn_del">
						删除
					</button>
				</td>
			</tr>
		</table>
	</div>
	<script language="javascript" type="text/javascript">
		function X() {
			cX();
		}
		function Y() {
			cY();
		}
		function W() {
			cW();
		}
		function H() {
			cH();
		}
		function mc() {
			var zt = $("#drp_zt").val();
			mc(zt);
		}
		function setZt() {
			var zt = $("#drp_zt").val();
			var h = $("#txt_zh").val();
		    var zjj = $("#txt_zjj").val();
		    if(h!=""){
		        if(zjj!=""){
		            oObj.innerHTML="<span style='font-size:"+h+";font-family:"+zt+";white-space:nowrap;letter-spacing:"+zjj+";'>"+document.getElementById("txt_mc").value+"</span>";
		        }else{
		            oObj.innerHTML="<span style='font-size:"+h+";font-family:"+zt+";white-space:nowrap;letter-spacing:1px;'>"+document.getElementById("txt_mc").value+"</span>";
		        }
		    }else{
		        if(zjj!=""){
		            oObj.innerHTML="<span style='font-size:16px;font-family:"+zt+";white-space:nowrap;letter-spacing:"+zjj+";'>"+document.getElementById("txt_mc").value+"</span>";
		        }else{
		            oObj.innerHTML="<span style='font-size:16px;font-family:"+zt+";white-space:nowrap;letter-spacing:1px;'>"+document.getElementById("txt_mc").value+"</span>";
		        }
		    } 
		}
		
		function zh() {			
			var zt = $("#drp_zt").val();
			var h = $("#txt_zh").val();
		    var zjj = $("#txt_zjj").val();
		    if(h!=""){
		        if(zjj!=""){
		            oObj.innerHTML="<span style='font-size:"+h+";font-family:"+zt+";white-space:nowrap;letter-spacing:"+zjj+";'>"+document.getElementById("txt_mc").value+"</span>";
		        }else{
		            oObj.innerHTML="<span style='font-size:"+h+";font-family:"+zt+";white-space:nowrap;letter-spacing:1px;'>"+document.getElementById("txt_mc").value+"</span>";
		        }
		    }else{
		        if(zjj!=""){
		            oObj.innerHTML="<span style='font-size:16px;font-family:"+zt+";white-space:nowrap;letter-spacing:"+zjj+";'>"+document.getElementById("txt_mc").value+"</span>";
		        }else{
		            oObj.innerHTML="<span style='font-size:16px;font-family:"+zt+";white-space:nowrap;letter-spacing:1px;'>"+document.getElementById("txt_mc").value+"</span>";
		        }
		    }
		}
		
		function saveTxm() {
			var printName = parent.$("#txt_printName").val();
			var tmmc = parent.$("#txt_tmmc").val();
			if (tmmc == "") {
				//parent.ui.alert("请输入条码名称！");
				alert("请输入条码名称！");
				return false;
			}
			var dno = parent.$("#drp_tmmb").val();//模板的编号
			var dtext = parent.$("#drp_tmmb").find("option:selected").text();//模板的名称
			var isdef = parent.$("#chk_isdef").val();//是否默认
			//var isdef = (parent.$("#chk_isdef").filter(":checked").length ? "1" : "0");
			var w = parent.$("#txt_tmW").val();
			var h = parent.$("#txt_tmH").val();
			var hsl = parent.$("#txt_Hsl").val();
			var ssl = parent.$("#txt_Ssl").val();
			var Hjj = parent.$("#txt_Hjj").val();
			var Ljj = parent.$("#txt_Ljj").val();
			var bmfs = parent.$("#sel_bmfs").val();//编码方式 
			var tmlb = parent.$("#drp_type").val();//条码类别
			var xblyz =  parent.$("#sel_xblyz").val();//比例因子窄 
			var yblyz =  parent.$("#sel_yblyz").val();//比例因子宽 
			var dyjfbl=parent.$("#txt_sorientation").val();//打印机分辨率
			var scfx=parent.$("#drp_scfx").val();//输出方向
			
			var tmproperty = [];
			tmproperty.push(printName);
			tmproperty.push(tmmc);
			tmproperty.push(w);
			tmproperty.push(h);
			tmproperty.push(hsl);
			tmproperty.push(ssl);
			tmproperty.push(Hjj);
			tmproperty.push(Ljj);
			tmproperty.push(bmfs);//tmpropertys[8]
			tmproperty.push(tmlb);
			tmproperty.push(xblyz);
			tmproperty.push(yblyz);
			tmproperty.push(dno);
			tmproperty.push(dtext);
			tmproperty.push(isdef);
			tmproperty.push(dyjfbl);//打印机分辨率[15]
			tmproperty.push(scfx);//输出方向[16]
			
			
			
			var $none=$("#txt_page").find(".none");
			var columnnames = [];//<span>
			var memos=[];//名称
			var cloumns=[];//id
			var cxposs=[];//X轴
			var cyposs=[];//Y轴
			var cwidths=[];//宽
			var cheighs=[];//高
			var fonts=[];//字体
			var fontsizes=[];//字体大小
			$none.each(function(){
				$this = $(this);
				columnnames.push($this.html().replace(/\%/g,'%25'));
				memos.push($this.children().html());
				cloumns.push($this.attr("id").substring(4));
				cxposs.push($this.css("left"));
				cyposs.push($this.css("top"));
				cwidths.push($this.css("width"));
				cheighs.push($this.css("height"));
				fonts.push($this.children().css("font-family").replace(/\'/g,"").replace(/"/g,""));
				fontsizes.push($this.children().css("font-size"));
			});
			var tmdesign=[];
			tmdesign.push(columnnames);
			tmdesign.push(memos);
			tmdesign.push(cloumns);
			tmdesign.push(cxposs);
			tmdesign.push(cyposs);
			tmdesign.push(cwidths);
			tmdesign.push(cheighs);
			tmdesign.push(fonts);
			tmdesign.push(fontsizes);
			  $.ajax({
				type:"post",
				data:"TMDESIGN="+tmdesign.join("%26")+"&TMPROPERTY="+tmproperty.join("%26"),
				url:"${pageContext.request.contextPath}/txm/doSave",
				dataType:"json",
				success:function(val){
					close(index);
					if(val!=null){
						if(dtext != tmmc){
							parent.$("#drp_tmmb").append("<option value='"+val.designno+"' selected='selected'>"+tmmc+"</option>");
							
						}
						if(val.designno!=''){
							$("#DESIGNNO").val(val.designno);
						}
						alert(val.msg);
					}
				},
				error:function(){
					close(index);
					alert("抱歉，系统出现问题！");
				},
				beforeSend:function(){
					index = loading(2);
				}
			});  
		}

		function Yl() {
			parent.Frameset2.rows = "0,*,45%";
			parent.V.scT();
			$("input[id!='ibtn_sj']").attr("disabled", "disabled");
		}
		function Sj() {
			parent.Frameset2.rows = "*,0,45%";
		}
		$(function(){
			$("#btn_del").click(function() {
				var tmmc = parent.$("#txt_tmmc").val();
				var tmbh = $("#DESIGNNO").val();
				//console.log(tmbh);
				 if(parent.$("#drp_tmmb").find("option").length ==1){
					alert("最后一条方案不允许删除!");
				}else{ 
					confirm("确定删除方案【" + tmmc + "】?",{title:"提示"},function(){
						$.ajax({
							type:"post",
							data:"DESIGNNO="+tmbh,
							url:"${pageContext.request.contextPath}/txm/doDel",
							dataType:"json",
							success:function(val){
								close(index);
								if(val!=null&&val.success){
									alert(val.msg);
									window.parent.location.reload();
								}else{
									alert(val.msg);
								}
							},
							error:function(){
								close(index);
								alert("抱歉，系统出现问题！");
							},
							beforeSend:function(){
								index = loading(2);
							}
						});
					});
				 } 
			});
		});
		
		function zjj() {
			zh();
		}
		function goTotop() {
			parent.goTotop();
		}
		function goTobottom() {
			parent.goTobottom();
		}
		function goToright() {
			parent.goToright();
		}
		function goToleft() {
			parent.goToleft();
		}
	</script>
</body>
</html>