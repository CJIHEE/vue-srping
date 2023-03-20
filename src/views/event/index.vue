<template>
  <div class="guide-wrapper">
    <div class="frame-widget side">
      <el-scrollbar>
        <el-menu
          default-active="0"
          @select="handleMenuSelect"
        >
          <el-menu-item
            v-for="(item, index) in menu"
            :key="item.id"
            :index="`${index}`"
          >
            <span>{{ item.label }}</span>
          </el-menu-item>
        </el-menu>
        <Tree @submitTreeKey="changeRouter" />
      </el-scrollbar>
    </div>
    <div class="frame-widget main">
      <el-scrollbar>
        <!-- REVIEW: 불필요한 출력 및 테스트코드 반드시 삭제 -->
        <!-- <component :is="activeMenu.component" /> -->
        <router-view />
      </el-scrollbar>
    </div>
  </div>
</template>

<script>
/**
 * ? REVIEW:
 * 코드 인덴트 확인
 * lint rule은 항상 확인 후 모두 처리
 */
import Tree from './components/Tree';

/**
 * 가이드 화면
 * @author Ju
 */
export default {
  name: 'WGuide',
  components: { Tree },
  data() {
    const menu = [
      {
        id: 'RGinfo',
        label: 'RGinfo',
        component: 'RootGroupInfo',
      },
    ];
    return {
      /**
       * 메뉴 데이터
       * @type {Array}
       */
      menu,
      /**
       * 활성화된 메뉴 정보
       * @type {Object}
       */
      activeMenu: menu[0],
    };
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    },
  },
  created() {
    this.$router.push({ name: 'RootGroupInfo', params: { id: 'root', name: 'root', treeKey: 1 } });
  },
  methods: {
    handleMenuSelect(index) {
      this.activeMenu = this.menu[index];
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    changeRouter(id, treename, treeKey) {
      this.treeKey = id;
      this.$router.push({ name: 'RootGroupInfo', params: { name: treename, treeKey: treeKey } });
      this.$store.commit('setTreeKey', id);
    },
  },
};
</script>

<style lang="scss" scoped>
.guide-wrapper {
  height: 100%;
  width: 100%;

  .side {
    float: left;
    width: 300px;
    height: 100%;
    margin-right: 15px;
    padding: 10px 0;

    .el-menu {
      border-right: none;
      .el-menu-item {
        height: 40px;
        line-height: 40px;
        &.is-active {
          outline: none;
          background-color: #f2f1fd;
        }
        &:not(:last-child) {
          margin-bottom: 4px;
        }
      }
    }
  }
  .main {
    float: right;
    width: calc(100% - 315px);
    height: 100%;
  }
}
</style>
