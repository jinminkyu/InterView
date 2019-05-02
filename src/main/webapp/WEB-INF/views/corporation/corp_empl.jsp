<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!DOCTYPE html>
<html>
<head>
<!-- Downloaded chart.css -->
<link rel="stylesheet" href="chart.css">

<!-- RawGit CDN chart.css -->
<link rel="stylesheet" href="https://cdn.rawgit.com/theus/chart.css/v1.0.0/dist/chart.css" />

</head>
<body>
	<div class="container">
		<div class="row">
			<div style="margin-left: 15px;">
				<%@ include file="/WEB-INF/views/corporation/module/top.jsp"%>
				<br>
				<%@ include file="/WEB-INF/views/corporation/module/left.jsp"%>
				<br>
				
 				<div style="margin-top: -157px; margin-left: 240px; border : 1px solid;width: 900px;background-color: white;">
 				
 				
<%-- 				<c:if employee_count ne '0'> --%>
		
<!-- 					<div style="border : 1px solid;width:350px;margin-left: 10px;margin-top: 10px;"> -->
<%-- 	출신학교 | ${ecount } | ${a.get(2)} --%>
<!-- 						<div class="charts"> -->
<%-- 								${eec.get(0).school_name } | ${eec2.get(0) } --%>
<%-- 								<div class="charts__chart chart--p${a.get(0)} chart--default" data-percent></div> --%>
<%-- 								${eec.get(1).school_name } | ${eec2.get(1) } --%>
<%-- 								<div class="charts__chart chart--p${a.get(1)} chart--default" data-percent></div> --%>
<%-- 								${eec.get(2).school_name } | ${eec2.get(2) } --%>
<%-- 								<div class="charts__chart chart--p${a.get(2)} chart--default" data-percent></div> --%>
<!-- 								<div class="charts__chart chart--p30 chart--default" data-percent></div> -->
<!-- 						</div> -->
<!-- 					</div>  -->
<!-- 					<div style="border : 1px solid;width:350px;margin-left: 370px;margin-top: -178px;"> -->



	전공 | ${ecount }
						<div class="charts">
								${em.get(0).major } | ${emc.get(0) }
								<div class="charts__chart chart--p${b.get(0)} chart--default" data-percent></div>
								${em.get(1).major } | ${emc.get(1) }
								<div class="charts__chart chart--p${b.get(1)} chart--default" data-percent></div>
								${em.get(2).major } | ${emc.get(2) }
								<div class="charts__chart chart--p${b.get(2)} chart--default" data-percent></div>
						</div>
<!-- 					</div>  -->
<%-- 					</c:if> --%>
					
				</div>
				<div style="margin-left:239px;margin-top:20px; border : 1px solid;;">

					<c:forEach var="uvos_userId" items="${uvos}">
						<div style="border:1px solid;height: 200px; width:200px; display:inline-block;background-color: white;margin-right: 20px;">
							

							<c:out value="${uvos_userId.user_id}"  /><br>
							<c:out value="${uvos_userId.user_name}"  /><br>
<%-- 							<c:out value="${uvos_userId.profile_path}" /><br> --%>
						</div>
					</c:forEach>

				</div>
		</div>
	</div>




</body>
</html>