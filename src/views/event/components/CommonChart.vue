<template>
  <div>
    <div class="chart">
      <highcharts :options="chartOptions" />
    </div>
  </div>
</template>

<script>
export default {
  name: 'CommonChart',
  props: {
    id: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      chartTitle: '이벤트 이력',
      chartOptions: {
        chart: { 
          zoomType: 'x' 
        },
        title: {
          text: '',
          align: 'left',
        },
        xAxis: {
          categories: this.categoryList,
        },
        yAxis: {
          title: {
            text: '',
          },
        },
        legend: {
          enabled: false,
        },
        series: [{}],
      },
    };
  },
  watch: {
    check: {
      handler() {
        if (this.check === 'chart') {
          this.setChartData();
        }
      },
      immediate: true,
    },
  },
  methods: {
    setChartData(categoryList, chartData, chartTitle) {
      //데이터 초기화
      this.chartOptions.series = [];
      //데이터 set
      this.chartOptions.title.text = chartTitle;
      this.chartOptions.title.align = 'left';
      this.chartOptions.xAxis.categories = categoryList;
      this.chartOptions.series.push({ color: 'rgb(161, 120, 161)', type: 'area', data: chartData });
    },
    setChartDetailData(categoryList, rndChartArray, rndChartArray2, chartTitle) {
      //데이터 초기화
      this.chartOptions.series = [];
      //데이터 set
      this.chartOptions.title.text = chartTitle;
      this.chartOptions.title.align = 'center';
      this.chartOptions.xAxis.categories = categoryList;
      this.chartOptions.legend.enabled = true;
      this.chartOptions.series.push({ name: '난수 데이터1', data: rndChartArray });
      this.chartOptions.series.push({ name: '난수 데이터2', color: 'green', data: rndChartArray2 });
    },
  },
};
</script>

<style>
.title {
  margin-bottom: 15px;
}
.chart {
  text-align: center;
}
</style>
