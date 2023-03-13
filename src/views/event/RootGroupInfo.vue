<template>
    <div>
        <!--장비 카드-->
        <div>
            <component 
            :is="componentName" 
            :id="id" 
            :name="name" 
            @submitObjNameId="setObjNameId"
            />
        </div>
        <!--차트-->
        <div style="margin-top: 15px;">
            <chart :id="id"> </chart>
        </div>
        <!-- 검색 및 필터-->
        <div class="parent">
            <div class="child">
                <el-select
                 v-model="value" 
                 filterable 
                 placeholder="전체">
                    <el-option
                     v-for="item in eventGrade"
                     :key="item.value"
                     :label="item.label"
                     :value="item.value">
                    </el-option>
                </el-select>
            </div>
            <div style="margin: 10px 0px; flex: 1.5;">
                <el-input
                 placeholder="검색"
                 v-model="searchWord"
                 @change="getEventList"
                 style="float:right; width: 35%;"
                 prefix-icon="el-icon-search"
                >
                </el-input>
            </div>
        </div>
        <!-- 이벤트 목록 조회-->
        <div>
            <el-table
             :data="tableData"
             border
             style="width: 100%">
                <el-table-column
                 label="등급"
                 width="180">
                    <template slot-scope="scope">
                        <el-tag
                         :style="styleBinding(scope.row.eventlevel_name)"
                         disable-transitions
                        >
                            {{scope.row.eventlevel_name}}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                 prop="obj_name"
                 label="장비"
                 width="180">
                </el-table-column>
                <el-table-column
                 prop="objtype_name"
                 label="장비 종류">
                </el-table-column>
                <el-table-column
                 prop="obj_company_name"
                 label="제조사">
                </el-table-column>
                <el-table-column
                 prop="rsc_name"
                 label="자원">
                </el-table-column>
                <el-table-column
                label="장애 메시지">
                    <template slot-scope="scope">
                        <el-tag 
                        @click="eventDetail(scope.row.rno), drawer = true"
                        style="cursor: pointer"
                        >
                            {{scope.row.event_message}}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                 prop="check_datetime"
                 label="발생 시각">
                </el-table-column>
            </el-table>
            <div class="block" style="margin-top: 10px;">
                <el-pagination
                 @size-change="handleSizeChange"
                 @current-change="handleCurrentChange"
                 :current-page.sync="currentPage"
                 :page-sizes="[25, 50, 100, 500]"
                 :page-size="sizePerPage"
                 layout="total, sizes, prev, pager, next, jumper"
                 :total="totalPage">
                </el-pagination>
            </div>
        </div>
        <!-- 이벤트 상세보기-->
        <el-drawer
         v-model="drawer" 
         title="이벤트 성능 정보"  
         :visible.sync="drawer" 
         size="50%" 
         >
            <div 
             v-for="(item,idx) in eventCardDetail" 
             :key="idx"
             style="margin: auto;"
            >
                <el-row :gutter="24" style="margin-left: 10%;">
                    <el-col 
                        :span="20"
                        shadow="always" 
                        style="margin: auto;">
                        <!--장비 상세보기 카드-->
                        <el-card>
                            <div class="parent">
                                <span style="margin-bottom: 10px;">
                                    <span class="span-style">· 장비명 </span>
                                    {{ item.obj_name }}
                                </span>
                            </div>
                            <div class="parent">   
                                <span>
                                    <span class="span-style">· 장애 메시지 </span>
                                    {{item.event_message}}
                                </span>
                            </div>
                        </el-card>
                        <!-- 상세보기 차트-->
                        <div>
                            <chartDetail :deviceDetailName="deviceDetailName">
                            </chartDetail>
                        </div>
                    </el-col>
                </el-row>
            </div>
        </el-drawer>
    </div>
</template>

<script>
import card from './components/card.vue'
import device from './components/device.vue'
import chart from './components/chart.vue'
import chartDetail from './components/chartDetail.vue'

