package com.watchtek.watchall.controller;

import java.text.ParseException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.watchtek.watchall.service.EventService;
import com.watchtek.watchall.vo.AjaxResultVO;

/**
 * 이벤트 요청 컨트롤러
 * 
 * @date 2021.03.22
 * @author Ju
 */
@Controller
@RequestMapping("event/")
public class EventController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventService eventService;

    /**
     * 이벤트 등급 정보를 반환한다. (테스트용)
     *
     * @return
     * @author Ju
     * @create-date : 2021. 3. 22.
     */
    @RequestMapping(value = "get-event-levels.do", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultVO getEventLevels()
    {
        AjaxResultVO resultVO = new AjaxResultVO(false, null);
        try
        {
            resultVO.setData(eventService.getEventLevels());
            resultVO.setSuccess(true);
        }
        catch (Exception e)
        {
            resultVO.setSuccess(false);
            LOGGER.error("[event] getEventLevels : " + e.getMessage(), e);
        }
        return resultVO;
        
    }

    
    
    //장비 트리 가져오기
    @RequestMapping(value="get-tree.do", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultVO getTreeData() 
    {
    	AjaxResultVO resultVO = new AjaxResultVO(false,null);
    	try {
    		resultVO.setData(eventService.getTreeData());
    		resultVO.setSuccess(true);
    		
    	}catch (Exception e) {
    		resultVO.setSuccess(false);
            LOGGER.error("[event] getEventLevels : " + e.getMessage(), e);
		}
    	return resultVO;
    	
    }
    
    //장비종류 카드 데이터 가져오기
    @RequestMapping(value="get-card.do", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultVO getCradData(@RequestParam("id")String id) {
    	AjaxResultVO resultVO = new AjaxResultVO(false,null);
    	HashMap<String, Object> paraMap = new HashMap<String,Object>();
    	//숫자인지 문자인지(장비 obj_id가 넘어왔는지 확인)
    	boolean isNumeric = id.chars().allMatch( Character::isDigit );
    	paraMap.put("id", id);
    	paraMap.put("isNumeric", isNumeric);
    	try {
    		resultVO.setData(eventService.getCardData(paraMap));
			resultVO.setSuccess(true);
		} catch (Exception e) {
			resultVO.setSuccess(false);
            LOGGER.error("[event] getEventLevels : " + e.getMessage(), e);
		}
    	
    	return resultVO;
    }
    
    
    //이벤트 테이블 데이터 조회
    @RequestMapping(value="get-event-list.do", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultVO getEventList(@RequestParam Map<String, Object> paraMap) {
    	AjaxResultVO resultVO = new AjaxResultVO(false,null);
    	//숫자인지 문자인지(장비 obj_id가 넘어왔는지 확인)
    	boolean isNumeric = ((CharSequence) paraMap.get("id")).chars().allMatch( Character::isDigit );
    	paraMap.put("isNumeric", isNumeric);	
    	//페이징(startRno,endRno,currentShowPageNo)
    	HashMap<String, Integer> pageMap = eventService.getPageNum(paraMap);
    	paraMap.put("startRno",pageMap.get("startRno"));
    	paraMap.put("endRno",pageMap.get("endRno"));
    	try {
    		resultVO.setData(eventService.getEvnetList(paraMap));
    		resultVO.setTotal(pageMap.get("totalPageNum"));
			resultVO.setSuccess(true);
			resultVO.setData2(eventService.getEventGrade());
		} catch (Exception e) {
			resultVO.setSuccess(false);
            LOGGER.error("[event] getEventLevels : " + e.getMessage(), e);
		}
    	return resultVO;
    }
    
    //이벤트 성능 정보 상세보기
    @RequestMapping(value="get-card-detail.do", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultVO getCardDetail(@RequestParam Map<String, Object> paraMap) {
    	AjaxResultVO resultVO = new AjaxResultVO(false,null);
    	boolean isNumeric = ((CharSequence) paraMap.get("id")).chars().allMatch( Character::isDigit );
    	paraMap.put("isNumeric", isNumeric);	
    	try {
    		resultVO.setData(eventService.getCardDetail(paraMap));
			resultVO.setSuccess(true);
		} catch (Exception e) {
			resultVO.setSuccess(false);
            LOGGER.error("[event] getEventLevels : " + e.getMessage(), e);
		}
    	System.out.println("data" + resultVO.toString());
    	return resultVO;
    }
    
    
    //차트 조회하기
    @RequestMapping(value="get-chart-data.do", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultVO getCharData(@RequestParam Map<String, Object> paraMap) {
    	AjaxResultVO resultVO = new AjaxResultVO(false,null);
    	boolean isNumeric = ((CharSequence) paraMap.get("id")).chars().allMatch( Character::isDigit );
    	paraMap.put("isNumeric", isNumeric);
    	try {
    		resultVO.setData(eventService.getChartData(paraMap));
			resultVO.setSuccess(true);
		} catch (Exception e) {
			resultVO.setSuccess(false);
            LOGGER.error("[event] getEventLevels : " + e.getMessage(), e);
		}
    	return resultVO;
    }
    
    //그룹 조회
    @RequestMapping(value="get-group.do", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultVO getGroupData() {
    	AjaxResultVO resultVO = new AjaxResultVO(false,null);
    	try {
    		resultVO.setData(eventService.getGroupData());
			resultVO.setSuccess(true);
		} catch (Exception e) {
			resultVO.setSuccess(false);
            LOGGER.error("[event] getEventLevels : " + e.getMessage(), e);
		}
    	return resultVO;
    }
    //그룹 추가
    @RequestMapping(value="add-group.do", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultVO addGroup(@RequestParam Map<String, Object> paraMap) {
    	AjaxResultVO resultVO = new AjaxResultVO(false,null);
    	try {
    		resultVO.setData(eventService.addGroup(paraMap));
			resultVO.setSuccess(true);
		} catch (Exception e) {
			resultVO.setSuccess(false);
            LOGGER.error("[event] getEventLevels : " + e.getMessage(), e);
		}
    	return resultVO;
    }
    
    
    //그룹 삭제
    @RequestMapping(value="delete-group.do", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultVO deleteGroup(@RequestParam Map<String, Object> paraMap) {
    	AjaxResultVO resultVO = new AjaxResultVO(false,null);
    	try {
    		resultVO.setData(eventService.deletGroup(paraMap));
			resultVO.setSuccess(true);
		} catch (Exception e) {
			resultVO.setSuccess(false);
            LOGGER.error("[event] getEventLevels : " + e.getMessage(), e);
		}
    	return resultVO;
    }
   
    //그룹 수정하기
    @RequestMapping(value="modify-group.do", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultVO modifyGroup(@RequestParam Map<String, Object> paraMap) {
    	AjaxResultVO resultVO = new AjaxResultVO(false,null);
    	try {
    		resultVO.setData(eventService.modifyGroup(paraMap));
			resultVO.setSuccess(true);
		} catch (Exception e) {
			resultVO.setSuccess(false);
            LOGGER.error("[event] getEventLevels : " + e.getMessage(), e);
		}
    	return resultVO;
    }
    
    //그룹 관리 대상 조회
    @RequestMapping(value="get-group-device.do", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultVO getGroupDevice(@RequestParam Map<String, Object> paraMap) {
    	AjaxResultVO resultVO = new AjaxResultVO(false,null);
    	try {
    		resultVO.setData(eventService.getGroupDevice(paraMap));
			resultVO.setSuccess(true);
		} catch (Exception e) {
			resultVO.setSuccess(false);
            LOGGER.error("[event] getEventLevels : " + e.getMessage(), e);
		}
    	return resultVO;
    }
    
    //관리대상 변경
    @RequestMapping(value="edit-group-of-device.do", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultVO editGroupOfDevice(@RequestParam Map<String, Object> paraMap) {
    	AjaxResultVO resultVO = new AjaxResultVO(false,null);
    	try {
    		resultVO.setData(eventService.editGroupOfDevice(paraMap));
			resultVO.setSuccess(true);
		} catch (Exception e) {
			resultVO.setSuccess(false);
            LOGGER.error("[event] getEventLevels : " + e.getMessage(), e);
		}
    	return resultVO;
    }
    
    
    
    //숫자인지 아닌지 판단
//    public boolean isNumeric(Map<String, Object> paraMap) {
//    	boolean isNumeric = ((CharSequence) paraMap.get("id")).chars().allMatch( Character::isDigit );
//    	return isNumeric;
//    }
    

}
