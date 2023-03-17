<template>
  <div>
    <!--장비 카드-->
    <div>
      <component
        :is="componentName"
        :id="id"
        :name="name"
        @submitObjNameId="setObjNameId"
      />
    </div>
    <!--차트-->
    <div style="margin-top: 15px;">
      <chart :id="id" />
    </div>
    <!-- 검색 및 필터-->
    <div class="parent">
      <div class="child">
        <el-select
          v-model="value"
          filterable
          placeholder="전체"
        >
          <el-option
            v-for="item in eventGrade"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </div>
      <div style="margin: 10px 0px; flex: 1.5;">
        <el-input
          v-model="searchWord"
          placeholder="검색"
          style="float:right; width: 35%;"
          prefix-icon="el-icon-search"
          @change="loadEventList"
        />
      </div>
    </div>
    <!-- 이벤트 목록 조회-->
    <div>
      <el-table
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          label="등급"
          width="180"
        >
          <template slot-scope="scope">
            <el-tag
              :style="styleBinding(scope.row.eventLevelName)"
              disable-transitions
            >
              {{ scope.row.eventLevelName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column
          prop="objName"
          label="장비"
          width="180"
        />
        <el-table-column
          prop="objtypeName"
          label="장비 종류"
        />
        <el-table-column
          prop="objCompanyName"
          label="제조사"
        />
        <el-table-column
          prop="rscName"
          label="자원"
        />
        <el-table-column label="장애 메시지">
          <template slot-scope="scope">
            <el-tag
              style="cursor: pointer"
              @click="eventDetail(scope.row.rno), (drawer = true)"
            >
              {{ scope.row.eventMessage }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column
          prop="checkDatetime"
          label="발생 시각"
        />
      </el-table>
      <div
        class="block"
        style="margin-top: 10px;"
      >
        <el-pagination
          :current-page.sync="currentPage"
          :page-sizes="[25, 50, 100, 500]"
          :page-size="sizePerPage"
          layout="total, sizes, prev, pager, next, jumper"
          :total="totalPage"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </div>
    <!-- 이벤트 상세보기-->
    <el-drawer
      v-model="drawer"
      title="이벤트 성능 정보"
      :visible.sync="drawer"
      size="50%"
    >
      <div
        v-for="(item, idx) in eventCardDetail"
        :key="idx"
        style="margin: auto;"
      >
        <el-row
          :gutter="24"
          style="margin-left: 10%;"
        >
          <el-col
            :span="20"
            shadow="always"
            style="margin: auto;"
          >
            <!--장비 상세보기 카드-->
            <el-card>
              <div class="parent">
                <span style="margin-bottom: 10px;">
                  <span class="span-style">· 장비명 </span>
                  {{ item.objName }}
                </span>
              </div>
              <div class="parent">
                <span>
                  <span class="span-style">· 장애 메시지 </span>
                  {{ item.eventMessage }}
                </span>
              </div>
            </el-card>
            <!-- 상세보기 차트-->
            <div>
              <chartDetail
                :id="id"
                :device-detail-name="deviceDetailName"
                :drawer="drawer"
              />
            </div>
          </el-col>
        </el-row>
      </div>
    </el-drawer>
  </div>
</template>

<script>
/**
 * ? REVIEW:
 * 코드 인덴트 확인
 * lint rule은 항상 확인 후 모두 처리
 * 컴포넌트, 인스턴스 옵션 순서 일관성있고 정렬되게 선언
 */
import card from './components/card';
import device from './components/device';
import chart from './components/chart';
import chartDetail from './components/chartDetail';
import EventApi from '@/api/event';

export default {
  name: 'RootGroupInfo',
  components: { card, device, chart, chartDetail },
  data() {
    return {
      deviceData: [],
      componentName: '',
      tableData: [],
      currentPage: 1,
      sizePerPage: 25,
      totalPage: 0,
      value: '',
      searchWord: '',
      chartData: [1, 2, 3, 4],
      eventGrade: [
        {
          value: 'Critical',
          label: 'Critical',
        },
        {
          value: 'Down',
          label: 'Down',
        },
        {
          value: 'Info',
          label: 'Info',
        },
        {
          value: 'Major',
          label: 'Major',
        },
        {
          value: 'Minor',
          label: 'Minor',
        },
        {
          value: 'Normal',
          label: 'Normal',
        },
      ],
      drawer: false,
      eventCardDetail: [],
      deviceDetailName: '',
      objDefineName: '',
      objNameList: '',
      startNum: 0,
    };
  },
  computed: {
    id() {
      return this.$store.state.treeKey;
    },
    name() {
      return this.$route.params.name;
    },
    styleBinding() {
      return (eventLevelName) => {
        let bgColor = '';
        let fontColor = 'white';
        if (eventLevelName === 'Normal') {
          bgColor = 'purple';
          fontColor = 'white';
        } else if (eventLevelName === 'Down') {
          bgColor = 'gray';
          fontColor = 'white';
        } else if (eventLevelName === 'Minor') {
          bgColor = 'orange';
          fontColor = 'white';
        } else if (eventLevelName === 'Major') {
          bgColor = 'yellow';
          fontColor = 'black';
        } else {
          bgColor = 'red';
          fontColor = 'white';
        }
        return { backgroundColor: bgColor, color: fontColor };
      };
    },
  },
  watch: {
    id: {
      handler() {
        this.changeComponent();
        this.currentPage = 1;
        this.sizePerPage = 25;
        this.startNum = 0;
        this.value = '';
        this.loadEventList();
        this.objNameList = '';
      },
      deep: true,
      immediate: true,
    },
    value() {
      this.loadEventList();
    },
  },
  methods: {
    changeComponent() {
      if (isNaN(this.id)) {
        this.componentName = 'card';
      } else {
        this.componentName = 'device';
      }
    },
    handleSizeChange(val) {
      this.sizePerPage = val;
      this.loadEventList();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.startNum = (this.currentPage - 1) * this.sizePerPage;
      this.loadEventList();
    },
    loadEventList() {
      const params = {
        id: this.id,
        currentPage: this.currentPage,
        sizePerPage: this.sizePerPage,
        value: this.value,
        searchWord: this.searchWord,
        objDefineName: this.objDefineName,
        objNameList: this.objNameList,
        startNum: this.startNum,
      };
      EventApi.getEventList(params)
        .then((response) => {
          if (response.data.data !== '') {
            this.tableData = response.data.data;
            this.totalPage = response.data.total;
          } else {
            this.tableData = [];
            this.totalPage = '';
          }
        })
        .catch((ex) => {
          console.log(ex);
        })
        .finally(() => {
          this.objDefineName = '';
        });
    },
    eventDetail(index) {
      const params = {
        index: index,
        id: this.id,
        value: this.value,
        searchWord: this.searchWord,
        objNameList: '',
      };
      EventApi.getEventDetail(params)
        .then((response) => {
          if (response.data.data !== '') {
            this.eventCardDetail = response.data.data;
            this.deviceDetailName = response.data.data[0].obj_name;
          }
        })
        .catch((ex) => {
          console.log(ex);
        });
    },
    //장비카드 클릭시 이벤트 목록 조회 변경
    setObjNameId(objDefineName, objNameList) {
      this.objDefineName = objDefineName;
      this.objNameList = objNameList.join(',');
      this.loadEventList();
      this.objDefineName = '';
    },
  },
};
</script>

<style scoped>
.title {
  margin-bottom: 15px;
}
.parent {
  display: flex;
}
.child {
  flex: 0.5;
  margin: 10px 10px 10px 0px;
}
.span-style {
  font-weight: bold;
  margin-right: 10px;
}
</style>