export default{ 
    name : 'RootGroupInfo',
    components : {card , device, chart ,chartDetail},
    computed : {
        id : function(){
            return  this.$route.params.id;
        },
        name : function(){
            return  this.$route.params.name;
        },
          styleBinding(){
            return (eventlevel_name) => {   
                let bgColor ='';
                let fontColor='white';
                if(eventlevel_name == 'Normal') {
                    bgColor = 'purple';
                    fontColor = 'white';
                }
                else if(eventlevel_name == 'Down'){
                    bgColor = 'gray';
                    fontColor = 'white';
                }
                else if(eventlevel_name == 'Minor'){
                    bgColor = 'orange';
                    fontColor = 'white';
                }
                else if(eventlevel_name == 'Major'){
                    bgColor = 'yellow';
                    fontColor = 'black';
                }
                else {
                    bgColor = 'red';
                    fontColor = 'white';
                }
    	    return { backgroundColor : bgColor , color : fontColor}
            }   
        }
    },
    watch: {
        id : {
            handler: function(){
                this.changeComponent();
                this.currentPage = 1;
                this.sizePerPage =25;
                this.value='';
                this.getEventList();
                this.objNameList ='';
            },
            deep: true,
            immediate: true
        },
        value(){
            this.getEventList();
        }
    },
    data() {
        return {
           deviceData : [],
           componentName : '',
           tableData: [],
            currentPage: 1,
            sizePerPage: 25,
            totalPage : 0,
            value : '',
            searchWord : '',  
            chartData : [1,2,3,4]  ,
            eventGrade : [{
                value : 'Critical',
                label : 'Critical',
                }, {
                value : 'Down',
                label : 'Down',
                },
                {
                value : 'Info',
                label : 'Info',
                },{
                value : 'Major',
                label : 'Major',
                }, {
                value : 'Minor',
                label : 'Minor',
                }, {
                value : 'Normal',
                label : 'Normal',
                }],   
            drawer : false,
            eventCardDetail : [],
            deviceDetailName : '',
            objDefineName:'',
            objNameList : ''
        }

    },
    methods: {
        changeComponent(){
            if(isNaN(this.id)){
                this.componentName = 'card';
            }
            else {
                this.componentName = 'device';
            }       
        },
        handleSizeChange(val) {
            this.sizePerPage = val;
            this.getEventList();
        },
        handleCurrentChange(val) {
            this.currentPage = val;
            this.getEventList();
        },
        getEventList(){
            this.$axios.get('app/event/get-event-list.do', { params: { 
                                                                        id: this.id , 
                                                                        currentPage:this.currentPage, 
                                                                        sizePerPage:this.sizePerPage, 
                                                                        value:this.value, 
                                                                        searchWord:this.searchWord,
                                                                        objDefineName : this.objDefineName,
                                                                        objNameList : this.objNameList
                                                                    }})
            .then(response => {
            //console.log(response.data.data)
            this.tableData = response.data.data;
            this.totalPage = response.data.total;
            // this.eventGrade = response.data.data2;
            })
            .catch((ex) => {
              console.log(ex);
            })
            .finally(() => {
            this.objDefineName ='';
            });
        }, 
        eventDetail(index){
            console.log("index : " + index);
            this.$axios.get('app/event/get-card-detail.do', { params: { 
                                                                        index: index,
                                                                        id: this.id , 
                                                                        value:this.value, 
                                                                        searchWord:this.searchWord,
                                                                        objNameList : ''}})
            .then(response => {
                //console.log(response.data.data)
                this.eventCardDetail = response.data.data;
                this.deviceDetailName = response.data.data[0].obj_name;
            })
            .catch((ex) => {
                console.log(ex);
            })
        },
        //장비카드 클릭시 이벤트 목록 조회 변경
        setObjNameId(objDefineName,objNameList){
            this.objDefineName = objDefineName;
            this.objNameList = objNameList.join(",");
            this.getEventList();
            this.objDefineName ='';
        }
    },   
}
</script>

<style scoped>
.title {
    margin-bottom: 15px;
}
.parent{
    display : flex;
}
.child {
    flex: 0.5;
    margin: 10px 10px 10px 0px
}
.span-style {
    font-weight: bold;
    margin-right: 10px
}
</style>
