<template>
    <div>
        <div class="chart">
            <div class="block">
                <el-date-picker
                v-model="value2"
                type="datetime"
                placeholder="Select date and time"
                style="margin-right: 10px;">
                </el-date-picker>
                <el-date-picker
                v-model="value3"
                type="datetime"
                placeholder="Select date and time"
                :disabled="disabledDate"
                >
                </el-date-picker>
            </div>
            <highcharts :options ="chartOptions"/>
        </div>
    </div>
</template>

<script>
import dayjs from 'dayjs'
export default{
    name : 'charList',
    props : ['id'],
    //props 데이터 변경 감지
    watch :{
        id:{
            handler: function(){
                if(this.id === '4'){
                this.value2 = '2023-02-16T15:00:00.000Z';
                this.value3 = '2023-02-22T15:00:00.000Z';
                }
                else{
                this.value2 ='2023-02-21T15:00:00.000Z',
                this.value3='2023-02-27T15:00:00.000Z'
                }
                this.getCharList();
            },
            immediate: true     
        },
        value2(){
            this.checkDate();
        },
        value3(){
            this.checkDate();
        }
    },
    data() {
        return {
            chartList:[],
            categorytyList:[],
            chartTitle: '이벤트 이력',
            chartOptions : {
                chart: {
                    zoomType: 'x'
                },
                title: {
                    text: '이벤트 이력',
                    align: 'left'
                },
                xAxis:{
                    categories: this.categoryList
                },
                yAxis: {
                    title: {
                        text: ''
                    },
                },
                legend: {
                    enabled: false
                },
                series: [{}]
            },
            value2 : '',
            value3 : '',
            testdate : '', 
            disabledDate : false,
        }
    },  
    methods: {
        getCharList(){
            this.$axios.get('app/event/get-chart-data.do', { params: { id: this.id , value : this.value2 , value2: this.value3 }})
            .then(response => {
                this.chartList = response.data.data.chartList;
                this.categoryList = response.data.data.categoryList;
                this.setChartData();
            })
            .catch((ex) => {
              console.log(ex);
            })
        }, 
        checkDate(){           
            let startDay = dayjs(this.value2).format("YYYY-MM-DD HH:mm:ss");
            let endDay =dayjs(this.value3).format("YYYY-MM-DD HH:mm:ss");
            if(startDay > endDay) {
                this.disabledDate = true;
                alert(startDay+"보다 이전 일자는 검색할 수 없습니다");
            }
            else {
                this.disabledDate = false;
                this.getCharList();
            }
        },
        setChartData(){
            //데이터 초기화
            const ChartData = [];
            this.chartOptions.series =[];
            //데이터 set
            this.chartOptions.xAxis.categories =this.categoryList;
            for(var i=0; i <this.chartList.length; i++){
                ChartData.push(this.chartList[i]);
            }
            this.chartOptions.series.push({color:'rgb(161, 120, 161)',type:'area' , data : ChartData });
        }
    },  
}
</script>

<style scoped>
.title {
    margin-bottom: 15px;
}
.chart{
    text-align: center;
}
</style>
