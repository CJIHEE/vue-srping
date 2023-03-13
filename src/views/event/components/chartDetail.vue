<template>
    <div>
        <div class="chart">
            <div class="block">
                <el-date-picker
                v-model="value2"
                type="datetime"
                placeholder="Select date and time"
                style="margin : 25px 5px;">
                </el-date-picker>
                <el-date-picker
                v-model="value3"
                type="datetime"
                placeholder="Select date and time"
                :disabled="disabledDate"
                >
                </el-date-picker>
            </div>
            <highcharts :options ="chartOptions "/>   
        </div>
    </div>
</template>
<script>

import dayjs from 'dayjs'
export default{
    name : 'charList',
    props : ['deviceDetailName'],
    watch :{
        id(){
            this.getCharList();
            this.value2 ='',
            this.value3=''
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
            rndChartArray : [],
            rndChartArray2 : [],
            disabledDate : false,
        }
    }, 
    created() {
        this.getCharList();
    },
    methods: {
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
        getCharList(){
            this.$axios.get('app/event/get-chart-data.do', { params: { id: 'chartDetail' , value : this.value2 , value2: this.value3 }})
            .then(response => {
                this.categoryList = response.data.data.categoryList;
                    //난수 발생
                    this.creatRandomNumber();
                    //데이터 set
                    this.setData();
            })
            .catch((ex) => {
              console.log(ex);
            })
        },
        //난수 발생
        creatRandomNumber(){
            this.rndChartArray = [];
                    for(var i=0; i <this.categoryList.length; i++){
                        let rnd = Math.floor(Math.random() * 101);
                        const rndArray = [];
                        rndArray.push(this.categoryList[i]);
                        rndArray.push(rnd);
                        this.rndChartArray.push(rndArray);
                    }
            this.rndChartArray2 = [];
            for(var i=0; i <this.categoryList.length; i++){
                let rnd = Math.floor(Math.random() * 101);
                const rndArray2 = [];
                rndArray2.push(this.categoryList[i]);
                rndArray2.push(rnd);
                this.rndChartArray2.push(rndArray2);
            }
        },
        setData(){
            //데이터 초기화
            this.chartOptions.series =[];
            //데이터 set
            this.chartOptions.xAxis.categories =this.categoryList;
            this.chartOptions.series.push({name : '난수 데이터1' , data :this.rndChartArray});
            this.chartOptions.series.push({name : '난수 데이터2' , color:'green' ,data :this.rndChartArray2});
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
