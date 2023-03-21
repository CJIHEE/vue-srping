<template>
  <div>
    그룹 목록
    <el-button @click="dialogFormVisible = true">
      추가
    </el-button>
    <el-button
      :disabled="isDisabled"
      @click="deleteGrop"
    >
      삭제
    </el-button>
    <!-- 그룹 목록 조회-->
    <div>
      <el-table
        :data="tableData"
        border
        style="width: 80%; margin-top: 20px;"
        @selection-change="handleSelectionChange"
        @row-dblclick="modifyDialog"
      >
        <el-table-column type="selection" />
        <el-table-column
          prop="managegroupId"
          label="그룹번호"
        />
        <el-table-column
          prop="managegroupName"
          label="그룹명"
        />
        <el-table-column
          prop="orderbyIndex"
          label="정렬순서"
        />
      </el-table>
    </div>
    <!-- 그룹 추가 dialog-->
    <div>
      <el-dialog
        title="그룹 추가"
        :visible.sync="dialogFormVisible"
        append-to-body
      >
        <el-form
          ref="ruleForm"
          :model="ruleForm"
          :rules="rules"
          label-width="120px"
          class="demo-ruleForm"
        >
          <el-form-item
            label="이름"
            prop="groupName"
            :label-width="formLabelWidth"
          >
            <el-input
              v-model="ruleForm.groupName"
              autocomplete="off"
            />
          </el-form-item>
          <el-form-item
            label="정렬순서"
            :label-width="formLabelWidth"
          >
            <el-input-number
              v-model="num"
              controls-position="right"
              :min="orderIndex"
            />
          </el-form-item>
        </el-form>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button
            type="primary"
            @click="addGroup('ruleForm')"
          >저장</el-button>
          <el-button @click="cancle">취소</el-button>
        </span>
      </el-dialog>
    </div>
    <!-- 그룹 수정 dialog-->
    <div>
      <el-dialog
        title="그룹 수정"
        :visible.sync="modifyFormVisible"
        append-to-body
      >
        <el-form
          ref="ruleForm"
          :model="ruleForm"
          :rules="rules"
          label-width="120px"
          class="demo-ruleForm"
        >
          <el-form-item
            label="이름"
            prop="groupName"
            :label-width="formLabelWidth"
          >
            <el-input
              v-model="ruleForm.groupName"
              autocomplete="off"
            />
          </el-form-item>
          <el-form-item
            label="정렬순서"
            :label-width="formLabelWidth"
          >
            <el-input-number
              v-model="modifyIndex"
              controls-position="right"
              :min="modifyIndex"
            />
          </el-form-item>
        </el-form>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button
            type="primary"
            @click="modifyGroup('ruleForm')"
          >수정</el-button>
          <el-button @click="cancle">취소</el-button>
        </span>
      </el-dialog>
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
 */
