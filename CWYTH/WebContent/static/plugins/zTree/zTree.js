/**
 * zTree二次封装插件
 * 此类对于zTree进行了二次封装，大量简化了zTree默认的配置内容，并添加了自定义的内容供本项目使用
 * 注：（1）目前测试能够良好匹配左侧树右侧iframe功能，没有测试单独树功能
 * 	  （2）目前此类提供两种方式，一种方式是带iframe的dom结构，第二种是仅仅提供树dom结构，通过传入的参数设置type为非iframe即可。
 * @author master
 * @version 0.1
 * @since 20161229
 */
;(function($){
	//树默认的参数
	var _treeDemoSetting = {
		type:"iframe",//树类型
		templateClass:{
			left:"left",//左侧树容易class
			right:"right",//右侧iframe的class
			iframeUrl:"",//右侧iframe的默认url
			iframeName:"zTreeIframeDemo",//右侧iframe的默认id和name
			container:"container-fluid"},//默认容器class
		title:{
			logoIcon:"icon-shenhe3",
			name:"默认树",
			closeIcon:"icon-zhankai",
			openIcon:"icon-shouqi2"},
//		search:{
//			placeholder:"请输入查询的内容",
//			searchInput:"searchInput"},
		tree:{
			id:"treeDemo",
			url:"",
			target:"zTreeIframeDemo"},
		async:{
			enable: true,
			url:"/GXGDZC7/tree/getDwJson",
			autoParam:["id", "name", "level"],
			type: "get"},
		view:{showLine:false},
		callback:{onClick:_expand}
	};
	var _zTreeObj = null;//缓存zTree对象
	$.extend({
		initZtree:_initZtree,//初始化ztree
		resizeDiv:_resizeFun,//边框可改变大小方法
	});
	
	//初始化树结构
	function _initZtree(treeDemoSetting){
		$.extend(true,_treeDemoSetting,treeDemoSetting);
		_initTemplate(_treeDemoSetting);//初始化模板
		_resizeFun("domleft","domright");//设置模板能够通过鼠标改变大小_treeDemoSetting.templateClass.right
		$.fn.zTree.init($("#"+_treeDemoSetting.tree.id), _treeDemoSetting);//初始化zTree
		_zTreeObj = $.fn.zTree.getZTreeObj(_treeDemoSetting.tree.id);//将初始化zTree对象缓存起来
		//_searchInput(_treeDemoSetting.search.searchInput);//为搜索框添加查询事件
		_closeTree(_treeDemoSetting)//添加树缩进事件
	}
	//dom模板
	var templateLeft = "<div class='{left} domleft'></div><i class='fa {openIcon} open'></i>";//左侧容器
	
	var templateRight = "<div class='{right} domright'><iframe frameborder='0' width='100%' height='100%' " +
	"scrolling='auto' src='{iframeUrl}' id='{iframeName}' name='{iframeName}'></iframe></div>";//右侧容器
	
	var templateTree = "<div class='treeTop'><div class='TreeTitle'>"+
	"<i class='fa {logoIcon} logo'></i>{name}<i class='fa closemenu {closeIcon}'></i></div>"+
	//"<div class='searchArea'><input type='text' name='search' value='' class='{searchInput}' placeholder='{placeholder}'><i class='fa icon-sousou sousou'></i></div>"+
	"</div><div class='treediv'><ul id='{treeId}' class='ztree'></ul></div>";//树容器
	
	//初始化dom模板
	function _initTemplate(treeDemoSetting){
		var containerClass = treeDemoSetting.templateClass.container;
		templateLeft = templateLeft.replace(/{left}/g,treeDemoSetting.templateClass.left);
		templateLeft = templateLeft.replace(/{openIcon}/g,treeDemoSetting.title.openIcon);
		$("."+containerClass).append(templateLeft);
		templateTree = templateTree.replace(/{logoIcon}/g,treeDemoSetting.title.logoIcon);
		templateTree = templateTree.replace(/{name}/g,treeDemoSetting.title.name);
		templateTree = templateTree.replace(/{closeIcon}/g,treeDemoSetting.title.closeIcon);
		//templateTree = templateTree.replace(/{placeholder}/g,treeDemoSetting.search.placeholder);
		templateTree = templateTree.replace(/{treeId}/g,treeDemoSetting.tree.id);
		//templateTree = templateTree.replace(/{searchInput}/g,treeDemoSetting.search.searchInput);
		$("."+treeDemoSetting.templateClass.left).append(templateTree);
		if(treeDemoSetting.type=="iframe"){//如果为默认，则是带有右侧iframe的模式，如果为其他，则为单独的树
			templateRight = templateRight.replace(/{right}/g,treeDemoSetting.templateClass.right);
			templateRight = templateRight.replace(/{iframeUrl}/g,treeDemoSetting.templateClass.iframeUrl);
			templateRight = templateRight.replace(/{iframeName}/g,treeDemoSetting.templateClass.iframeName);
		}
		$("."+containerClass).append(templateRight);
		
		$(".treediv").css({"height":($(".domleft").height() - $(".treeTop").height()) + "px","width":$(".domleft").width() + "px"});
	}
	//设置树节点点击展开
	function _expand(event,treeId,treeNode,clickFlag){
		if (treeNode.isParent) {
			_zTreeObj.expandNode(treeNode);
		}
		$("#"+_treeDemoSetting.templateClass.iframeName).attr("src",treeNode.href);	
	}
	//设置查询功能
	function _searchInput(className){
		$("."+className).keyup(function(){
			var val = $(this).val();
			if(val!=""&&val!=undefined){
				_treeDemoSetting.async.otherParam = {"searchInput":$("."+className).val()};
				$.fn.zTree.init($("#"+_treeDemoSetting.tree.id),_treeDemoSetting);
			}else{
				_treeDemoSetting.async.otherParam = {"searchInput":""};
				$.fn.zTree.init($("#"+_treeDemoSetting.tree.id),_treeDemoSetting);
			}
		});
	}
	//缩进树功能
	function _closeTree(treeDemoSetting){
		var openClass = _treeDemoSetting.title.openIcon;
		var closeClass = _treeDemoSetting.title.closeIcon;
		var top = treeDemoSetting.templateClass.left;
		$("."+closeClass).bind("click",function(){
			var left = $(this).parents("."+top);
			left.animate({
				left:-left.width()
			});
			$(".domright").animate({
				left:12,
				width:$(".container-fluid").width()-12
			});
			$("."+openClass).show("slow");
			
			$(".treediv").hide();
			//$("input[name='search']").hide();
		});
		$("."+openClass).on("click",function(){
			$(this).hide();
			var left = $(this).parent().find("."+top);
			left.animate({
				left:0
			});
			var leftWidth = $(".domleft").width();
			$(".domright").animate({
				"left":leftWidth,
				"width":$(".container-fluid").width()-leftWidth
			});

			//$("input[name='search']").css("width",(leftWidth-8) + "px");//这个8是$(".domleft")这个div有左右两边各4px的border

			$(".treediv").show();
			//$("input[name='search']").show();
		});
	}
	//设置div边框可改变大小
	function _resizeFun(classNameleft,classNameright){
		var divBorderleft = classNameleft;
		var divBorderright = classNameright;
		var theobject = null;  //This gets a value as soon as a resize start
		function resizeObject() {
			this.el    = null; //pointer to the object
			this.dir   = "";   //type of current resize (n, s, e, w, ne, nw, se, sw)
			this.grabx = null; //Some useful values
			this.graby = null;
			this.width = null;
			this.height = null;
			this.left = null;
			this.top = null;
		}
		//Find out what kind of resize! Return a string inlcluding the directions
		//获取移动的方向
		function getDirection(el) {
			var xPos, yPos, offset, dir;
			dir = "";
			xPos = window.event.offsetX;
			yPos = window.event.offsetY;
			offset = 3; //The distance from the edge in pixels
			if (yPos<offset) 
				dir += "n";//north 上边
			else if (yPos > el.offsetHeight-offset) 
				dir += "s"; //south 下边
			if (xPos<offset) 
				dir += "w";//右边
			else if (xPos > el.offsetWidth-offset)
				dir += "e";//左边
			return dir;
		}

		function doDown() {
			var el = getReal(event.srcElement, "className", divBorderleft);
			if (el == null) {
				theobject = null;
				return;
			}
			dir = getDirection(el);
			if (dir == "") return;
			theobject = new resizeObject();
			theobject.el = el;
			theobject.dir = dir;
			theobject.grabx = window.event.clientX;
			theobject.graby = window.event.clientY;
			theobject.width = el.offsetWidth;
			theobject.height = el.offsetHeight;
			theobject.left = el.offsetLeft;
			theobject.top = el.offsetTop;
			window.event.returnValue = false;
			window.event.cancelBubble = true;
		}
		function doUp() {
			if (theobject != null) {
				theobject = null;
			}
		}
		function doMove() {
			var el, xPos, yPos, str, xMin, yMin;
			xMin = 8; //The smallest width possible
			yMin = 8; //height

			el = getReal(event.srcElement, "className", divBorderleft);

			if (el.className.indexOf(divBorderleft)>0) {
				str = getDirection(el);
				if (str == "") str = "default";
				else str += "-resize";
				el.style.cursor = str;
			}
			
			//Dragging starts here
			if(theobject != null) {
				if (dir.indexOf("e") != -1){
					theobject.el.style.width = Math.max(xMin, theobject.width + window.event.clientX - theobject.grabx) + "px";
					$(".domright").css("left",theobject.el.style.width);
					$(".domright").css("width",$(".domright").width($(".container-fluid").width()-theobject.el.style.width));
					$(".domright").css("width",$(".container-fluid").width()-$(".domleft").width());
				}
				if (dir.indexOf("s") != -1){
					theobject.el.style.height = Math.max(yMin, theobject.height + window.event.clientY - theobject.graby) + "px";
				}
				if (dir.indexOf("w") != -1) {
					theobject.el.style.left = Math.min(theobject.left + window.event.clientX - theobject.grabx, theobject.left + theobject.width - xMin) + "px";
					theobject.el.style.width = Math.max(xMin, theobject.width - window.event.clientX + theobject.grabx) + "px";
				}
				if (dir.indexOf("n") != -1) {
					theobject.el.style.top = Math.min(theobject.top + window.event.clientY - theobject.graby, theobject.top + theobject.height - yMin) + "px";
					theobject.el.style.height = Math.max(yMin, theobject.height - window.event.clientY + theobject.graby) + "px";
				}
				///$(".treediv").css("width",$("input[name='search']").width() + "px");
				window.event.returnValue = false;
				window.event.cancelBubble = true;
			}
		}

		function getReal(el, type, value) {
			temp = el;
			while ((temp != null) && (temp.tagName != "BODY")) {
				if (eval("temp." + type) == value) {
					el = temp;
					return el;
				}
				temp = temp.parentElement;
			}
			return el;
		}

		document.onmousedown = doDown;
		document.onmouseup   = doUp;
		document.onmousemove = doMove;

	}
	
})(jQuery);
window.onresize = function(){
	$(".treediv").css({"height":($(".domleft").height() - $(".treeTop").height()) + "px","width":$(".domleft").width() + "px"});
}
