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
                    <el-button 
                     @click="changeGroup">
                        저장
                    </el-button>
                    <el-button @click="cancle">
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
                        prop="managegroup_id"
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
                insertList : [],
                deleteList : [],
                originalCheckList : [],
                newMultipleSelection : [],
                selectGroupName :'',
                selectGroupId:'',
                managegroup_id:'',
                
 
            }
        },
        watch : {
            //그룹 테이블 변경 초기화할때 마다 1번행 자동 선택
            groupTableData(){
                if(this.selectGroupName == ''){
                    this.selectGroupName = this.groupTableData[0].managegroup_name;
                    this.managegroup_id = this.groupTableData[0].managegroup_id;
                    return  this.setCurrent(this.groupTableData[0]);
                }
                else{
                    this.selectGroupName = this.selectGroupName;
                    this.managegroup_id = this.managegroup_id;
                    return  this.setCurrent(this.currentRow);
                }
            },
        },
        methods: {
        //그룹 조회
            getGroupTableData(){
                this.$axios.get('app/event/get-group.do')
                .then(response => {
                    console.log(response.data.data)
                    this.groupTableData = response.data.data
                })
                .catch((ex) => {
                    console.log(ex);
                })
                },
                // handleCurrentChange(val) {
                //     this.currentRow = val;
                // },
        //장비 조회
             getDeviceTableData(){
                this.$axios.get('app/event/get-group-device.do', { params: { searchWord:this.searchWord }} )
                .then(response => {
                    console.log(response.data.data)
                    this.groupDeviceTableData = response.data.data
                })
                .catch((ex) => {
                    console.log(ex);
                })
                .finally(() => {
                    // 페이지 로드시 그룹의 첫번째 자동선택되면 그에 맞는 관리장비 체크박스 선택
                    this.findDevice(this.selectGroupName,this.managegroup_id);
                });
                },
        //그룹 선택 cahnge
            handleCurrentChange(val) {
                this.currentRow = val === null ? this.currentRow : val;
                //this.currentRow = val
                this.selectGroupName = this.currentRow.managegroup_name; //그룹 테이블에서 선택한 그룹이름
                this.managegroup_id = this.currentRow.managegroup_id;
                //관리장비 체크박스 선택
                this.findDevice(this.selectGroupName,this.managegroup_id);                
            },    
            setCurrent(row) {
                this.$refs.singleTable.setCurrentRow(row);
            }, 
        //체크박스 지정 
            toggleSelection(rows) {
                if (rows) {
                rows.forEach(row => {
                    this.$refs.multipleTable.toggleRowSelection(row);
                });
                } else {
                this.$refs.multipleTable.clearSelection();
                }
            },
        //체크박스 change
            handleSelectionChange(val){
                this.multipleSelection = val;
            },
        //그룹 선택시 해당 장비들 찾기
            findDevice(GroupName,GroupId){
                this.checkList =[];
                this.toggleSelection();
                for (var i = 0; i < this.groupDeviceTableData.length; i++) {
                    let manageGroupIdArray = this.groupDeviceTableData[i].managegroup_id.split(',').map(Number);     
                    let boolean = manageGroupIdArray.includes(GroupId);
                    if (boolean){
                        this.checkList.push(this.groupDeviceTableData[i]); 
                    }  
                    else{
                    }
                } 
                //체크박스 선택
                this.toggleSelection(this.checkList);    
            },
        //관리 그룹 변경
            changeGroup(){
            //데이터 초기화 
                this.resetList();
            //obj_id 추출    
                this.extractiedObjId();
            //기존 checkList에서 제거 될때
                this.deleteCheckList();
            //기존 checkList에서 추가될때
                this.addCheckList(); 
                this.editGroupOfDevice();
            }, 
            resetList(){
                this.insertList =[];
                this.deleteList =[];
                this.newMultipleSelection =[];  
                this.originalCheckList =[];
            },
            extractiedObjId(){
                //기존배열 에서 obj_id만 추출
                for (var i = 0; i < this.checkList.length; i++) {
                    this.originalCheckList.push(this.checkList[i].obj_id); 

                    } 
                    //새로 체크한 배열에서 obj_id만 추출
                    for(var i = 0; i < this.multipleSelection.length; i++){
                    this.newMultipleSelection.push(this.multipleSelection[i].obj_id); 
                }  
            },
            deleteCheckList(){
                console.log(this.newMultipleSelection)
                for (var i = 0; i < this.checkList.length; i++) {
                    let originalId = this.checkList[i].obj_id;
                    let boolean = this.newMultipleSelection.includes(originalId); // 새로 체크한 배열에 기존 배열값 있는지 여부
                    if(!boolean){
                        this.deleteList.push(originalId); //삭제 리스트에 담기
                    }
                    } 
                    console.log(this.deleteList)  
            },
            addCheckList(){
                console.log(this.originalCheckList)
                for(var i = 0; i < this.multipleSelection.length; i++){
                    let changedId = this.multipleSelection[i].obj_id;
                    let boolean = this.originalCheckList.includes(changedId) // 기존 배열에 새로 체크한 배열값 있는 지 여부
                    if(!boolean){
                        this.insertList.push(changedId) // 추가 리스트에 담기
                    }
                }
            },
            cancle(){
                this.findDevice(this.selectGroupName);   
            },
            editGroupOfDevice(){
                const newInsertList = this.insertList.join(",");
                const newdeleteList = this.deleteList.join(",");
                this.$axios.get('app/event/edit-group-of-device.do', { params: { insertList:newInsertList , deleteList:newdeleteList, managegroupId : this.managegroup_id  }} )
                .then(response => {
                    this.$message('그룹 장비를 저장했습니다');
                })
                .catch((ex) => {
                    this.$message.error('오류가 발생했습니다');
                })
                .finally(() => {
                    this.getDeviceTableData();
                });
            }
        
        } 
    }            

    </script>
    
<style scoped>

.el-table .color-row {
    background: rgb(105, 174, 201);
  }

</style>