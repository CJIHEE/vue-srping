<template>
    <div>
        <div class="title">
            {{ name }}
        </div>
        <el-row :gutter="12">
            <el-col 
                :span="4"
                shadow="always" 
                v-for="(item,index) in cardList" 
                :key="index"
                >
                <el-card >
                <p>{{item.obj_define_name}}</p>
                <p>{{item.count}}</p>
                </el-card>
            </el-col>
        </el-row>

    </div>
</template>

<script>

export default {
    name :'CardCompo',
    props : ['id','name'],
    //props 데이터 변경 감지
    watch :{
        id(){
            this.getCardData();
        },
    },
    mounted() {
        this.getCardData();
    },
    data() {
        return {
            cardList : [],
            test: '',
        }
    },
    methods : {
        getCardData(){
            console.log("id: " + this.id)
            this.$axios.get('app/event/get-card.do', { params: { id: this.id } })
            .then(response => {
            //console.log(response.data.data)
            this.cardList = response.data.data
            })
            .catch((ex) => {
              console.log(ex);
            })
        }, 



    },
}
    
</script>

<style scoped>

p{
    text-align: center;
}
.title {
    margin-bottom: 15px;
    font-weight: bold;
}
</style>
