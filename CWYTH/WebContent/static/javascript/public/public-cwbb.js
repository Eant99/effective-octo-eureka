/**
 * 报表中心公共JS，包括计算 只读 隐藏
 */
//两个项目的合计计算
	function computeTwo(row1,row2,sum,type,cal){
		var je1 = 0;
		var je2	= 0;
		var hjje=0;
		je1=$(".tr_"+row1+" [name='"+type+"']").val();
		je2=$(".tr_"+row2+" [name='"+type+"']").val();
		je1=je1.replace(/,/g,"");
		je2=je2.replace(/,/g,"");
		if(isNaN(je1)||je1==""){
			je1 = 0;
		}
		if(isNaN(je2)||je2==""){
			je2 = 0;
		}
		switch(cal){
		case "+":
			hjje = parseFloat(je1) + parseFloat(je2);break;
		case "-":
			hjje = parseFloat(je1) - parseFloat(je2);break;
		case "*":
			hjje = parseFloat(je1) * parseFloat(je2);break;
		case "/":
			hjje = parseFloat(je1) / parseFloat(je2);break;
		}
		$(".tr_"+sum+" [name='"+type+"']").val(hjje.toFixed(2));
	}
	//中间无其它项目的资产合计
	function computeLinked(start,end,sum,type){
		var je = 0;
		var hjje = 0;
		//合计金额
		for(var i=start;i<=end;i++){
			je = $(".tr_"+i+" [name='"+type+"']").val();
			je=je.replace(/,/g,"");
			if(isNaN(je)||je==""){
				je = 0;
			}
			hjje = parseFloat(hjje) + parseFloat(je);
		}
		$(".tr_"+sum+" [name='"+type+"']").val(hjje.toFixed(2));
	}
	//不连续的多项目计算
	function computeArray(rows,sum,type){
		var je = 0;
		var hjje = 0;
		//合计金额
		for(var i=0;i<rows.length;i++){
			je = $(".tr_"+rows[i]+" [name='"+type+"']").val();
			je=je.replace(/,/g,"");
			if(isNaN(je)||je==""){
				je = 0;
			}
			hjje = parseFloat(hjje) + parseFloat(je);
		}
		$(".tr_"+sum+" [name='"+type+"']").val(hjje.toFixed(2));
	}
	 //只读连续行数的内容
	function readonlyLinked(start,end,type){
		for(var i=start;i<=end;i++){
			$(".tr_"+i+" [name='"+type+"']").attr("readonly","readonly");
		}
	}
	//只读非连续行数的内容
	function readonlyArray(rows,type){
		for(var i=0;i<rows.length;i++){
			$(".tr_"+rows[i]+" [name='"+type+"']").attr("readonly","readonly");
		}
	}
	//隐藏连续行数的内容
	function hiddenLinked(start,end,type){
		for(var i=start;i<=end;i++){
			$(".tr_"+i+" [name='"+type+"']").addClass("hidden");
		}
	}
	//隐藏非连续行数的内容
	function hiddenArray(rows,type){
		for(var i=0;i<rows.length;i++){
			$(".tr_"+rows[i]+" [name='"+type+"']").addClass("hidden");
		}
	}