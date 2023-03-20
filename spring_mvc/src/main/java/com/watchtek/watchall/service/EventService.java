package com.watchtek.watchall.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watchtek.watchall.mapper.EventMapper;
import com.watchtek.watchall.vo.DeviceVO;

/**
 * 이벤트 웹 서비스
 * 
 * @date 2021.03.22
 * @author Ju
 */
@Service
public class EventService {
	@Autowired
	EventMapper mapper;

	/**
	 * 이벤트 등급 정보를 반환한다. (테스트용)
	 *
	 * @return
	 * @author Ju
	 * @create-date : 2021. 3. 22.
	 */
	public List<Map<String, Object>> getEventLevels() {
		return mapper.getEventLevels();
	}

	/**
	 * 트리 정보를 반환한다.
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 14.
	 */
	public List<Map<String, Object>> getTreeData() {
		// 원본 데이터 받아오기
		List<Map<String, Object>> originalList = mapper.getTreeData();
		List<Map<String, Object>> changedList = new ArrayList<>();

		// 내려줄 List에 입력할 트리의그룹 값 Map에 넣기
		for (Map<String, Object> originalMap : originalList) {
			Map<String, Object> changedMap = new HashMap<>();
			changedMap.put("id", "group" + (int) ((double) originalMap.get("treeKey")));
			changedMap.put("label", originalMap.get("label"));
			changedMap.put("treeKey", "1." + (int) ((double) originalMap.get("treeKey")));

			// 변경된 List에 입력할 트리의 노드 값 List로 만들기 *시작
			List<Map<String, Object>> newChildren = new ArrayList<>();
			// 문자열을 리스트로
			List<Object> originalChildren = Arrays.asList(String.valueOf(originalMap.get("Node")).split(","));
			List<Object> originalChildrenId = Arrays.asList(String.valueOf(originalMap.get("obj_id")).split(","));
			// 노드값 Map에 넣기
			for (int i = 0; i < originalChildren.size(); i++) {
				Map<String, Object> newChild = new HashMap<>();
				// treekey로 obj_id 값 넣어주기(null 처리)
				if (originalChildrenId.get(i) != "null") {
					newChild.put("id", originalChildrenId.get(i));
					newChild.put("label", originalChildren.get(i));
					newChild.put("treeKey",
							"1." + (int) ((double) originalMap.get("treeKey")) + "." + originalChildrenId.get(i));
				}
				// 노드 Map값 List로 만들기
				newChildren.add(newChild);
			}
			// 노드값List를 내려줄 List에 Map으로 넣어주기
			changedMap.put("children", newChildren);
			// 위에서 넣어준 (그룹Map값)+(노드의 List형식의 Map값)을 최종 내려줄 List에 넣어주기
			changedList.add(changedMap);
		}
		return changedList;
	}

	/**
	 * root&group의 카드 정보를 반환한다.
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 14.
	 */
	public List<Map<String, Object>> getCardData(Map<String, Object> paraMap) {
		// 숫자인지 문자인지(장비 obj_id가 넘어왔는지 확인)
		boolean isNumeric = ((CharSequence) paraMap.get("id")).chars().allMatch(Character::isDigit);
		;
		paraMap.put("isNumeric", isNumeric);
		// 그룹 명일때만
		if (!isNumeric && !("root".equals(paraMap.get("id")))) {
			String intStr = getNumber(paraMap);
			paraMap.put("id", intStr);
		}

		return mapper.getCardData(paraMap);
	}

	/**
	 * 노드 장비 카드정보를 반환한다.
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 14.
	 */
	public Object getDeviceData(Map<String, Object> paraMap) {
		Optional<DeviceVO> devo = Optional.ofNullable(mapper.getDeviceData(paraMap));
		if (devo.isPresent()) {
			return devo.get();
		} else {
			return devo;
		}
	}

	/**
	 * 이벤트 테이블 정보를 반환한다.
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 15.
	 */
	public List<Map<String, Object>> getEvnetList(Map<String, Object> paraMap) {
		// 이벤트 목록인지 이벤트 상세보기인지
		paraMap.put("eventListOReventDetail", "eventList");

		// 숫자인지 문자인지(장비 obj_id가 넘어왔는지 확인)
		boolean isNumeric = ((CharSequence) paraMap.get("id")).chars().allMatch(Character::isDigit);
		;
		paraMap.put("isNumeric", isNumeric);
		if (!isNumeric && !("root".equals(paraMap.get("id"))) && !("chartDetail".equals(paraMap.get("id")))) {
			String intStr = getNumber(paraMap);
			paraMap.put("id", intStr);
		}

		if ("" != paraMap.get("objNameList")) {
			List<Object> objList = Arrays.asList(String.valueOf(paraMap.get("objNameList")).split(","));
			paraMap.put("objList", objList);
		}
		// total 구하기
		getPageNum(paraMap);
		return mapper.getEventList(paraMap);
	}

