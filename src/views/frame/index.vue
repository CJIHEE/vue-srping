<template>
  <el-container class="frame-wrapper">
    <w-frame-menu/>
    <div class="frame-main-wrapper">
      <w-frame-header/>
      <w-frame-section/>
    </div>
  </el-container>
</template>

<script>
import WFrameHeader from './components/FrameHeader';
import WFrameMenu from './components/FrameMenu';
import WFrameSection from './components/FrameSection';

/**
 * 프레임
 * @author Ju
 */
export default {
  name: 'WAppFrame',
  components: {
    WFrameHeader,
    WFrameMenu,
    WFrameSection,
  },
};
</script>

<style>

</style>
