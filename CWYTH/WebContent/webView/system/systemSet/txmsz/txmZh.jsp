<%@page import="com.googosoft.service.xtsz.txmsz.TxmService"%>
<%@page import="com.googosoft.util.Validate"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>条形码设置</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body class="sm_edit_body">
<div class="top" style="border: 5px;height:100%;background-color: white;">
	<form name="form1" method="post" action="" id="form1">
		<input type="hidden" name="DESIGNNO" id="DESIGNNO" value="${DESIGNNO }"/>
		<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="" />
		<input type="hidden" name="__EVENTVALIDATION" id="__EVENTVALIDATION" value="" />
		<div id="d1" style="position: absolute; cursor: n-resize; z-index: 6; height: 2px;" onmousedown="onDown()"></div>
		<div id="d2" style="position: absolute; cursor: e-resize; z-index: 6;" onmousedown="onDown1()"></div>
		<div id="txt_page" style="border: #5994E6 1px solid; width: 66mm; height: 44mm;position:relative; margin-left: 2.5mm; margin-top: 1mm;">
			<div id="txt_Tyqbh" align="center" class="display"
				style="left: 15px; width: 210px; height: 40px; position: absolute; background-color: #ccffff; display: none; top: 137px; cursor: move;"
				onmousedown="javascript:moveStart(event,'txt_Tyqbh');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">[20120922]</span></div>
			<div id="txt_Lyqbh" class="display"
				style="height: 20px; width: 85px; position: absolute; top: 17px; left: 47px; cursor: move; z-index: 5; background: #99cc66; display: none"
				onmousedown="javascript:moveStart(event,'txt_Lyqbh');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">资产编号</span></div>
			<div id="txt_Byqbh" class="display"
				style="position: absolute; height: 20px; width: 85px; background-color: #ccffff; top: 17px; left: 135px; cursor: move; display: none"
				onmousedown="javascript:moveStart(event,'txt_Byqbh');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">[资产编号]</span></div>
			<div id="txt_Lyqmc" class="display"
				style="left: 47px; width: 85px; background: #99cc66; z-index: 5; height: 20px; position: absolute; top: 40px; display: ; cursor: move;display: none"
				onmousedown="javascript:moveStart(event,'txt_Lyqmc');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">资产名称</span></div>
			<div id="txt_Byqmc" class="display"
				style="left: 135px; width: 85px; height: 20px; position: absolute; background-color: #ccffff; display: ; top: 40px; cursor: move;display: none"
				onmousedown="javascript:moveStart(event,'txt_Byqmc');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">[资产名称]</span></div>
			<div id="txt_Lflh" class="display"
				style="height: 20px; width: 85px; position: absolute; top: 63px; left: 47px; cursor: move; z-index: 5; background: #99cc66; display: none"
				onmousedown="javascript:moveStart(event,'txt_Lflh');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">分类号</span></div>
			<div id="txt_Bflh" class="display"
				style="position: absolute; height: 20px; width: 85px; background-color: #ccffff; top: 63px; left: 135px; cursor: move; display: none"
				onmousedown="javascript:moveStart(event,'txt_Bflh');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">[分类号]</span></div>
			<div id="txt_Lflmc" class="display"
				style="height: 20px; width: 85px; position: absolute; top: 86px; left: 47px; cursor: move; z-index: 5; background: #99cc66; display: none"
				onmousedown="javascript:moveStart(event,'txt_Lflmc');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">分类名称</span></div>
			<div id="txt_Bflmc" class="display"
				style="position: absolute; height: 20px; width: 85px; background-color: #ccffff; top: 86px; left: 135px; cursor: move; display: none"
				onmousedown="javascript:moveStart(event,'txt_Bflmc');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">[分类名称]</span></div>
			<div id="txt_Ldj" class="display"
				style="height: 20px; width: 85px; position: absolute; top: 109px; left: 47px; cursor: move; z-index: 5; background: #99cc66; display: none"
				onmousedown="javascript:moveStart(event,'txt_Ldj');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">单价</span></div>
			<div id="txt_Bdj" class="display"
				style="position: absolute; height: 20px; width: 85px; background-color: #ccffff; top: 109px; left: 135px; cursor: move; display: none"
				onmousedown="javascript:moveStart(event,'txt_Bdj');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">[单价]</span></div>
			<div id="txt_Lzj" class="display"
				style="height: 20px; width: 85px; position: absolute; top: 132px; left: 47px; cursor: move; z-index: 5; background: #99cc66; display: none"
				onmousedown="javascript:moveStart(event,'txt_Lzj');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">总价</span></div>
			<div id="txt_Bzj" class="display"
				style="position: absolute; height: 20px; width: 85px; background-color: #ccffff; top: 132px; left: 135px; cursor: move; display: none"
				onmousedown="javascript:moveStart(event,'txt_Bzj');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">[总价]</span></div>
			<div id="txt_Lsydw" class="display"
				style="height: 20px; width: 85px; position: absolute; top: 155px; left: 46px; cursor: move; z-index: 5; background: #99cc66; display: none"
				onmousedown="javascript:moveStart(event,'txt_Lsydw');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">使用单位</span></div>
			<div id="txt_Bsydw" class="display"
				style="left: 135px; width: 85px; height: 20px; position: absolute; background-color: #ccffff; display:none ; top: 155px; cursor: move;"
				onmousedown="javascript:moveStart(event,'txt_Bsydw');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">[使用单位]</span></div>
			<div id="txt_Lsyr" class="display"
				style="height: 20px; width: 85px; position: absolute; top: 178px; left: 46px; cursor: move; z-index: 5; background: #99cc66; display: none"
				onmousedown="javascript:moveStart(event,'txt_Lsyr');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">使用人</span></div>
			<div id="txt_Bsyr" class="display"
				style="left: 135px; width: 85px; height: 20px; position: absolute; background-color: #ccffff; display: none; top: 178px; cursor: move;"
				onmousedown="javascript:moveStart(event,'txt_Bsyr');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">[使用人]</span></div>
			<div id="txt_Lgzrq" class="display"
				style="height: 20px; width: 85px; position: absolute; top: 201px; left: 46px; cursor: move; z-index: 5; background: #99cc66; display: none"
				onmousedown="javascript:moveStart(event,'txt_Lgzrq');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">购置日期</span></div>
			<div id="txt_Bgzrq" class="display"
				style="position: absolute; height: 20px; width: 85px; background-color: #ccffff; top: 201px; left: 135px; cursor: move; display: none"
				onmousedown="javascript:moveStart(event,'txt_Bgzrq');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">[购置日期]</span></div>
			<div id="txt_Ljfkm" class="display"
				style="height: 20px; width: 85px; position: absolute; top: 224px; left: 46px; cursor: move; z-index: 5; background: #99cc66; display: none"
				onmousedown="javascript:moveStart(event,'txt_Ljfkm');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">经费来源</span></div>
			<div id="txt_Bjfkm" class="display"
				style="position: absolute; height: 20px; width: 85px; background-color: #ccffff; top: 224px; left: 135px; cursor: move; display: none"
				onmousedown="javascript:moveStart(event,'txt_Bjfkm');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">[经费来源]</span></div>
			<div id="txt_Ljsh" class="display"
				style="height: 20px; width: 85px; position: absolute; top: 247px; left: 46px; cursor: move; z-index: 5; background: #99cc66; display: none"
				onmousedown="javascript:moveStart(event,'txt_Ljsh');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">机身号</span></div>
			<div id="txt_Bjsh" class="display"
				style="position: absolute; height: 20px; width: 85px; background-color: #ccffff; top: 247px; left: 135px; cursor: move; display: none"
				onmousedown="javascript:moveStart(event,'txt_Bjsh');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">[机身号]</span></div>
			<div id="txt_Lcch"  class="display"
				style="left: 47px; width: 85px; background: #99cc66; z-index: 5; height: 20px; position: absolute; top: 270px; display:none ; cursor: move;"
				onmousedown="javascript:moveStart(event,'txt_Lcch');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">出厂编号</span></div>
			<div id="txt_Bcch" class="display"
				style="left: 135px; width: 85px; height: 20px; position: absolute; background-color: #ccffff; display:none ; top: 270px; cursor: move;"
				onmousedown="javascript:moveStart(event,'txt_Bcch');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">[出厂号]</span></div>
			<div id="txt_Lgg" class="display"
				style="height: 20px; width: 85px; position: absolute; top: 293px; left: 46px; cursor: move; z-index: 5; background: #99cc66; display: none"
				onmousedown="javascript:moveStart(event,'txt_Lgg');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">规格</span></div>
			<div id="txt_Bgg" class="display"
				style="position: absolute; height: 20px; width: 85px; background-color: #ccffff; top: 293px; left: 135px; cursor: move; display: none"
				onmousedown="javascript:moveStart(event,'txt_Bgg');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">[规格]</span></div>
			<div id="txt_Lxh" class="display"
				style="left: 47px; width: 85px; background: #99cc66; z-index: 5; height: 20px; position: absolute; top: 316px; display:none ; cursor: move;"
				onmousedown="javascript:moveStart(event,'txt_Lxh');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">型号</span></div>
			<div id="txt_Bxh" class="display"
				style="left: 135px; width: 85px; height: 20px; position: absolute; background-color: #ccffff; display:none ; top: 316px; cursor: move;"
				onmousedown="javascript:moveStart(event,'txt_Bxh');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">[型号]</span></div>
			<div id="txt_Lt" align="center" class="display"
				style="left: 45px; width: 165px; background: #99cc66; z-index: 5; height: 20px; position: absolute; top: 1px; display: none; cursor: move;"
				onmousedown="javascript:moveStart(event,'txt_Lt');"><span style="font-size:16px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">${xxmc}</span></div>
			<div id="txt_Lcfdd" class="display"
				style="height: 20px; width: 85px; position: absolute; top: 339px; left: 46px; cursor: move; z-index: 5; background: #99cc66; display: none"
				onmousedown="javascript:moveStart(event,'txt_Lcfdd');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">存放地点</span></div>
			<div id="txt_Bcfdd" class="display"
				style="left: 135px; width: 85px; height: 20px; position: absolute; background-color: #ccffff; display: none; top: 339px; cursor: move;"
				onmousedown="javascript:moveStart(event,'txt_Bcfdd');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">[存放地点]</span></div>
			<div id="txt_Lpzh" class="display"
				style="height: 20px; width: 85px; position: absolute; top: 362px; left: 46px; cursor: move; z-index: 5; background: #99cc66; display: none"
				onmousedown="javascript:moveStart(event,'txt_Lpzh');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">凭证号</span></div>
			<div id="txt_Bpzh" class="display"
				style="position: absolute; height: 20px; width: 85px; background-color: #ccffff; top: 362px; left: 135px; cursor: move; display: none"
				onmousedown="javascript:moveStart(event,'txt_Bpzh');"><span style="font-size:12px;font-family:NSimSun;white-space:nowrap;letter-spacing:0;">[凭证号]</span></div>
		</div>
		<input name="txt_tmmc" type="text" id="txt_tmmc" style="display: none"
			value="311" /> <input type="submit" name="Button1" value="Button"
			id="Button1" style="display: none" />
	</form>
	<hr/>
	</div>
	<%@include file="/static/include/public-manager-js.inc"%>
	<div class="buttom" style="margin-top:20px;">
	<jsp:include page="txmRight.jsp"></jsp:include>
	</div>
	<script language="javascript" type="text/javascript">
	$(function() {
		parent.pagesize();
		<c:forEach var="list" items="${list}"> 
			$("#txt_${list.cloumn}").css({width:'${list.cwidth}'});
			$("#txt_${list.cloumn}").css({height:'${list.cheigh}'});
			$("#txt_${list.cloumn}").css({left:'${list.cxpos}'});
			$("#txt_${list.cloumn}").css({top:'${list.cypos}'});
			$("#txt_${list.cloumn}").html('${list.columnname}');			
			$("#txt_${list.cloumn}").css({display:""});
			$("#txt_${list.cloumn}").css("display","block");
			$("#txt_${list.cloumn}").attr({class:"none"});
		</c:forEach>
		var type= "${type}";
		if(type=="1"){
			$("#txt_Tyqbh").css("background-color","");
			$("#txt_Tyqbh").css({top:'100px;'});
			$("#txt_Tyqbh").html("<img src=\"${pageContext.request.contextPath}/webView/system/systemSet/txmsz/tongjiAnalyse/images/rwm.png\" style=\"width:100%\"/>");
		}else{
			$("#txt_Tyqbh").css("background-color","#ccffff");
			$("#txt_Tyqbh").html("<SPAN style=\"FONT-SIZE: 32px; FONT-FAMILY: C39HrP36DlTt; WHITE-SPACE: nowrap; LETTER-SPACING: 1px\">[20120922]</SPAN>");
		}
	});
	
    function Get(_sId){
       return document.getElementById(_sId);
    }
      var oObj=""; 
      function moveStart (event, _sId){
          oObj =Get(_sId);
          if(oObj.id.indexOf("B",0)>0){
        	  $("#txt_mc").attr("disabled","disabled");
          }else if(oObj.id.indexOf("T",0)>0){
        	  $("#txt_mc").attr("disabled","disabled");
          }else{
        	  $("#txt_mc").attr("disabled","");
          }
          $("#txt_mc").val(oObj.innerText);
          $("#txt_X").val(oObj.style.left);
          $("#txt_Y").val(oObj.style.top);
          $("#txt_W").val(oObj.style.width);
          $("#txt_H").val(oObj.style.height);
          var T=oObj.innerHTML;
          try{
            //针对IE6和IE8不同样式格式进行修改
             if(T.indexOf("size:",0)>0||T.indexOf("SIZE:",0)>0){
                var zh="";
                if(T.indexOf("SIZE:",0)>0){
                	zh=T.substring(T.indexOf("SIZE:",0)+6);
                }else{
                	zh=T.substring(T.indexOf("size:",0)+5);
                }
                if(zh.indexOf(";",0)>0){
                    zh=zh.substr(0,zh.indexOf(";",0));
                }else if(zh.indexOf("\"",0)>0){
                   zh=zh.substring(0,zh.indexOf("\"",0));
                }
                 document.getElementById("txt_zh").value=zh;
              }else{
                document.getElementById("txt_zh").value="";
              }
          }catch(err){
              document.getElementById("txt_zh").value="";
          }
          
          try{
               if(T.indexOf("LETTER-SPACING",0)>0||T.indexOf("letter-spacing",0)>0){
                  var zjj="";
                  if(T.indexOf("LETTER-SPACING:",0)>0){
                  	zjj=T.substring(T.indexOf("LETTER-SPACING:",0)+16);
                  }else{
                  	zjj=T.substring(T.indexOf("letter-spacing:",0)+15);
                  }
                  if(zjj.indexOf(";",0)>0){
                      zjj=zjj.substr(0,zjj.indexOf(";",0));
                  }else if(zjj.indexOf("\"",0)>0){
                     zjj=zjj.substring(0,zjj.indexOf("\"",0));
                  }
                   document.getElementById("txt_zjj").value=zjj;
                }else{
                  document.getElementById("txt_zjj").value="";
                }
                
            }catch(err){
                document.getElementById("txt_zjj").value="";
            }
         
          try{
        	  var zt="";
        	  if(T.indexOf("FONT-FAMILY:",0)>0){
        		  zt=T.substring(T.indexOf("FONT-FAMILY:",0)+13);
              }else{
            	  zt=T.substring(T.indexOf("font-family:",0)+12);
              }
              if(zt.indexOf(";",0)>0){
                  zt=zt.substr(0,zt.indexOf(";",0));
              }else{
                  zt=zt.substr(0,zt.indexOf("\"",0));
              }
              document.getElementById("drp_zt").selectedIndex=0;
              for(var i=0;i<document.getElementById("drp_zt").options.length;i++){
                if(document.getElementById("drp_zt").options[i].value==zt){
                    document.getElementById("drp_zt").selectedIndex=i;
                }
              }
         }catch(err){
            document.getElementById("drp_zt").selectedIndex=0;
         }
         oObj.onmousemove = mousemove;
         oObj.onmouseup = mouseup;          
          oEvent = window.event ? window.event : event;
          var dragData = {x : oEvent.clientX, y : oEvent.clientY};
          var backData = {x : parseInt(oObj.style.top), y : parseInt(oObj.style.left)};
          function mousemove(){
          var oEvent = window.event ? window.event : event;
          var iLeft = oEvent.clientX - dragData["x"] + parseInt(oObj.style.left);
          var iTop = oEvent.clientY - dragData["y"] + parseInt(oObj.style.top);
          $(oObj).css("left",iLeft);
          $(oObj).css("top",iTop);
          setDivFlex(); 
          dragData = {x: oEvent.clientX, y: oEvent.clientY};   
          oObj.title="坐标："+oObj.style.top+"*"+oObj.style.left;
          document.getElementById("txt_Y").value=iTop+"px";
          document.getElementById("txt_X").value=iLeft+"px";
     	}
        function mouseup(){
           var oEvent = window.event ? window.event : event;
           oObj.onmousemove = null;
           oObj.onmouseup = null;
           if(oEvent.clientX < 1 || oEvent.clientY < 1 || oEvent.clientX > document.body.clientWidth || oEvent.clientY > document.body.clientHeight){
             oObj.style.left = backData.y;
             oObj.style.top = backData.x;
           }
        }
     }
   
     function keyup(event){
           if(oObj!=""){
                if(event.keyCode=="46")
                {
                    oObj.style.display="none";
                }
                if(event.keyCode=="37")//左
                {
                    oObj.style.left=parseInt(oObj.style.left)-1;
                }
                if(event.keyCode=="38")//上
                {
                    oObj.style.top=parseInt(oObj.style.top)-1;
                }
                if(event.keyCode=="39")//右
                {
                    oObj.style.left=parseInt(oObj.style.left)+1;
                }
                if(event.keyCode=="40")//下
                {
                    oObj.style.top=parseInt(oObj.style.top)+1;
                }
                setDivFlex();
                
                oObj.title="坐标："+oObj.style.top+"*"+oObj.style.left;
                document.getElementById("txt_Y").value=oObj.style.top;
                document.getElementById("txt_X").value=oObj.style.left;
           }
     }
 
