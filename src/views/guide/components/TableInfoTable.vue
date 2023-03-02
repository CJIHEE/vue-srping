<template>
  <el-table :data="data" style="width: 100%">
    <el-table-column label="PK" width="40">
      <template #default="scope">
        <i v-if="scope.row.pk" class="fas fa-key" style="color: #9691f2;"></i>
      </template>
    </el-table-column>
    <el-table-column prop="field" label="필드" width="200"> </el-table-column>
    <el-table-column prop="name" label="이름" width="200"> </el-table-column>
    <el-table-column prop="desc" label="설명"> </el-table-column>
  </el-table>
</template>

<script>
/**
 * 테이블 정보 테이블
 * @author Ju
 */
export default {
  name: 'WTableInfoTable',
  props: {
    /**
     * 테이블 데이터
     */
    data: {
      type: Array,
    },
  },
};
</script>

<style lang="scss" scoped></style>
