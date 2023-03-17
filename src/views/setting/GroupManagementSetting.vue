<template>
  <div>
    <div style="display: flex;">
      <!-- 그룹 목록 테이블-->
      <span style="flex: 0.5;">
        <div style="height: 30px;">그룹 목록</div>
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
              prop="managegroupId"
              label="그룹번호"
            />
            <el-table-column
              prop="managegroupName"
              label="그룹명"
            />
          </el-table>
        </div>
      </span>
      <!--관리대상 설정 테이블-->
      <span style="flex: 1;">
        <div style="display: flex; width: 90%;">
          <div style="height: 30px; flex: 1;">
            관리대상 설정
            <el-button-group>
              <el-button @click="checkAllBox"> ☑ </el-button>
              <el-button
                style="margin-right: 10px;"
                @click="clearAllBox"
              > ☐ </el-button>
            </el-button-group>
          </div>
          <div style="flex : 1.5; text-align: right;">
            <el-button @click="changeGroup">저장</el-button>
            <el-button @click="cancle"> 취소</el-button>
            <el-input
              v-model="searchWord"
              placeholder="검색"
              style="width: 30%; margin-left: 10px;"
              @change="getDeviceTableData"
            >
              <i
                slot="prefix"
                class="el-input__icon el-icon-search"
              />
            </el-input>
          </div>
        </div>
        <div>
          <el-table
            ref="multipleTable"
            :data="groupDeviceTableData"
            border
            style="width: 90%; margin-top: 20px;"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" />
            <el-table-column
              prop="objName"
              label="장비명"
            />
            <el-table-column
              prop="objtypeName"
              label="종류"
            />
          </el-table>
        </div>
      </span>
    </div>
  </div>
</template>

<script>
import EventApi from '@/api/event';
/**
 * ? REVIEW:
 * 코드 인덴트 확인
 * lint rule은 항상 확인 후 모두 처리
 * 컴포넌트, 인스턴스 옵션 순서 일관성있고 정렬되게 선언
 * !! 변수는 반드시 카멜케이스로
 */