export default {
  name: 'GroupSetting',
  data() {
    //서버에서 가져온 그룹 목록과 비교하여 유효성 검사
    // ? REVIEW: 문법 혼용 주의 (es6 사용 권장)
    var checkGroupName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('그룹 이름을 입력해 주세요.'));
      } else {
        let flag = false;
        this.groupNameList.forEach((groupName) => {
          if (value === groupName) {
            flag = true;
          }
        });
        if (flag) {
          callback(new Error('이미 있는 그룹명입니다. 다른 이름을 입력해 주세요.'));
        }
        callback();
      }
    };
    return {
      tableData: [],
      groupNameList: [],
      multipleSelection: [],
      array: [],
      selectArray: [],
      dialogFormVisible: false,
      modifyFormVisible: false,
      num: '',
      orderIndex: '',
      modifyIndex: '',
      ruleForm: {
        groupName: '',
      },
      rules: {
        groupName: [{ validator: checkGroupName, required: true, trigger: 'blur' }],
      },
      checkRuleForm: false,
      formLabelWidth: '120px',
    };
  },
  computed: {
    isDisabled() {
      if (this.multipleSelection.length === 0) {
        return true;
      } else {
        return false;
      }
    },
  },
  watch: {
    orderIndex() {
      this.num = this.orderIndex;
    },
  },
  created() {
    this.getTableData();
  },
  methods: {
    //그룹 조회
    getTableData() {
      EventApi.getGroupList()
        .then((response) => {
          this.tableData = response.data.data;
          //그룹 아이디 추출
          this.tableData.forEach((group) => {
            this.groupNameList.push(group.managegroupName);
          });
          //정렬순서 최대값으로 설정
          this.setMaxIndex();
        })
        .catch((ex) => {
          console.log(ex);
        });
      this.resetData();
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    //정렬순서 최대값으로 설정
    setMaxIndex() {
      const indexArray = [];
      this.tableData.forEach((tableData) => {
        indexArray.push(tableData.orderbyIndex);
      });
      this.orderIndex = Math.max.apply(null, indexArray) + 1;
    },
    //그룹 추가
    addGroup(formName) {
      //유효성 검사
      this.checkRule(formName);
      if (this.checkRuleForm) {
        this.dialogFormVisible = false;
      } else {
        this.dialogFormVisible = true;
      }
      //유효성 검사 통과시 데이터 추가
      if (this.checkRuleForm) {
        const params = { managegroupName: this.ruleForm.groupName, orderbyIndex: this.orderIndex };
        EventApi.addGroup(params)
          .then((response) => {
            if (response.data.success) {
              this.$message({
                showClose: true,
                message: '그룹을 추가하였습니다',
                type: 'success',
              });
              this.getTableData();
              this.resetData();
            } else {
              this.$message.error('오류가 발생하였습니다.');
            }
          })
          .catch((ex) => {
            console.log(ex);
          });
      } else {
        this.$message.error('오류가 발생하였습니다.');
      }
      this.checkRuleForm = false;
      this.resetData();
    },
    //삭제
    deleteGrop() {
      if (confirm('그룹을 삭제하시겠습니까?') == true) {
        //시퀀스 번호 배열에 담기
        this.multipleSelection.forEach((multipleSelection) => {
          this.array.push(multipleSelection.managegroupId);
        });
        //배열 중복제거
        this.selectArray = this.array.filter((v, i) => this.array.indexOf(v) === i).join(',');
        //배열보내기
        const params = { selectArray: this.selectArray };
        EventApi.deleteGroup(params)
          .then((response) => {
            if (response.data.success) {
              this.$message({
                showClose: true,
                message: '그룹을 삭제하였습니다',
                type: 'success',
              });
              this.getTableData();
              this.resetData();
            } else {
              this.$message.error('오류가 발생하였습니다.');
            }
          })
          .catch((ex) => {
            console.log(ex);
          });
        //배열초기화
        this.array = [];
        this.selectArray = [];
        //데이터 초기화
        this.resetData();
      }
    },
    //유효성 검사
    checkRule(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.checkRuleForm = true;
        } else {
          this.checkRuleForm = false;
          return false;
        }
      });
    },
    //취소
    cancle() {
      this.resetData();
      this.dialogFormVisible = false;
      this.modifyFormVisible = false;
    },
    //데이터 초기화 
    resetData() {
      this.ruleForm.groupName = '';
      this.groupNameList = [];
      this.num = this.orderIndex;
    },
    //수정 dialog에 data set
    modifyDialog(row) {
      this.modifyFormVisible = true;
      this.ruleForm.groupName = row.managegroupName;
      this.modifyIndex = row.orderbyIndex;
    },
    //수정
    modifyGroup(formName) {
      //유효성 검사
      this.checkRule(formName);
      this.checkRuleForm == true ? (this.modifyFormVisible = false) : (this.modifyFormVisible = true);
      if (this.checkRuleForm) {
        const params = { managegroupName: this.ruleForm.groupName, orderbyIndex: this.modifyIndex };
        EventApi.modifyGroup(params)
          .then((response) => {
            if (response.data.success) {
              this.$message({
                showClose: true,
                message: '그룹을 수정하였습니다',
                type: 'success',
              });
              this.getTableData();
            } else {
              this.$message.error('오류가 발생하였습니다.');
            }
          })
          .catch((ex) => {
            console.log(ex);
          });
      } else {
        this.$message.error('오류가 발생하였습니다.');
      }
      this.checkRuleForm = false;
      this.ruleForm.groupName = '';
    },
  },
};
</script>

<style scoped></style>
