<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="/css/personalConnection/personalConnection.css" rel="stylesheet">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$(document).ready(function() {
	var divWidth  = "511"; 
	$("#btnslidelt").on("click",function(){
		$("#content").stop(true,true);
		
		   var moveX   = parseInt($("#content").css("margin-left"));
	
		   if( moveX < 0 )
		   {
		    $("#content").animate({"margin-left":"+=" + divWidth + "px"},500);
		   }
	});

	$("#btnSlidegt").on("click",function(){
		 $("#content").stop(true,true);
	
		   var moveX   = parseInt($("#content").css("margin-left"));
	
		   if( -1022 < moveX )
		   {
		    $("#content").animate({"margin-left":"-=" + divWidth + "px"},500);
		   }
	});
	
	$(document).scroll(function() {
	    var maxHeight = $(document).height();
	    var currentScroll = $(window).scrollTop() + $(window).height();

	    if (maxHeight <= currentScroll + 100) {
	    	$.ajax({
	    		type : "POST",
 	    		url : "/personalConnection",
//  	    		dataType : "JSON",    //옵션이므로 JSON으로 받을게 아니면 안써도 됨
 	    		data : { },
	    		success : function(result) {
	    		//통신이 성공적으로 이루어졌을 때 처리하고 싶은 함수
	    			$("#content2").append('<li><div class="whiteBox">1sdfgsdfgsdfgsdfg<br>sadf</div></li><li><div class="whiteBox">2sdfgsdfgsdfgsdfg<br>sadf</div></li><li><div class="whiteBox">3sdfgsdfgsdfgsdfg<br>sadf</div></li><li><div class="whiteBox">4sdfgsdfgsdfgsdfg<br>sadf</div></li><li><div class="whiteBox">5sdfgsdfgsdfgsdfg<br>sadf</div></li><li><div class="whiteBox">6sdfgsdfgsdfgsdfg<br>sadf</div></li><li><div class="whiteBox">7sdfgsdfgsdfgsdfg<br>sadf</div></li><li><div class="whiteBox">8sdfgsdfgsdfgsdfg<br>sadf</div></li><li><div class="whiteBox">9sdfgsdfgsdfgsdfg<br>sadf</div></li>');
	    		}
	    	});
	    }
	});
});

</script>
<div class="container">
<div class="row">
<div>
<div class="row">
			  <div id="pc_leftmenu" class="col-md-3">
			        <div id="pc_leftmenuBox" class="whiteBox">
			         	<a href="/connections">
			         		<span style="padding-right: 5px;"><i class="fas fa-user-friends"></i></span>1촌 ${connections_count }명
			         	</a>
			           <a id="pc_leftmenuBorder" href="/feedFollowing">
			           	<span><i class="fas fa-building" ></i></span>회사 (${coporations_count})개
			           </a>
			        </div>
			  </div>
				<div class="col-md-6" style="margin-right: -25px; ">
					<div class="row">
						<div class="col-md-12" >
							<div id="pc_resiceConnection" class="whiteBox">
								<label style="padding-right: 295px;">받은 일촌 신청 </label><a href="/connectionReceiveApply">모두 보기</a><br>
							</div>
						</div>
						<div class="col-md-12" style="padding: 20px 0 20px 15px; ">
							<div id="pc_find" class="whiteBox">
								<label style="padding-right: 300px;padding-top: 10px">아는 동문 찾기</label>
								<button id="btnslidelt" class="btn btn-default" style="border: 0px;margin-left: 28px;">&lt;</button>
								<button id="btnSlidegt" class="btn btn-default" style="border: 0px;">&gt;</button><br/>
								<ul  id="content" style="list-style:none;width:3000px; padding-left: 5px;">
									<c:forEach items="${schoolFriends }" var="friend">
										<li>
											<div class="whiteBox">
												<div style="background-image: url(/background?mem_id=${friend.user_id});height: 70px; margin-top: -15px;"></div>
												<div style=" margin-top: -50px;">
													<div style="width: 108px;height: 108px;background-image:url(/profile?mem_id=${friend.user_id});background-repeat: no-repeat;background-size: cover;background-position: center;margin-left: 30px;border: 4px solid #E3EEF2;border-radius: 100px;"></div>
													<div style="margin-top: 5px;"><strong>${friend.user_name}</strong></div>
													<div style="font-size: 16px;padding-left: 5px;padding-right: 5px; text-overflow: ellipsis; display: inline-block; width: 146.97px; white-space: nowrap; overflow: hidden;margin-bottom: 50px;">${friend.introduce}</div>
													<button class="btn btn-default" style="border-color: #0073b1;border-style: solid;padding-left: 40px;padding-right: 40px;">1촌 맺기</button>
												</div>
											</div>
										</li>
									</c:forEach>
								</ul>
							</div>
						</div>
						<div class="col-md-12">
							<div id="pc_find" class="whiteBox" style="width:512px;height:auto; box-sizing: content-box; ">
								<label style="font-size: 17px;">회원님을 위한 맞춤 추천</label>
								<label style="font-size: 17px;"><a href="*">mentos</a></label>
								<ul  id="content2" style="list-style:none;width:553px; padding-left: 0px; padding-top: 10px">
									<li><div class="whiteBox">1sdfgsdfgsdfgsdfg<br>sadf</div></li>
									<li><div class="whiteBox">2sdfgsdfgsdfgsdfg<br>sadf</div></li>
									<li><div class="whiteBox">3sdfgsdfgsdfgsdfg<br>sadf</div></li>
									<li><div class="whiteBox">4sdfgsdfgsdfgsdfg<br>sadf</div></li>
									<li><div class="whiteBox">5sdfgsdfgsdfgsdfg<br>sadf</div></li>
									<li><div class="whiteBox">6sdfgsdfgsdfgsdfg<br>sadf</div></li>
									<li><div class="whiteBox">7sdfgsdfgsdfgsdfg<br>sadf</div></li>
									<li><div class="whiteBox">8sdfgsdfgsdfgsdfg<br>sadf</div></li>
									<li><div class="whiteBox">9sdfgsdfgsdfgsdfg<br>sadf</div></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-2" >
					<div class="whiteBox" style="width:314px; height: 256px;">Level 2: .col-xs-4 .col-sm-6</div>
					footer
				</div>
</div>
</div>
</div>
</div>
