<template>
    <div>
        <div class="chart">
            <div class="block">
                <el-date-picker
                v-model="value2"
                type="datetime"
                placeholder="Select date and time"
                style="margin: 15px;">
                </el-date-picker>
                <el-date-picker
                v-model="value3"
                type="datetime"
                placeholder="Select date and time"
                >
                </el-date-picker>
            </div>
            <highcharts :options ="chartOptions "/>   
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
    data() {
        return {
            chartList:[],
            chartList2:[1,23,4,5,6,],
            categorytyList:[],
            chartOptions : {
                
                title: {
                    text: '성능추이',
                    
                },
                xAxis: {
                    categories: this.categoryList,
                    label : {
                        fommatter:function(){
                            return this.value + '%';
                        }
                    },

                },
                yAxis: {
                    title: {
                    text: ''
                    },
                    label : {
                        fommatter:function(){
                            return this.value + '%';
                        }
                    },
                    plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                    }]
                },
                tooltip: {
                    valueSuffix: ''
                },
                series: [ ]

            },
            value2 : '2023-02-22T15:00:00.000Z',
            value3 : '2023-02-23T15:00:00.000Z',
        }
    }, 
    created() {
        this.getCharList();
    },
    methods: {
        checkDate(){
            if(this.value3 > this.value2) {
                alert("똑바로")
            }
            else {
                this.getCharList();
            }
        },
        getCharList(){
            this.$axios.get('app/event/get-chart-data.do', { params: { id: 'chartDetail' , value : this.value2 , value2: this.value3 }})
            .then(response => {
                this.chartOptions.series =[];
                this.categoryList = response.data.data.categoryList
                this.chartOptions.xAxis.categories =this.categoryList
                    //난수 발생
                    const rndChartArray = [];
                    for(var i=0; i <this.categoryList.length; i++){
                        let rnd = Math.floor(Math.random() * 101);
                        const rndArray = [];
                        rndArray.push(this.categoryList[i]);
                        rndArray.push(rnd);
                        rndChartArray.push(rndArray)
                    }

                    const rndChartArray2 = [];
                    for(var i=0; i <this.categoryList.length; i++){
                        let rnd = Math.floor(Math.random() * 101);
                        const rndArray2 = [];
                        rndArray2.push(this.categoryList[i]);
                        rndArray2.push(rnd);
                        rndChartArray2.push(rndArray2)
                    }
                    this.chartOptions.series.push({name : '난수 데이터1' , data :rndChartArray})
                    this.chartOptions.series.push({name : '난수 데이터2' , color:'green' ,data :rndChartArray2})

            })
            .catch((ex) => {
              console.log(ex);
            })
        },


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
