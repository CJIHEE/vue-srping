package com.watchtek.watchall.controller;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
public class EventController {
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
	public AjaxResultVO getEventLevels() {
		AjaxResultVO resultVO = new AjaxResultVO(false, null);
		try {
			resultVO.setData(eventService.getEventLevels());
			resultVO.setSuccess(true);
		} catch (Exception e) {
			resultVO.setSuccess(false);
			LOGGER.error("[event] getEventLevels : " + e.getMessage(), e);
		}
		return resultVO;

	}

	/**
	 * 트리 정보를 반환한다.
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 14.
	 */
	@RequestMapping(value = "get-tree.do", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResultVO getTreeData()

	{
		AjaxResultVO resultVO = new AjaxResultVO(false, null);
		try {
			resultVO.setData(eventService.getTreeData());
			resultVO.setSuccess(true);

		} catch (Exception e) {
			resultVO.setSuccess(false);
			LOGGER.error("[event] getTreeData : " + e.getMessage(), e);
		}
		return resultVO;

	}

	/**
	 * 장비 카드 정보를 반환한다.
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 14.
	 */
	@RequestMapping(value = "get-card.do", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResultVO getCradData(@RequestParam Map<String, Object> paraMap) {
		AjaxResultVO resultVO = new AjaxResultVO(false, null);
		try {
			resultVO.setData(eventService.getCardData(paraMap));
			resultVO.setSuccess(true);
		} catch (Exception e) {
			resultVO.setSuccess(false);
			LOGGER.error("[event] getCradData : " + e.getMessage(), e);
		}

		return resultVO;
	}

	/**
	 * 노드 장비 정보를 반환한다.
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 14.
	 */
	@RequestMapping(value = "get-device.do", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResultVO getDeviceData(@RequestParam Map<String, Object> paraMap) {
		AjaxResultVO resultVO = new AjaxResultVO(false, null);
		try {
			resultVO.setData(eventService.getDeviceData(paraMap));
			resultVO.setSuccess(true);
		} catch (Exception e) {
			resultVO.setSuccess(false);
			LOGGER.error("[event] getDeviceData : " + e.getMessage(), e);
		}
		return resultVO;
	}

	/**
	 * 이벤트 테이블 정보를 반환한다.
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 15.
	 */
	@RequestMapping(value = "get-event-list.do", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResultVO getEventList(@RequestBody Map<String, Object> paraMap) {
		AjaxResultVO resultVO = new AjaxResultVO(false, null);
		try {
			resultVO.setData(eventService.getEvnetList(paraMap));
			resultVO.setTotal(paraMap.get("totalPageNum"));
			resultVO.setSuccess(true);
		} catch (Exception e) {
			resultVO.setSuccess(false);
			LOGGER.error("[event] getEventList : " + e.getMessage(), e);
		}
		return resultVO;
	}

	/**
	 * 이벤트 성능 상세정보를 반환한다.
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 15.
	 */
	@RequestMapping(value = "get-card-detail.do", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResultVO getCardDetail(@RequestParam Map<String, Object> paraMap) {
		AjaxResultVO resultVO = new AjaxResultVO(false, null);
		try {
			resultVO.setData(eventService.getCardDetail(paraMap));
			resultVO.setSuccess(true);
		} catch (Exception e) {
			resultVO.setSuccess(false);
			LOGGER.error("[event] getCardDetail : " + e.getMessage(), e);
		}
		return resultVO;
	}

	/**
	 * 차트 정보를 반환한다.
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 15.
	 */
	@RequestMapping(value = "get-chart-data.do", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResultVO getChartData(@RequestParam Map<String, Object> paraMap) {
		AjaxResultVO resultVO = new AjaxResultVO(false, null);
		try {
			resultVO.setData(eventService.getChartData(paraMap));
			resultVO.setSuccess(true);
		} catch (Exception e) {
			resultVO.setSuccess(false);
			LOGGER.error("[event] getChartData : " + e.getMessage(), e);
		}
		return resultVO;
	}

}
