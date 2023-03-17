<template>
  <div>
    <div class="chart">
      <div class="block">
        <el-date-picker
          v-model="startDate"
          type="datetime"
          placeholder="Select date and time"
          style="margin-right: 10px;"
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
        ref="chart"
      />
    </div>
  </div>
</template>


<script>
import dayjs from 'dayjs';
import EventApi from '@/api/event';
import commonChart from './CommonChart';

export default {
  name: 'CharList',
  components: { commonChart },
  props: {
    id: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      chartList: [],
      categorytyList: [],
      startDate: '',
      endDate: '',
      testdate: '',
      disabledDate: false,
      chartData: [],
      check: 'chart',
    };
  },
  watch: {
    id: {
      handler() {
        if (this.id === '4') {
          this.startDate = '2023-02-16T15:00:00.000Z';
          this.endDate = '2023-02-22T15:00:00.000Z';
        } else {
          (this.startDate = '2023-02-21T15:00:00.000Z'), (this.endDate = '2023-02-27T15:00:00.000Z');
        }
        this.getCharttList();
      },
      immediate: true,
    },
    startDate() {
      this.checkDate();
    },
    endDate() {
      this.checkDate();
    },
  },
  methods: {
    getCharttList() {
      const params = { id: this.id, startDate: this.startDate, endDate: this.endDate };
      EventApi.getChartList(params)
        .then((response) => {
          if (response.data.success === true) {
            if (response.data.data.chartList.length > 0) {
              this.chartList = response.data.data.chartList;
              this.categoryList = response.data.data.categoryList;
              this.setChartData();
              const chartTitle = '이벤트 이력';
              this.$refs.chart.setChartData(this.categoryList, this.chartData, chartTitle);
            } else {
              this.chartList = [];
              this.categorytyList = [];
              this.setChartData();
            }
          } else {
            this.$message({
              message: '데이터 전송이 실패했습니다',
              type: 'error',
            });
          }
        })
        .catch((e) => {
          this.$message({
            message: '차트를 조회하지 못했습니다',
            type: 'error',
          });
          throw new Error(e);
        });
    },
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
        this.getCharttList();
      }
    },
    setChartData() {
      //데이터 초기화
      this.chartData = [];
      //차트데이터 만들기
      this.chartList.forEach((chartValue) => {
        this.chartData.push(chartValue);
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
