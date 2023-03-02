import axios from 'axios';
import { baseUrl } from '@/config/index';

const PREFIX_URL = `${baseUrl}/event`;
export default {
  /**
   * 이벤트 등급 정보를 조회한다.
   * @author Ju
   */
  getEventLevels() {
    return axios.get(`${PREFIX_URL}/get-event-levels.do`);
  },
};
