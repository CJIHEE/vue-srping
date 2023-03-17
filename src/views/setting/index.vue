<template>
  <div>
    <div>
      <el-drawer
        v-model="drawerValue"
        title="title"
        :visible.sync="drawerValue"
        size="100%"
        style="margin: auto;"
        :before-close="handleClose"
      >
        <div
          slot="title"
          style="color: white;"
        >
          설정
        </div>
        <div class="setting">
          <el-row
            :gutter="12"
            style="margin: auto;"
          >
            <el-col style="margin: auto;">
              <el-button @click="changeComponent('GroupSetting')">
                그룹 설정
              </el-button>
              <el-button @click="changeComponent('GroupManagementSetting')">
                그룹 관리 대상 설정
              </el-button>
            </el-col>
          </el-row>
        </div>
        <div style="margin-left: 4%;">
          <component
            :is="componentName"
            class="component"
          />
        </div>
      </el-drawer>
    </div>
  </div>
</template>

<script>
/**
 * ? REVIEW:
 * 코드 인덴트 확인
 * lint rule은 항상 확인 후 모두 처리
 * 컴포넌트, 인스턴스 옵션 순서 일관성있고 정렬되게 선언
 * !! 변수 반드시 의미있게 작성해주세요 (value2, value3이 뭔지 다른사람은 모릅니다)
 * !! 변수는 반드시 카멜케이스로
 */
import GroupManagementSetting from './GroupManagementSetting';
import GroupSetting from './GroupSetting';

export default {
  name: 'SettingIndex',
  components: { GroupManagementSetting, GroupSetting },
  props: { drawer: Boolean },
  data() {
    return {
      drawerValue: this.drawer,
      componentName: 'GroupSetting',
    };
  },
  watch: {
    // ? REVIEW: 문법 혼용 주의 (es6 사용 권장)
    drawer() {
      this.drawerValue = this.drawer;
    },
  },
  methods: {
    changeComponent(componentName) {
      this.componentName = componentName;
    },
    handleClose() {
      this.$emit('changeDrawer');
    },
  },
};
</script>

<style>
.component {
  margin-top: 30px;
  margin-left: 20px;
}

.el-drawer__header {
  background-color: black;
  padding: 20px;
}
.setting {
  width: 90%;
  margin-left: 5%;
  padding-bottom: 5px;
  border-bottom: 1px solid rgb(206, 206, 206);
}
.el-icon-close {
  color: white;
}
</style>
