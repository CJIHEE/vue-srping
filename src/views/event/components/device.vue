<template>
  <div>
    <div>
      <div class="title">
        {{ deviceData.objName }}({{ deviceData.ipaddressIpv4 }})
      </div>
      <el-row :gutter="12">
        <el-col
          :span="24"
          shadow="always"
        >
          <el-card>
            <div class="parent">
              <span style="margin-bottom: 10px;">
                <span class="span-style"> · 장비명 </span>
                {{ deviceData.objName }}
              </span>
              <span>
                <span class="span-style"> · 장비 구분</span>
                {{ deviceData.objDefineName }}
              </span>
              <span>
                <span class="span-style"> · 제조사</span>
                {{ deviceData.objCompanyName }}
              </span>
            </div>
            <div class="parent">
              <span>
                <span class="span-style"> · IP</span>
                {{ deviceData.ipaddressIpv4 }}
              </span>
              <span>
                <span class="span-style"> · 장비 종류</span>
                {{ deviceData.objtypeName }}
              </span>
              <span>
                <span class="span-style"> · 모델 </span>
                {{ deviceData.objProductId }}
              </span>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
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
  name: 'CardCompo',
  props: {
    id: {
      type: String,
      required: true
    },
    name: {
      type: String,
      default: 'root',
    },
  },
  data() {
    return {
      deviceData: {
        objName: '-',
        ipaddressIpv4: '-',
        objDefineName: '-',
        objCompanyName: '-',
        objtypeName: '-',
        objProductId: '-',
      },
    };
  },
  //props 데이터 변경 감지
  watch: {
    id: {
      handler() {
        this.loadDeviceData();
      },
      immediate: true,
    },
  },
  methods: {
    loadDeviceData() {
      const params = { id: this.id };
      EventApi.getDeviceData(params)
        .then((response) => {
          if (response.data.success === true) {
            if (response.data.data.present === false) {
              (this.deviceData.objName = '-'), (this.deviceData.ipaddressIpv4 = '-'), (this.deviceData.objDefineName = '-'), (this.deviceData.objCompanyName = '-'), (this.deviceData.objtypeName = '-'), (this.deviceData.objProductId = '-');
            } else {
              this.deviceData = response.data.data;
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
            message: '장비를 조회하지 못했습니다',
            type: 'error',
          });
          throw new Error(e);
        });
    },
  },
};
</script>
<style scoped>
p {
  text-align: center;
}

.parent {
  display: flex;
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