<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<tr class="tr_"${num}>
	<td class="btn_td"><div class="deleteBtn"></div></td>
	<td>
	<input type="text" id="txt_fymc${num}" class="form-control input-radius null" name="fymc${num}" value="${fymc}">
	</td>
	<td>
	<input type="text" id="txt_je${num}" class="form-control input-radius sz number null" name="je${num}" value=""
		onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
	</td>
	<td>
	<input type="text" id="txt_pjzs${num}" class="form-control input-radius sz null int" name="pjzs${num}" value=""
			  onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');">
	</td>
	<td>
	<input type="text" id="txt_bz" class="form-control input-radius" name="bz" value="" placeholder="最多输入100个字符">
	</td>
</tr>
</body>
</html>