<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script src="js/menu.js"></script>
<script src="js/global.js"></script>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
   <div class="container">
	   <div class="navbar-header">
	   	  <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
	          <span class="icon-bar"></span>
	          <span class="icon-bar"></span>
	          <span class="icon-bar"></span>
          </button>
	      <a class="navbar-brand" href="<%=path%>"><s:text name="header_navbar_brand"></s:text></a>
	   </div>
	   <div class="collapse navbar-collapse">
	      <ul class="nav navbar-nav" style="float:right;">

	      	<s:if test="#session.user!=null">
	         	<li class=" pull-right"><s:a namespace="/user" action="logout"><s:text name="header_logout"></s:text></s:a></li>
	         </s:if>
	         <s:else>
		         <li class="pull-right"><s:a namespace="/user" action="user_loginPage"><s:text name="header_login"></s:text></s:a></li>
	         </s:else>
	      </ul>
	   </div>
   </div>
</nav>
<script>
$('.collapse').collapse("toggle");
function clear(){writeCookie('lastMenu', '', 60*60*24);}
</script>