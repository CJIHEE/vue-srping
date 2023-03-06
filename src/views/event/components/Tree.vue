<template>
    <div>
        <el-input
        placeholder="Filter keyword"
        v-model="filterText">
        </el-input>
        <el-tree
            class="filter-tree"
            :data="data"
            :props="defaultProps"
            default-expand-all
            @node-click="handleNodeClick"
            ref="tree">
        </el-tree>
    </div>
</template>

<script>

export default {
    name : 'treeData',
    data() {
        return {
            filterText: '',
            data: [{
                id:'root',
                label: 'ROOT',
                children: [],
                }],
            defaultProps: {
            children: 'children',
            label: 'label'
            },
            indexId : '',   
        }
    },    
    mounted() {
      this.fetchData();
    },
    methods : {
        fetchData(){
        return this.$axios
        .get('app/event/get-tree.do') 
            .then((response) => {
              console.log(response);
              this.data[0].children = response.data.data;
            })
            .catch((ex) => {
              console.log(ex);
            });
        },
        handleNodeClick(data) {
        this.$emit('submitTreeKey', data.id, data.label)    
        this.indexId = data.id

      }
    }
  
}
</script>

<style scoped>

</style>
