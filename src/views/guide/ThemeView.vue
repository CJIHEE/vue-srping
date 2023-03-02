<template>
  <div>
    <div class="mr-b25">
      <div class="title-medium mr-b10">Button</div>
      <el-row>
        <el-button>Default</el-button>
        <el-button type="primary">Primary</el-button>
        <el-button type="success">Success</el-button>
        <el-button type="info">Info</el-button>
        <el-button type="warning">Warning</el-button>
        <el-button type="danger">Danger</el-button>
      </el-row>
    </div>
    <div>
      <div class="title-medium mr-b10">Table</div>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column label="Date" width="180">
          <template #default="scope">
            <i class="el-icon-time"></i>
            <span style="margin-left: 10px">{{ scope.row.date }}</span>
          </template>
        </el-table-column>
        <el-table-column label="Name" width="180">
          <template #default="scope">
            <el-popover trigger="hover" placement="top">
              <p>Name: {{ scope.row.name }}</p>
              <p>Addr: {{ scope.row.address }}</p>
              <template #reference>
                <el-tag size="medium">{{ scope.row.name }}</el-tag>
              </template>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column label="Operations">
          <template #default="scope">
            <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">Delete</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
/**
 * 테마 확인용 화면
 * @author Ju
 */
export default {
  name: 'WTheme',
  data() {
    return {
      /**
       * 테이블 데이터
       * @type {Array}
       */
      tableData: [
        {
          date: '2016-05-03',
          name: 'Tom',
          address: 'No. 189, Grove St, Los Angeles',
        },
        {
          date: '2016-05-02',
          name: 'Tom',
          address: 'No. 189, Grove St, Los Angeles',
        },
        {
          date: '2016-05-04',
          name: 'Tom',
          address: 'No. 189, Grove St, Los Angeles',
        },
        {
          date: '2016-05-01',
          name: 'Tom',
          address: 'No. 189, Grove St, Los Angeles',
        },
      ],
    };
  },
};
</script>

<style lang="scss" scoped></style>
