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
                <el-card
                shadow="always"
                @click.native="getObjNameTable(item.obj_define_name,index)"
                class="card-style"
                :class="{ showColor : objNameList.includes(item.obj_define_name) ===true }"
                >
                <p>{{item.obj_define_name}}</p>
                <p>{{item.count}}대</p>
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
        id : {
            handler: function(){
                this.getCardData();
            },
            immediate: true
        },
    },
    data() {
        return {
            cardList : [],
            test: '',
            clcikCardIndex: '',
            ceheckCard : '',
            checkboxGroup1: [],
            objNameList : [],
        }
    },
    methods : {
        getCardData(){
            this.$axios.get('app/event/get-card.do', { params: { id: this.id } })
            .then(response => {
            //console.log(response.data.data)
            this.cardList = response.data.data;
            })
            .catch((ex) => {
              console.log(ex);
            })
        }, 
        getObjNameTable(objDefineName){
            //objNameList 배열에 있으면 삭제, 없으면 추가
            if(this.objNameList.includes(objDefineName)) {
                let findIndex = this.objNameList.indexOf(objDefineName);
                this.objNameList.splice(findIndex,1);
            }
            else{
                this.objNameList.push(objDefineName);
            }
            this.$emit('submitObjNameId',objDefineName,this.objNameList);
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
.card-style{
    cursor: pointer; 
    background-color:  rgba(184, 219, 248, 0.76); 
    color: rgb(44, 91, 243); 
    font-weight: bold;
}
.showColor{
    background-color: rgba(5, 40, 199, 0.76);
    color: white;
}
.el-checkbox-button.is-checked .el-checkbox-button__inner {
    color: var(--el-checkbox-button-checked-text-color);
    background-color: #208eff;
    border-color: var(--el-checkbox-button-checked-border-color);
    box-shadow: -1px 0 0 0 var(--el-color-primary-light-7);
}


</style>