//拖动调整大小 
var oldwidth;
var oldheight;
function onDown(){
    document.getElementById("d1").setAttribute("onmousemove", function(){startResize()}); 
    document.getElementById("d1").setAttribute("onmouseup", function(){endResize()});
    oldheight=event.clientY; 
}
 
function startResize(){
   var DList = oObj;
   var nX = event.clientX; 
   var nY = event.clientY; 
   try{
        DList.style.height =parseInt(nY)-parseInt(DList.style.top)+"px";
    }catch(err){
        DList.style.height="0px";
    }
    document.getElementById("d2").style.height=DList.style.height;
    
    var resizeDiv=document.getElementById("d1");
    resizeDiv.style.top=parseInt(oObj.style.top)+parseInt(oObj.offsetHeight)+"px";
    oObj.title="坐标："+oObj.style.top+"*"+oObj.style.left+"\n长宽："+oObj.offsetWidth+"*"+oObj.offsetHeight;
      
    document.getElementById("txt_W").value=oObj.offsetWidth;
    document.getElementById("txt_H").value=oObj.offsetHeight;
} 
 
//调整大小结束 
function endResize(){ 
   document.getElementById("d1").onmousemove=function(){}; 
} 
 
//d2
var oldwidth1;
var oldheight1;
function onDown1()
{
    document.getElementById("d2").setAttribute("onmousemove", function(){startResize1()}); 
    document.getElementById("d2").setAttribute("onmouseup", function(){endResize1()});
    oldheight1=event.clientX; 
}
 
