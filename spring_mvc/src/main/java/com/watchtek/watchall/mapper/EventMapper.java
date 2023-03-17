package com.watchtek.watchall.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 이벤트 매퍼
 *
 * @author Ju
 * @version 1.0
 */
public interface EventMapper
{
    /**
     * 이벤트 등급 정보를 조회한다.
     *
     * @return
     * @author Ju
     * @create-date : 2021. 3. 22.
     */
    public List<Map<String, Object>> getEventLevels();

    //트리정보 조회
	public List<Map<String, Object>> getTreeData();

	//카드 데이터 조회
	public List<Map<String, Object>> getCardData(HashMap<String, Object> paraMap);

	//이벤트 목록 조회
	public List<Map<String, Object>> getEventList(Map<String, Object> paraMap);

	//총 페이지 알아오기
	public int getTotalPage(Map<String, Object> paraMap);

	//이벤트 등급 조회
	public List<Map<String, Object>> getEventGrade();
	
	//차트 데이터 조회
	public List<Map<String, Object>> getChartData(Map<String, Object> paraMap);

	//이벤트 성능 상세 정보 조회
	public List<Map<String, Object>> getCardDetail(Map<String, Object> paraMap);
	
	//그룹 정보 조회
	public List<Map<String, Object>> getGroupData();

	//그룹 추가하기
	public int addGroup(Map<String, Object> paraMap);

	//그룹 삭제하기
	public int deletGroup(Map<String, Object> paraMap);

	//그룹 수정하기
	public int modifyGroup(Map<String, Object> paraMap);

	//그룹 관리 대상 조회하기
	public List<Map<String, Object>> getGroupDevice(Map<String, Object> paraMap);

	//장비 그룹 추가
	public int addGroupOfDevice(Map<String, Object> paraMap);
	//장비 그룹 삭제
	public int delteGroupOfDevice(Map<String, Object> paraMap);
	//인덱스 최대값 가져오기
	public int findMaxIndex();
}
