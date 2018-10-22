/*
*联想输入带括号的
*
*/
//原来的拓展 去掉了OtherConditions参数
//增加三个参数方法
//1.GetOtherConditions 函数参数,该函数是获取AJAX传输过程中的其他参数
//2.ActionOnPreSearch 函数参数,该函数在模糊查询以及校验之前运行 返回值bool,如果为false,则终止查询
//3.ActionOnCompleted 函数参数,在用户选取了相应的结果后执行.
jQuery.fn.bindChange=function(RequestPage,RequestMenuForList,GetOtherConditions,ActionOnPreSearch,ActionOnCompleted){
	var newiframe=$("<iframe id='iframepopup'></iframe>");
    var newObj=$("<ul id='popup'></ul>");
    
    GetOtherConditions=GetOtherConditions==undefined?null:GetOtherConditions;
    var selectedItem=null;
    //设置高亮显示的条目
    var setSelectedItem=function(item){
        selectedItem=item;
        if(selectedItem==null){
            newObj.hide();
            newiframe.hide();
            return;
        }
        if(selectedItem<0){
            selectedItem=0;
        }
        if(selectedItem>=newObj.find('li').length){
            selectedItem=newObj.find('li').length-1;
        }
        for(var ii=0;ii<newObj.find('li').length;ii++){
            newObj.find('li').eq(ii).css({
                "color":"#000000",
                "background-color":"#FFFFFF",
                "font-size":"12px"
            });
        }
        newObj.find('li').eq(selectedItem).css({
        	"color":"#FFFFFF",
        	"background-color":"highlight"
        });
        newObj.show();
        newiframe.show();
    };
    
    var populateSearchField=function(obj){
        if(newObj.find('li').length>0){
            obj.val(newObj.find('li').eq(selectedItem).text());
            //为验证框架进行的适配代码开始
            $(obj).focus();
            $(obj).trigger("blur");
            //为验证框架进行的适配代码结束
            setSelectedItem(null);
            //点击回车时导致为空
            if(ActionOnCompleted!=undefined && ActionOnCompleted!=null){
                ActionOnCompleted();
            }
        }
    };
    
    $(this).attr('autocomplete','off').bind("keyup",function(event){
    	//键盘码>40或者空格键 删除键
        if(event.keyCode>40 ||event.keyCode==8||event.keyCode==32){
            if(ActionOnPreSearch!=undefined && ActionOnPreSearch!=null){
                var ifcontinue=ActionOnPreSearch();
                if(ifcontinue==false){
                    newObj.hide();
                    newiframe.hide();
                    return;
                }
            };
        
            newObj.find("li").each(function(){
                $(this).remove();
            });
            var obj=$(this);
            var inputValue=$(this).val();
            if(inputValue==""){
                newObj.hide();
                newiframe.hide(); 
            }
            else{
                //var iset = $(this).offset();
                var iset = $(this).position();
                var width = $(this).width();
                var ctlleft = iset.left;
                var ctltop=iset.top;
                if($("#d_info").attr("id") != undefined){
                    ctlleft = ctlleft+$("#d_info").scrollLeft();
                    ctltop=ctltop+$("#d_info").scrollTop();
                }else{
                    ctltop=ctltop;
                }
                var AjaxDataString="menu="+RequestMenuForList+"&inputvalue="+inputValue;
                if(GetOtherConditions!=null){
                    AjaxDataString+="&"+GetOtherConditions($(this));
                }
                $.ajax({
                	type:"post",
                    url:RequestPage,
                    dataType: "text",
                    processData:false,
                    data:AjaxDataString,
                    success:function(result){
                    	if(result.trim().indexOf("<!DOCTYPE html>")==0){
                    	}else{
                    		if(result==undefined || result==null || result==''){
                                setSelectedItem(null);
                                newObj.remove();
                                newiframe.remove();
                           }else{
                               var displayInfo=result.trim().split(';;'); 
                               if(displayInfo==null){
                                   setSelectedItem(null);
                                   newObj.remove();
                                   newiframe.remove();
                               }else if(displayInfo.length=="1" && event.keyCode!=8){
                               	//点击删除按钮的时候，即使只有一个选项，也不要把值赋给文本框，否则不能连续点击删除按钮
                                   $(obj).val(displayInfo[0]);
                                   newObj.remove();
                                   newiframe.remove();
                                   //为验证框架进行的适配代码开始
                                   $(obj).focus();
                                   $(obj).trigger("blur");
                                   //为验证框架进行的适配代码结束
                                   if(ActionOnCompleted!=undefined && ActionOnCompleted!=null){
                                       ActionOnCompleted();
                                   } 
                               }else{
                                   newObj.show();
                                   var ii=displayInfo.length;
                                   if(ii<5){
                                       ii=5;
                                   }
                                   if(ii>12){
                                       ii=12;
                                   }
                                   newObj.css({
   	                                "position":"absolute",
   	                                "left":ctlleft,
   	                                "top":function(){return ctltop;},
   	                                "width":width+7,
   	                                "list-style":"none",
   	                                "margin":"0px",
   	                                "padding":"0",
   	                                "border":"1px solid #A9A9A9",
   	                                "background-color":"#FFFFFF",
   	                                "cursor":"hand",
   	                                "height":function(){return ii*16;},
   	                                "overflow-y":"auto",
   	                                "z-index":100
                                   });
                                   
                                   newiframe.css({
   	                                "position":"absolute",
   	                                "left":function(){return iset.left;},
   	                                "top":function(){return ctltop+20;},
   	                                "width":width+7,
   	                                "list-style":"none",
   	                                "margin":"0px",
   	                                "padding":"0",
   	                                "border":"1px solid #A9A9A9",
   	                                "background-color":"#FFFFFF",
   	                                "cursor":"hand",
   	                                "height":function(){return ii*16;},
   	                                "overflow-y":"auto",
   	                                "z-index":99,
   	                                "filter":"alpha(opacity=0)"
                                   });
                                   for(var i=0;i<=displayInfo.length-1;i++){
                                       var option=$("<li class='selectLi'></li>");
                                       option.text(displayInfo[i]).appendTo(newObj).click(function(){
                                           obj.val($(this).text());
                                           newObj.remove();
                                           newiframe.remove(); 
                                           //为验证框架进行的适配代码开始
                                           obj.focus();
                                           obj.trigger("blur");
                                           //为验证框架进行的适配代码结束
                                           if(ActionOnCompleted!=undefined && ActionOnCompleted!=null)
                                           {
                                               ActionOnCompleted();
                                           } 
                                       }).mouseover(function(){
                                           for(var gg=0;gg<i;gg++)
                                           {
                                               if(displayInfo[gg]==$(this).text())
                                               {
                                                   setSelectedItem(gg);
                                               }
                                           }
                                       });
                                       option.css({
                                           "margin-left":"3px",
                                           "margin-top":"1px",
                                           "white-space":"nowrap"
                                       });
                                   } 
                                   setSelectedItem(0);
                                   $(obj).after(newiframe);
                                   $(obj).after(newObj);
                                   $(newiframe).width($(newObj).width());
                               }
                           } 
                    	}
                    },
                    beforeSend:function(){
                       
                    },
                    error:function (){
                        return null;
                    }
                }); 
            }
        }
        else if(event.keyCode==38 && selectedItem!=null)//向上的箭头按键
        {
            setSelectedItem(selectedItem-1);
            event.preventDefault();
        }
        else if(event.keyCode==40 && selectedItem!=null)//向下的箭头按键
        {
            setSelectedItem(selectedItem+1);
            event.preventDefault();
        }
        else if(event.keyCode==27 && selectedItem!=null)//esc按键
        {
            setSelectedItem(null);
        }
    });
    
    $(this).blur(function(event){//鼠标移除事件
    	ValidateData($(this),RequestPage,RequestMenuForList+"Validate",GetOtherConditions,$(this));
        setTimeout(function(){
        	newObj.hide();
       		newiframe.hide();
        },250);
    });
    $(this).keypress(function(event){
        if(event.keyCode==13 && selectedItem!=null){//回车键
            var obj=$(this);
            populateSearchField(obj);
            event.preventDefault();
        }
    });
    
    $(this).bind("focus",function(){
        newObj.find("li").each(function(){
            $(this).remove();
        });
        
        if(ActionOnPreSearch!=undefined && ActionOnPreSearch!=null){
            var ifcontinue=ActionOnPreSearch();
            if(ifcontinue==false){
                newObj.hide();
                newiframe.hide();  
                return;
            }
        } 
        
        var obj=$(this);
        var inputValue=$(this).val();
        if(inputValue==""){
            newObj.hide();
            newiframe.hide();
        }else{
            //var iset = $(this).offset();
            var iset = $(this).position();
            var width = $(this).width();
            var ctlleft = iset.left;
            var ctltop=iset.top;
            if($("#d_info").attr("id") != undefined){
                ctlleft = ctlleft+$("#d_info").scrollLeft();
                ctltop=ctltop+$("#d_info").scrollTop();
            }else{
                ctltop=ctltop+20;
            }
                
            var AjaxDataString="menu="+RequestMenuForList+"&inputvalue="+inputValue;
            if(GetOtherConditions!=null){
                AjaxDataString+="&"+GetOtherConditions($(this));
            }
            $.ajax({
            	type:"post",
            	url:RequestPage,
            	dataType: "text",
            	processData:false,
            	data:AjaxDataString,
            	success:function(result){
            		if(result.trim().indexOf("<!DOCTYPE html>")==0){
                	}else{
                		if(result==undefined || result==null || result==''){//返回结果为空，则移除提示框
    	                    newObj.remove();
    	                    newiframe.remove();
                    	}else{
                    		var displayInfo=result.trim().split(';;'); 
                    		if(displayInfo==null){
    	                        newObj.remove();
    	                        newiframe.remove();
                    		}else if(false){
    	                        obj.val(displayInfo[0]);
    	                        newObj.remove();
    	                        newiframe.remove();
    	                        obj.focus();
    	                        obj.trigger("blur");
    	                        if(ActionOnCompleted!=undefined && ActionOnCompleted!=null){
    	                            ActionOnCompleted();
    	                        } 
                    		}else{
                    			newObj.show();
    	                        var ii=displayInfo.length;
    	                        if(ii<5){
    	                            ii=5;
    	                        }
    	                        if(ii>12){
    	                            ii=12;
    	                        }
    	                        newObj.css({
    		                        "position":"absolute",
    		                        "left":function(){return ctlleft;},
    		                        "top":function(){return ctltop;},
    		                        "width":width+7,
    		                        "list-style":"none",
    		                        "margin":"0px",
    		                        "padding":"0",
    		                        "border":"1px solid #A9A9A9",
    		                        "background-color":"#FFFFFF",
    		                        "cursor":"hand",
    		                        "height":function(){return ii*16;},
    		                        "overflow-y":"auto",
    		                        "z-index":100
    	                        });
                            
                            newiframe.css({
    	                        "position":"absolute",
    	                        "left":function(){return ctlleft;},
    	                        "top":function(){return ctltop;},
    	                        "width":width+7,
    	                        "list-style":"none",
    	                        "margin":"0px",
    	                        "padding":"0",
    	                        "border":"1px solid #A9A9A9",
    	                        "background-color":"#FFFFFF",
    	                        "cursor":"hand",
    	                        "height":function(){return ii*16;},
    	                        "overflow-y":"hidden",
    	                        "z-index":99,
    	                        "filter":"alpha(opacity=0)"
                            });
                            
                            for(var kk=0;kk<=displayInfo.length-1;kk++){
                                var option=$("<li></li>");
                                option.text(displayInfo[kk]).appendTo(newObj).click(function(){
                                    obj.val($(this).text());
                                    newObj.remove();
                                    newiframe.remove(); 
                                    if(ActionOnCompleted!=undefined && ActionOnCompleted!=null){
                                        ActionOnCompleted();
                                    } 
                                }).mouseover(function(){
                                    for(var gg=0;gg<kk;gg++){
                                        if(displayInfo[gg]==$(this).text()){
                                            setSelectedItem(gg);
                                        }
                                    }
                                });
                                option.css({
                                    "margin-left":"3px",
                                    "margin-top":"1px",
                                    "white-space":"nowrap"
                                });
                            }
                            setSelectedItem(0);
                            $(obj).after(newiframe);
                            $(obj).after(newObj);
                            $(newiframe).width($(newObj).width());
                        }
                    }
                	}
            },
            beforeSend:function(){
               
            },
            error:function (){
                return null;
            }
        }); 
        }
    });
};
//验证输入框内容
function ValidateData(ctlName,RequestPage,RequestMenuForValidate,GetOtherConditions,$obj){
    var inputvalue=$(ctlName).val();
    var boolFlag= false;
    if(inputvalue==""){
        return true;
    }
    var AjaxDataString="menu="+RequestMenuForValidate+"&inputvalue="+inputvalue;
    if(GetOtherConditions!=null){
        AjaxDataString+="&"+GetOtherConditions($obj);
    }
    $.ajax({
    	type:"post",
        url:RequestPage,
        contentType:"application/x-www-form-urlencoded;charset=UTF-8",
        dataType: "json",
        processData:false,
        async:false,
        data:AjaxDataString,
        success:function(result){
            if(result.success=="false" || result == ""){
                boolFlag= false;
                $(ctlName).val("");
            }else{
                boolFlag= true;
            }
        },
        beforeSend:function(){
        },
        error:function (error){
            boolFlag= false;
            $(ctlName).val("");
        }
    });
    return boolFlag;
};
/*
*******************联想输入***********************uuuuuuuu*****
*	widthExpand 联想输入框扩展的宽度 ，数值类型，默认0
*	flag 是否对验证文本框内容进行验证，boolean类型，默认true
*	whetherGetOut 是否取得括号外内容，boolean类型,默认false,eg:(0001)哈哈哈，如果true则取哈哈哈，否则取0001；
*/
jQuery.fn.bindChange_2=function(RequestPage,RequestMenuForList,GetOtherConditions,ActionOnPreSearch,ActionOnCompleted,widthExpand,flag,whetherGetOut){
	var newiframe=$("<iframe id='iframepopup'></iframe>");
    var newObj=$("<ul id='popup'></ul>");
    //初始化方法参数
    if(widthExpand == undefined||isNaN(widthExpand)){
    	widthExpand = 0;
    }else{
    	widthExpand = parseInt(widthExpand);
    }
    if(flag == null){
		flag = true;
	}
    if(whetherGetOut == null){
    	whetherGetOut = false;
    }
    GetOtherConditions=(GetOtherConditions==undefined||GetOtherConditions=="")?null:GetOtherConditions;
    //
    var selectedItem=null;
    //设置高亮显示的条目
    var setSelectedItem=function(item){
        selectedItem=item;
        if(selectedItem==null){
            newObj.remove();
            newiframe.remove();
            return;
        }
        if(selectedItem<0){
            selectedItem=0;
        }
        if(selectedItem>=newObj.find('li').length){
            selectedItem=newObj.find('li').length-1;
        }
        for(var ii=0;ii<newObj.find('li').length;ii++){
            newObj.find('li').eq(ii).css({
                "color":"#000000",
                "background-color":"#FFFFFF",
                "font-size":"12px"
            });
        }
        newObj.find('li').eq(selectedItem).css({
        	"color":"#FFFFFF",
        	"background-color":"highlight"
        });
        newObj.show();
        newiframe.show();
    };
    
    var populateSearchField=function(obj){
        if(newObj.find('li').length>0){
        	var value = newObj.find('li').eq(selectedItem).text();
        	if(value.indexOf(")")>0){
     		   if(whetherGetOut){
     			   value = value.substring(value.indexOf(")")+1,value.length);
     		   }else{
     			   value = value.substring(1,value.indexOf(")"));
     		   }
     	   }
            obj.val(value);
            setSelectedItem(null);
            obj.change();
            //点击回车时导致为空
            if(ActionOnCompleted!=undefined && ActionOnCompleted!=null){
                ActionOnCompleted(obj);
            }
        }
    };
//    $(this).keypress(function(event){
//        if(event.keyCode==13 && selectedItem!=null){//回车键
//            var obj=$(this);
//            populateSearchField(obj);
//            event.preventDefault();
//        }
//    });
    $(this).attr('autocomplete','off').bind("keyup",function(event){
    	 if(event.keyCode==13 && selectedItem!=null){//回车键
             var obj=$(this);
             populateSearchField(obj);
             event.preventDefault();
         }
    	//键盘码>40或者空格键 删除键
        if(event.keyCode>40 ||event.keyCode==8||event.keyCode==32){
            if(ActionOnPreSearch!=undefined && ActionOnPreSearch!=null){
                var ifcontinue=ActionOnPreSearch();
                if(ifcontinue==false){
                    newObj.remove();
                    newiframe.remove();
                    return;
                }
            };
        
            newObj.find("li").each(function(){
                $(this).remove();
            });
            var obj=$(this);
            var inputValue=$(this).val();
            if(inputValue==""){
                newObj.remove();
                newiframe.remove(); 
            }
            else{
                //var iset = $(this).offset();
                var iset = $(this).position();
                var width = $(this).width() + widthExpand;
                var height = $(this).height();
                var ctlleft = iset.left;
                var ctltop=iset.top;
                if($("#d_info").attr("id") != undefined){
                    ctlleft = ctlleft+$("#d_info").scrollLeft();
                    ctltop=ctltop+$("#d_info").scrollTop();
                }else{
                    ctltop=ctltop+height;
                }
                var AjaxDataString="menu="+RequestMenuForList+"&inputvalue="+inputValue;
                if(GetOtherConditions!=null){
                    AjaxDataString+="&"+GetOtherConditions($(this));
                }
                $.ajax({
                	type:"post",
                    url:RequestPage,
                    dataType: "text",
                    processData:false,
                    data:AjaxDataString,
                    success:function(result){
                    	if(result.trim().indexOf("<!DOCTYPE html>")==0){
                    	}else{
                    		if(result==undefined || result==null || result==''){
                                setSelectedItem(null);
                                newObj.remove();
                                newiframe.remove();
                           }else{
                               var displayInfo=result.trim().split(';;'); 
                               if(true){
                                   newObj.show();
                                   var ii=displayInfo.length;
                                   if(ii<5){
                                       ii=5;
                                   }
                                   if(ii>12){
                                       ii=12;
                                   }
                                   newObj.css({
   	                                "position":"absolute",
   	                                "left":ctlleft,
   	                                "top":function(){return ctltop;},
   	                                "width":width+7,
   	                                "list-style":"none",
   	                                "margin":"0px",
   	                                "padding":"0",
   	                                "border":"1px solid #A9A9A9",
   	                                "background-color":"#FFFFFF",
   	                                "cursor":"hand",
   	                                "height":function(){return ii*16;},
   	                                "overflow-y":"auto",
   	                                "z-index":100
                                   });
                                   
                                   newiframe.css({
   	                                "position":"absolute",
   	                                "left":function(){return iset.left;},
   	                                "top":function(){return ctltop;},
   	                                "width":width+7,
   	                                "list-style":"none",
   	                                "margin":"0px",
   	                                "padding":"0",
   	                                "border":"1px solid #A9A9A9",
   	                                "background-color":"#FFFFFF",
   	                                "cursor":"hand",
   	                                "height":function(){return ii*16;},
   	                                "overflow-y":"auto",
   	                                "z-index":99,
   	                                "filter":"alpha(opacity=0)"
                                   });
                                   for(var i=0;i<=displayInfo.length-1;i++){
                                       var option=$("<li class='selectLi'></li>");
                                       option.text(displayInfo[i]).appendTo(newObj).click(function(){
                                    	   var value = $(this).text();
                                    	   if(value.indexOf(")")>0){
                                    		   if(whetherGetOut){
                                    			   value = value.substring(value.indexOf(")")+1,value.length);
                                    		   }else{
                                    			   value = value.substring(1,value.indexOf(")"));
                                    		   }
                                    	   }
                                           obj.val(value);
                                           newObj.remove();
                                           newiframe.remove(); 
                                           //为验证框架进行的适配代码开始
//                                           obj.focus();
                                           obj.trigger("change");
                                           //为验证框架进行的适配代码结束
                                           if(ActionOnCompleted!=undefined && ActionOnCompleted!=null)
                                           {
                                               ActionOnCompleted(obj);
                                           } 
                                       }).mouseover(function(){
                                           for(var gg=0;gg<i;gg++)
                                           {
                                               if(displayInfo[gg]==$(this).text())
                                               {
                                                   setSelectedItem(gg);
                                               }
                                           }
                                       });
                                       option.css({
                                           "margin-left":"3px",
                                           "margin-top":"1px",
                                           "white-space":"nowrap"
                                       });
                                   } 
                                   setSelectedItem(0);
                                   $(obj).after(newiframe);
                                   $(obj).after(newObj);
                                   $(newiframe).width($(newObj).width());
                               }
                           } 
                    	}
                    },
                    beforeSend:function(){
                       
                    },
                    error:function (){
                        return null;
                    }
                }); 
            }
        }
        else if(event.keyCode==38 && selectedItem!=null)//向上的箭头按键
        {
            setSelectedItem(selectedItem-1);
            event.preventDefault();
        }
        else if(event.keyCode==40 && selectedItem!=null)//向下的箭头按键
        {
            setSelectedItem(selectedItem+1);
            event.preventDefault();
        }
        else if(event.keyCode==27 && selectedItem!=null)//esc按键
        {
            setSelectedItem(null);
        }
    });
    //验证
    $(this).change(function(event){
    	if(flag){
    		ValidateData_2($(this),RequestPage,RequestMenuForList+"Validate",GetOtherConditions,$(this));
    	}
    });
    $(this).blur(function(){
    	setTimeout(function(){
    		newObj.remove();
    		newiframe.remove();
    	},250);
    })
  
};
//验证输入框内容
function ValidateData_2(ctlName,RequestPage,RequestMenuForValidate,GetOtherConditions,$obj){
    var inputvalue=$(ctlName).val();
    var boolFlag= false;
    if(inputvalue==""){
        return true;
    }
    var AjaxDataString="menu="+RequestMenuForValidate+"&inputvalue="+inputvalue;
    if(GetOtherConditions!=null){
        AjaxDataString+="&"+GetOtherConditions($obj);
    }
    $.ajax({
    	type:"post",
        url:RequestPage,
        contentType:"application/x-www-form-urlencoded;charset=UTF-8",
        dataType: "json",
        processData:false,
        async:false,
        data:AjaxDataString,
        success:function(result){
            if(result.success=="false" || result == ""){
                boolFlag= false;
                $(ctlName).val("");
            }else{
                boolFlag= true;
            }
        },
        beforeSend:function(){
        },
        error:function (error){
            boolFlag= false;
            $(ctlName).val("");
        }
    });
    return boolFlag;
};