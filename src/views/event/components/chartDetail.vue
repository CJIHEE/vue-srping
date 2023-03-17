<template>
  <div>
    <div class="chart">
      <div class="block">
        <el-date-picker
          v-model="startDate"
          type="datetime"
          placeholder="Select date and time"
          style="margin : 25px 5px;"
        />
        <el-date-picker
          v-model="endDate"
          type="datetime"
          placeholder="Select date and time"
          :disabled="disabledDate"
        />
      </div>
    </div>
    <div>
      <commonChart
        :id="id"
        ref="chartDetail"
      />
    </div>
  </div>
</template>
<script>
/**
 * ? REVIEW:
 * 코드 인덴트 확인
 * lint rule은 항상 확인 후 모두 처리
 * 컴포넌트, 인스턴스 옵션 순서 일관성있고 정렬되게 선언
 * !! 변수 반드시 의미있게 작성해주세요 (startDate, value3이 뭔지 다른사람은 모릅니다)
 * !! chart와 중복된 코드는 재사용성을 위해 붆리하도록
 */
import dayjs from 'dayjs';
import EventApi from '@/api/event';
import commonChart from './CommonChart';

export default {
  name: 'CharDetailList',
  components: { commonChart },
  props: {
    deviceDetailName: {
      type: String,
      default: '',
    },
    id: {
      type: [String, Number],
      default: 'root',
    },
    drawer: {
      type: [Boolean],
      required: true,
    },
  },
  data() {
    return {
      chartList: [],
      categorytyList: [],
      startDate: '2023-02-22T15:00:00.000Z',
      endDate: '2023-02-23T15:00:00.000Z',
      rndChartArray: [],
      rndChartArray2: [],
      disabledDate: false,
      categoryList: [],
    };
  },
  watch: {
    drawer() {
      this.loadCharList();
      this.startDate = '2023-02-22T15:00:00.000Z';
      this.endDate = '2023-02-23T15:00:00.000Z';
    },
    startDate() {
      this.checkDate();
    },
    endDate() {
      this.checkDate();
    },
  },
  created() {
    this.loadCharList();
  },
  methods: {
    checkDate() {
      const startDay = dayjs(this.startDate).format('YYYY-MM-DD HH:mm:ss');
      const endDay = dayjs(this.endDate).format('YYYY-MM-DD HH:mm:ss');
      if (startDay > endDay) {
        this.disabledDate = true;
        this.$message({
          message: `${startDay} 보다 이전 일자는 검색할 수 없습니다.`,
          type: 'error',
        });
      } else {
        this.disabledDate = false;
        this.loadCharList();
      }
    },
    loadCharList() {
      const params = { id: 1, startDate: this.startDate, endDate: this.endDate, chartDetail: 'true' };
      EventApi.getChartList(params)
        .then((response) => {
          this.categoryList = response.data.data.categoryList;
          //난수 발생
          this.creatRandomNumber();
          //데이터 set
          const chartTitle = '성능 추이';
          this.$refs.chartDetail.setChartDetailData(this.categoryList, this.rndChartArray, this.rndChartArray2, chartTitle);
        })
        .catch((ex) => {
          console.log(ex);
        });
    },
    //난수 발생
    creatRandomNumber() {
      this.rndChartArray = [];
      this.categoryList.forEach((categoriesValue) => {
        let rnd = Math.floor(Math.random() * 101);
        const rndArray = [];
        rndArray.push(categoriesValue);
        rndArray.push(rnd);
        this.rndChartArray.push(rndArray);
      });
      this.rndChartArray2 = [];
      this.categoryList.forEach((categoriesValue) => {
        let rnd = Math.floor(Math.random() * 101);
        const rndArray2 = [];
        rndArray2.push(categoriesValue);
        rndArray2.push(rnd);
        this.rndChartArray2.push(rndArray2);
      });
    },
  },
};
</script>

<style scoped>
.title {
  margin-bottom: 15px;
}
.chart {
  text-align: center;
}
</style>