function startResize1() 
{ 
   
   var DList1 = oObj;
   var nX1 = event.clientX; 
   var nY1 = event.clientY; 
   try{
     DList1.style.width =parseInt(nX1)-parseInt(DList1.style.left)+"px";
   }catch (err){
     DList1.style.width ="0px";
   }
   document.getElementById("d1").style.width=DList1.style.width;
   var resizeDiv1=document.getElementById("d2");
   resizeDiv1.style.left=parseInt(oObj.style.left)+parseInt(oObj.style.width)+"px";
   oObj.title="坐标："+oObj.style.top+"*"+oObj.style.left+"\n长宽："+oObj.offsetWidth+"*"+oObj.offsetHeight;
   document.getElementById("txt_W").value=oObj.offsetWidth;
   document.getElementById("txt_H").value=oObj.offsetHeight;
} 
    //调整大小结束 
    function endResize1(){ 
       document.getElementById("d2").onmousemove=function(){}; 
    } 
 
    function cX(){
        var nX=document.getElementById("txt_X").value;
        if(nX=="")
        {
            return;
        }
        try{
          oObj.style.left=nX;
        }catch(err){
         oObj.style.left="0px";
        }
        
        setDivFlex();
    }
    function cY(){
        var nY=document.getElementById("txt_Y").value;
        if(nY=="")
        {
            return;
        }
        try{
         oObj.style.top=nY;
        }catch(err){
          oObj.style.top="0px";
        }
        setDivFlex();
    }
 
    function cW(){
        var nW=document.getElementById("txt_W").value;        
        if(nW==""){
            return;
        }
        if(nW==""){
            nW="100px";
        }
        
        try{
        oObj.style.width=nW;
        }catch(err){
        oObj.style.width="0px";
        }
       setDivFlex();  
    }
    function cH(){
        var nH=document.getElementById("txt_H").value;
        if(nH==""){
            return;
        }
        if(nH==""){
            nH="30px";
        }
           try{
            oObj.style.height=nH;
           }catch(err){
            oObj.style.height="0px";
           }
       setDivFlex();
    }
 
