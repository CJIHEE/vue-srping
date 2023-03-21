package com.watchtek.watchall.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.watchtek.watchall.mapper.SettingMapper;

/**
 * 이벤트 웹 서비스
 * 
 * @date 2023.03.17
 * @author JIHEE
 */
@Service
public class SettingService {
	@Autowired
	SettingMapper mapper;

	/**
	 * 관리 그룹 정보를 반환한다.
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 15.
	 */
	public List<Map<String, Object>> getGroupData() {
		return mapper.getGroupData();
	}

	/**
	 * 관리 그룹을 추가한다.
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 15.
	 */
	public int addGroup(Map<String, Object> paraMap) {

		return mapper.addGroup(paraMap);
	}

	/**
	 * 관리 그룹을 삭제한다
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 15.
	 */
	public int deletGroup(Map<String, Object> paraMap) {
		List<Object> deleteList = Arrays.asList(String.valueOf(paraMap.get("selectArray")).split(","));
		paraMap.put("deleteList", deleteList);

		return mapper.deletGroup(paraMap);
	}

	/**
	 * 관리 그룹을 수정한다
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 15.
	 */
	public int modifyGroup(Map<String, Object> paraMap) {

		return mapper.modifyGroup(paraMap);
	}

	/**
	 * 그룹별 관리 장비 정보를 반환한다
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 15.
	 */
	public List<Map<String, Object>> getGroupDevice(Map<String, Object> paraMap) {

		return mapper.getGroupDevice(paraMap);
	}

	/**
	 * 장비의 관리그룹을 변경한다
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 15.
	 */
	@Transactional
	public int editGroupOfDevice(Map<String, Object> paraMap) {
		int successCount = 0;
		int maxIndex = 0;
		if (!("".equals(paraMap.get("insertList")))) {
			List<Object> insertList = Arrays.asList(String.valueOf(paraMap.get("insertList")).split(","));
			// 다중 insert
			for (Object objId : insertList) {
				// 기존 인덱스 최대값
				maxIndex = mapper.findMaxIndex();
				paraMap.put("orderbyIndex", maxIndex + 1);
				paraMap.put("objId", objId);
				successCount += mapper.addGroupOfDevice(paraMap);
			}
		}
		if (!("".equals(paraMap.get("deleteList")))) {
			List<Object> deleteList = Arrays.asList(String.valueOf(paraMap.get("deleteList")).split(","));
			// 다중 delete
			for (Object objId : deleteList) {
				paraMap.put("objId", objId);
				successCount += mapper.delteGroupOfDevice(paraMap);
			}
		}
		return successCount;
	}

}
