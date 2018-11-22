<%@ page contentType="text/html; charset=euc-kr"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- /////////////////////// EL / JSTL 적용으로 주석 처리 ////////////////////////
<%@ page import="java.util.List"%>

<%@ page import="com.model2.mvc.service.domain.*"%>
<%@ page import="com.model2.mvc.common.Search"%>
<%@page import="com.model2.mvc.common.Page"%>
<%@page import="com.model2.mvc.common.util.CommonUtil"%>

<%
	List<Purchase> list = (List<Purchase>) request.getAttribute("list");
	Page resultPage = (Page) request.getAttribute("resultPage");

	Search search = (Search) request.getAttribute("search");
	//==> null 을 ""(nullString)으로 변경
	String searchCondition = CommonUtil.null2str(search.getSearchCondition());
	String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
%>/////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// --%>

<html>
<head>
<title>구매 목록조회</title>

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

	<div style="width: 98%; margin-left: 10px;">

		<form name="detailForm" action="/purchase/listPurchase" method="post">

			<table width="100%" height="37" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"
						width="15" height="37"></td>
					<td background="/images/ct_ttl_img02.gif" width="100%"
						style="padding-left: 10px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="93%" class="ct_ttl01">구매 목록조회</td>
							</tr>
						</table>
					</td>
					<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"
						width="12" height="37"></td>
				</tr>
			</table>

			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>
					<td colspan="14">전체 ${resultPage.totalCount } 건수, 현재 ${resultPage.currentPage} 페이지
					</td>
				</tr>
				<tr>
					<td class="ct_list_b" width="100">No</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">회원ID</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">회원명</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="100">수량</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">전화번호</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">배송현황</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">구매확정</td>
				</tr>
				<tr>
					<td colspan="14" bgcolor="808285" height="1"></td>
				</tr>
				<%-- /////////////////////// EL / JSTL 적용으로 주석 처리 ////////////////////////
				<%
					for (int i = 0; i < list.size(); i++) {
						Purchase purchase = (Purchase) list.get(i);
						System.out.println("listPurchase.jsp에서 TranCode 출력 : " + purchase.getTranCode());
				%>
				<% } %>  
				<tr class="ct_list_pop">
					<td align="center">
					<a href="/getPurchase.do?tranNo=<%=purchase.getTranNo()%>"><%= i + 1 %></a>
					</td>
					<td></td>
					<td align="left">
					<a href="/getUser.do?userId=<%=purchase.getBuyer().getUserId()%>"><%=purchase.getBuyer().getUserId()%></a>
					</td>
					<td></td>
					<td align="left"><%=purchase.getReceiverName()%></td>
					<td></td>
					<td align="left"><%=purchase.getReceiverPhone()%></td>
					<td></td>
					<td align="left">현재 <%
						if (purchase.getTranCode().trim().equals("1")) {
					%>
						구매완료 <%
						} else if (purchase.getTranCode().trim().equals("2")) {
					%> 배송중 <%
						} else if (purchase.getTranCode().trim().equals("3")) {
					%>
						배송완료 <%
						}
					%> 상태 입니다.
					</td>
					<td></td>
					<td align="left">
						<%
							if ((purchase.getTranCode().trim()).equals("2")) {
						%> 
						<a href="/updateTranCode.do?tranNo=<%=purchase.getTranNo()%>&tranCode=3">물건도착</a>
						<%}	%>
					</td>

				</tr>
				<tr>
					<td colspan="11" bgcolor="D6D7D6" height="1"></td>
				</tr>
				<% } %>  %>/////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// --%>
				<c:set var="i" value="0" />
				<c:forEach var="purchase" items="${list}">
				<c:set var="i" value="${ i+1 }" />
				<tr class="ct_list_pop">
				<td align="center">
				<a href="/purchase/getPurchase?tranNo=${purchase.tranNo}">${ i }</a></td>
					<td></td>
					<td align="left">
					<a href="/user/getUser?userId=${purchase.buyer.userId}">${purchase.buyer.userId}</a></td>
					<td></td>
					<td align="left">${purchase.receiverName}</td>
					<td></td>
					<td align="center">${purchase.tranAmount}</td>
					<td></td>
					<td align="left">${purchase.receiverPhone}</td>
					<td></td>
					<td align="left">현재 
							<c:if test = "${fn:trim(purchase.tranCode)=='1'}">
							구매완료 
							</c:if>
							<c:if test = "${fn:trim(purchase.tranCode)=='2'}">
							배송중 
							</c:if>
							<c:if test = "${fn:trim(purchase.tranCode)=='3'}">
							배송완료
							</c:if>
							상태 입니다.
					</td>
					<td></td>
					<td align="left">
						<c:if test = "${fn:trim(purchase.tranCode)=='2'}">
						<a href="/purchase/updateTranCode?tranNo=${purchase.tranNo}&tranCode=3">물건도착</a>
						</c:if>
					</td>

				</tr>
				<tr>
					<td colspan="14" bgcolor="D6D7D6" height="1"></td>
				</tr>	
				</c:forEach>
			</table>

			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>
					<td align="center">
						<input type="hidden" id="currentPage" name="currentPage" value=""/>
						<%-- /////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// 		  
						<% if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ %>
								◀ 이전
						<% }else{ %>
								<a href="javascript:fncGetPurchaseList('<%=resultPage.getCurrentPage()-1%>')">◀ 이전</a>
						<% } %>

						<%	for(int i=resultPage.getBeginUnitPage();i<= resultPage.getEndUnitPage() ;i++){	%>
								<a href="javascript:fncGetPurchaseList('<%=i %>');"><%=i %></a>
						<% 	}  %>
	
						<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage() ){ %>
								이후 ▶
						<% }else{ %>
								<a href="javascript:fncGetPurchaseList('<%=resultPage.getEndUnitPage()+1%>')">이후 ▶</a>
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