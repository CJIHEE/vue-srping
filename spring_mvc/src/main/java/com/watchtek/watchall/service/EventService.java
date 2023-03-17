package com.watchtek.watchall.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.watchtek.watchall.mapper.EventMapper;

/**
 * 이벤트 웹 서비스
 * 
 * @date 2021.03.22
 * @author Ju
 */
@Service
public class EventService
{
    @Autowired
    EventMapper mapper;

    /**
     * 이벤트 등급 정보를 반환한다. (테스트용)
     *
     * @return
     * @author Ju
     * @create-date : 2021. 3. 22.
     */
    public List<Map<String, Object>> getEventLevels()
    {
        return mapper.getEventLevels();
    }

    //트리정보 조회
	public  List<Map<String, Object>> getTreeData() {
		//원본 데이터 받아오기
		List<Map<String, Object>> originalList = mapper.getTreeData();
		int id = 1; //고유값
		List<Map<String,Object>> changedList = new ArrayList<>();
		
		//내려줄 List에 입력할 트리의그룹 값 Map에 넣기
		for(Map<String,Object> originalMap : originalList) {
			Map<String, Object> changedMap = new HashMap<>();
			changedMap.put("id", originalMap.get("label"));
			changedMap.put("label", originalMap.get("label"));
			
			// 변경된 List에 입력할 트리의 노드 값 List로 만들기 *시작
			List<Map<String,Object>> newChildren = new ArrayList<>();	
			//문자열을 리스트로
			List<Object> originalChildren = Arrays.asList(String.valueOf(originalMap.get("Node")).split(","));
			List<Object> originalChildrenId = Arrays.asList(String.valueOf(originalMap.get("obj_id")).split(","));
			//노드값 Map에 넣기
			for(int i = 0; i< originalChildren.size(); i++) {
				Map<String, Object> newChild = new HashMap<>();
				//treekey로 obj_id 값 넣어주기
				newChild.put("id",originalChildrenId.get(i));
				newChild.put("label", originalChildren.get(i));
				// 노드 Map값 List로 만들기
				newChildren.add(newChild);
			}
			//노드값List를 내려줄 List에 Map으로 넣어주기 
			changedMap.put("children", newChildren);
			//위에서 넣어준 (그룹Map값)+(노드의 List형식의 Map값)을 최종 내려줄 List에 넣어주기
			changedList.add(changedMap);
        }
        // 결과 출력
        //System.out.println("최종값 : " +changedList);
		return changedList;
	}
	
	//카드 데이터 가져오기
	public List<Map<String, Object>> getCardData(HashMap<String, Object> paraMap) {		
		return mapper.getCardData(paraMap);
	}

	//이벤트 목록 조회
	public List<Map<String, Object>>getEvnetList(Map<String, Object> paraMap) {
		paraMap.put("detail","1");
		
		if(paraMap.get("objNameList") != "") {
    		List<Object> objList = Arrays.asList(String.valueOf(paraMap.get("objNameList")).split(",")); 
    		paraMap.put("objList",objList);
    	}
		return mapper.getEventList(paraMap);
		
	}
	
	//차트 데이터 조회
	public Map<String, Object> getChartData(Map<String, Object> paraMap) throws ParseException {
		
		Map<String,Object> dateMap = new HashMap<>();
		//날짜 계산하기
		if(paraMap.get("value")=="" || paraMap.get("value2")=="") {
			paraMap.put("selectDate","0");
			
		}
		else {
			dateMap = calData(paraMap);
			paraMap.put("dateTime1",dateMap.get("dateTime1"));
		    paraMap.put("dateTime2",dateMap.get("dateTime2"));
			paraMap.put("selectDate",dateMap.get("selectDate"));
		}

		//차트 데이터 조회
		
		List<Map<String, Object>> originalList = mapper.getChartData(paraMap);
		ArrayList<Object> chartList = new ArrayList<Object>();
		ArrayList<Object> categoryList = new ArrayList<Object>();
		for(Map<String,Object> originalMap : originalList) {
			categoryList.add((Object)(originalMap.get("DATETIME")));
			chartList.add((Object)(originalMap.get("COUNT")));
		}
		//리스트 Map에 담기
		Map<String,Object> changedMap= new HashMap();
		changedMap.put("chartList", chartList);
		changedMap.put("categoryList", categoryList);

		System.out.println("chartList" + changedMap.toString());
		
		return changedMap ;
	}
	
