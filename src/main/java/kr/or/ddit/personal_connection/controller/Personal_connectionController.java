package kr.or.ddit.personal_connection.controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.alarm.model.AlarmVo;
import kr.or.ddit.alarm.service.IAlarmService;
import kr.or.ddit.career_info.model.Career_infoVo;
import kr.or.ddit.corporation.model.CorporationVo;
import kr.or.ddit.corporation.service.ICorporationService;
import kr.or.ddit.education_info.model.Education_infoVo;
import kr.or.ddit.follow.model.FollowVo;
import kr.or.ddit.follow.service.IFollowService;
import kr.or.ddit.hashtag.model.HashtagVo;
import kr.or.ddit.member.model.MemberVo;
import kr.or.ddit.personal_connection.model.Personal_connectionVo;
import kr.or.ddit.personal_connection.service.IPersonal_connectionService;
import kr.or.ddit.recruit.model.RecruitVo;
import kr.or.ddit.recruit.service.IRecruitService;
import kr.or.ddit.users.model.UsersVo;
import kr.or.ddit.util.pagination.PaginationVo;

@Controller
public class Personal_connectionController {
	
	private Logger logger = LoggerFactory.getLogger(Personal_connectionController.class);
	
	@Resource(name="personalService")
	private IPersonal_connectionService personalService; 
	
	@Resource(name="followService")
	private IFollowService followService;
	
	@Resource(name="alarmService")
	private IAlarmService alarmService;
	
	@Resource(name="recruitService")
	private IRecruitService recrService;
	
	@Resource(name="corporationService")
	private ICorporationService corpService;
	
	@RequestMapping(path={"/personalConnection"})
	public String personalConnectionView(Model model , HttpSession session, HttpServletRequest req) throws ParseException {
		
		ServletContext application = req.getServletContext();
		String path = application.getRealPath("/upload");
	      
		logger.debug(path + File.separator);
		
		MemberVo memberVo =(MemberVo) session.getAttribute("SESSION_MEMBERVO");
		
		FollowVo followVo = new FollowVo();
		followVo.setMem_id(memberVo.getMem_id());
		
		String user_id = memberVo.getMem_id();
		logger.debug("user_id++ {} " , user_id);
		
		int connections_count = personalService.connections_count(memberVo);
		int coporations_count = personalService.coporations_count(followVo);
		List<UsersVo> schoolFriends = 
				personalService.schoolFriendsSearch(user_id);
		
		model.addAttribute("connections_count" , connections_count);
		model.addAttribute("coporations_count", coporations_count);
		model.addAttribute("schoolFriends", schoolFriends);
		logger.debug("schoolFriends++ {}" , schoolFriends);
		
		/////////////////////////////// newList
		
		// 광고 부분 -> 신규 채용공고 (newList)
		List<RecruitVo> newList = recrService.getNewList();
		
		// newList size : 7. index 6 -> index 0에 add.
		newList.add(0, newList.get(6));
		
		List<String> newImgList = new ArrayList<>();
		List<String> newIdList = new ArrayList<>();
		List<String> newNmList = new ArrayList<>();
		List<String> newTimeList = new ArrayList<>();
		
		for(int i=0; i < newList.size(); i++){
			RecruitVo rVo = newList.get(i);
			CorporationVo cVo = corpService.select_corpInfo(rVo.getCorp_id());
			newImgList.add(cVo.getLogo_path());
			newIdList.add(cVo.getCorp_id());
			newNmList.add(cVo.getCorp_name());
			
			String start_date = rVo.getStart_date();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm");
			Date start = sdf.parse(start_date);
			Date now = new Date();
			
			long temp_time = now.getTime() - start.getTime();
			
			int time_diff = (int) (temp_time / (60*1000));
			
			if(time_diff < 2){
				newTimeList.add("방금");
			}else if(time_diff < 60){
				newTimeList.add(time_diff + "분");
			}else if(time_diff < 1440){
				newTimeList.add(time_diff/60 + "시간");
			}else if(time_diff < 43200){
				newTimeList.add(time_diff/(60*24) + "일");
			}else{
				newTimeList.add(time_diff/(60*24*30) + "달");
			}				
		}		
		
		model.addAttribute("newList", newList);
		model.addAttribute("newImgList", newImgList);
		model.addAttribute("newIdList", newIdList);
		model.addAttribute("newNmList", newNmList);
		model.addAttribute("newTimeList", newTimeList);
		
		/////////////////////////////// newList		
		
		return "personalTiles";
	}
	
	
	@RequestMapping(path={"/recommendUsers"})
	public String recommendUsersView(HttpSession session , Model model, PaginationVo paginationVo) {
		
		MemberVo memberVo = (MemberVo) session.getAttribute("SESSION_MEMBERVO");
		paginationVo.setUser_id(memberVo.getMem_id());
		paginationVo.setPageSize(4);
		
		List<UsersVo> userList = personalService.recommendUsers(paginationVo);
		
		model.addAttribute("userList", userList);
		
		return "/personalConnection/recommend/recommendUsers";
	}
	
	
	@RequestMapping(path={"/recommendCorpor"})
	public String recommendCorporView(HttpSession session , PaginationVo paginationVo , Model model) {
		
		MemberVo memberVo = (MemberVo) session.getAttribute("SESSION_MEMBERVO");
		paginationVo.setUser_id(memberVo.getMem_id());
		paginationVo.setPageSize(4);
		
		List<CorporationVo> corporList = personalService.recommendCorpor(paginationVo);
		
		model.addAttribute("corporList", corporList);
		
		return "/personalConnection/recommend/recommendCorpor";
	}
	
	
	@RequestMapping(path={"/connections"})
	public String connectionsView(HttpSession session , Model model , HttpServletRequest req, String sort) {
		
		MemberVo memberVo = (MemberVo) session.getAttribute("SESSION_MEMBERVO");
		List<UsersVo> personalList = null;
		
		memberVo.setMem_id(memberVo.getMem_id());
		
		
		if(sort.equals("new")) {
			personalList = personalService.select_connections(memberVo);
		}else{
			personalList = personalService.select_connectionsName(memberVo);
		}
		int connections_count = personalService.connections_count(memberVo);
		
		model.addAttribute("personalList", personalList);
		model.addAttribute("connections_count" , connections_count);
		
		return "connectionsTiles";
	}
	