export default {
  name: 'GroupManagementSetting',
  data() {
    return {
      groupTableData: [],
      groupDeviceTableData: [],
      currentRow: null,
      multipleSelection: [],
      checkList: [],
      searchWord: '',
      managegroupID: '',
      insertList: [],
      deleteList: [],
      originalCheckList: [],
      newMultipleSelection: [],
      selectGroupId: '',
      managegroupId: '',
    };
  },
  watch: {
    //그룹테이블에서 선택한 그룹에 맞는 그룹장비 체크박스 자동 선택
    groupTableData() {
      if (this.managegroupId === '') {
        // ? REVIEW: 널 체크 주의 check
        if (this.groupTableData.length > 0) {
          this.managegroupId = this.groupTableData[0].managegroupId;
          return this.setCurrent(this.groupTableData[0]);
        }
      } else {
        this.setCurrent(this.currentRow);
        return this.managegroupId;
      }
    },
  },
  created() {
    this.getGroupTableData();
    this.getDeviceTableData();
  },
  methods: {
    //그룹 조회
    getGroupTableData() {
      EventApi.getGroupList()
        .then((response) => {
          this.groupTableData = response.data.data;
        })
        .catch((ex) => {
          console.log(ex);
        });
    },
    //장비 조회
    getDeviceTableData() {
      const params = { searchWord: this.searchWord };
      EventApi.getGroupDevice(params)
        .then((response) => {
          this.groupDeviceTableData = response.data.data;
        })
        .catch((ex) => {
          console.log(ex);
        })
        .finally(() => {
          //페이지 로드시 그룹 자동선택되면 그에 맞는 관리장비 체크박스 선택
          this.findDevice(this.managegroupId);
        });
    },
    //그룹 선택 cahnge
    handleCurrentChange(val) {
      // ? REVIEW: 널 체크 주의 check
      if (val === null) {
        this.managegroupId = 1; //그룹 테이블에서 선택한 그룹id
        return this.currentRow;
      } else {
        this.currentRow = val;
        this.managegroupId = this.currentRow.managegroupId; //그룹 테이블에서 선택한 그룹id
      }
      //관리장비 체크박스 선택
      this.findDevice(this.managegroupId);
    },
    setCurrent(row) {
      this.$refs.singleTable.setCurrentRow(row);
    },
    //체크박스 지정
    toggleSelection(rows) {
      if (rows) {
        rows.forEach((row) => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    //체크박스 change
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    //그룹 선택시 해당 장비들 찾기
    findDevice(GroupId) {
      this.checkList = [];
      this.toggleSelection();
      this.groupDeviceTableData.forEach((groupDevice) => {
        const manageGroupIdArray = groupDevice.managegroupId.split(',').map(Number); //장비가 속한 그룹 리스트
        let checkDeviceInList = manageGroupIdArray.includes(Number(GroupId)); //그룹리스트에 해당 장비가 속해있는지 여부
        if (checkDeviceInList) {
          this.checkList.push(groupDevice); //체크 리스트에 담기
        }
      });
      //체크박스 선택
      if (this.checkList.length > 0) {
        this.toggleSelection(this.checkList);
      } else {
        this.toggleSelection();
      }
    },
    //관리 그룹 변경
    changeGroup() {
      //데이터 초기화
      this.resetList();
      //objId 추출
      this.extractiedObjId();
      //기존 checkList에서 제거 될때
      this.deleteCheckList();
      //기존 checkList에서 추가될때
      this.addCheckList();
      this.editGroupOfDevice();
    },
    resetList() {
      this.insertList = [];
      this.deleteList = [];
      this.newMultipleSelection = [];
      this.originalCheckList = [];
    },
    extractiedObjId() {
      //기존배열 에서 obj_id만 추출
      this.checkList.forEach((checkList) => {
        this.originalCheckList.push(checkList.objId);
      });
      //새로 체크한 배열에서 obj_id만 추출
      this.multipleSelection.forEach((multipleSelection) => {
        this.newMultipleSelection.push(multipleSelection.objId);
      });
    },
    deleteCheckList() {
      this.checkList.forEach((checkList) => {
        const originalId = checkList.objId;
        let checkData = this.newMultipleSelection.includes(originalId); // 새로 체크한 배열에 기존 배열값 있는지 여부
        if (!checkData) {
          this.deleteList.push(originalId); //삭제 리스트에 담기
        }
      });
    },
    addCheckList() {
      this.multipleSelection.forEach((multipleSelection) => {
        let changedId = multipleSelection.objId;
        let checkData = this.originalCheckList.includes(changedId); // 기존 배열에 새로 체크한 배열값 있는 지 여부
        if (!checkData) {
          this.insertList.push(changedId); // 추가 리스트에 담기
        }
      });
    },
    cancle() {
      this.findDevice(this.managegroupId);
    },
    editGroupOfDevice() {
      const newInsertList = this.insertList.join(',');
      const newdeleteList = this.deleteList.join(',');
      const params = { insertList: newInsertList, deleteList: newdeleteList, managegroupId: this.managegroupId };
      EventApi.editGroupOfDevice(params)
        .then((response) => {
          if (response.data.success === true) {
            this.$message({
              showClose: true,
              message: '그룹 장비를 저장했습니다',
              type: 'success',
            });
          } else {
            this.$message({
              message: '데이터 전송이 실패했습니다',
              type: 'error',
            });
          }
        })
        .catch((ex) => {
          console.log(ex);
          this.$message.error('오류가 발생했습니다');
        })
        .finally(() => {
          this.getDeviceTableData();
        });
    },
    checkAllBox() {
      this.toggleSelection();
      this.toggleSelection(this.groupDeviceTableData);
    },
    clearAllBox() {
      this.toggleSelection();
    },
  },
};
</script>

<style scoped>
.el-table .color-row {
  background: rgb(105, 174, 201);
}
</style>
