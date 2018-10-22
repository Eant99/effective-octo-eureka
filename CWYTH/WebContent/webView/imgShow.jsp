<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width,Sinitial-scale=1.0" />
<title>图片预览</title>
<style type="text/css">
	html,body,.pp_pic_holder{
		width:100%;
		height:100%;
		margin:0px auto;
		padding:0px;
		border:0px;
		overflow:hidden;
	}
	div{
		margin:0px auto;
		padding:0px;
		border:0px;
	}
	a,p,span{
		display:inline-block;
		*float:left;
		border:none;
	}
	a img{
		width:100%;
		height:100%;
		border:none;
	}
	#fullResImage{
		margin:0px auto;
		padding:0px;
		border:0px;
	}
	.pp_content_container{
		overflow:auto;
		text-align: center;
	}
	.pp_bottom{
		width:100%;
		height:60px;
		line-height:40px;
		position:relative;
		/*display:none;*/
		display:inline-block;
	}
	.pp_bottom_left{
		position:absolute;
		left:10px;
		top:10px;
	}
	.pp_bottom_right{
		position:absolute;
		right:10px;
		top:10px;
	}

	.pp_bottom_left a {
		width: 30px;
		height: 30px;
		margin-left:4px;
		margin-right:4px;
	}
	.pp_bottom_left a:hover {
		width: 34px;
		height: 34px;
		margin-left:0px;
		margin-right:0px;
	}
	
	.currentTextHolder{
		
	}
	
	.hide{
		display:none;
	}
	.show{
		display:inline-block;
	}
</style>
</head>
<body>
	<div class="pp_pic_holder">
		<div class="pp_content_container">
			<img id="fullResImage" src="" alt="" title=""  style="margin-top:25px;width:900px;"/>
		</div>
		<div class="pp_bottom">
			<div class="pp_bottom_left">
				<a href="javascript:void(0)" class="pp_close" title="关闭"><img src="${pageContext.request.contextPath}/static/images/button/page_gb.png"/></a>
				<p class="currentTextHolder"></p>
				<a href="javascript:void(0)" class="pp_arrow_one hide" title="第一张"><img src="${pageContext.request.contextPath}/static/images/button/page_sy.png"/></a>
				<a href="javascript:void(0)" class="pp_arrow_previous hide" title="上一张"><img src="${pageContext.request.contextPath}/static/images/button/page_pre.png"/></a>
				<a href="javascript:void(0)" class="pp_arrow_lar" title="点击或按住鼠标放大图片，鼠标抬起或离开按钮停止放大图片"><img src="${pageContext.request.contextPath}/static/images/button/page_fd.png"/></a>
				<a href="javascript:void(0)" class="pp_arrow_nar" title="点击或按住鼠标缩小图片，鼠标抬起或离开按钮停止缩小图片"><img src="${pageContext.request.contextPath}/static/images/button/page_sx.png"/></a>
				<a href="javascript:void(0)" class="rotate" title="点击旋转图片"><img src="${pageContext.request.contextPath}/static/images/button/page_rotate.jpg"/></a>
	            <a href="javascript:void(0)" class="pp_arrow_next hide" title="下一张"><img src="${pageContext.request.contextPath}/static/images/button/page_next.png"/></a>
	            <a href="javascript:void(0)" class="pp_arrow_last hide" title="最后一张"><img src="${pageContext.request.contextPath}/static/images/button/page_my.png"/></a>
            </div>
            <div class="pp_bottom_right">
            	<span style="height:40px;line-height:40px;">当前第<span id="s_dq"></span>张/共<span id="s_zs"></span>张</span>
            </div>
		</div>
	</div>
