<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- /////////////////////// EL / JSTL 적용으로 주석 처리 ////////////////////////
<%@ page import="java.util.List"  %>

<%@ page import="com.model2.mvc.service.domain.Product" %>
<%@ page import="com.model2.mvc.common.Search" %>
<%@page import="com.model2.mvc.common.Page"%>
<%@page import="com.model2.mvc.common.util.CommonUtil"%>

<%
	List<Product> list= (List<Product>)request.getAttribute("list");
	Page resultPage=(Page)request.getAttribute("resultPage");

	Search search = (Search)request.getAttribute("search");
//==> null 을 ""(nullString)으로 변경
	String searchCondition = CommonUtil.null2str(search.getSearchCondition());
	String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
	
	String mode = request.getParameter("menu");
%>/////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// --%>

<html>
<head>

<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
<title>상품 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
//검색 / page 두가지 경우 모두 Form 전송을 위해 JavaScrpt 이용  
	function fncGetList(currentPage) {
		document.getElementById("currentPage").value = currentPage;
   		document.detailForm.submit();		
	}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">
  <form name="detailForm" action="/product/listProduct?menu=${param.menu}" method="post"> 
 
<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
					<%-- 
					<% if(mode.equals("manage")) {%>
							상품 관리
					<% }else if(mode.equals("search")) {%>
							상품 목록 조회
					<% } %>	--%>
					<c:if test = "${param.menu == 'manage'}">
						상품 관리
					</c:if>
					<c:if test = "${param.menu == 'search'}">
						상품 목록 조회
					</c:if>
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<%-- /////////////////////// EL / JSTL 적용으로 주석 처리 ////////////////////////
				<option value="0"<%= (searchCondition.equals("0") ? "selected" : "")%>>상품번호</option>
				<option value="1"<%= (searchCondition.equals("1") ? "selected" : "")%>>상품명</option>
				<option value="2"<%= (searchCondition.equals("2") ? "selected" : "")%>>상품가격</option>
				/////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// --%>
				<option disabled selected value> --선택-- </option>
				<option value="0"  ${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>상품번호</option>
				<option value="1"  ${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>상품명</option>
				<option value="2"  ${ ! empty search.searchCondition && search.searchCondition==2 ? "selected" : "" }>상품가격</option>
				
			</select>
			
			
			<input type="text" name="searchKeyword" 
						value="${! empty search.searchKeyword ? search.searchKeyword : ""}"  
						class="ct_input_g" style="width:200px; height:20px" > 
			
			<input type="radio" name="searchPriceCondition"  value="0"  ${ ! empty search.searchPriceCondition && search.searchPriceCondition==0 ? "checked" : "" }>높은가격순
			<input type="radio" name="searchPriceCondition"  value="1"  ${ ! empty search.searchPriceCondition && search.searchPriceCondition==1 ? "checked" : "" }>낮은가격순
			 <c:if test = "${search.searchCondition != null || search.searchPriceCondition != null}">
				<b><a href="/product/listProduct?menu=${param.menu}">필터해제</a></b>
			</c:if>
		</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetList('1');">검색</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<%--<td colspan="11" >전체   <%= resultPage.getTotalCount() %> 건수, 현재 <%= resultPage.getCurrentPage() %> 페이지</td>--%>
		<td colspan="11" >
			전체  ${resultPage.totalCount } 건수, 현재 ${resultPage.currentPage}  페이지
		</td>	
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="100">수량</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">등록일</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>	
		<c:if test = "${product.proTranCode==null || fn:trim(product.proTranCode)=='0'||fn:trim(product.proTranCode)=='4'}">
			<td class="ct_line02"></td>
			<td class="ct_list_b" width="50">장바구니</td>	
		</c:if>
	</tr>
	<tr>
		<td colspan="14" bgcolor="808285" height="1"></td>
	</tr>
	<%-- /////////////////////// EL / JSTL 적용으로 주석 처리 ////////////////////////
	<% 	
		for(int i=0; i<list.size(); i++) {
			Product vo = list.get(i);
	%>	
	<tr class="ct_list_pop">
		<td align="center" ><%= i + 1 %></td>
		<td></td>
				<% if(mode.equals("manage")) {%>
				<td align="left"><a href="/updateProductView.do?prodNo=<%= vo.getProdNo()%>&menu=manage"><%= vo.getProdName() %></a></td>
				<% }else if(mode.equals("search")) {%>
				<td align="left"><a href="/getProduct.do?prodNo=<%= vo.getProdNo()%>&menu=search"><%= vo.getProdName() %></a></td>
				<% } %>
		<td></td>
		<td align="left"><%= vo.getPrice() %></td>
		<td></td>
		<td align="left"><%= vo.getRegDate()%></td>
		<td></td>
		<td align="left">
		
			판매중
		
		</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>	
	<% } %>  %>/////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// --%>
	<c:set var="i" value="0" />
	<c:forEach var="product" items="${list}">
		<c:set var="i" value="${ i+1 }" />
		<tr class="ct_list_pop">
			<td align="center">${ i }</td>
			<td></td>
			
			<c:if test = "${param.menu == 'manage'}">
				<td align="left"><a href="/product/updateProduct?prodNo=${product.prodNo}&menu=manage">${product.prodName }</a></td>
			</c:if>
			<c:if test = "${param.menu == 'search'}">
				<td align="left"><a href="/product/getProduct?prodNo=${product.prodNo}&menu=search">${product.prodName }</a></td>
			</c:if>
		<td></td>
		<td align="left">${product.price }</td>
		<td></td>
		<td align="center">${product.prodAmount }</td>
		<td></td>
		<td align="left">${product.regDate }</td>
		<td></td>
		<td align="left">					
					
					<c:if test = "${product.prodAmount > 0 }" >
					판매중
					</c:if>
					<c:if test = "${product.prodAmount == 0 }" >
					재고없음
						<c:if test = "${param.menu=='manage'&&fn:trim(product.proTranCode)=='1'}" >
						<a href="/updateTranCodeByProd?prodNo=${product.prodNo}&tranCode=2">배송하기</a>
						</c:if>
						<c:if test = "${param.menu=='manage'&&fn:trim(product.proTranCode)=='2'}" >
								(배송중)
						</c:if>
						<c:if test = "${param.menu=='manage'&&fn:trim(product.proTranCode)=='3'}" >
								(배송완료)
						</c:if>
					</c:if>
		
		</td>	
		<c:if test = "${product.prodAmount > 0 }">
		<td></td>
		<td align="center"><a href="/cart/addCart?prodNo=${product.prodNo}"><i class="fas fa-shopping-cart"></i></a></td>
		</c:if>
	</tr>
	<tr>
		<td colspan="14" bgcolor="D6D7D6" height="1"></td>
	</tr>
	</c:forEach>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		<input type="hidden" id="currentPage" name="currentPage" value=""/>
		<%-- /////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// 		   
			<% if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ %>
					◀ 이전
			<% }else{ %>
					<a href="javascript:fncGetProductList('<%=resultPage.getCurrentPage()-1%>')">◀ 이전</a>
			<% } %>

			<%	for(int i=resultPage.getBeginUnitPage();i<= resultPage.getEndUnitPage() ;i++){	%>
					<a href="javascript:fncGetProductList('<%=i %>');"><%=i %></a>
			<% 	}  %>
	
			<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage() ){ %>
					이후 ▶
			<% }else{ %>
					<a href="javascript:fncGetProductList('<%=resultPage.getEndUnitPage()+1%>')">이후 ▶</a>
			<% } %>
			 /////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// --%>
	
		 	<jsp:include page="../common/pageNavigator.jsp"/>

    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>
