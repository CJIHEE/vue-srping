<template>
    <div>
        <div class="chart">
            <div class="block">
                <span class="demonstration"></span>
                <el-date-picker
                v-model="value2"
                type="datetime"
                placeholder="Select date and time">
                </el-date-picker>

                <span class="demonstration"></span>
                <el-date-picker
                v-model="value3"
                type="datetime"
                placeholder="Select date and time"
                >
                </el-date-picker>
            </div>


            <highcharts :options ="chartOptions"/>
           
        </div>


    </div>
</template>

<script>


export default{
    name : 'charList',
    props : ['deviceDetailName'],
    //props 데이터 변경 감지
    watch :{
        id(){
            this.getCharList();
            this.value2 ='',
            this.value3=''
        },
        value2(){
            this.getCharList();
        },
        value3(){
            this.getCharList();
        }
    },
    mounted() {
        this.getCharList();

    },
    data() {
        return {
            chartList:[],
            chartList2:[1,23,4,5,6,],
            categorytyList:[],
            chartOptions : {

                chart: {
                    zoomType: 'x'
                },
                title: {
                    text: '성능 추이',
                    align: 'left'
                },
                xAxis:{
                    categories: this.categorytyList
                },
                yAxis: {
                    title: {
                        text: ''
                    },
                },
                legend: {
                    enabled: false
                },

                series: {
                    type: 'area',
                    data: this.chartList,
                }
            },
            value2 : '2023-02-21T15:00:00.000Z',
            value3 : '2023-02-27T15:00:00.000Z',
        }
    },  
    mounted() {
        this.getCharList();
    },
    methods: {

        getCharList(){
            this.$axios.get('app/event/get-chart-detail.do', { params: { id: this.deviceDetailName , value : this.value2 , value2: this.value3 }})
            .then(response => {
            this.chartList = response.data.data.chartList
            this.categorytyList = response.data.data.categorytyList

            this.chartOptions.series.data =this.chartList;
            this.chartOptions.xAxis.categories =this.categorytyList

            this.categorytyList = response.data.data.categoryList;   
            })
            .catch((ex) => {
              console.log(ex);
            })
        }, 
        checkDate(){
            if(this.value3 > this.value2) {
                alert("똑바로")
            }
            else {
                this.getCharList();
            }
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
