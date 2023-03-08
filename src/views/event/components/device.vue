<template>
    <div>
        <div  
         v-for="(item,index) in deviceData" 
         :key="index"
        >
            <div class="title">
                {{ item.obj_name }}({{item.ipaddress_ipv4  }})
            </div>
            <el-row :gutter="12">
                <el-col 
                    :span="24"
                    shadow="always" 
                    >
                    <el-card >
                        <div class="parent">
                            <span style="margin-bottom: 10px;"><span class="span-style"> · 장비명</span>{{item.obj_name}}</span>
                            <span><span class="span-style"> · 장비 구분</span>{{item.obj_define_name}}</span>
                            <span><span class="span-style"> · 제조사</span>{{item.obj_company_name}}</span>
                        </div>
                        <div class="parent">   
                            <span><span  class="span-style"> · IP</span>{{item.ipaddress_ipv4}}</span>
                            <span><span class="span-style"> · 장비 종류</span>{{item.objtype_name}}</span>
                            <span><span class="span-style"> · 모델</span>{{item.obj_product_id}}</span>
                        </div>
                    
                    </el-card>
                </el-col>
            </el-row>
    </div>
    </div>
</template>

<script>

export default{
    name :'CardCompo',
    props : ['id','name'],
    //props 데이터 변경 감지
    watch :{
        id(){
            this.getDeviceData();
        },
    },
    mounted() {
        this.getDeviceData();
    },
    data() {
        return {
            deviceData : [],
        }
    },
    methods : {
        getDeviceData(){
            this.$axios.get('app/event/get-card.do', { params: { id: this.id} })
            .then(response => {
                //console.log(response.data.data)

                this.deviceData = response.data.data
            })
            .catch((ex) => {
                console.log(ex);
            })
        }   

    },
}
    
</script>

<style scoped>

p{
    text-align: center;
}

.parent {
    display : flex;
}
span {
    flex: 1;
}
.span-style {
    font-weight: bold;
    margin-right: 10px;
}
.title {
    margin-bottom: 15px;
    font-weight: bold;
}
</style>
