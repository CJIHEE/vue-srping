package com.watchtek.watchall.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.watchtek.watchall.vo.DeviceVO;

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

    /**
     * 트리 정보를 반환한다. 
     *
     * @return
     * @author JIHEE
     * @create-date : 2023. 3. 14.
     */
	public List<Map<String, Object>> getTreeData();

	/**
     * 장비 카드 정보를 반환한다.
     *
     * @return
     * @author JIHEE
     * @create-date : 2023. 3. 14.
     */
	public List<Map<String, Object>> getCardData(Map<String, Object> paraMap);

	 /**
     * 노드 장비 정보를 반환한다. 
     *
     * @return
     * @author JIHEE
     * @create-date : 2023. 3. 14.
     */
	public List<Map<String, Object>> getEventList(Map<String, Object> paraMap);

	/**
     * 이벤트 테이블 정보를 반환한다. 
     *
     * @return
     * @author JIHEE
     * @create-date : 2023. 3. 15.
     */
	public int getTotalPage(Map<String, Object> paraMap);

	/**
     * 이벤트 성능 상세정보를 반환한다. 
     *
     * @return
     * @author JIHEE
     * @create-date : 2023. 3. 15.
     */
	public List<Map<String, Object>> getEventGrade();
	
	 /**
     * 차트 정보를 반환한다. 
     *
     * @return
     * @author JIHEE
     * @create-date : 2023. 3. 15.
     */
	public List<Map<String, Object>> getChartData(Map<String, Object> paraMap);

	 /**
	  * 이벤트 성능 상세정보를 반환한다. 
	  *
	  * @return
	  * @author JIHEE
	  * @create-date : 2023. 3. 15.
	  */
	public List<Map<String, Object>> getCardDetail(Map<String, Object> paraMap);
	
	/**
     * 관리 그룹 정보를 반환한다. 
     *
     * @return
     * @author JIHEE
     * @create-date : 2023. 3. 15.
     */
	public List<Map<String, Object>> getGroupData();

	/**
     * 관리 그룹을 추가한다. 
     *
     * @return
     * @author JIHEE
     * @create-date : 2023. 3. 15.
     */
	public int addGroup(Map<String, Object> paraMap);

	/**
     * 관리 그룹을 삭제한다
     *
     * @return
     * @author JIHEE
     * @create-date : 2023. 3. 15.
     */
	public int deletGroup(Map<String, Object> paraMap);

	/**
     * 관리 그룹을 수정한다
     *
     * @return
     * @author JIHEE
     * @create-date : 2023. 3. 15.
     */
	public int modifyGroup(Map<String, Object> paraMap);

	/**
     * 그룹별 관리 장비 정보를 반환한다
     *
     * @return
     * @author JIHEE
     * @create-date : 2023. 3. 15.
     */
	public List<Map<String, Object>> getGroupDevice(Map<String, Object> paraMap);

	 /**
     * 장비의 관리그룹을 변경한다
     *
     * @return
     * @author JIHEE
     * @create-date : 2023. 3. 15.
     */
	public int addGroupOfDevice(Map<String, Object> paraMap);
	 /**
     * 장비의 관리그룹을 삭제한다
     *
     * @return
     * @author JIHEE
     * @create-date : 2023. 3. 15.
     */
	public int delteGroupOfDevice(Map<String, Object> paraMap);
	 /**
     * 그룹의 관리장비의 인덱스 최대값을 반환한다.
     *
     * @return
     * @author JIHEE
     * @create-date : 2023. 3. 15.
     */
	public int findMaxIndex();
	/**
     * 노드 장비 정보를 반환한다. 
     *
     * @return
     * @author JIHEE
     * @create-date : 2023. 3. 14.
     */
	public DeviceVO getDeviceData(Map<String, Object> paraMap);
}
