<template>
  <div>
    <el-input
      v-model="filterText"
      placeholder="Filter keyword"
    />
    <el-tree
      ref="tree"
      class="filter-tree"
      :data="data"
      :props="defaultProps"
      :filter-node-method="filterNode"
      default-expand-all
      @node-click="handleNodeClick"
    />
  </div>
</template>

<script>
import EventApi from '@/api/event';
/**
 * ? REVIEW:
 * 코드 인덴트 확인
 * lint rule은 항상 확인 후 모두 처리
 */
export default {
  name: 'TreeData',
  data() {
    return {
      filterText: '',
      data: [
        {
          id: 'root',
          label: 'ROOT',
          children: [],
        },
      ],
      defaultProps: {
        children: 'children',
        label: 'label',
      },
      indexId: '',
    };
  },
  watch :{
    filterText(val) {
        this.$refs.tree.filter(val);
      }
  },
  mounted() {
    this.fetchData();
  },
  methods: {
    filterNode(value, data) {
        if (value === '') {
          return true;
        }
        return data.label?.indexOf(value) !== -1;
    },
    fetchData() {
      EventApi.getTree()
        .then((response) => {
          if (response.data.success === true) {
            if ('' !== response.data.data) {
              this.data[0].children = response.data.data;
            }
          } else {
            this.$message({
              message: '데이터 전송이 실패했습니다',
              type: 'error',
            });
          }
        })
        .catch((e) => {
          this.$message({
            message: '트리 정보를 조회하지 못했습니다',
            type: 'error',
          });
          throw new Error(e);
        });
    },
    handleNodeClick(data) {
      this.$emit('submitTreeKey', data.id, data.label, data.treeKey);
      this.indexId = data.id;
    },

  },
};
</script>

<style scoped></style>
