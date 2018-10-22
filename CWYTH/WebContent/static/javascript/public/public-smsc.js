/**
 * 扫二维码上传图片
 * path:"${pageContext.request.contextPath}"
 * data:json对象，例如{"dbh":"${yqbh}","djlx":"000000","fold":"zcxx","rybh":"${rybh}","mkbh":"${mkbh}"}
 * 		其中这个mkbh是获取图片类型用的，不一定是当前模块的模块编号，rybh是当前登录人的编号
 * fileinputid:上传控件的id，只要id不要选择器标识
 * pname:调用public-window.js里的getPname()方法
 * 调用时，页面上只需要引用本js，在需要出现扫码上传的时候，初始化一下就好（调用 SmscLoad("${pageContext.request.contextPath}",{"dbh":"${yqbh}","djlx":"000000","fold":"zcxx","rybh":"${rybh}","mkbh":"${mkbh}"},"imageFile",getPname());）
 * 但需要注意，这个js除了需要fileinput以外，还需要依赖jq，layer，public-window.js，public-js.js（或public-json.js）
 */
var smscinter;
var smsclayer;
var SmscLoad = function(path,data,fileinputid,pname){
	SmscClear();
	//给页面添加触发本弹窗的按钮
	$(":file").parents("div.box-panel").find("div.box-header div.sub-title").after('<button type="button" class="btn btn-default" style="margin-left:10px;" id="btn_smsc_show">手机拍照</button>');
	//添加按钮的点击事件
	$("#btn_smsc_show").on("click",function(){
		QRCodeLoad(path,data,fileinputid,pname);
	});
};
//清除与扫码上传有关的内容
var SmscClear = function(){
	if($("#btn_smsc_show").length > 0){
		$("#btn_smsc_show").remove();
	}
};
//加载二维码的核心事件
var QRCodeLoad = function(path,data,fileinputid,pname){
	//如果定时器已经存在就先清除
	if(smscinter){
		window.clearInterval(smscinter);
	}
	
	//开始初始化弹窗
	var html = new StringBuilder();
	html.Append('<div id="div_smsc">');
		html.Append('<div id="div_smsc_qrcode" style="height:240px;padding:20px 40px 10px 40px;background-color:#ffffff;">');
			html.Append('<img id="img_smsc_qrcode" cache="false" src="' + path + '/static/images/uploadFiles/twoDimensionCode/jzx.gif" style="width:100%;height:100%;margin:0px auto;" title="正在生成二维码，请稍后..."/>');
		html.Append('</div>');
		html.Append('<div id="div_smsc_wxts" style="height:20px;line-height:20px;font-size:12px;color:Red;margin:0px;padding:0px;padding-left:20px;background-color:#ffffff;">正在生成二维码，请稍后...</div>');
	html.Append('</div>');
	
	getTopFrame().layer.open({
		type: 1,
		title:'请用手机扫描二维码上传图片',
		area : ['300px' , '320px'],
		content:html.ToString(),
		cancel:function(index, layero){
			if(smsclayer == index){
				$.ajax({
					type:"post",
					url:path + "/file/getFilesByMkbhDbh",
					data:{"dbh":data.id,"mkbh":data.mkbh},
					async:false,
					success:function(val){
						var d = JSON.getJson(val);
						getIframeControl(pname,fileinputid).fileinput('refresh',{
			                initialPreview:d.fjView,
			                initialPreviewConfig:d.fjConfig
			            });
						
						var tplxmark = getIframDoc(pname).find(".btn-mark.active");
						if(tplxmark.length > 0){
							tplxmark.click();
						}
					}
				});
			}
		},
		success:function(layero, index){
			if(smsclayer){
				getTopFrame().layer.close(smsclayer);
			}
			smsclayer = index;
			
			//生成二维码
			$.ajax({
				type:"post",
				url:path + "/phone/ImgUploadCreateQRCode",
				data:data,
				success:function(val){
					var d = JSON.getJson(val);
					if(d.success){
						$(layero).find("#img_smsc_qrcode").attr("src",d.QRCodeUrl);
						$(layero).find("#img_smsc_qrcode").attr("title","点击刷新");
						$(layero).find("#img_smsc_qrcode").css({"cursor":"pointer"});
						
						//点击二维码刷新
						$(layero).find("#img_smsc_qrcode").on("click",function(){
							if($(this).attr("title") == "点击刷新"){
								QRCodeLoad(path,data,fileinputid,pname);
							}
						});
						
						//开启定时器，控制失效时间
						var timer = parseInt(d.QRCodeTime);
						$(layero).find("#div_smsc_wxts").prop("innerHTML","温馨提示：二维码将在" + d.QRCodeTime + "分钟后失效！");
						smscinter = setInterval(function(){
							timer = timer - 1;
							if(timer > 0){
								$(layero).find("#div_smsc_wxts").prop("innerHTML","温馨提示：二维码将在" + timer + "分钟后失效！");
							}
							else{
								//当到达失效时间时，刷新二维码
								QRCodeLoad(path,data,fileinputid,pname);
							}
						},timer*60*1000);
					}else {
						alert(d.msg);
						getTopFrame().layer.close(smsclayer);
					}
				},
				error:function(val){
					alert("生成二维码失败！");
					getTopFrame().layer.close(smsclayer);
				}
			});
		}
	});
};