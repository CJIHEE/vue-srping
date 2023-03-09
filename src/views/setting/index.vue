<template>
    <div>
        <div>
        <el-drawer 
        v-model="drawerValue" title="설정"  
        :visible.sync="drawerValue" 
        size="100%" 
        style="margin: auto;"
        :before-close="handleClose"
        >
            <div>
                <el-row 
                :gutter="12" 
                style="margin: auto;"
                >
                    <el-col 
                    style="margin: auto;"
                    >
                        <el-button 
                         @click="changeComponent('GroupSetting')">
                            그룹 설정
                        </el-button>
                        <el-button
                         @click="changeComponent('GroupManagementSetting')">
                            그룹 관리 대상 설정
                        </el-button>
                    </el-col>
                </el-row>
            </div>
            <div>
                <component 
                v-bind:is="componentName"
                class="component">
                </component>
            </div>
        </el-drawer>
    </div>

    </div>
</template>

<script>
import GroupManagementSetting from './GroupManagementSetting.vue'
import GroupSetting from './GroupSetting.vue'

export default {
    name : 'settingIndex',
    props : ['drawer'],
    data() {
        return {
            drawerValue : this.drawer,
            componentName :'GroupSetting',
        }
    },
    watch: {
        drawer: function() { // watch it
            this.drawerValue = this.drawer;
        }
    }, 
    components : {GroupManagementSetting , GroupSetting},
    methods: {
        changeComponent(componentName){
            this.componentName = componentName;
            console.log("componentName:" + this.componentName)
        },
        handleClose(){
            this.$emit('changeDrawer');
        }
    },
    
}
</script>

<style scoped>
.component {
    margin-top: 30px;
    margin-left: 20px;

}

</style>