<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>编辑项目信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style>
	.radio-group{
		height: 25px!important;
		line-height: 25px;
		vertical-align: middle;
		display: inline-block;
	}
	.radio-group > .rdo{
		margin: 0px 0px 0px 10px;
		height: 25px;
	}
.aa{
text-align:right;
}
.tables {
    margin-left: 20px;
    max-width: 100%;
    margin-bottom: 20px;
    width:50%;
   }
 .p1{
    text-align:center;
    font-family:宋体;
    color:red;
    font-size:18px;
   }
 [class^=td_first_]{
 	text-align:center;
 	width:100%;
 	height:30px;
 }
 [class^=td_second_]{
 	width:100%;
 	height:30px;
 }
 [class^=td_three_]{
 	width:100%;
 	height:30px;
 }
 [class$=first_2],[class$=first_1],[class$=first_3]{
 width:20%;
 }
 [class$=second_1],[class$=second_2],[class$=second_3]{
 width:30%;
 }
 .td_input{
 	width:100%;
 	border:none;
 }
 a{
	 text-decoration:none;
 }
 .addBtn0,.addBtn1,.addBtn2,.addBtn3{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.addBtn0:after,.addBtn1:after,.addBtn2:after,.addBtn3:after{
/* 		2722 */
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 17px;
	    position: relative;
	    cursor: pointer;
	}
	.delBtn1,.delBtn2,.delBtn3{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.delBtn1:after,.delBtn2:after,.delBtn3:after{
/* 		2014 */
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 1em;
	    position: relative;
	    cursor: pointer;
	    top:3px;
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
</style>

</head>
<body>
<div id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="czr"  value="">
	<input type="hidden" name="czrq" value=""/>
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>查看项目信息</span>
		</div>
        <div class="pull-right">
<!--             <button type="button" class="btn btn-default" id="btn_save" >保存</button> -->
<button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	
	<div class="box" style="top:36px">
	<form id="myform1">
	     <input type="hidden" id="guid" name="guid" value="E3A85BE7EEF54604873BF914C63CC6A1">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>查看项目基本信息</div>
            		<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>部门名称</span>
							<input type="text" id="txt_bmbh" value="(101)党委（学校）办公室" name="bmbh" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请输入部门名称" disabled/>
						    <span class="input-group-btn"><button type="button" id="btn_bmbh" class="btn btn-link" disabled>选择</button></span>
						    
						</div>
                    </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目编号</span>
							<input type="text" id="txt_xmbh" name="xmbh" readonly  value="010101" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请输入项目编号" disabled/>
						</div>
                    </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目名称</span>
							<input type="text" id="txt_xmmc" name="xmmc" value="2121" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请输入项目名称" readonly/>
						</div>
                    </div>
				</div>
				<div class="row">
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目大类</span>
                            <input type="text" id="txt_xmmc" name="xmmc" value="科研经费" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请输入项目名称" readonly/>
                        </div>
					</div>
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目类别</span>
                            <input type="text" id="txt_xmmc" name="xmmc" value="教育" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请输入项目名称" readonly/>
                        </div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目类型</span>
							<input type="text" id="txt_xmlx" value="(02)项目类型名称" name="xmlx" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请选择项目类型" disabled/>
							<input type="hidden" id="txt_xmlxid" value="D3DD16EEF94A4A72917027274EC95AA7" name="xmlxid" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"/>
						    <span class="input-group-btn"><button type="button" id="btn_xmlx" class="btn btn-link" disabled>选择</button></span>
						</div>
                    </div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>负责人</span>
							<input type="text" id="txt_fzr" value="(2013093)范正宽" name="fzr" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请选择负责人" disabled/>

							
							<span class="input-group-btn"><button type="button" id="btn-fzr" class="btn btn-link" disabled>选择</button></span>
						</div>
                    </div>
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon">项目属性</span>
                            <select id="txt_xmsx" class="form-control input-radius select2" name="xmsx" disabled>
                           		<option value="01" selected >部门经费</option>
                           		<option value="02"  >个人经费</option>
                            
                            </select>
                        </div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">归口部门</span>
						
							<input type="text" id="txt_gkbm" name="gkbm" value="(104)纪委办公室（监察室）" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请选择归口部门" disabled/>

						    
							<span class="input-group-btn"><button type="button" id="btn_gkbm" class="btn btn-link" disabled>选择</button></span>
						</div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4">
						<div class="input-group ">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle;">是否启用</span>
							<div class="radiodiv">
							<span class="radio-group">
								<input type="radio" name="sfqy" class="rdo" value="1" disabled checked/>
								<span style="float: right">是</span>
							</span>
							<span class="radio-group">
								<input type="radio" name="sfqy" class="rdo" value="0" disabled  />
								<span style="float: right">否</span>
							</span>
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group ">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle;" disabled>是否完工</span>
							<div class="radiodiv">
							<span class="radio-group">
								<input type="radio" name="sfwg" class="rdo" value="1" disabled />
								<span style="float: right">是</span>
							</span>
							<span class="radio-group">
								<input type="radio" name="sfwg" class="rdo" value="0" disabled checked />
								<span style="float: right">否</span>
							</span>
							</div>
						</div>
					</div>							
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle; "><span class="required">*</span>开始时间</span>
							<input type="text" id="txt_kssj" name="kgrq" class="form-control input-radius date" disabled
							style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" value="2018-03-13" placeholder="请输入开始时间" />
						</div>
                    </div>
				</div>
				<div class="row">
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle;"><span class="required">*</span>结束时间</span>
							<input type="text" id="txt_jssj" name="wgrq" class="form-control input-radius date"  
							style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" value="2018-03-30"  placeholder="请输入结束时间" disabled/>
						</div>							
					</div>							
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle;"><span class="required">*</span>预算金额（元）</span>
							<input type="text" id="txt_xmmc" name="ysje" class="form-control input-radius number" 
							style="border-bottom-right-radius: 4px;text-align:right;border-top-right-radius: 4px;" value="1.00"  placeholder="请输入预算金额" readonly/>
						</div>
					</div>							
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle; "><span class="required">*</span>剩余金额（元）</span>
							<input type="text" id="txt_xmmc" name="syje" class="form-control input-radius number" 
							style="border-bottom-right-radius: 4px;text-align:right;border-top-right-radius: 4px;" value="1.00"  placeholder="请输入剩余金额" readonly/>
						</div>
                    </div>
				</div>
				
                 <div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle; "><span class="required">*</span>项目经费是否允许超标</span>
							<div class="radiodiv">&nbsp; &nbsp;
								<input type="radio" name="xmjfsfyxcb" class="rdo" value="1" disabled/>
								 <label >是</label> &nbsp; &nbsp; &nbsp;
								<input type="radio" name="xmjfsfyxcb" class="rdo" value="0" disabled checked />
								 <label >否</label>
							</div>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle; ">超标比例（百分比）</span>
							<input type="text" id="txt_cbbl" name="cbbl" class="form-control input-radius" readonly value="1" onKeyUp="this.value=this.value.replace(/[^\d\.]/g,'')"
							style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  />
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle; "><span class="required">*</span>是否进行经济分类费用控制</span>
							<div class="radiodiv">&nbsp; &nbsp;
								<input type="radio" name="sfjxjjflfykz" class="rdo" value="1" disabled/>
								 <label >是</label> &nbsp; &nbsp; &nbsp;
								<input type="radio" name="sfjxjjflfykz" class="rdo" value="0" disabled checked />
								 <label >否</label>
							</div>
						</div>
                    </div>
                </div>
                <div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle; "><span class="required">*</span>是否科研</span>
							<div class="radiodiv">&nbsp; &nbsp;
								<input type="radio" name="sfky" class="rdo" value="1" disabled />
								 <label >是</label> &nbsp; &nbsp; &nbsp;
								<input type="radio" name="sfky" class="rdo" value="0" disabled />
								 <label >否</label>
							</div>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle; "><span class="required">*</span>政府采购金额（元）</span>
							<input type="text" id="txt_zfcgje" name="zfcgje" class="form-control input-radius number" 
							style="border-bottom-right-radius: 4px;text-align:right;border-top-right-radius: 4px;" readonly value="1.00" />
							<input type="hidden" id="txt_zfcgsyje" name="zfcgsyje" class="form-control input-radius number"/>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle; "><span class="required">*</span>非政府采购金额（元）</span>
							<input type="text" id="txt_fzfcgje" name="fzfcgje" class="form-control input-radius number" 
							style="border-bottom-right-radius: 4px;text-align:right;border-top-right-radius: 4px;"  readonly value="1.00"/>
							<input type="hidden" id="txt_fzfcgsyje" name="fzfcgsyje" class="form-control input-radius number"/> 
						</div>
                    </div>
<!--                     <div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle; "><span class="required">*</span>会计科目允许禁止</span> -->
<!-- 							<div class="radiodiv">&nbsp; &nbsp; -->

<!-- 								 <label >允许</label> &nbsp; &nbsp; &nbsp; -->

<!-- 								 <label >禁止</label> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!--                     </div> -->
<!--                     <div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle; "><span class="required">*</span>经济科目允许禁止</span> -->
<!-- 							<div class="radiodiv">&nbsp; &nbsp; -->

<!-- 								 <label >允许</label> &nbsp; &nbsp; &nbsp; -->

<!-- 								 <label >禁止</label> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!--                     </div> -->
                </div>
			</div>
         </div>
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>添加项目控制信息</div>
            		<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
<!--                     <div class="col-md-4"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<span class="input-group-addon">预算类型</span> -->
<!-- <!-- 							<input type="text" id="txt_yslx" name="yslx" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请选择预算类型" /> -->
<!-- <!-- 							<span class="input-group-btn"><button type="button" id="btn-yslx" class="btn btn-link">选择</button></span> --> 
<!-- 								<select id="txt_xmsx" class="form-control input-radius select2" name="yslx" disabled> -->
<!-- 	                            	<option value="">未选择</option> -->



<!-- 	                            </select> -->
<!-- 						</div> -->
<!--                     </div> -->
					<div class="col-md-4">
						<div class="input-group ">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle;" disabled>是否财政支出</span>
							<div class="radiodiv">
							<span class="radio-group">
								<input type="radio" name="sfczzc" class="rdo" value="1" disabled/>
								<span style="float: right">是</span>
							</span>
							<span class="radio-group">
								<input type="radio" name="sfczzc" class="rdo" value="0" disabled checked />
								<span style="float: right">否</span>
							</span>
							</div>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group ">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle;" disabled>是否国库</span>
							<div class="radiodiv">
							<span class="radio-group">
								<input type="radio" name="sfgk" class="rdo" value="1" disabled/>
								<span style="float: right">是</span>
							</span>
							<span class="radio-group">
								<input type="radio" name="sfgk" class="rdo" value="0" disabled checked />
								<span style="float: right">否</span>
							</span>
							</div>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">国库信息码</span>
							<input type="text" id="txt_gkxxm" name="gkxxm" value="" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请输入国库信息码" readonly/>
						</div>
                    </div>
				</div>
			</div>
         </div>
         </form>
          <form id="table1" class="myform myformPzby" mc="editsr">
			<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>会计科目控制</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
        	<div class="row">
        			<table id="mydatatables" class="tables table-striped table-bordered" style="border-collapse: collapse">
        			<tbody class="tbody1">
        				<tr >
<!-- 					    	<td class="td_first_1"  style="text-align:center;" valign="middle"> -->
<!-- 					    		操作 -->
<!-- 					    	</td> -->
					    	<td class="td_first_first_1" style="text-align:center;" valign="middle">
					    		预算类型
					    	</td>
					    	<td class="td_first_second_1" style="text-align:center;" valign="middle">
					    		科目编号
					    	</td>
					    	<td class="td_first_three_1"  style="text-align:center;" valign="middle">
					    		科目名称
					    	</td>
				   		</tr>
				   		
				   			
				   				
        			 	<tr id="pbtr_1" class="pdtr_1 pd_tr1">
<!-- 					    	<td class="td_first_1"> -->
<!-- 					    		<div class="addBtn1 add1"></div> -->
<!-- 					    	</td> -->
							<td class="td_second_first_2" style="text-align:center;" valign="middle">
					    		基本支出
					    	</td>
					    	<td class="td_second_second_2">
					    		<div class="input-group">
						    	    <input type="text" readonly="readonly" style="width:100%;border:none;" class="aa" name="kmbh" id="txt_srkbh" value="1002">
						    	    <input type="hidden" name="xmbh" id="txt_xmbh" class="a" value="E3A85BE7EEF54604873BF914C63CC6A1">
						    	    <span class="input-group-btn">
	  				    				<button type="button" id="btn_srkm" class="btn btn-link btn-custom">选择</button>
	 				    			</span> 
 				    			</div>
					    	</td>
					    	<td class="td_second_three_3">
					    		<input type="text" readonly="readonly" style="width:100%;border:none;" name="kmmc" id="txt_srkm" value="银行存款" />
					    	</td>
				   		</tr>
				   		<tr></tr>
				   		<tr id="pbtr_2" class="pdtr_2 pd_tr2">
<!-- 					    	<td class="td_first_1"> -->
<!-- 					    		<div class="addBtn1 add1"></div> -->
<!-- 					    	</td> -->
							<td class="td_three_first_3" style="text-align:center;" valign="middle">
					    		项目支出
					    	</td>
					    	<td class="td_three_second_3">
					    		<div class="input-group">
						    	    <input type="text" readonly="readonly" style="width:100%;border:none;" class="aa" name="kmbh2" id="txt_srkbh2" value="">
						    	    <input type="hidden" name="xmbh" id="txt_xmbh" class="a" value="E3A85BE7EEF54604873BF914C63CC6A1">
						    	    <span class="input-group-btn">
	  				    				<button type="button" id="btn_srkm2" class="btn btn-link btn-custom">选择</button>
	 				    			</span> 
 				    			</div>
					    	</td>
					    	<td class="td_three_three_3">
					    		<input type="text" readonly="readonly" style="width:100%;border:none;" name="kmmc2" id="txt_srkm2" value=""/>
					    	</td>
				   		</tr>
				   		
				   			
				   			
				   		
				   		
        			</tbody>
				</table>
		 </div>
		 </div>
		 </div>
		 </form>
		 
		 <!-- 经济分类 -->
<!-- 		  <form id="table3" class="myform myformPzby" mc="editjjfl"> -->
<!-- 			<div class="box-panel"> -->
<!-- 			<div class="box-header clearfix"> -->
<!--             	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>经济科目控制</div> -->
<!--             	<div class="actions"> -->
<!--             		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a> -->
<!--             	</div> -->
<!--         	</div> -->
<!-- 			<hr class="hr-normal"> -->
<!-- 			<div class="container-fluid box-content"> -->
<!--         	<div class="row"> -->
<!--         			<table id="mydatatables" class="tables table-striped table-bordered" style="border-collapse: collapse"> -->
<!--         			<tbody class="tbody3"> -->
<!--         				<tr > -->
<!-- <!-- 					    	<td class="td_first_1"  style="text-align:center;" valign="middle"> --> 
<!-- <!-- 					    		操作 --> 
<!-- <!-- 					    	</td> --> 
<!-- 					    	<td class="td_second_1" style="text-align:center;" valign="middle"> -->
<!-- 					    		科目编号 -->
<!-- 					    	</td> -->
<!-- 					    	<td class="td_three_1"  style="text-align:center;" valign="middle"> -->
<!-- 					    		科目名称 -->
<!-- 					    	</td> -->
<!-- 				   		</tr> -->



<!--         			 	<tr id="jjfltr_1" class="jjfltr_11"> -->
<!-- <!-- 					    	<td class="td_first_1"> --> 
<!-- <!-- 					    		<div class="addBtn3 add3"></div> --> 
<!-- <!-- 					    	</td> --> 
					  
<!-- 					    	<td class="td_second_1"> -->
					    	



<!-- <!-- 					    	     <span class="input-group-btn"> --> 
<!-- <!--  				    			<button type="button" id="btn_jjflkm" bh="txt_jjflkbh" mc="txt_jjflkm" class="btn btn-link btn-custom">选择</button> --> 
<!-- <!--  				    			</span> --> 
 				    				
<!-- 					    	</td> -->
					    
<!-- 					    	<td class="td_three_1"> -->

<!-- 					    	</td> -->
<!-- 				   		</tr> -->



<!--         			 	<tr id="jjfltr_1" class="jjfltr_11 jjfl_1"> -->
<!-- <!-- 					    	<td class="td_first_1"> --> 
<!-- <!-- 					    		<div class="addBtn3 add3"></div> --> 
<!-- <!-- 					    	</td> --> 
					  
<!-- 					    	<td class="td_second_1"> -->
					    	  
<!-- 					    	    <input type="text" style="width:100%;border:none;" class="cc" name="kmbh" id="txt_jjflkbh" value="" readonly> -->
<!-- 					    	      <input type="hidden" style="width:20%;border:none;" name="jjflguid" id="txt_jjflguid" value=""> -->

<!-- <!-- 					    	     <span class="input-group-btn"> --> 
<!-- <!--  				    			<button type="button" id="btn_jjflkm" bh="txt_jjflkbh" mc="txt_jjflkm" class="btn btn-link btn-custom">选择</button> --> 
<!-- <!--  				    			</span> --> 
 				    				
<!-- 					    	</td> -->
					    
<!-- 					    	<td class="td_three_1"> -->
<!-- 					    		<input type="text" style="width:100%;border:none;" name="kmmc" id="txt_jjflkm" value="" readonly/> -->
<!-- 					    	</td> -->
<!-- 				   		</tr> -->


				   		
<!--         			</tbody> -->
<!-- 				</table> -->
<!-- 		 </div> -->
<!-- 		 </div> -->
<!-- 		 </div> -->
<!-- 		 </form> -->
	</div>
</div>

<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
var target = "${ctx}/xmgl/xmcx/xmchaxun";
	var xmym = "${xmym}";
//保存方法
// addbuttonsr();
	addbuttonzc();
// 	addbuttonjjfl();
	function addbuttonsr(){
		var a = $(".tbody1").find("tr").length;
		var aaa="${srmap}";
		
		if(aaa.length>2){
			$(".add1").removeClass("addBtn1")
			
			$(".add1").addClass("delBtn1");		
			var $parentTr = $(".tbody1 tr:last").clone();
			$parentTr.find("input:not(.a)").val("");
			
			$(".tbody1 tr:last").after($parentTr);
			
			$(".tbody1 tr:last").find(".add1").removeClass("delBtn1");
			$(".tbody1 tr:last").find(".add1").addClass("addBtn1");
			$(".tbody1 tr:last").addClass("pd_tr1");
		}else{
			
		}
	}
	
	 $("#txt_bmbh").bindChange("${ctx}/suggest/getXx","CD");
	 $("#txt_fzr").bindChange("${ctx}/suggest/getXx","R");
	 $("#txt_xmlx").bindChange("${ctx}/suggest/getXx","XMLX");
	 $("#txt_gkbm").bindChange("${ctx}/suggest/getXx","CD");
	function addbuttonzc(){
		var a = $(".tbody2").find("tr").length;
		var aaa="${zcmap}";
		
		if(aaa.length>2){
			$(".add2").removeClass("addBtn2")
			
			$(".add2").addClass("delBtn2");		
			var $parentTr = $(".tbody2 tr:last").clone();
			$parentTr.find("input:not(.b)").val("");
			
			$(".tbody2 tr:last").after($parentTr);
			
			$(".tbody2 tr:last").find(".add2").removeClass("delBtn2");
			$(".tbody2 tr:last").find(".add2").addClass("addBtn2");
			$(".tbody2 tr:last").addClass("zc_1");

		}else{
			
		}
	}
	function addbuttonjjfl(){
		var a = $(".tbody3").find("tr").length;
		var aaa="${jjflmap}";
		
		if(aaa.length>2){
			$(".add3").removeClass("addBtn3")
			
			$(".add3").addClass("delBtn3");		
			var $parentTr = $(".tbody3 tr:last").clone();
			$parentTr.find("input:not(.c)").val("");
			
			$(".tbody3 tr:last").after($parentTr);
			
			$(".tbody3 tr:last").find(".add3").removeClass("delBtn3");
			$(".tbody3 tr:last").find(".add3").addClass("addBtn3");
			$(".tbody3 tr:last").addClass("jjfl_1");
		}else{
			
		}
	}
 $("#btn_save").click(function(e){
		doSave1(validate,"myform1",target+"/doSaveU",function(val){
			
		});
	});
	function doSave1(_validate, _formId, _url, _success, _fail){
		var index;
		var valid;
		if(_validate){
			_validate.bootstrapValidator("validate");
			valid = $("#myform1").data('bootstrapValidator').isValid();
		} else {
			valid = true;
		}
		if(valid){
			$.ajax({
				type:"post",
				url:_url,
				dataType:"json",
				data:arrayToJson($('#' + _formId).serializeArray()),
				success:function(val){						
						next();
				},
				error:function(val){
				}	
			});
		}
	}
	$.fn.serializeObject1 = function(start,end){
		var f = {"list":[]};
	    var a = this.serializeArray();
	    var o = {};
	    $.each(a, function() {
	    	if (this.name == start) {
	        	o = {};
	        	o[this.name] = this.value;
	        } else if(this.name == end){
	        	o[this.name] = this.value;
	        	f["list"].push(o);
	        }else{
	        	o[this.name] = this.value;
	        }
	    });
	    return f;
	};
	var validate = $("#myform1").bootstrapValidator({fields:{
        bmbh:{validators:{notEmpty: {message: '部门名称不能为空'}}},
        xmbh:{validators:{notEmpty: {message: '项目编号不能为空'},regexp: {regexp: /^[\w_]+$/,message: '项目编号只能包含大写、小写、数字和下划线'},stringLength:{message:'项目编号过长',max:'50'}}},
        xmmc:{validators:{notEmpty: {message: '项目名称不能为空'},stringLength:{message:'项目编号过长',max:'50'}}},
        xmdl:{validators: {notEmpty: {message: '项目大类不能为空'}}},
        xmlb:{validators: {notEmpty: {message: '项目类别不能为空'}}},
        xmlx:{validators: {notEmpty: {message: '项目类型不能为空'}}},
        fzr:{validators: {notEmpty: {message: '负责人不能为空'}}},
        srkm:{validators:{notEmpty:{message: '会计科目控制不能为空'}}},
        zckm:{validators:{ notEmpty:{message: '支出科目不能为空'}}},
        jjflzckm:{validators:{notEmpty:{message:"经济分类支出科目不能为空"}}},
        kgrq:{validators:{ notEmpty:{message: '开始时间不能为空'}}},
        wgrq:{validators:{ notEmpty:{message: '结束时间不能为空'}}},
        syje:{validators:{ notEmpty:{message: '剩余金额不能为空'}}},
        ysje:{validators:{ notEmpty:{message: '预算金额不能为空'}}},
        gkxxm:{validators:{stringLength:{message:'国库信息吗过长',max:'32'}}}
        }
	      });





//检查项目编号是否已经存在
function checkXmbhExist(){
	var tag = false;
	var xmbh = $("#txt_xmbh").val();
	$.ajax({
		type:"post",
		//url:ADDRESS+"/xmxx/checkXmbh",
		url:target+"/checkXmbhExist",
		data:"xmbh="+xmbh,
		async:false,
		success:function(val){
			var result = JSON.getJson(val);
			if(result.exist){
				tag = true;
				alert("该项目编号已经存在！");
			}
		}
	})
	return tag;
}
$(function(){
	//验证方法
	
	//保存按钮
//    $("#btn_save").click(function(){
// 	   	var url = target+"/xmxxAdd";
// 		doSave1(validate,$("#myform"),url,function(val){
// 			var result = JSON.getJson(val);
// 			if(result.success){
// 				alert("保存成功！");
// 			}
// 		});
// 	});
	//返回按钮
	$("#btn_back").click(function(){
		//window.location.href = target+"/goXmcxPage";
		history.back(-1);
	});
	//刷新按钮
	$(".reload").click(function(){
		 window.location.reload();
	});
	
	//负责人弹窗
	$(document).on("click","#btn-fzr",function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_fzr","负责人","920","630");
	});
	//项目弹窗
	$(document).on("click","[id^=btn_xmlx]",function(){
		select_commonWin("${ctx}/ysgl/xmsz/xmxx/getxmlxall?controlId=txt_xmlx&id=txt_xmlxid","项目信息","920","630");	
		
	});
	//归口部门弹窗
	$(document).on("click","#btn_gkbm",function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_gkbm","归口部门","920","630");
	});
	$(document).on("click","#btn_bmbh",function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_bmbh","部门信息","920","630");
	});
	
	
	//会计科目控制弹窗
	$(document).on("click","#btn-srkm",function(){
		select_commonWin("${ctx}/window/goSrkmPage?controlId=txt_srkm","会计科目控制","920","630");
	});
	//支出科目弹窗
	$(document).on("click","#btn-zckm",function(){
		select_commonWin("${ctx}/window/goZckmPage?controlId=txt_zckm","支出科目","920","630");
	});
	//经济分类支出科目弹窗
	$(document).on("click","#btn-jjflzckm",function(){
		select_commonWin("${ctx}/window/goJjflkmPage?controlId=txt_jjflzckm","经济分类支出科目","920","630");
	});
	//日期验证
	$("#txt_kssj,#txt_jssj").blur(function(){
		dateVerify();
	})
	//验证日期
	function dateVerify(){
		var kssj = $("#txt_kssj").val();
		var jssj = $("#txt_jssj").val();

		if(!isNull(kssj) && !isNull(jssj)){
			
			 if(jssj < kssj){
				alert("结束时间不能小于开始时间！");
				$("#txt_jssj").val("");
			}
		}
	}
});
function addkm(guid){
	var sr = $(".pdtr_1");
	var zc = $(".zctr_11");
	var jjfl=$(".jjfltr_11");
	if(sr.length>1){
		$(".pdtr_1:not(.pd_tr1)").remove();
	}
	if(zc.length>1){
		$(".zctr_11:not(.zc_1)").remove();
	}
	if(jjfl.length>1){
		$(".jjfltr_11:not(.jjfl_1)").remove();
	}
	
	//会计科目控制信息
	$.ajax({
		type:"post",
		url:"${ctx}/ysgl/xmsz/xmxx/getsrkm",
		dataType:"json",
		data:"xmlxbh="+guid,
		success:function(val){				
				$.each(val,function(i,v){
					 var $tr = $(".pd_tr1").clone();
					     $tr.removeClass("pd_tr1");	
					    // $tr.removeClass("pd_tr1");	
					    $tr.find("[name='kmbh']").val(v.SRKMBH);
						$tr.find("[name='kmmc']").val(v.KMMC);
						
						$tr.find("[name='kmbh']").attr("id","txt_srkbh"+v.GUID);
						$tr.find("[name='kmmc']").attr("id","txt_srkm"+v.GUID);
						
						$tr.find("[type='button']").attr("id","btn_srkm"+v.GUID);
						$tr.find("[type='button']").attr("mc","txt_srkm"+v.GUID);
						$tr.find("[type='button']").attr("bh","txt_srkbh"+v.GUID);
						$(".pd_tr1").before($tr);	
				});
				//添加删除样式
				$(".add1").removeClass("addBtn1")				
				$(".add1").addClass("delBtn1");	
				$(".tbody1 tr:last").find(".add1").removeClass("delBtn1");
				$(".tbody1 tr:last").find(".add1").addClass("addBtn1");
			//	$(".tbody1 tr").removeClass("pd_tr1");
			//	$(".tbody1 tr:last").addClass("pd_tr1");
			
		},
		error:function(val){
							
		}	
	});
	//zc科目信息
	$.ajax({
		type:"post",
		url:"${ctx}/ysgl/xmsz/xmxx/getzckm",
		dataType:"json",
		data:"xmlxbh="+guid,
		success:function(val){				
				$.each(val,function(i,v){
					 var $tr = $(".zc_1").clone();
					     $tr.removeClass("zc_1");					   
					    $tr.find("[name='kmbh']").val(v.ZCKMBH);
						$tr.find("[name='kmmc']").val(v.KMMC);
						
						$tr.find("[name='kmbh']").attr("id","txt_zckbh"+v.GUID);
						$tr.find("[name='kmmc']").attr("id","txt_zckm"+v.GUID);
						
						$tr.find("[type='button']").attr("id","btn_zckm"+v.GUID);
						$tr.find("[type='button']").attr("mc","txt_zckm"+v.GUID);
						$tr.find("[type='button']").attr("bh","txt_zckbh"+v.GUID);

						
						
						$("#zctr_1").before($tr);	
				});
				//添加删除样式
				$(".add2").removeClass("addBtn2")				
				$(".add2").addClass("delBtn2");	
				$(".tbody2 tr:last").find(".add2").removeClass("delBtn2");
				$(".tbody2 tr:last").find(".add2").addClass("addBtn2");
				//$(".tbody2 tr").removeClass("zc_1");
				//$(".tbody2 tr:last").addClass("zc_1");
			
		},
		error:function(val){
							
		}	
	});
	//经济分类科目信息
	$.ajax({
		type:"post",
		url:"${ctx}/ysgl/xmsz/xmxx/getjjflkm",
		dataType:"json",
		data:"xmlxbh="+guid,
		success:function(val){				
				$.each(val,function(i,v){
					 var $tr = $("#jjfltr_1").clone();
					     $tr.removeClass("jjfltr_1");					   
					    $tr.find("[name='kmbh']").val(v.JJFL);
						$tr.find("[name='kmmc']").val(v.KMMC);
						$("#jjfltr_1").before($tr);	
				});
				//添加删除样式
				$(".add3").removeClass("addBtn3")				
				$(".add3").addClass("delBtn3");	
				$(".tbody3 tr:last").find(".add3").removeClass("delBtn3");
				$(".tbody3 tr:last").find(".add3").addClass("addBtn3");
				$(".tbody3 tr").removeClass("jjfl_1");
				$(".tbody3 tr:last").addClass("jjfl_1");
			
		},
		error:function(val){
							
		}	
	});
	
}
function next(){
	var srkmbh = $(".pd_tr1").find("[name=kmbh]").val();
	var zckmbh = $(".zc_1").find("[name=kmbh]").val();
	var jjflkmbh = $(".jjfl_1").find("[name=kmbh]").val();
	var srkmbh1 = $(".pd_tr1").find("[name=kmmc]").val();
	var zckmbh1 = $(".zc_1").find("[name=kmmc]").val();
	var jjflkmbh1 = $(".jjfl_1").find("[name=kmmc]").val();
	if(srkmbh.length==0 &&srkmbh1.length==0){
		$(".pd_tr1").remove();
	}
	if(zckmbh.length==0 &&zckmbh1.length==0){
		$(".zc_1").remove();
	}
	if(jjflkmbh.length==0&&jjflkmbh1.length==0){
		$(".jjfl_1").remove();
	}
	
	var mytable = $(".myform");
	var xmxxbh = $("#guid").val();
	//var aryId = [];
	$.each(mytable,function(i,v){
		//var bbbb= $("#pbtr_1:last").find("[name=kmbh]").val();
		var $this = $(this);
		var id = $this.attr("id");
		var mc = $this.attr("mc")
	var json = $("#"+id+"").serializeObject1("kmbh","kmmc");  //json对象		
	var jsonStr = JSON.stringify(json);  //json字符串
	$.ajax({
		        url:"${ctx}/ysgl/xmsz/xmxx/"+mc+"?xmxxbh="+xmxxbh,
	   			type:"post",
	   			data:"json="+jsonStr,
	   			success:function(data){
				// 
				//window.location.href = "${ctx}/pzlx/gopzlxListPage";
	   			}
	
			}); 
	});
	alert("保存成功！"); 
	window.location.href  = target+"/goXmxxPage";
}
function addsrkm(kmbh,kmmc){
	for(var i=0;i<kmbh.length;i++){
		var a = $(".aa");
		var kmbh1="";
		$.each(a,function(){
			kmbh1 +=$(this).val()+",";
		});
	   if(kmbh1.indexOf(kmbh[i])=="-1"){
		   var $parentTr = $(".pd_tr1").clone();
			$parentTr.removeClass("pd_tr1");

			$parentTr.find("input:not(.a)").val("");
			$parentTr.find("[id=txt_srkbh]").val(kmbh[i]);
			$parentTr.find("[id=txt_srkm]").val(kmmc[i]);		
			$(".pd_tr1 ").before($parentTr);
			$(".add1").addClass("delBtn1");	
			$(".add1").removeClass("addBtn1");	
			$(".add1:last").addClass("addBtn1");
			$(".add1:last").removeClass("delBtn1");
		 }
		
	}
	
}
function addzckm(kmbh,kmmc){
	for(var i=0;i<kmbh.length;i++){
		var a = $(".bb");
		var kmbh1="";
		$.each(a,function(){
			kmbh1 +=$(this).val()+",";
		});
	   if(kmbh1.indexOf(kmbh[i])=="-1"){
		   var $parentTr = $(".zc_1").clone();
			$parentTr.removeAttr("id");
			$parentTr.removeClass("zc_1");
			$parentTr.find("input:not(.b)").val("");
			$parentTr.find("[id=txt_zckbh]").val(kmbh[i]);
			$parentTr.find("[id=txt_zckm]").val(kmmc[i]);		
			$(".zc_1").before($parentTr);
			$(".add2").addClass("delBtn2");
			$(".add2").removeClass("addBtn2");	
			$(".add2:last").addClass("addBtn2");
			$(".add2:last").removeClass("delBtn2");
		 }
	}
	
}
function addjjflkm(kmbh,kmmc){
	for(var i=0;i<kmbh.length;i++){
		var a = $(".cc");
		var kmbh1="";
		$.each(a,function(){
			kmbh1 +=$(this).val()+",";
		});
	   if(kmbh1.indexOf(kmbh[i])=="-1"){
			var $parentTr = $(".jjfl_1 ").clone();
			$parentTr.removeAttr("id");
			$parentTr.removeClass("jjfl_1");
			$parentTr.find("input:not(.c)").val("");
			$parentTr.find("[id=txt_jjflkbh]").val(kmbh[i]);
			$parentTr.find("[id=txt_jjflkm]").val(kmmc[i]);		
			$(".jjfl_1 ").before($parentTr);
			$(".add3").addClass("delBtn3");	
			$(".add3").removeClass("addBtn3");	
			$(".add3:last").addClass("addBtn3");
			$(".add3:last").removeClass("delBtn3");
		 }
	}
	
}
</script>
</body>
</html>