package kr.or.ddit.util.view;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.corporation.model.CorporationVo;
import kr.or.ddit.corporation.service.ICorporationService;
import kr.or.ddit.member.model.MemberVo;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.users.model.UsersVo;
import kr.or.ddit.users.service.IUsersService;

@RequestMapping("/view")
@Controller
public class ViewController {
	
	@Resource(name="memberService")
	private IMemberService memService;
	
	@Resource(name="usersService")
	private IUsersService userService;
	
	@Resource(name="corporationService")
	private ICorporationService corpService;
	
	
	@RequestMapping("/imageView")
	public void imageView(HttpServletRequest req, HttpServletResponse resp, @RequestParam("mem_id")String mem_id, @RequestParam("division")String division) throws IOException {
		
		resp.setContentType("image");
		ServletContext application = req.getServletContext();
		
		MemberVo mVo = memService.select_memberInfo(mem_id);
		
		if(mVo == null) {
			return;
		}
		
		String path = "";
		
		if(mVo.getMem_division().equals("1")) {
			UsersVo uVo = userService.select_userInfo(mem_id);
			
			if(uVo.getProfile_path() != null && division.equals("pf")) {
				path = uVo.getProfile_path();
			} else if(uVo.getProfile_path() == null && division.equals("pf")){
				path = "/images/profile/basicProfile.png";
			} else if(uVo.getBg_path() != null && division.equals("bg")) {
				path = uVo.getBg_path();
			} else if(uVo.getBg_path() == null && division.equals("bg")) {
				path = "/images/profile/basicBackground.png";
			}
			
		} else if(mVo.getMem_division().equals("2")) {
			CorporationVo cVo = corpService.select_corpInfo(mem_id);
			
			if(cVo.getLogo_path() != null && division.equals("pf")) {
				path = cVo.getLogo_path();
			} else if(cVo.getLogo_path() == null && division.equals("pf")){
				path = "/images/corporation/basic/basicCorporation.png";
			} else if(cVo.getBg_path() != null && division.equals("bg")) {
				path = cVo.getBg_path();
			} else if(cVo.getBg_path() == null && division.equals("bg")) {
				path = "/images/profile/basicBackground.png";
			} 
			
		}
		
		FileInputStream fis = new FileInputStream(new File(application.getRealPath(path)));
		
		ServletOutputStream sos = resp.getOutputStream();
		
		byte[] buff = new byte[512];
		int len = 0; 
		while((len = fis.read(buff)) > -1) {
			sos.write(buff);
		}
		sos.close();
		fis.close();
	}
	
	
	
}
