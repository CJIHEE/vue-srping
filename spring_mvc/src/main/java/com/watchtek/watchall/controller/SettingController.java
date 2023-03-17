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

import com.watchtek.watchall.service.SettingService;
import com.watchtek.watchall.vo.AjaxResultVO;

/**
 * 셋팅 요청 컨트롤러
 * 
 * @date 2023.03.17
 * @author JIHEE
 */
@Controller
@RequestMapping("setting/")
public class SettingController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SettingController.class);

	@Autowired
	private SettingService settingService;

	/**
	 * 관리 그룹 정보를 반환한다.
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 15.
	 */
	@RequestMapping(value = "get-group.do", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResultVO getGroupData() {
		AjaxResultVO resultVO = new AjaxResultVO(false, null);
		try {
			resultVO.setData(settingService.getGroupData());
			resultVO.setSuccess(true);
		} catch (Exception e) {
			resultVO.setSuccess(false);
			LOGGER.error("[event] getGroupData : " + e.getMessage(), e);
		}
		return resultVO;
	}

	/**
	 * 관리 그룹을 추가한다.
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 15.
	 */
	@RequestMapping(value = "add-group.do", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResultVO addGroup(@RequestBody Map<String, Object> paraMap) {
		AjaxResultVO resultVO = new AjaxResultVO(false, null);
		try {
			resultVO.setData(settingService.addGroup(paraMap));
			resultVO.setSuccess(true);
		} catch (Exception e) {
			resultVO.setSuccess(false);
			LOGGER.error("[event] addGroup : " + e.getMessage(), e);
		}
		return resultVO;
	}

	/**
	 * 관리 그룹을 삭제한다
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 15.
	 */
	@RequestMapping(value = "delete-group.do", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResultVO deleteGroup(@RequestBody Map<String, Object> paraMap) {
		AjaxResultVO resultVO = new AjaxResultVO(false, null);
		try {
			resultVO.setData(settingService.deletGroup(paraMap));
			resultVO.setSuccess(true);
		} catch (Exception e) {
			resultVO.setSuccess(false);
			LOGGER.error("[event] deleteGroup : " + e.getMessage(), e);
		}
		return resultVO;
	}

	/**
	 * 관리 그룹을 수정한다
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 15.
	 */
	@RequestMapping(value = "modify-group.do", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResultVO modifyGroup(@RequestBody Map<String, Object> paraMap) {
		AjaxResultVO resultVO = new AjaxResultVO(false, null);
		try {
			resultVO.setData(settingService.modifyGroup(paraMap));
			resultVO.setSuccess(true);
		} catch (Exception e) {
			resultVO.setSuccess(false);
			LOGGER.error("[event] modifyGroup : " + e.getMessage(), e);
		}
		return resultVO;
	}

	/**
	 * 그룹별 관리 장비 정보를 반환한다
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 15.
	 */
	@RequestMapping(value = "get-group-device.do", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResultVO getGroupDevice(@RequestParam Map<String, Object> paraMap) {
		AjaxResultVO resultVO = new AjaxResultVO(false, null);
		try {
			resultVO.setData(settingService.getGroupDevice(paraMap));
			resultVO.setSuccess(true);
		} catch (Exception e) {
			resultVO.setSuccess(false);
			LOGGER.error("[event] getGroupDevice : " + e.getMessage(), e);
		}
		return resultVO;
	}

	/**
	 * 장비의 관리그룹을 변경한다
	 *
	 * @return
	 * @author JIHEE
	 * @create-date : 2023. 3. 15.
	 */
	@RequestMapping(value = "edit-group-of-device.do", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResultVO editGroupOfDevice(@RequestBody Map<String, Object> paraMap) {
		AjaxResultVO resultVO = new AjaxResultVO(false, null);
		try {
			resultVO.setData(settingService.editGroupOfDevice(paraMap));
			resultVO.setSuccess(true);
		} catch (Exception e) {
			resultVO.setSuccess(false);
			LOGGER.error("[event] editGroupOfDevice : " + e.getMessage(), e);
		}
		return resultVO;
	}

}