	@RequestMapping(path={"/feedFollowing"})
	public String feedFollowingView(Model model, HttpSession session) {
		MemberVo memberVo =(MemberVo) session.getAttribute("SESSION_MEMBERVO");
		
		FollowVo followVo = new FollowVo();
		followVo.setMem_id(memberVo.getMem_id());
		followVo.setDivision("11");
		
		List<CorporationVo> corporationList = personalService.select_followCoporation(followVo);
		int allFollowCount = personalService.allFollowCnt(followVo);
		
		model.addAttribute("corporationList", corporationList);
		model.addAttribute("allFollowCount", allFollowCount);
		logger.debug("corporationList {}" , corporationList);
		
		return "feedFollowingTiles";
	}
	
	
	@RequestMapping(path={"/connectionReceiveApply"})
	public String connectionApplyView(HttpSession session, Model model) {
		MemberVo memberVo = (MemberVo) session.getAttribute("SESSION_MEMBERVO");
		
		String receive_id = memberVo.getMem_id();
		
		List<UsersVo> connectionReceiveList = personalService.select_connectionReceiveList(receive_id);
		logger.debug("connectionReceiveList++ {}" , connectionReceiveList);
		
		model.addAttribute("connectionReceiveList", connectionReceiveList);
		
		return "connectionReceiveApplyTiles";
	}
	
	
	@RequestMapping(path={"/receiveApply"})
	public String receiveApply(Personal_connectionVo personalVo) {
	
		personalService.update_connectionReceiveApply(personalVo);
		
		AlarmVo alarmInfo = new AlarmVo();
		alarmInfo.setMem_id(personalVo.getUser_id());
		alarmInfo.setAlarm_check("0");
		alarmInfo.setDivision("25");
		alarmInfo.setSend_id(personalVo.getReceive_id());
		alarmInfo.setAlarm_separate("05");
		
		alarmService.insert_alarmInfo(alarmInfo);
		
		return "redirect:/connectionReceiveApply";
	
	}
	
	
	@RequestMapping(path={"/receiveCancel"})
		public String receiveCancel(Personal_connectionVo personalVo) {
		
		personalService.delete_connectionCancel(personalVo);
		
			return "redirect:/connectionReceiveApply";
	}
	
	
	@RequestMapping(path={"/sendCancel"})
		public String sendCancel(Personal_connectionVo personalVo) {
		
		personalService.delete_connectionCancel(personalVo);
		
			return "redirect:/connectionSendApply";
	}
	
	
	@RequestMapping(path={"/connectionSendApply"})
	public String connectionSendApplyView(HttpSession session , Model model) {
		MemberVo memberVo = (MemberVo) session.getAttribute("SESSION_MEMBERVO");
		
		String user_id = memberVo.getMem_id();
		
		List<UsersVo> connectionSendList = personalService.select_connectionSendList(user_id);
		logger.debug("connectionSendList+++ {}" , connectionSendList);
		
		model.addAttribute("connectionSendList", connectionSendList);
		
		
		return "connectionSendApplyTiles";
	}
	
	
	@RequestMapping(path={"/filterSearch"})
	public String filterSearchView(HttpSession session , Model model) {
		MemberVo memberVo = (MemberVo) session.getAttribute("SESSION_MEMBERVO");
		
		String user_id = memberVo.getMem_id();
		
		List<UsersVo> localList 		  = personalService.filterSearchLocal(user_id);
		List<Career_infoVo> presentCorpor = personalService.filterSearchPresentCorpor(user_id);
		List<Career_infoVo> pastCorpor 	  = personalService.filterSearchPastCorpor(user_id);
		List<Career_infoVo> jobPosition   = personalService.filtersearchjobPosition(user_id);
		List<Education_infoVo> school 	  = personalService.filterSearchSchool(user_id);
		
		model.addAttribute("localList", localList);
		model.addAttribute("presentCorpor", presentCorpor);
		model.addAttribute("pastCorpor", pastCorpor);
		model.addAttribute("jobPosition", jobPosition);
		model.addAttribute("school", school);

		
		return "filterSearchTiles";
	}
	

