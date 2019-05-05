package kr.or.ddit.follow.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.follow.model.FollowVo;
import kr.or.ddit.util.pagination.PaginationVo;

public interface IFollowService {

	int insert_follow(FollowVo followVo);

	int delete_follow(FollowVo followVo);

	List<FollowVo> select_followList(String mem_id);

	List<FollowVo> select_followKindList(PaginationVo paginationVo);
	
	int getFollowingCnt(String mem_id);
	
	int getFollowerCnt(String mem_id);
	
	List<FollowVo> select_followerList(String mem_id);
	
	List<FollowVo> select_followingList(String mem_id);
	
	/**
	 * Method : select_oneFollow
	 * 작성자 : jin
	 * 변경이력 :
	 * @param followVo
	 * @return
	 * Method 설명 : 사용자와 상대방과의 팔로우 검색
	 */
	FollowVo select_oneFollow(FollowVo followVo);
	
	/**
	 * Method : select_hashtagFollowCount
	 * 작성자 : goo84
	 * 변경이력 :
	 * @param ref_keyword
	 * @return
	 * Method 설명 : 해시태그 팔로워 수 조회
	 */
	int select_hashtagFollowCount(String ref_keyword);
	
	/**
	 * Method : select_followHashtagInfo
	 * 작성자 : goo84
	 * 변경이력 :
	 * @param followVo
	 * @return
	 * Method 설명 : 태그 팔로우 유무체크
	 */
	int select_followHashtagInfo(FollowVo followVo);
	
	int delete_personalFollow(String follow_code);
	
	int insert_feedFollow(FollowVo followVo);
	
	/**
	 * Method : inseret_corpFollow
	 * 작성자 : goo84
	 * Method : select_followChatList
	 * 작성자 : jin
	 * 변경이력 :
	 * @param mem_id
	 * @return
	 * Method 설명 : 채팅 초대할 유저의 팔로우들(회사,회원)
	 */
	List<Map<String, String>> select_followChatList(String mem_id);
	 
	/**
	 * Method : follow_unfollow
	 * 작성자 : jin
	 * 변경이력 :
	 * @param followVo
	 * @return
	 * Method 설명 : 회사 팔로우
	 * Method 설명 :회사 팔로우 언팔로우 유무체크
	 */
	int insert_corpFollow(FollowVo followVo);
	
	/**
	 * Method : delete_corpFollow
	 * 작성자 : goo84
	 * 변경이력 :
	 * @param followVo
	 * @return
	 * Method 설명 : 회사 언팔로우
	 */
	int delete_corpFollow(FollowVo followVo);
	
	int insert_userFollow(FollowVo followVo);

	int delete_userFollow(FollowVo followVo);
	
	/**
	 * Method : select_hashtagFollowList
	 * 작성자 : jin
	 * 변경이력 :
	 * @param user_id
	 * @return
	 * Method 설명 : 유저가 팔로우한 해쉬태그 리스트 조회
	 */
	List<FollowVo> select_hashtagFollowList (String user_id);
}
