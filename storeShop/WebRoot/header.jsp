<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<script type="text/javascript">
		
		//页面加载
		$(function(){
			//发送请求到Servlet查询所有分类信息
			//location.href="${pageContext.request.contextPath}/ProductServlet?method=findAllCategory";
			
			/*此时，我们发现数据虽然查询出来显示了，但是页面在不断地刷新！为什么呢？
				index.jsp静态包含header.jsp；在header.jsp中只要这个页面一加载就立马发送请求,在服务器端的servlet中又转发到index.jsp，它有会加载header.jsp页面
				
			  如何解决呢？
			  	导致这个问题的原因就是：只要加载header.jsp页面就会发送请求！只要分类信息有数据就不发送请求了，没有数据(用户第一次访问首页)才发送！
			  	怎么处理分类信息有没有数据的问题？判断li的长度(大于0，说明有数据！)
			*/
			
			//分类信息没有数据才发送请求
			/* if($("#categoryinfo li").size()==0){
				location.href="${pageContext.request.contextPath}/ProductServlet?method=findAllCategory";
			} */
			
			/*
				上面已经解决了页面不断刷新的问题，但是我们发现一个新的问题，无法进入注册和登录页面了！！！！
				为什么呢？注册和登录页面都静态包含了header.jsp，那么只要访问登录或者注册页面，就会立马加载header.jsp,指向Servlet，但是servlet转发到index.jsp
				
				导致上面的问题，主要是由于使用使用了转发！！！！可以使用异步方式来处理这个问题！！！！
			*/
			
			$.post("${pageContext.request.contextPath}/ProductServlet",{method:"findAllCategorys"},function(data){
				//非空判断
				if(data!=""){
					//遍历
					$(data).each(function(i,m){
						//alert(m['cname']);
						//将分类名称添加到li标签中去
						var temp = "<li><a href='${pageContext.request.contextPath}/ProductServlet?method=findAllProductByCidForPage&pageNumber=1&cid="+m['cid']+"'>"+m['cname']+"</a></li>";
						//将li标签追加到ul标签中去
						$("#categoryinfo").append(temp);
					});
				}
			},"json");
		});
	</script>
<!--
         	描述：菜单栏
         -->
<div class="container-fluid">
	<div class="col-md-4">
		<img src="img/logo2.png" />
	</div>
	<div class="col-md-4">
		<img src="${pageContext.request.contextPath}/img/header.png" />
	</div>
	<div class="col-md-4" style="padding-top:20px">
		<ol class="list-inline">
			<c:choose>
				<c:when test="${not empty exsitUser }">
					<li>欢迎尊贵的会员：<span style="color:red">${exsitUser.username }</span></li>
					<li><a href="${pageContext.request.contextPath}/OrderServlet?method=findAllOrdersByUidForPage&pageNumber=1">我的订单</a></li>
					<li><a href="${pageContext.request.contextPath}/cart.jsp">购物车</a></li>
					<li><a href="${pageContext.request.contextPath}/UserServlet?method=logout">注销</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="${pageContext.request.contextPath}/login.jsp">登录</a></li>
					<li><a href="${pageContext.request.contextPath}/register.jsp">注册</a></li>
					<li><a href="${pageContext.request.contextPath}/cart.jsp">购物车</a></li>
				</c:otherwise>
			</c:choose>
		</ol>
	</div>
</div>
<!--
         	描述：导航条
         -->
<div class="container-fluid">
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">首页</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav" id="categoryinfo">
					<!-- 显示所有的分类信息 -->
					<c:if test="${not empty categorys }">
						<c:forEach items="${categorys }" var="category">
							<li><a href="#">${category.cname }</a></li>
						</c:forEach>
					</c:if>
				
					<!-- <li class="active"><a href="#">手机数码<span class="sr-only">(current)</span></a></li>
					<li><a href="#">电脑办公</a></li>
					<li><a href="#">电脑办公</a></li>
					<li><a href="#">电脑办公</a></li> -->
				</ul>
				<form class="navbar-form navbar-right" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>

			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
</div>