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
public interface EventMapper {
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
	 * 노드 장비 정보를 반환한다.
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 14.
	 */
	public DeviceVO getDeviceData(Map<String, Object> paraMap);
}
