import axios from 'axios';
import { baseUrl } from '@/config/index';

const PREFIX_URL = `${baseUrl}/event`;
const SETTING_URL = `${baseUrl}/setting`;
export default {
  /**
   * 이벤트 등급 정보를 조회한다.
   * @author Ju
   */
  getEventLevels() {
    return axios.get(`${PREFIX_URL}/get-event-levels.do`);
  },
  getTree() {
    return axios.get(`${PREFIX_URL}/get-tree.do`);
  },
  getCardData(params) {
    return axios.post(`${PREFIX_URL}/get-card.do`, {}, { params });
  },
  getDeviceData(params) {
    return axios.post(`${PREFIX_URL}/get-device.do`, {}, { params });
  },
  getEventList(params) {
    return axios.post(`${PREFIX_URL}/get-event-list.do`, params);
  },
  getEventDetail(params) {
    return axios.get(`${PREFIX_URL}/get-card-detail.do`, { params });
  },
  getChartList(params) {
    return axios.get(`${PREFIX_URL}/get-chart-data.do`, { params });
  },
  getGroupList() {
    return axios.get(`${SETTING_URL}/get-group.do`);
  },
  addGroup(params) {
    return axios.post(`${SETTING_URL}/add-group.do`, params);
  },
  deleteGroup(params) {
    return axios.post(`${SETTING_URL}/delete-group.do`, params);
  },
  modifyGroup(params) {
    return axios.post(`${SETTING_URL}/modify-group.do`, params);
  },
  getGroupDevice(params) {
    return axios.get(`${SETTING_URL}/get-group-device.do`, { params });
  },
  editGroupOfDevice(params) {
    return axios.post(`${SETTING_URL}/edit-group-of-device.do`, params);
  }

};