function mc(zt){
   	t=zt;
    h=document.getElementById("txt_zh").value;
    zjj=document.getElementById("txt_zjj").value;
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
 
function setDivFlex(){
      //用来移动纵坐标 同控件宽度 捕获层高时
      document.getElementById("d1").style.width=oObj.offsetWidth;//宽度
      document.getElementById("d1").style.height="2px";
      document.getElementById("d1").style.left=oObj.style.left;
      document.getElementById("d1").style.top=(parseInt(oObj.style.top)+parseInt(oObj.offsetHeight)-1)+"px";
      //用来移动横坐标 
      document.getElementById("d2").style.width="4px";
      document.getElementById("d2").style.height=oObj.offsetHeight;
      document.getElementById("d2").style.left=(parseInt(oObj.style.left)+parseInt(oObj.offsetWidth)-2)+"px";
      document.getElementById("d2").style.top=oObj.style.top; 
}
    
function goToright(){    
	$("#txt_page div").each(function(i){
	        if($(this)[0].id!='d1'&&$(this)[0].id!='d2'){
		        var left=$(this).offset().left;
		        if(left<200)
			        $(this).animate({left:"+=1"});
			}
		});
}
function goToleft(){
	$('#txt_page div').each(function(i){
	        if($(this)[0].id!='d1'&&$(this)[0].id!='d2'){
		        var left=$(this).offset().left;
		        if(left>1)
			        $(this).animate({left:"-=1"});
			}
		});
}
function goTobottom(){
	$('#txt_page div').each(function(i){
	        if($(this)[0].id!='d1'&&$(this)[0].id!='d2'){
			    var top=$(this).offset().top;
			    if(top>1)
				    $(this).animate({top:"+=1"});
		    }
			});
}
function goTotop(){
	$('#txt_page div').each(function(i){
	        if($(this)[0].id!='d1'&&$(this)[0].id!='d2'){
			    var top=$(this).offset().top;
			    if(top>1)
				    $(this).animate({top:"-=1"});
		     }
	});
}
    </script>

</body>
</html>