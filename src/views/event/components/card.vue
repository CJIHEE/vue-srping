<template>
  <div>
    <div class="title">
      {{ name }}
    </div>
    <el-row :gutter="12">
      <el-col
        v-for="(item, index) in cardList"
        :key="index"
        :span="4"
        shadow="always"
      >
        <el-card 
          shadow="always" 
          class="card-style" 
          :class="{ showColor: objNameList.includes(item.obj_define_name) === true }" 
          @click.native="getObjNameTable(item.obj_define_name, index)"
        >
          <p>{{ item.obj_define_name }}</p>
          <p>{{ `${item.count} ${unit}` }}</p>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import EventApi from '@/api/event';
/**
 * ? REVIEW:
 * 코드 인덴트 확인
 * lint rule은 항상 확인 후 모두 처리
 * 컴포넌트, 인스턴스 옵션 순서 일관성있고 정렬되게 선언
 * !! 변수는 항상 카멜케이스로
 */
export default {
  name: 'CardCompo',
  props: {
    id: {
      type:  String ,
      required: true,
    },
    name: {
      type: String,
      default : 'root'
    },
  },
  data() {
    return {
      cardList: [],
      test: '',
      clcikCardIndex: '',
      ceheckCard: '',
      checkboxGroup1: [],
      objNameList: [],
      unit: '대',
    };
  },
  // props 데이터 변경 감지
  watch: {
    id: {
      handler() {
        this.loadCardData();
      },
      immediate: true,
    },
  },
  methods: {
    loadCardData() {
      const params = { id: this.id };
      EventApi.getCardData(params)
        .then((response) => {
          if (response.data.success === true) {
            if (response.data.data !== null) {
              this.cardList = response.data.data;
            }
          } else {
            this.$message({
              message: '데이터 전송에 실패했습니다',
              type: 'error',
            });
          }
        })
        .catch((e) => {
          this.$message({
            message: '장비를 조회하지 못했습니다',
            type: 'error',
          });
          throw new Error(e);
        })
        .finally(() => {
          this.loading = false;
        });
    },
    getObjNameTable(objDefineName) {
      //objNameList 배열에 있으면 삭제, 없으면 추가
      if (this.objNameList.includes(objDefineName)) {
        const findIndex = this.objNameList.indexOf(objDefineName);
        this.objNameList.splice(findIndex, 1);
      } else {
        this.objNameList.push(objDefineName);
      }
      this.$emit('submitObjNameId', objDefineName, this.objNameList);
    },
  },
};
</script>

<style scoped>
p {
  text-align: center;
}
.title {
  margin-bottom: 15px;
  font-weight: bold;
}
.card-style {
  cursor: pointer;
  background-color: rgba(184, 219, 248, 0.76);
  color: rgb(44, 91, 243);
  font-weight: bold;
}
.showColor {
  background-color: rgba(5, 40, 199, 0.76);
  color: white;
}
.el-checkbox-button.is-checked .el-checkbox-button__inner {
  color: var(--el-checkbox-button-checked-text-color);
  background-color: #208eff;
  border-color: var(--el-checkbox-button-checked-border-color);
  box-shadow: -1px 0 0 0 var(--el-color-primary-light-7);
}
</style>