	@RequestMapping(path={"/feed"})
	public String feedView(String str,Model model, HttpSession session) {
		MemberVo memberVo =(MemberVo) session.getAttribute("SESSION_MEMBERVO");
		
		FollowVo followVo = new FollowVo();
		followVo.setMem_id(memberVo.getMem_id());
		
		
		if (str.equals("connections")) {
			List<UsersVo> followConnections =
					personalService.select_followConnections(memberVo);
			model.addAttribute("followConnections", followConnections);
			
			return "/personalConnection/feedFilter/feedConnections";
			
		}else if(str.equals("connectionEtc")) {
			List<UsersVo> connectionsEtcList =
					personalService.select_followConnectionsEtc(memberVo);
			model.addAttribute("connectionsEtcList", connectionsEtcList);
			
			return "/personalConnection/feedFilter/feedConnectionEtc";
			
		}else if(str.equals("company")) {
			
			followVo.setDivision("11");
			List<CorporationVo> corporationList =
					personalService.select_followCoporation(followVo);
			model.addAttribute("corporationList", corporationList);
			
			return "/personalConnection/feedFilter/feedCompany";
			
		}else{
			
			List<FollowVo> hashTagList =
					personalService.select_followHashTag(memberVo);
			model.addAttribute("hashTagList", hashTagList);
			
			return "/personalConnection/feedFilter/feedHashTag";
		}
		
	}
	
	
	@RequestMapping(path={"/feedFollow"})
	public String feedFollowView(HttpSession session , Model model) {
		
		MemberVo memberVo =(MemberVo) session.getAttribute("SESSION_MEMBERVO");
		String mem_id = memberVo.getMem_id();
		
		
		FollowVo followVo = new FollowVo();
		followVo.setMem_id(memberVo.getMem_id());
		followVo.setDivision("11");
		int allFollowCount = personalService.allFollowCnt(followVo);
		
		
		List<HashMap<String,String>> resultMapList = new ArrayList<HashMap<String,String>>();
		
		List<CorporationVo> corpList = personalService.feedFollowCorporation(mem_id);
		for(CorporationVo list : corpList) {
			HashMap<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("title", list.getCorp_name());
			resultMap.put("content", list.getIndustry_type());
			resultMap.put("corp_id", list.getCorp_id());
			resultMap.put("imgPath", list.getLogo_path());
			resultMap.put("type", "c");
			resultMapList.add(resultMap);
		}
		
		List<UsersVo> UserList = personalService.feedFollowUser(mem_id);
		for(UsersVo list : UserList) {
			HashMap<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("title", list.getUser_name());
			resultMap.put("content", list.getIntroduce());
			resultMap.put("user_id", list.getUser_id());
			resultMap.put("imgPath", list.getProfile_path());
			resultMap.put("type", "u");
			resultMapList.add(resultMap);
		}
		
		List<HashtagVo> hashTagList = personalService.feedFollowHashTag(mem_id);
		for(HashtagVo list : hashTagList) {
			HashMap<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("title", list.getHashtag_name());
			resultMapList.add(resultMap);
		}
		
		Collections.shuffle(resultMapList);
		
		model.addAttribute("resultMapList",resultMapList);
		model.addAttribute("allFollowCount", allFollowCount);

		return "feedFollowTiles";
	}
	
	
	@RequestMapping(path={"/peopleSearch"})
	public String peopleSearch(String user_id,
							   String[] localArr,
							   String[] presentCorporArr,
							   String[] pastCorporArr,
							   String[] jobPositionArr,
							   String[] schoolArr){
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		ArrayList<String> localList 		= new ArrayList<>();
		ArrayList<String> presentCorporList = new ArrayList<>();
		ArrayList<String> pastCorporList 	= new ArrayList<>();
		ArrayList<String> jobPositionList 	= new ArrayList<>();
		ArrayList<String> schoolList 		= new ArrayList<>();
		
		localList.toArray(localArr);
		presentCorporList.toArray(presentCorporArr);
		pastCorporList.toArray(pastCorporArr);
		jobPositionList.toArray(jobPositionArr);
		schoolList.toArray(schoolArr);
		
		map.put("localList", localList);
		map.put("presentCorporList", presentCorporList);
		map.put("pastCorporList", pastCorporList);
		map.put("jobPositionList", jobPositionList);
		map.put("schoolList", schoolList);
		map.put("user_id", user_id);
		
		return "";
	}
	
	
	@RequestMapping(path={"/connectionSend"})
	public String connectionSend(Personal_connectionVo personalVo) {
		
		personalService.insert_connections(personalVo);
		
		AlarmVo alarmInfo = new AlarmVo();
		alarmInfo.setMem_id(personalVo.getReceive_id());
		alarmInfo.setAlarm_check("0");
		alarmInfo.setDivision("25");
		alarmInfo.setSend_id(personalVo.getUser_id());
		alarmInfo.setAlarm_separate("04");
		
		alarmService.insert_alarmInfo(alarmInfo);
		
		return "redirect:/personalConnection";
	}
	
	
	@RequestMapping(path={"/recommendUser"})
	public String recommendUser(Personal_connectionVo personalVo) {
		
		personalService.insert_connections(personalVo);
		
		AlarmVo alarmInfo = new AlarmVo();
		alarmInfo.setMem_id(personalVo.getReceive_id());
		alarmInfo.setAlarm_check("0");
		alarmInfo.setDivision("25");
		alarmInfo.setSend_id(personalVo.getUser_id());
		alarmInfo.setAlarm_separate("04");
		
		alarmService.insert_alarmInfo(alarmInfo);
		
		return "redirect:/personalConnection";
	}
	
	
	@RequestMapping(path={"/connectionOff"})
	public String connectionOff(Personal_connectionVo personalVo,String sort) {
		
		personalService.delete_connections(personalVo);
		
		return "redirect:/connections?sort="+sort;
	}
	
	
	@RequestMapping(path={"/followCorpor"})
	public String followCorpor(FollowVo followVo) {
		
		personalService.insert_followCorporation(followVo);
		
		return "redirect:/personalConnection";	
	}
	
	@RequestMapping(path={"/deleteFollow"})
	public String deleteFollow(String follow_code) {
		
		followService.delete_personalFollow(follow_code);
		
		return "redirect:/feedFollowing";	
	}
	
	@RequestMapping(path={"/insertFollow"})
	public String insertFollow(FollowVo followVo) {
		if(followVo.getDivision().equals("16")){
			followVo.setRef_keyword("#"+followVo.getRef_keyword());
		}
		
		followService.insert_feedFollow(followVo);
		
		if(followVo.getDivision().equals("11") || followVo.getDivision().equals("43")){
			
			AlarmVo alarmInfo = new AlarmVo();
			
			alarmInfo.setMem_id(followVo.getRef_keyword());
			alarmInfo.setAlarm_check("0");
			alarmInfo.setDivision("14");
			alarmInfo.setSend_id(followVo.getMem_id());
			alarmInfo.setAlarm_separate("06");
			
			alarmService.insert_alarmInfo(alarmInfo);
		}
		
		return "redirect:/feedFollow";	
	}
	
	
}
