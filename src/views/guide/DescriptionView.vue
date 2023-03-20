<template>
  <div class="desc-wrapper">
    <div class="title-large mr-b10">
      Front-end 최종 과제
    </div>
    <p>
      개발에는 정답은 없습니다.<br>
      템플릿 프로젝트에 작성된 코드는 '예제'일 뿐 정답이 아닙니다. <em>항상 더 좋은 코드를 작성하기 위해 고민, 또 고민해야합니다.</em> <br>
      <em>절대 이해 없는 무분별한 참고, Copy&Paste는 지양해야 합니다.</em> 역량 없는 개발자로 가는 지름길입니다.<br>
    </p>
    <p>{{ result }}</p>
    <div
      v-for="(item,index) in result" 
      :key="index"
    >
      {{ item.name }}
    </div>
  </div>
</template>

<script>
export default {
  name: 'DescriptionName',
  data() {
    return {
      result : [],
    }
  },
  created(){
    this.fetchData();
  },
  methods : {
      fetchData(){
        return this.$axios
        .get('app/test.do') 
            .then((response) => {
              console.log(response);
              this.result = response.data;
            })
            .catch((ex) => {
              console.log(ex);
            });
        }
    }
};
</script>

<style lang="scss" scoped>
.desc-wrapper {
  p {
    color: #5b5b61;
  }
  em {
    color: rgb(250, 83, 83);
    font-style: inherit;
  }
}
</style>
