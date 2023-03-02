<template>
  <div class="guide-wrapper">
    <div class="frame-widget side">
      <el-scrollbar>
        <el-menu 
        default-active="0" 
        @select="handleMenuSelect"
        >
          <el-menu-item v-for="(item, index) in menu" :index="`${index}`" :key="item.id">
            <span>{{ item.label }}</span>
          </el-menu-item>
        </el-menu>
      </el-scrollbar>
    </div>
    <div class="frame-widget main">
      <el-scrollbar>
        <component :is="activeMenu.component" />
       
      </el-scrollbar>
    </div>
  </div>
</template>

<script>
import WDescriptionView from './DescriptionView';
import WTableInfoView from './TableInfoView';
import WThemeView from './ThemeView';
import WTestView from './TestView';

/**
 * 가이드 화면
 * @author Ju
 */
export default {
  name: 'WGuide',
  components: {
    WDescriptionView,
    WTableInfoView,
    WThemeView,
    WTestView,
  },
  data() {
    const menu = [
      {
        id: 'desc',
        label: 'Description',
        component: 'WDescriptionView',
      },
      {
        id: 'table',
        label: 'Table Info',
        component: 'WTableInfoView',
      },
      {
        id: 'theme',
        label: 'Theme',
        component: 'WThemeView',
      },
      {
        id: 'test',
        label: 'Test',
        component: 'WTestView',
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
      Treekey : '',
    };
  },
  methods: {
    handleMenuSelect(index) {
      this.activeMenu = this.menu[index];
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
