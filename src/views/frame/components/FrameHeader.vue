<template>
  <el-header class="frame-header">
    <el-row class="header-wrapper" type="flex" justify="space-between" align="middle">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item>Root</el-breadcrumb-item>
        <el-breadcrumb-item>Group1</el-breadcrumb-item>
        <el-breadcrumb-item>Server#1</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="frame-tool">
        <i class="fas fa-user-circle"></i>
        <el-popover placement="top-start" width="240" trigger="click">
          <div style="text-align:center;">
            <div class="logo"></div>
            <div class="info">Made by Dewberry Team.Ju (2021)</div>
            <div class="copyright">Copyright © Watchtek Co.,Ltd. <br/>All Rights Reserved.</div>
          </div>
          <template #reference>
            <i class="info-icon fas fa-info-circle"></i>
          </template>
        </el-popover>
      </div>
    </el-row>
  </el-header>
</template>

<script>
/**
 * 프레임 헤더
 * @author Ju
 */
export default {
  name: 'WFrameHeader',
};
</script>

<style scoped>
.logo {
  display: inline-block;
  width: 100px;
  height: 100px;
  margin-top: 9px;
  margin-left: 9px;
  background: url('../../../assets/img/dewberry_blue.png') no-repeat center 0;
  background-size: cover;
}
.info {
  color: #7075ff;
}
.copyright {
  font-size: 12px;
  color: #9796a0;
}
</style>
