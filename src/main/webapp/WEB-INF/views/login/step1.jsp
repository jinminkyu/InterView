<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
		
		
		<div class="jk-row">
	  	 	<div id="modal-head" class="jk-modal-head">
	  	 		
	  	 		<img src="${ cp }/images/step1.png"  width="500" height="80">
	  	 		
	  	 	</div>
  	 	</div>
	  	 
	  	 
		<div class="jk-row">
		     <div id="modal-body" class="jk-modal-body">
		     		
		     		<!-- step1  -->
					<form id="step1">
						<div class="wrap-input-custom validate-input m-b-50" data-validate="Username is reauired">
							<span class="label-input100">ID</span>
							<input class="input100" type="text" id="id" name="id" placeholder=" Your ID">
							<span class="focus-input100" data-symbol="&#xf206;"></span>
						</div>
						
						<div class="wrap-input-custom2">
							<button id="idCheck" class="jk-btn-long" type="button">중복검사</button>
						</div>			 
						
						<div class="wrap-input-custom validate-input m-b-50"
							data-validate="Password is required">
							<span class="label-input100">Pass word</span>
							<input class="input100" type="password" id="pass" name="pass" placeholder=" Your Password">
							<span class="focus-input100" data-symbol="&#xf190;"></span>
						</div>
						<div class="wrap-input-custom validate-input m-b-50"
							data-validate="Username is reauired">
							<span class="label-input100">Your Name</span><input class="input100"
								type="text" id="name" name="name" placeholder=" Your Name"><span
								class="focus-input100" data-symbol="&#xf206;"></span> 
						</div>
						<div class="wrap-input-custom validate-input m-b-50"
							data-validate="Username is reauired">
							<span class="label-input100">e-mail</span><input class="input100"
								type="text" id="email" name="email" placeholder=" e-mail"><span
								class="focus-input100" data-symbol="&#xf206;"></span>
						</div>
						<input type="hidden" name="division" value="1">
					</form>
					
			 </div>
		 </div>  
	     
	     <div class="jk-row">
		     <div id="modal-footer" class="jk-modal-footer">
		     		<button id="nextStep" class="jk-join-btn" type="button">Next</button>
	    			<button id="close" class="jk-close-btn" type="button">Close</button>  
		     </div>
	   	 </div>
	   	 
	
		
		
		<script>
		
		//ID 중복체크
		var idCheck = false;
		$("#idCheck").on('click', function(){
			
			var input_id = $("#id").val();
			
			if($("#id").val().trim() == "") {
				alert("사용자 아이디를 입력해주세요	");
				$("#id").focus();
				return false;
			}
			
			console.log(input_id);
			
			$.ajax({
	  			url : "${cp}/signUp/idCheck",
	  			method : "post",
	  			data : {"id" : input_id},
	  			success : function(data){
	  				console.log(data);
	  				
	  				if(data == "error"){
	  					alert("존재하는 ID 입니다");
	  					$("#idCheck").html("사용불가");
	  					$("#idCheck").css("background", "#D94739");
	  				}
	  				
	  				if(data == "success"){
	  					alert("사용가능한 ID 입니다");
						idCheck=true;
	  					$("#id").attr("readonly", true);
	  					$("#idCheck").html("사용가능");
	  					$("#idCheck").css("background", "#7DB150");
	  				}
	  			}
		  	});
			
			
		});
		
		$("#nextStep").on('click', function(){
			
			//아이디
			if($("#id").val().trim() == "") {
				alert("사용자 아이디를 입력해주세요	");
				$("#id").focus();
				return false;
			}
			
			//비밀번호
			/* if($("#pass").val().trim() == "") {
				console.log($("#pass").val());
				alert("비밀번호를 입력해주세요");
				$("#pass").focus();
				return false;
			} */
			
			//이름
			if($("#name").val().trim() == "") {
				alert("사용자 아름을 입력해주세요");
				$("#name").focus();
				return false;
			}
			
			//이메일
			if($("#email").val().trim() == "") {
				alert("이메일을 입력해주세요");
				$("#email").focus();
				return false;
			} 
			
			if(idCheck == false) {
				alert("ID 중복체크를 통과해주세요");
				return false;
			}
			
			
			//step1 - user
			var step1 = $("#step1").serializeObject();  //.serialize();
			console.log("step1 : " + JSON.stringify(step1));
			
	  		$.ajax({
	  			
	  			url : "${cp}/signUp/error_email",
	  			method : "post",
	  			data : JSON.stringify(step1),
	  			contentType : "application/json; charset=uft-8",
	  			success : function(data){
	  				console.log(data);
	  				
	  				if(data == "error"){
	  					alert("잘못된 이메일 주소 입니다");
	  					return;
	  				}
	  				
	  				if(data == "success"){
	  					$.ajax({
		  		  			
		  		  			url : "${cp}/signUp/goStep2",
		  		  			method : "post",
		  		  			data : JSON.stringify(step1),
		  		  			contentType : "application/json; charset=uft-8",
		  		  			success : function(data){
		  		  				console.log(data);
		  		  				
		  		  				$(".jk-modal").html(data);
		  		  			}
		  		  		});
	  				}
	  			}
	  		});
		});
		
		//닫기 버튼 클릭
	  	$('#close').on('click',function(){
	  		
	  		var result = confirm("지금 까지 입력한 정보가 삭제 됩니다");
	  		
	  		if(result){
	  			$.ajax({
	  	  			
	  	  			url : "${cp}/signUp/cancel",
	  	  			method : "post",
	  	  			contentType : "application/json; charset=uft-8",
	  	  			success : function(data){
	  	  				console.log(data);
	  	  				
	  	  			}
	  	  		});
	  			
				$('.jk-modalsasun').css('display','none');
	  		} 
		});
			
	  		
		</script>
		
		
		
		
    
		
	
