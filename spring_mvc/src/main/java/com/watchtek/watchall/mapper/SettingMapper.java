package com.watchtek.watchall.mapper;

import java.util.List;
import java.util.Map;

import com.watchtek.watchall.vo.DeviceVO;

/**
 * 셋팅 매퍼
 *
 * @author JIHEE
 * @version 1.0
 */

public interface SettingMapper {
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

}
