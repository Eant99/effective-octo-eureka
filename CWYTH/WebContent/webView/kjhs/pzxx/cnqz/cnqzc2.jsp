<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.googosoft.constant.Constant"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>列选择</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<style type="text/css">
	body{
		overflow-x:hidden;
	}
</style>
<style type="text/css">
	button{
		background-color: #00acec !important;
		color: white !important;
	}
</style>
<body class="contrast-red" >
<div id='wrapper'>
<section>
    <div class='row' id='content-wrapper'>
        <div class='col-md-12'>
             <div class="box">
                 <div class='box-content' style="padding-bottom: 0; overflow:visible;">
                 <button type="button" class="btn btn-primary" id="btn_cx"><i class="fa icon-cx"></i>确定</button>
                 	<div class="alert alert-info" style="padding: 6px;margin-bottom: 4px;">
			          	<strong>提示：</strong><c:if test="${param.from == 'wxjfhb'}">（1）</c:if>不选择默认为全部</strong>;
			        </div>
			        
			        <hr class="hr-normal" id="hr">
                     <div class='responsive-table'>
                     <div class='scrollable-area'>
                     <table id="mydatatables" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
					     <thead>
					     <tr id="header">
					          <th></th>
					          <th>序号</th>
					          <th>名称</th>
					     </tr>
					     </thead>
					     <tbody>
					     	<tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_shzt" name="pzrq"/>
					          </td>
					          <td>1</td>
					          <td>审核状态</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_rybh" name="pzrq"/>
					          </td>
					          <td>2</td>
					          <td>人员编号</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_xm" name="zy"/>
					          </td>
					          <td>3</td>
					          <td>姓名</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_bm" name="wldc"/>
					          </td>
					          <td>4</td>
					          <td>部门</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_rylb" name="wldc"/>
					          </td>
					          <td>5</td>
					          <td>人员类别</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_rylx" name="wldc"/>
					          </td>
					          <td>6</td>
					          <td>人员类型</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_gwgz" name="wldc"/>
					          </td>
					          <td>7</td>
					          <td>岗位工资</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_xjgz" name="wldc"/>
					          </td>
					          <td>8</td>
					          <td>薪级工资</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_xzft" name="wldc"/>
					          </td>
					          <td>9</td>
					          <td>新住房贴</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_wybt" name="wldc"/>
					          </td>
					          <td>10</td>
					          <td>物业补贴</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_dszf" name="wldc"/>
					          </td>
					          <td>11</td>
					          <td>独生子费</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_jcjx" name="wldc"/>
					          </td>
					          <td>12</td>
					          <td>基础绩效</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_jljx1" name="wldc"/>
					          </td>
					          <td>13</td>
					          <td>奖励绩效1</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_bsbt" name="wldc"/>
					          </td>
					          <td>14</td>
					          <td>博士补贴</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_gwbt" name="wldc"/>
					          </td>
					          <td>15</td>
					          <td>岗位补贴</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_bxqzbbt" name="wldc"/>
					          </td>
					          <td>16</td>
					          <td>北校区值班补贴</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_sybt" name="wldc"/>
					          </td>
					          <td>17</td>
					          <td>生育补贴</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_jhbt" name="wldc"/>
					          </td>
					          <td>18</td>
					          <td>教护补贴</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_zjbt" name="wldc"/>
					          </td>
					          <td>19</td>
					          <td>驻济补贴</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_htbt" name="wldc"/>
					          </td>
					          <td>20</td>
					          <td>合同补贴</td>
					     </tr>
					     
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_qtbt" name="wldc"/>
					          </td>
					          <td>21</td>
					          <td>其他补贴</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_bgz" name="wldc"/>
					          </td>
					          <td>22</td>
					          <td>补工资</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_jkf" name="wldc"/>
					          </td>
					          <td>23</td>
					          <td>监考费</td>
					     </tr>
					      <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_fzgzl" name="wldc"/>
					          </td>
					          <td>24</td>
					          <td>辅助工作量</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_zsjl" name="wldc"/>
					          </td>
					          <td>25</td>
					          <td>招生奖励</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_fdyyjzbbt" name="wldc"/>
					          </td>
					          <td>26</td>
					          <td>辅导员夜间值班补贴</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_khj" name="wldc"/>
					          </td>
					          <td>27</td>
					          <td>考核奖</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_dhf" name="wldc"/>
					          </td>
					          <td>28</td>
					          <td>电话费</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_bt" name="wldc"/>
					          </td>
					          <td>29</td>
					          <td>补贴</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_zfbt" name="wldc"/>
					          </td>
					          <td>30</td>
					          <td>租房补贴</td>
					     </tr>
					     
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_yfhj" name="wldc"/>
					          </td>
					          <td>31</td>
					          <td>应发合计</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_jiangkef" name="wldc"/>
					          </td>
					          <td>32</td>
					          <td>讲课费</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_bgzks" name="wldc"/>
					          </td>
					          <td>33</td>
					          <td>补工资扣税</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_jsx" name="wldc"/>
					          </td>
					          <td>34</td>
					          <td>计税项</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_qnjsx" name="wldc"/>
					          </td>
					          <td>35</td>
					          <td>全年计税项</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_qnjsx1" name="wldc"/>
					          </td>
					          <td>36</td>
					          <td>全年计税项1</td>
					     </tr>
					     
					     
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_qnjsx2" name="wldc"/>
					          </td>
					          <td>37</td>
					          <td>全年计税项2</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_qnjsx3" name="wldc"/>
					          </td>
					          <td>38</td>
					          <td>全年计税项3</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_bjcxjxgz2014jsjs" name="wldc"/>
					          </td>
					          <td>39</td>
					          <td>补基础性绩效工资2014计税基数</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_bxbt2014n1d10yjsjs" name="wldc"/>
					          </td>
					          <td>40</td>
					          <td>北校补贴2014年1到10月计税基数</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_jsjs" name="wldc"/>
					          </td>
					          <td>41</td>
					          <td>计税基数</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_zfjj" name="wldc"/>
					          </td>
					          <td>42</td>
					          <td>住房积金</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_pgjj" name="wldc"/>
					          </td>
					          <td>43</td>
					          <td>聘公积金</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_ylbx" name="wldc"/>
					          </td>
					          <td>44</td>
					          <td>医疗保险</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_bgjj" name="wldc"/>
					          </td>
					          <td>45</td>
					          <td>补公积金</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_dks" name="wldc"/>
					          </td>
					          <td>46</td>
					          <td>代扣税</td>
					     </tr>
					      <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_bnse" name="wldc"/>
					          </td>
					          <td>47</td>
					          <td>本年税额</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_snse" name="wldc"/>
					          </td>
					          <td>48</td>
					          <td>上年税额</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_bs" name="wldc"/>
					          </td>
					          <td>49</td>
					          <td>补税</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_fz" name="wldc"/>
					          </td>
					          <td>50</td>
					          <td>房租</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_syj" name="wldc"/>
					          </td>
					          <td>51</td>
					          <td>失业金</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_nqf" name="wldc"/>
					          </td>
					          <td>52</td>
					          <td>暖气费</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_nqf1" name="wldc"/>
					          </td>
					          <td>53</td>
					          <td>暖气费1</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_wyf" name="wldc"/>
					          </td>
					          <td>54</td>
					          <td>物业费</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_sbj" name="wldc"/>
					          </td>
					          <td>55</td>
					          <td>社保金</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_sjdck" name="wldc"/>
					          </td>
					          <td>56</td>
					          <td>四季度菜款</td>
					     </tr>
					      <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_kk" name="wldc"/>
					          </td>
					          <td>57</td>
					          <td>扣款</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_ylj" name="wldc"/>
					          </td>
					          <td>58</td>
					          <td>养老金</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_jk" name="wldc"/>
					          </td>
					          <td>59</td>
					          <td>借款</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_axyrj" name="wldc"/>
					          </td>
					          <td>60</td>
					          <td>爱心一日捐</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_kkhj" name="wldc"/>
					          </td>
					          <td>61</td>
					          <td>扣款合计</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_sfhj" name="wldc"/>
					          </td>
					          <td>62</td>
					          <td>实发合计</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_gzyf" name="wldc"/>
					          </td>
					          <td>63</td>
					          <td>工资月份</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_bh" name="wldc"/>
					          </td>
					          <td>64</td>
					          <td>编号</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_xh" name="wldc"/>
					          </td>
					          <td>65</td>
					          <td>序号</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_jtf" name="wldc"/>
					          </td>
					          <td>66</td>
					          <td>交通费</td>
					     </tr>
					      <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_nzj" name="wldc"/>
					          </td>
					          <td>67</td>
					          <td>年终奖</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_nzjdks" name="wldc"/>
					          </td>
					          <td>68</td>
					          <td>年终奖代扣税</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_gzdks" name="wldc"/>
					          </td>
					          <td>69</td>
					          <td>工资代扣税</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_kshj" name="wldc"/>
					          </td>
					          <td>70</td>
					          <td>扣税合计</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_gh" name="wldc"/>
					          </td>
					          <td>71</td>
					          <td>工号</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_sfzb" name="wldc"/>
					          </td>
					          <td>72</td>
					          <td>工号</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_bkylbx" name="wldc"/>
					          </td>
					          <td>73</td>
					          <td>补扣医疗保险</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_bksyj" name="wldc"/>
					          </td>
					          <td>74</td>
					          <td>补扣失业金</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_bkylj" name="wldc"/>
					          </td>
					          <td>75</td>
					          <td>补扣养老金</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_bksbj" name="wldc"/>
					          </td>
					          <td>76</td>
					          <td>补扣社保金</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_sfdy" name="wldc"/>
					          </td>
					          <td>77</td>
					          <td>是否党员</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_rdqk" name="wldc"/>
					          </td>
					          <td>78</td>
					          <td>入党情况</td>
					     </tr>
					     
					     
					     </tbody>
				     </table>
                     </div>
                     </div>
                  </div>
              </div>
         </div>
      </div>
</section>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function (){
	var winId = getTopFrame().layer.getFrameIndex(window.name);
	
//     //双击事件
//     $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
//     	var val = $(this).find("[name='wldc']").val();
//     	console.log(val);
//     	if(val==''||val==null||val=='undefined'){
//     		alert("没有可以选择的数据！");
//     	}else{
//     		getIframeControl("${param.pname}","${param.controlId}").val(val);
//         	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
//         	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
//         	close(winId);
//     	}
//     });
	//取消
   	$("#btn_cancel").on("click",function(){
   		close(winId);
   	});
	$("#btn_cx").click(function(){
		var col = $("#mydatatables tr:not(#header)").find(":checkbox").filter(":checked");
		var cols = [];
		$.each(col.next(),function(){
			cols.push($(this).val());
		});
		getIframeControl("${param.pname}","${param.controlId}").val("tr_sh111,tr_sh222,"+cols.join(","));
    	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
    	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
    	getIframWindow("${param.pname}").selectCol();
    	close(winId);
	});
});
</script>
</body>
</html>