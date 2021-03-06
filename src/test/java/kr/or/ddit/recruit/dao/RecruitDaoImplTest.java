package kr.or.ddit.recruit.dao;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.recruit.model.RecruitVo;
import kr.or.ddit.test.LogicTestConfig;
import kr.or.ddit.users.model.UsersVo;

public class RecruitDaoImplTest extends LogicTestConfig{
	private Logger logger = LoggerFactory.getLogger(RecruitDaoImplTest.class);

	@Resource(name="recruitDao")
	private IRecruitDao recrDao;
	
	/**
	 * 
	 * Method : testGetRecrTest
	 * 작성자 : PC19
	 * 변경이력 :
	 * Method 설명 : 전체 채용공고 수 조회 테스트.
	 */
	@Test
	public void testGetRecrTest() {
		/***Given***/
		
		/***When***/
		int recrCnt = recrDao.getRecrCnt();

		/***Then***/
		assertNotNull(recrCnt);
	}
	
	/**
	 * 
	 * Method : testGetAllRecr
	 * 작성자 : PC19
	 * 변경이력 :
	 * Method 설명 : 전체 채용공고 조회 테스트.
	 */
	@Test
	public void testGetAllRecr() {
		/***Given***/

		/***When***/
		List<RecruitVo> recrList = recrDao.getAllRecr();

		/***Then***/
		assertNotNull(recrList);
	}
	
	/**
	 * 
	 * Method : testGetLastViewRecr
	 * 작성자 : PC19
	 * 변경이력 :
	 * Method 설명 : 특정 유저가 마지막으로 조회한 채용공고 조회.
	 */
	@Test
	public void testGetLastViewRecr() {
		/***Given***/

		/***When***/
		RecruitVo lVRVo = recrDao.getLastViewRecr("brown");
		logger.debug("lVRVo? : {}", lVRVo);

		/***Then***/
		assertNotNull(lVRVo);
	}
	
	/**
	 * 
	 * Method : testGetRecrByType
	 * 작성자 : PC19
	 * 변경이력 :
	 * Method 설명 : 특정 업무분야로 채용공고 목록 조회.
	 */
	@Test
	public void testGetRecrByType() {
		/***Given***/

		/***When***/
		List<RecruitVo> searchList = recrDao.getRecrByType("웹테스터");

		/***Then***/
		assertNotNull(searchList);
	}

	/**
	 * 
	 * Method : testUpdateRecr
	 * 작성자 : PC19
	 * 변경이력 :
	 * Method 설명 : 채용공고 수정 테스트.
	 */
	@Test
	public void testUpdateRecr() {
		/***Given***/
		RecruitVo rVo = recrDao.getRecr("1");
		rVo.setApp_count(rVo.getApp_count());

		/***When***/
		int updateCnt = recrDao.updateRecr(rVo);

		/***Then***/
		assertNotNull(updateCnt);
	}
	
	/**
	 * 
	 * Method : testSearchRecrListByCorp_name
	 * 작성자 : PC19
	 * 변경이력 :
	 * Method 설명 : 특정 회사 이름으로 검색한 채용공고 목록 조회 테스트.
	 */
	@Test
	public void testSearchRecrListByCorp_name() {
		/***Given***/

		/***When***/
		List<RecruitVo> recrList = recrDao.searchRecrListByCorp_name("lg");

		/***Then***/
		assertNotNull(recrList);
	}
	
	// 날짜 변환 테스트.
	@Test
	public void testDate() throws ParseException {
		/***Given***/
		RecruitVo rVo = recrDao.getRecr("29");
		String start_date = rVo.getStart_date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm");
		Date start = sdf.parse(start_date);	
		Date now = new Date();
		
		int temp_time = (int) (now.getTime() - start.getTime());		
		int time_diff = temp_time / (60*1000);

		/***When***/
		logger.debug("now time? : {}", now.getTime());
		logger.debug("start time? : {}", start.getTime());
		logger.debug("time_diff? : {}", time_diff);

		/***Then***/
		//assert
	}

	/**
	 * 
	 * Method : testGetNewList
	 * 작성자 : PC19
	 * 변경이력 :
	 * Method 설명 : 신규 채용공고 목록 조회 테스트.
	 */
	@Test
	public void testGetNewList() {
		/***Given***/

		/***When***/
		List<RecruitVo> newList = recrDao.getNewList();

		/***Then***/
		assertNotNull(newList);
	}

	/**
	 * 
	 * Method : testSearchRecrListByJob_local
	 * 작성자 : PC19
	 * 변경이력 :
	 * Method 설명 : 특정 지역으로 검색한 채용공고 목록 조회 테스트.
	 */
	@Test
	public void testSearchRecrListByJob_local() {
		/***Given***/

		/***When***/
		List<RecruitVo> recrList = recrDao.searchRecrListByJob_local("대전");
		logger.debug("list size? : {}", recrList.size());

		/***Then***/
		assertNotNull(recrList);
	}
	
	/**
	 * 
	 * Method : testGetAllRecrDesc
	 * 작성자 : PC19
	 * 변경이력 :
	 * Method 설명 : 날짜 역순으로 전체 채용공고 조회 테스트.
	 */
	@Test
	public void testGetAllRecrDesc() {
		/***Given***/

		/***When***/
		List<RecruitVo> recrList = recrDao.getAllRecrDesc();

		/***Then***/
		assertNotNull(recrList);
	}

	/**
	 * 
	 * Method : testGetPersonalUserId
	 * 작성자 : PC19
	 * 변경이력 :
	 * Method 설명 : 특정 채용공고의 회사에 경력이 있는 일촌 목록 조회 테스트.
	 */
	@Test
	public void testGetPersonalUserId() {
		/***Given***/

		/***When***/
		List<String> personalUserIdList = recrDao.getPersonalUserId("5/joker");

		/***Then***/
		assertNotNull(personalUserIdList);
	}


	
	
	
	
	
}



