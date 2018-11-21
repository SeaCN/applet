package com.q.wechat.service;

import java.util.List;
import java.util.Map;

import com.q.wechat.entity.meten.SuggestionBean;

public interface ISuggestionService {
	int addSugg(SuggestionBean suggestion);

	List<SuggestionBean> selectAll();

	List<Map<String, Object>> selectByParams(Map<String, Object> params);

	int updateSugg(SuggestionBean suggestion);

	SuggestionBean selectById(Integer id);

	List<Map<String, Object>> selectByUserid(int userid);

	int selectTotolNum(Map<String, Object> params);

	List<Map<String, Object>> selectByPage(Map<String, Object> params);
}
