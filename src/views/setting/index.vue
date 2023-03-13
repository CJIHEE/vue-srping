<template>
    <div>
        <div>
            <el-drawer 
            v-model="drawerValue" title="title" 
            :visible.sync="drawerValue" 
            size="100%" 
            style="margin: auto;"
            :before-close="handleClose"
            >
                <div slot="title" style="color: white;">
                    설정
                </div>
                <div class="setting">
                    <el-row  :gutter="12" style="margin: auto;">
                        <el-col  style="margin: auto;" >
                            <el-button @click="changeComponent('GroupSetting')">
                                그룹 설정
                            </el-button>
                            <el-button @click="changeComponent('GroupManagementSetting')">
                                그룹 관리 대상 설정
                            </el-button>
                        </el-col>
                    </el-row>
                </div>
                <div style="margin-left: 4%;">
                    <component  v-bind:is="componentName" class="component"> 
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
        drawer: function() {
            this.drawerValue = this.drawer;
        }
    }, 
    components : {GroupManagementSetting , GroupSetting},
    methods: {
        changeComponent(componentName){
            this.componentName = componentName;
        },
        handleClose(){
            this.$emit('changeDrawer');
        }
    },
}
</script>

<style>
.component {
    margin-top: 30px;
    margin-left: 20px;
}

.el-drawer__header{
    background-color: black;
    padding: 20px;
}
.setting {
    width: 90%;
    margin-left: 5%; 
    padding-bottom: 5px; 
    border-bottom: 1px solid rgb(206, 206, 206);
}
.el-icon-close{
    color: white;
}

</style>