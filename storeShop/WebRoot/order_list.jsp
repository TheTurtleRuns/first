<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>订单列表页面</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />

		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
			}
			
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
		</style>
	</head>

	<body>

			<!--
            	描述：菜单栏
            	
            -->
			<!--
            	描述：导航条
            -->
            <!-- 
            	因为菜单栏和导航条在任何页面都有使用，
            	
            	所以为了方便统一维护，增强代码的扩展性
            	
            	我们将其抽取成为一个单独的JSP页面--header.jsp
            	
            	然后静态导入进来即可
             -->
			<%@include file="header.jsp" %>

		<div class="container">
			<div class="row">

				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong>我的订单</strong>
					<table class="table table-bordered">
						<!-- 显示所有的订单 -->
						<c:if test="${not empty pageBean && not empty pageBean.rows }">
							<c:forEach items="${pageBean.rows }" var="order">
								<tbody>
									<tr class="success">
										<th colspan="3">订单编号:&nbsp;&nbsp;<span style="color:red">${order.oid }</span> </th>
										<th colspan="2">
											订单状态：
											<c:choose>
												<c:when test="${order.state==0 }">
													<a href="${pageContext.request.contextPath }/OrderServlet?method=findOrderInfoByOid&oid=${order.oid}">未付款</a>
												</c:when>
												<c:when test="${order.state==1 }">
													已付款未发货
												</c:when>
												<c:when test="${order.state==2 }">
													已发货未签收
												</c:when>
												<c:when test="${order.state==3 }">
													已签收未评价
												</c:when>
												<c:otherwise>
													订单异常
												</c:otherwise>
											</c:choose>
										</th>
									</tr>
									<tr class="warning">
										<th>图片</th>
										<th>商品</th>
										<th>价格</th>
										<th>数量</th>
										<th>小计</th>
									</tr>
									<!-- 显示所有的订单项和商品信息的数据 -->
									<c:forEach items="${order.list }" var="oi">
										<tr class="active">
											<td width="60" width="40%">
												<input type="hidden" name="id" value="22">
												<img src="${pageContext.request.contextPath}/${oi.product.pimage}" width="70" height="60">
											</td>
											<td width="30%">
												<a target="_blank">${oi.product.pname}</a>
											</td>
											<td width="20%">
												￥${oi.product.shop_price}
											</td>
											<td width="10%">
												${oi.count}
											</td>
											<td width="15%">
												<span class="subtotal">￥${oi.subtotal}</span>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</c:forEach>
						</c:if>
					</table>
				</div>
			</div>
			<div style="text-align: center;">
				<ul class="pagination">
					<!-- 上一页：当前页为第一页，上一页不可用 -->
					<c:if test="${not empty pageBean }">
						<c:choose>
							<c:when test="${pageBean.pageNumber==1 }">
								<li class="disabled">
									<a href="javascript:void(0);" aria-label="Previous">
										<span aria-hidden="true">&laquo;</span>
									</a>
								</li>
							</c:when>
							<c:otherwise>
								<li >
									<a href="${pageContext.request.contextPath}/OrderServlet?method=findAllOrdersByUidForPage&pageNumber=${param.pageNumber-1}" aria-label="Previous">
										<span aria-hidden="true">&laquo;</span>
									</a>
								</li>
							</c:otherwise>
						</c:choose>
					</c:if>
					
					<!-- 当前页：当前页不可点 -->
					<c:if test="${not empty pageBean }">
						<c:forEach begin="1" end="${pageBean.totalPage }" var="i">
							<c:choose>
								<c:when test="${pageBean.pageNumber==i }">
									<li class="active"><a href="javascript:void(0);">${i }</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageContext.request.contextPath}/OrderServlet?method=findAllOrdersByUidForPage&pageNumber=${i}">${i }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:if>
					
					<!-- 下一页：当前页为最后一页，下一页不可用 -->
					<c:if test="${not empty pageBean }">
						<c:choose>
							<c:when test="${pageBean.pageNumber==pageBean.totalPage }">
								<li class="disabled">
									<a href="javascript:void(0);" aria-label="Next">
										<span aria-hidden="true">&raquo;</span>
									</a>
								</li>
							</c:when>
							<c:otherwise>
								<li>
									<a href="${pageContext.request.contextPath}/OrderServlet?method=findAllOrdersByUidForPage&pageNumber=${param.pageNumber+1}" aria-label="Next">
										<span aria-hidden="true">&raquo;</span>
									</a>
								</li>
							</c:otherwise>
						</c:choose>
					</c:if>
				</ul>
			</div>
		</div>

		<div style="margin-top:50px;">
			<img src="${pageContext.request.contextPath}/image/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势" />
		</div>

		<div style="text-align: center;margin-top: 5px;">
			<ul class="list-inline">
				<li><a>关于我们</a></li>
				<li><a>联系我们</a></li>
				<li><a>招贤纳士</a></li>
				<li><a>法律声明</a></li>
				<li><a>友情链接</a></li>
				<li><a target="_blank">支付方式</a></li>
				<li><a target="_blank">配送方式</a></li>
				<li><a>服务声明</a></li>
				<li><a>广告声明</a></li>
			</ul>
		</div>
		<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
			Copyright &copy; 2005-2016 传智商城 版权所有
		</div>
	</body>

</html>