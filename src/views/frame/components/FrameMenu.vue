<template>
  <div>
    <nav>
      <ul style="height: calc(100% - 50px);">
        <li class="logo-wrapper">
          <div>
            <i class="logo"></i>
            <span>WatchAll</span>
          </div>
        </li>
        <li class="active">
          <div @click="goGuidePage()">
            <i class="far fa-file-alt" style="font-size: 20px !important;"></i>
            <span>가이드</span>
          </div>
        </li>
        <li>
          <div  @click="goEventPage()">
            <i class="far fa-bell" style="font-size: 20px !important;"></i>
            <span>이벤트</span>
          </div>
        </li>
      </ul>
      <ul class="setting-menu" style="height: 100px;">
        <li>
          <div
            @click="settingDrawer(),drawer = true">
            <i class="fas fa-cog"></i>
            <span>설정</span>
          </div>
        </li>
      </ul>
    </nav>
    
    <div>
      <settingIndex :drawer="drawer" @changeDrawer="settingDrawer"> </settingIndex>
    </div>
</div>
</template>

<script>
/**
 * 프레임 매뉴
 * @author Ju
 */

//import EventBus from '@/EventBus';
import SettingIndex from '../../setting/index.vue'

export default {
  name: 'WFrameMenu',
  components : {SettingIndex },
  data() {
    return {
      drawer : false
    }
  },
  methods: {
    goGuidePage(){
      this.$router.push('/guide');
    },
    goEventPage(){
      this.$router.push('/event');
    },
    settingDrawer(){
      this.drawer= false 
    }
  },

};
</script>

<style></style>
