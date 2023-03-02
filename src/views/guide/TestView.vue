<template>
  <div>
    <div class="title-medium mr-b10">이벤트 등급 (API 테스트용)</div>
    <el-table v-loading="loading" :data="data" style="width: 100%">
      <el-table-column prop="eventlevelId" label="이벤트 등급 아이디"> </el-table-column>
      <el-table-column prop="eventlevelName" label="이벤트 등급 이름"> </el-table-column>
      <el-table-column prop="eventlevelColorBg" label="배경 색상"> </el-table-column>
      <el-table-column prop="eventlevelColorText" label="텍스트 색상"> </el-table-column>
      <el-table-column prop="orderbyIndex" label="정렬 순서"> </el-table-column>
    </el-table>
  </div>
</template>

<script>
import EventApi from '@/api/event';

/**
 * 테스트 화면
 * @author Ju
 */
export default {
  name: 'WTestView',
  data() {
    return {
      /**
       * 테이블 데이터
       * @type {Array}
       */
      data: [],
      /**
       * 데이터 로딩 여부
       * @type {Boolean}
       */
      loading: false,
    };
  },
  mounted() {
    this.loadEventLevelData();
  },
  methods: {
    /**
     * 이벤트 등급 정보를 조회한다.
     * @author Ju
     */
    loadEventLevelData() {
      this.loading = true;
      EventApi.getEventLevels()
        .then((response) => {
          if (response.data.success === true) {
            this.data = response.data.data;
          } else {
            this.$message({
              message: '이벤트 등급 정보를 조회하지 못하였습니다',
              type: 'error',
            });
          }
        })
        .catch((e) => {
          this.$message({
            message: '이벤트 등급 정보를 조회하지 못하였습니다',
            type: 'error',
          });
          throw new Error(e);
        })
        .finally(() => {
          this.loading = false;
        });
    },
  },
};
</script>