	//날짜 계산하기
	public Map<String, Object> calData(Map<String, Object> paraMap) throws ParseException {
		 Map<String,Object> dateMap = new HashMap<>(); 
		
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		 
		 //date로 변환
		 String date1 = String.valueOf(paraMap.get("value"));
		 Date format1 = dateFormat.parse(date1);
		 String date2 = String.valueOf(paraMap.get("value2"));
		 Date format2 = dateFormat.parse(date2);

		 long diffHor = (format2.getTime() - format1.getTime()) / 3600000; //시 차이
		
		 //장비 디테일 차트
		 if(paraMap.get("id").equals("chartDetail")) {
			 dateMap.put("selectDate","1");
		 }
		//일반 차트 경우의 수 나누기 (1시간이내/1시간~2일이내/2일이상)
		 else {
			 if(diffHor <= 1) {
				 dateMap.put("selectDate","1");
			 }
			 else if (diffHor > 1 && diffHor <= 48 ) {
				 dateMap.put("selectDate","2");
			 }
			 else {
				 dateMap.put("selectDate","3");
			 }
		 }
		 
		  
		 //캘린더로 변환
		 Calendar calendarDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		 calendarDate.setTime(format1);
		 Calendar calendarDate2 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		 calendarDate2.setTime(format2);
		
		 //시간 오차 맞추기
		 calendarDate.add(Calendar.HOUR, 9);
		 calendarDate2.add(Calendar.HOUR, 9);
		 
		 //최종 형식 date로 변환
		 SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String calender = sdformat.format(calendarDate.getTime());
		 String calender2 = sdformat.format(calendarDate2.getTime());
		 
		 dateMap.put("dateTime1", calender);
		 dateMap.put("dateTime2",calender2);
		 
		 return dateMap;
	}
	

	//페이징
	public HashMap<String, Integer> getPageNum(Map<String, Object> paraMap) {
		
		int currentShowPageNo = 1;
		if(paraMap.get("currentPage") != null) {
			currentShowPageNo = Integer.parseInt((String) paraMap.get("currentPage"));
		}
		int sizePerPage =25;
		if(paraMap.get("sizePerPage") != null) {
			sizePerPage = Integer.parseInt((String) paraMap.get("sizePerPage"));// 한페이지당 보여줄 게시물 건수
		}
		int startRno = ((currentShowPageNo) - 1)*sizePerPage +1;
		int endRno = startRno + sizePerPage - 1 ;
		
		HashMap<String, Integer> pageMap = new HashMap<String, Integer>();
		pageMap.put("startRno", startRno);
		pageMap.put("endRno", endRno);
		
		//totlaPage 조회
		if(paraMap.get("objNameList") != "") {
    		List<Object> objList = Arrays.asList(String.valueOf(paraMap.get("objNameList")).split(",")); 
    		paraMap.put("objList",objList);
    	}
		int totalPageNum = mapper.getTotalPage(paraMap);
		pageMap.put("totalPageNum",totalPageNum);
		return pageMap;
	}

	public List<Map<String, Object>> getEventGrade() {
		return mapper.getEventGrade();
	}
	
	//이벤트 장비 상세정보
	public List<Map<String, Object>> getCardDetail(Map<String, Object> paraMap) {
		paraMap.put("detail","2");
		return  mapper.getEventList(paraMap);
	}
	
	//그룹 조회하기
	public List<Map<String, Object>> getGroupData() {
		return mapper.getGroupData();
	}

	//그룹 추가하기
	public int addGroup(Map<String, Object> paraMap) {
		
		return mapper.addGroup(paraMap);
	}

	//그룹 삭제하기
	public int deletGroup(Map<String, Object> paraMap) {
		List<Object> deleteList= Arrays.asList(String.valueOf(paraMap.get("selectArray")).split(","));
		paraMap.put("deleteList",deleteList);
		
		return mapper.deletGroup(paraMap);
	}

	//그룹 수정
	public int modifyGroup(Map<String, Object> paraMap) {
		
		return mapper.modifyGroup(paraMap);
	}

	// 그룹 관리 장비 조회
	public List<Map<String, Object>> getGroupDevice(Map<String, Object> paraMap) {
		
		return mapper.getGroupDevice(paraMap);
	}

	//장비 그룹 변경
	@Transactional
	public int editGroupOfDevice(Map<String, Object> paraMap) {
		int n = 0;
		int maxIndex = 0;
    	if(paraMap.get("insertList") != "") {
    		List<Object> insertList = Arrays.asList(String.valueOf(paraMap.get("insertList")).split(","));
    		//다중 insert
    		for( Object objId : insertList) {
    			//기존 인덱스 최대값 
    			maxIndex = mapper.findMaxIndex();
        		paraMap.put("orderbyIndex", maxIndex+1);
    			paraMap.put("objId", objId); 
    			n += mapper.addGroupOfDevice(paraMap);
    		}
    	}
    	if(paraMap.get("deleteList") != "") {
    		List<Object> deleteList = Arrays.asList(String.valueOf(paraMap.get("deleteList")).split(","));   		
    		//다중 delete
    		for( Object objId : deleteList) {;
    			paraMap.put("objId", objId);
    			n +=mapper.delteGroupOfDevice(paraMap);
    		}
    	}
		return n;
	}
	







	
	
}
