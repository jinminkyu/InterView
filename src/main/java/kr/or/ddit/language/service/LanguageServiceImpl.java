package kr.or.ddit.language.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.language.dao.ILanguageDao;
import kr.or.ddit.language.model.LanguageVo;

@Service("languageService")
public class LanguageServiceImpl implements ILanguageService {

	@Resource(name="languageDao")
	private ILanguageDao languageDao;
	
	/**
	 * Method : insert_language
	 * 작성자 : jin
	 * 변경이력 :
	 * @param languagevo
	 * @return
	 * Method 설명 : 외국어 등록
	 */
	@Override
	public int insert_language(LanguageVo languagevo) {
		return languageDao.insert_language(languagevo);
	}

	/**
	 * Method : select_language
	 * 작성자 : jin
	 * 변경이력 :
	 * @param user_id
	 * @return
	 * Method 설명 : 사용자 외국어 등록
	 */
	@Override
	public List<LanguageVo> select_language(String user_id) {
		return languageDao.select_language(user_id);
	}

}