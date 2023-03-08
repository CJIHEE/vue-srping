<template>
     <div >
      
        <div style="display: flex;">
            <span style="flex: 0.5;">
                <div style="height: 30px;">
                    그룹 목록 
                </div>
                <div>
                    
                    <el-table
                    ref="singleTable"
                    :data="groupTableData"
                    border
                    style="width: 90%; margin-top: 20px;"
                    highlight-current-row
                    @current-change="handleCurrentChange"
                    >
                        <el-table-column
                        prop="managegroup_id"
                        label="그룹번호"
                        >
                        </el-table-column>
                        <el-table-column
                        prop="managegroup_name"
                        label="그룹명"
                        >
                        </el-table-column>
                   
                    </el-table>
               
                </div>
             </span>
             <span style="flex: 1;">
                <div style="height: 30px;">
                    관리대상 설정
                    <el-button>
                        저장
                    </el-button>
                    <el-button>
                        취소
                    </el-button>
                    <el-input
                        placeholder="검색"
                        v-model="searchWord"
                        @change='getDeviceTableData'
                        style="width: 30%; margin-left: 10px;">
                        <i slot="prefix" class="el-input__icon el-icon-search"></i>
                    </el-input>
                </div>
                <div>
                    <el-table
                    ref="multipleTable"
                    :data="groupDeviceTableData"
                    border
                    style="width: 90%; margin-top: 20px;"
                    @selection-change="handleSelectionChange"
                    >
                        <el-table-column
                        type="selection"
                        >
                        </el-table-column>
                        <el-table-column
                        prop="obj_name"
                        label="장비명"
                        >
                        </el-table-column>
                        <el-table-column
                        prop="objtype_name"
                        label="종류"
                        >
                        </el-table-column>
                        <el-table-column
                        prop="managegroup_name"
                        label="종류"
                        >
                        </el-table-column>
                    </el-table>
                </div>
            </span>
            
        </div>

    </div>
</template>

<script>



    export default {
        name : 'GroupManagementSetting',
        created() {
            this.getGroupTableData();
            this.getDeviceTableData();
        },
        data() {
            return {
                groupTableData : [],
                groupDeviceTableData : [],
                currentRow : null,
                multipleSelection: [],
                checkList : [],
                searchWord : '',
                managegroupID:'',

            }
        },
        watch : {
            //그룹 테이블 변경 초기화할때 마다 1번행 자동 선택
            groupTableData(){
                return  this.setCurrent(this.groupTableData[0]);
            },
            // groupDeviceTableData(){
            //     console.log("name : " +this.groupTableData[0].managegroup_name)
            //     return this.findDevice(this.groupTableData[0].managegroup_name);
            // }
            // checkList(){
            //     return this.toggleSelection(this.checkList);
            // }
        },
        methods: {
            //그룹 조회
            getGroupTableData(){
                this.$axios.get('app/event/get-group.do')
                .then(response => {
                    console.log(response.data.data)
                    this.groupTableData = response.data.data
                    console.log("그룹 데이터 주입")
                    //그룹 아이디 추출
                    // for (var i = 0; i < this.groupTableData.length; i++) {
                    //         this.groupNameList.push(this.groupTableData[i].managegroup_name)
                    // } 
                })
                .catch((ex) => {
                    console.log(ex);
                })
                
                },
                handleCurrentChange(val) {
                    this.currentRow = val;
                },
             //장비 조회
             getDeviceTableData(){
                this.$axios.get('app/event/get-group-device.do', { params: { searchWord:this.searchWord }} )
                .then(response => {
                    console.log(response.data.data)
                    this.groupDeviceTableData = response.data.data
                    console.log("장비 데이터 주입")
                    this.findDevice(this.groupTableData[0].managegroup_name);
                    //그룹 아이디 추출
                    // for (var i = 0; i < this.groupTableData.length; i++) {
                    //         this.groupNameList.push(this.groupTableData[i].managegroup_name)
                    // } 
                    //console.log("두번째 set작동")
                   
                })
                .catch((ex) => {
                    console.log(ex);
                })
                },
                //그룹 선택 cahnge
                handleCurrentChange(val) {
                    this.currentRow = val;
                    console.log("콘솔작동")
                    const selectGroupName = this.currentRow.managegroup_name; //그룹 테이블에서 선택한 그룹이름

                    //장비 찾기 메소드 호출
                    this.findDevice(selectGroupName);                
                },
                setCurrent(row) {
                    console.log("set작동")
                    this.$refs.singleTable.setCurrentRow(row);
                }, 
                 //체크박스 지정 
                toggleSelection(rows) {
                    console.log("체크박스")
                    if (rows) {
                    rows.forEach(row => {
                        this.$refs.multipleTable.toggleRowSelection(row);
                        console.log("check")
                    });
                    } else {
                    this.$refs.multipleTable.clearSelection();
                    console.log("clear")
                    }
                },
                //체크박스 change
                handleSelectionChange(val){
                    this.multipleSelection = val;
                },
                //그룹 선택시 해당 장비들 찾기
                findDevice(selectGroupName){
                    this.toggleSelection();
                    this.checkList =[];
                    //그룹 선택변경시 체크해줄 관리대상 찾기
                    for (var i = 0; i < this.groupDeviceTableData.length; i++) {
                        let GroupNameOfDevice = this.groupDeviceTableData[i].managegroup_name
                        if(selectGroupName === GroupNameOfDevice ){
                            this.checkList.push(this.groupDeviceTableData[i]);
                        }
                    } 
                    this.toggleSelection(this.checkList); 
                },
        
            }
        }      

    </script>
    
<style scoped>

.el-table .color-row {
    background: rgb(105, 174, 201);
  }

</style>