	/**
	 * 이벤트 성능 상세정보를 반환한다.
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 15.
	 */
	public List<Map<String, Object>> getCardDetail(Map<String, Object> paraMap) {
		// 이벤트 목록인지 이벤트 상세보기인지
		paraMap.put("eventListOReventDetail", "eventDetail");
		boolean isNumeric = ((CharSequence) paraMap.get("id")).chars().allMatch(Character::isDigit);
		paraMap.put("isNumeric", isNumeric);
		if (!isNumeric && !("root".equals(paraMap.get("id")))) {
			String intStr = String.valueOf(getNumber(paraMap));
			paraMap.put("id", intStr);
		}
		return mapper.getEventList(paraMap);
	}

	/**
	 * 차트 정보를 반환한다.
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 15.
	 */
	public Map<String, Object> getChartData(Map<String, Object> paraMap) throws ParseException {
		// 숫자인지 문자인지(장비 obj_id가 넘어왔는지 확인)
		boolean isNumeric = ((CharSequence) paraMap.get("id")).chars().allMatch(Character::isDigit);
		;
		paraMap.put("isNumeric", isNumeric);
		if (!isNumeric && !("root".equals(paraMap.get("id"))) && !("chartDetail".equals(paraMap.get("id")))) {
			String intStr = String.valueOf(getNumber(paraMap));
			paraMap.put("id", intStr);
		}

		Map<String, Object> dateMap = new HashMap<>();

		// 날짜 계산하기
		if ("" == paraMap.get("startDate") || "" == paraMap.get("endDate")) {
			paraMap.put("selectDate", "0");

		} else {
			dateMap = calData(paraMap);
			paraMap.put("dateTime1", dateMap.get("dateTime1"));
			paraMap.put("dateTime2", dateMap.get("dateTime2"));
			paraMap.put("selectDate", dateMap.get("selectDate"));
		}

		// 차트 데이터 조회
		List<Map<String, Object>> originalList = mapper.getChartData(paraMap);
		ArrayList<Object> chartList = new ArrayList<Object>();
		ArrayList<Object> categoryList = new ArrayList<Object>();
		for (Map<String, Object> originalMap : originalList) {
			categoryList.add((Object) (originalMap.get("DATETIME")));
			chartList.add((Object) (originalMap.get("COUNT")));
		}
		// 리스트 Map에 담기
		Map<String, Object> changedMap = new HashMap<String, Object>();
		if (chartList != null && categoryList != null) {
			changedMap.put("chartList", chartList);
			changedMap.put("categoryList", categoryList);
		}

		return changedMap;
	}

	/**
	 * 차트 날짜를 계산한다
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 15.
	 */
	public Map<String, Object> calData(Map<String, Object> paraMap) throws ParseException {
		Map<String, Object> dateMap = new HashMap<>();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

		// date로 변환
		String date1 = String.valueOf(paraMap.get("startDate"));
		Date format1 = dateFormat.parse(date1);
		String date2 = String.valueOf(paraMap.get("endDate"));
		Date format2 = dateFormat.parse(date2);

		long diffHor = (format2.getTime() - format1.getTime()) / 3600000; // 시 차이

		// 장비 디테일 차트
		if ("true".equals(paraMap.get("chartDetail"))) {
			dateMap.put("selectDate", "1");
		}
		// 일반 차트 경우의 수 나누기 (1시간이내/1시간~2일이내/2일이상)
		else {
			if (diffHor <= 1) {
				dateMap.put("selectDate", "1");
			} else if (diffHor > 1 && diffHor <= 48) {
				dateMap.put("selectDate", "2");
			} else {
				dateMap.put("selectDate", "3");
			}
		}

		// 캘린더로 변환
		Calendar calendarDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendarDate.setTime(format1);
		Calendar calendarDate2 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendarDate2.setTime(format2);

		// 시간 오차 맞추기
		calendarDate.add(Calendar.HOUR, 9);
		calendarDate2.add(Calendar.HOUR, 9);

		// 최종 형식 date로 변환
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String calender = sdformat.format(calendarDate.getTime());
		String calender2 = sdformat.format(calendarDate2.getTime());

		dateMap.put("dateTime1", calender);
		dateMap.put("dateTime2", calender2);

		return dateMap;
	}

	/**
	 * total 정보를 반환한다.
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 15.
	 */
	public void getPageNum(Map<String, Object> paraMap) {
		// totlaPage 조회
		if (paraMap.get("objNameList") != "") {
			List<Object> objList = Arrays.asList(String.valueOf(paraMap.get("objNameList")).split(","));
			paraMap.put("objList", objList);
		}
		int totalPageNum = mapper.getTotalPage(paraMap);

		paraMap.put("totalPageNum", totalPageNum);
	}

	public List<Map<String, Object>> getEventGrade() {
		return mapper.getEventGrade();
	}

	/**
	 * treekey 정보에서 숫자만 추출한다
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 15.
	 */
	public String getNumber(Map<String, Object> paraMap) {
		String id = String.valueOf(paraMap.get("id"));
		String intStr = id.replaceAll("[^\\d]", "");
		return intStr;
	}

}
