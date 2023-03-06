<template>
    <div>
        <div class="chart">
            <div class="block">
                <span class="demonstration">Default</span>
                <el-date-picker
                v-model="value2"
                type="datetime"
                placeholder="Select date and time">
                </el-date-picker>

                <span class="demonstration">With shortcuts</span>
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
            this.getCharList();
            this.value2 ='',
            this.value3=''
        },
        value2(){
            //this.insertData(this.value2);
            console.log("value: " + this.value2)
            this.getCharList();
        },
        value3(){
            this.getCharList();
            console.log("value: " + this.value3)
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
                    text: '이벤트 이력',
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
            value2 : '',
            value3 : '',
        }
    },
    mounted() {
        this.getCharList();
    },
    methods: {

        getCharList(){
            this.$axios.get('app/event/get-chart-data.do', { params: { id: this.id , value : this.value2 , value2: this.value3 }})
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
