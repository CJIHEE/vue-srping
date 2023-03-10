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
    props : ['id'],
    //props 데이터 변경 감지
    watch :{
        id(){
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
        value2(){
            this.getCharList();
        },
        value3(){
            this.getCharList();
        }
    },
    data() {
        return {
            chartList:[],
            chartList2:[1,23,4,5,6,],
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

                series: [{}
                    // type: 'area',
                    // data: this.chartList,
                ]
            },
            value2 : '',
            value3 : '',
        }
    },  
    mounted() {
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
    methods: {
        getCharList(){
            console.log("value2 : " +this.value2)
            console.log("id : " + this.id)
            this.$axios.get('app/event/get-chart-data.do', { params: { id: this.id , value : this.value2 , value2: this.value3 }})
            .then(response => {
                const ChartData = [];
                this.chartOptions.series =[];
                this.chartList = response.data.data.chartList
                this.categoryList = response.data.data.categoryList
                // this.chartOptions.series.data =this.chartList;
                 this.chartOptions.xAxis.categories =this.categoryList
                for(var i=0; i <this.chartList.length; i++){
                    ChartData.push(this.chartList[i])
                }
                this.chartOptions.series.push({color:'rgb(161, 120, 161)',type:'area' , data : ChartData })
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
