<template>
    <div>
        <div>
            <component :is="componentName" :id="id" :name="name"/>
        </div>
       
        <div>
            차트
        </div>
        <el-select v-model="value" filterable placeholder="전체">
            <el-option
              v-for="item in eventGrade"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        <div>
            <el-table
                :data="tableData"
                border
                style="width: 100%">
                <el-table-column
                prop="eventlevel_name"
                label="등급"
                width="180">
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
                prop="event_message"
                label="장애 메시지">
                </el-table-column>
                <el-table-column
                prop="check_datetime"
                label="발생 시각">
                </el-table-column>
            </el-table>
            <div class="block">
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
    </div>
</template>

<script>
import card from './components/card.vue'
import device from './components/device.vue'

export default{
    name : 'RootGroupInfo',
    components : {card , device},
    computed : {
        id : function(){
            return  this.$route.params.id
        },
        name : function(){
            return  this.$route.params.name
        }
    },
    watch: {
        id(){
            this.changeComponent();
            this.currentPage = 1;
            this.sizePerPage =25;
            this.value='';
            this.getEventList();
        },
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
        }
    },
    mounted() {
        this.changeComponent();
        this.getEventList();
    },
    methods: {
        changeComponent(){
            if(isNaN(this.id)){
                this.componentName = 'card'
            }
            else {
                this.componentName = 'device'
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
            console.log()
            this.$axios.get('app/event/get-event-list.do', { params: { id: this.id , currentPage:this.currentPage, sizePerPage:this.sizePerPage} })
            .then(response => {
            console.log(response.data.data)
            this.tableData = response.data.data;
            this.totalPage = response.data.total;
            // this.eventGrade = response.data.data2;

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

</style>
