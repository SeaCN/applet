package com.q.wechat.dao;

import java.util.List;
import java.util.Map;

import com.q.wechat.entity.meten.FeedbackBean;
public interface IFeedbackDao {
	int addFeedback(FeedbackBean feedback);
	List<FeedbackBean> selectAll();
	List<FeedbackBean> selectByUserid(int userid);
	int selectTotolNum(Map<String, Object> params);
	List<Map<String, Object>> selectByPage(Map<String, Object> params);
	List<Map<String, Object>> selectBySugg(Integer suggestionid);
}