<script src="${pageContext.request.contextPath}/static/javascript/jquery/jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/javascript/public/public-json.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/javascript/jquery/jquery.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/javascript/jquery/jquery.rotate.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		var bl = 1;//缩放比例，在显示照片的时候，会把当前图片的缩放比例计算出来
		var set_position = 0;
		var dqurl = "<%=request.getParameter("dqurl")%>";
		var imglist = "<%=request.getParameter("imglist")%>";
		var json = JSON.getJson(imglist);
		$("#s_zs").prop("innerHTML",json.length);
		if(json.length > 1){
			$(".pp_arrow_next,.pp_arrow_last").removeClass("hide");
		}
		for(var i = 0; i < json.length; i++){
			if(dqurl == json[i].url){
				set_position = i;
			}
		}
		
		var cnt = json.length - 1;//最后一个索引
		var changePage = function(direction) {
			$("#fullResImage").css({"width":"900px","height":""});
			if (direction == 'previous') {
				set_position--;
				if (set_position < 0) {
					set_position = 0;
					return;
				};
			} else if (direction == 'next') {
				set_position++;
				if (set_position > cnt) {
					set_position = cnt;
				}
			} else {
				set_position = direction;
			};
			
			$("#s_dq").prop("innerHTML",set_position + 1);

			var imgurl = json[set_position].url;
			if(imgurl.substr(0,4) == "data"){//图片还没有上传
				
			}
			else{
				//imgurl = "http://" + window.location.host + imgurl;
				imgurl = ""+imgurl;
			}
			$("#fullResImage").attr("src",imgurl);
			$("#fullResImage").attr("alt",json[set_position].name);
			$("#fullResImage").attr("title",json[set_position].name);
			
			var image = new Image();   
	    	image.src = json[set_position].url;  
			bl = image.width / image.height;
			bl = (bl == 0 ?  1 : bl);
			
			$(".pp_arrow_one,.pp_arrow_previous,.pp_arrow_next,.pp_arrow_last").removeClass("hide");
			if(set_position == 0){
				$(".pp_arrow_one,.pp_arrow_previous").addClass("hide");
			}
			if(set_position == cnt){
				$(".pp_arrow_next,.pp_arrow_last").addClass("hide");
			}
		};
		
		$(".pp_arrow_one").click(function(){
			changePage(0);
		});
		$(".pp_arrow_previous").click(function(){
			changePage("previous");
		});
		
		$(".pp_arrow_last").click(function(){
			changePage(cnt);
		});
		$(".pp_arrow_next").click(function(){
			changePage("next");
		});
		
		$(".pp_close").click(function(){
			window.close();
		});
		
		var zxsj = 1000;//定时器执行间隔，以毫秒为单位
		var sl = 20;//每次缩放的像素数
		var fddsq = null,sxdsq = null;
		$(".pp_arrow_lar").mousedown(function(){//放大
			if(fddsq){
				clearInterval(fddsq);
				fddsq = null;
			}
			fddsq = setInterval(function(){
				var fdwidth = $("#fullResImage").width() + sl;
				var fdheight = fdwidth/bl;
				$("#fullResImage").css({"width":fdwidth + "px","height":fdheight + "px"});
			}, zxsj);
		});
		var num = 0; 
		$(".rotate").click(function(){ 
			num ++; 
			$("#fullResImage").rotate({angle:90*num}); 
		}); 
// 		$(".rotate").click(function(){//click事件,每点击一次,顺时针旋转45°.
// 	        //图片旋转,通过$.animate()方法
// 	        $(this).animate({
// 	            rotate:"+=90deg"  //为rotate属性赋值,注意：deg为角度单位
// 	        },'slow');
// 	    });
		$(".pp_arrow_lar").click(function(){
			var fdwidth = $("#fullResImage").width() + sl;
			var fdheight = fdwidth/bl;
			$("#fullResImage").css({"width":fdwidth + "px","height":fdheight + "px"});
		});
		$(".pp_arrow_lar").mouseup(function(){//放大
			if(fddsq){
				clearInterval(fddsq);
				fddsq = null;
			}
		});
		$(".pp_arrow_lar").hover(function(){
			if(fddsq){
				clearInterval(fddsq);
				fddsq = null;
			}
		},function(){
			if(fddsq){
				clearInterval(fddsq);
				fddsq = null;
			}
		});
		
		$(".pp_arrow_nar").mousedown(function(){//缩小
			if(sxdsq){
				clearInterval(sxdsq);
				sxdsq = null;
			}
			sxdsq = setInterval(function(){
				var sxwidth = $("#fullResImage").width() - sl;
				var sxheight = sxwidth/bl;
				if(sxheight <= 0){
					sxheight = 0;
				}
				if(sxwidth <= 0){
					sxwidth = 0;
				}
				if(sxheight == 0 || sxwidth == 0){
					clearInterval(sxdsq);
					sxdsq = null;
				}
				else{
					$("#fullResImage").css({"width":sxwidth + "px","height":sxheight + "px"});
				}
			}, zxsj);  
		});
		$(".pp_arrow_nar").click(function(){
			var sxwidth = $("#fullResImage").width() - sl;
			var sxheight = sxwidth/bl;
			if(sxheight <= 0){
				sxheight = 0;
			}
			if(sxwidth <= 0){
				sxwidth = 0;
			}
			if(sxheight == 0 || sxwidth == 0){
				clearInterval(sxdsq);
				sxdsq = null;
			}
			else{
				$("#fullResImage").css({"width":sxwidth + "px","height":sxheight + "px"});
			}
		});
		$(".pp_arrow_nar").mouseup(function(){//缩小
			if(sxdsq){
				clearInterval(sxdsq);
				sxdsq = null;
			}
		});
		$(".pp_arrow_nar").hover(function(){
			if(sxdsq){
				clearInterval(sxdsq);
				sxdsq = null;
			}
		},function(){
			if(sxdsq){
				clearInterval(sxdsq);
				sxdsq = null;
			}
		});
		
		changePage(set_position);
		
		changeDivHeight();
		window.onresize = function(){  
            changeDivHeight();
		}
	});
	var changeDivHeight = function(){
		$(".pp_content_container").css({"width":$("body").width() + "px","height":($("body").height()-$(".pp_bottom").height()) + "px"});
	}
</script>
</body>
</html>