<template>
  <div>
    <div class="title-large mr-b10">Front-end 최종 과제 테이블 정보</div>
    <div v-for="(item, index) in data" class="table-info" :key="index">
      <div class="title-medium mr-b10">{{ item.title }}</div>
      <w-table-info-table :data="item.tableData" />
    </div>
  </div>
</template>

<script>
import WTableInfoTable from './components/TableInfoTable';

/**
 * 테이블 정보 화면
 * @author Ju
 */
export default {
  name: 'WTableInfo',
  components: {
    WTableInfoTable,
  },
  data() {
    return {
      data: [
        {
          title: '관리 대상(obj)',
          tableData: [
            {
              pk: true,
              field: 'obj_id',
              name: '관리 대상 아이디',
              desc: '-',
            },
            {
              field: 'obj_name',
              name: '관리 대상 이름',
              desc: '-',
            },
            {
              field: 'ipaddress_applytype',
              name: 'IP 주소 타입',
              desc: '-',
            },
            {
              field: 'ipaddress_ipv4',
              name: 'IP 주소 (v4)',
              desc: 'ipaddress_applytype이 "IPv4"일 때 제공',
            },
            {
              field: 'ipaddress_ipv6',
              name: 'IP 주소 (v6)',
              desc: 'ipaddress_applytype이 "IPv6"일 때 제공',
            },
            {
              field: 'obj_define_id',
              name: '장비 분류(정의) 아이디',
              desc: 'obj_define 테이블과 obj_define_id로 Relatiopship 가짐',
            },
            {
              field: 'objtype_id',
              name: '장비 종류 아이디',
              desc: 'objtype 테이블과 objtype_id로 Relatiopship 가짐',
            },
            {
              field: 'obj_company_id',
              name: '제조사 아이디',
              desc: 'obj_company 테이블과 obj_company로 Relatiopship 가짐',
            },
          ],
        },
        {
          title: '장비 분류(obj_define)',
          tableData: [
            {
              pk: true,
              field: 'obj_define_id',
              name: '장비 분류(정의) 아이디',
              desc: 'obj_define 테이블과 obj_define_id로 Relatiopship 가짐',
            },
            {
              field: 'obj_define_name',
              name: '장비 분류 이름',
              desc: '-',
            },
            {
              field: 'orderby_index',
              name: '정렬 순서',
              desc: '-',
            },
          ],
        },
        {
          title: '장비 종류(objtype)',
          tableData: [
            {
              pk: true,
              field: 'objtype_id',
              name: '장비 종류 아이디',
              desc: 'objtype 테이블과 objtype_id로 Relatiopship 가짐',
            },
            {
              field: 'objtype_name',
              name: '장비 종류 이름',
              desc: '-',
            },
            {
              field: 'orderby_index',
              name: '정렬 순서',
              desc: '-',
            },
          ],
        },
        {
          title: '제조사 (obj_company)',
          tableData: [
            {
              pk: true,
              field: 'obj_company_id',
              name: '제조사 아이디',
              desc: 'obj_company 테이블과 obj_company_id로 Relatiopship 가짐',
            },
            {
              field: 'obj_company_name',
              name: '제조사 이름',
              desc: '-',
            },
            {
              field: 'orderby_index',
              name: '정렬 순서',
              desc: '-',
            },
          ],
        },
        {
          title: '이벤트 이력 (event_hist)',
          tableData: [
            {
              pk: true,
              field: 'seq_number',
              name: '이벤트 번호',
              desc: '-',
            },
            {
              field: 'obj_id',
              name: '관리 대상 아이디',
              desc: 'obj 테이블과 obj_id로 Relatiopship로 가짐',
            },
            {
              field: 'rsc_name',
              name: '자원',
              desc: '-',
            },
            {
              field: 'event_message',
              name: '장애 메시지',
              desc: '-',
            },
            {
              field: 'eventlevel_id',
              name: '이벤트 등급 아아디',
              desc: 'eventlevel 테이블과 eventlevel_id로 Relatiopship 가짐',
            },
            {
              field: 'check_datetime',
              name: '발생 시각',
              desc: '-',
            },
          ],
        },
        {
          title: '이벤트 등급 (eventlevel)',
          tableData: [
            {
              pk: true,
              field: 'eventlevel_id',
              name: '이벤트 등급 아이디',
              desc: '-',
            },
            {
              field: 'eventlevel_name',
              name: '이벤트 등급 이름',
              desc: '-',
            },
            {
              field: 'eventlevel_color_bg',
              name: '이벤트 등급 배경 색상',
              desc: '-',
            },
            {
              field: 'eventlevel_color_text',
              name: '이벤트 텍스트 배경 색상',
              desc: '-',
            },
            {
              field: 'orderby_index',
              name: '정렬 순서',
              desc: '-',
            },
          ],
        },
        {
          title: '그룹 (managegrp)',
          tableData: [
            {
              pk: true,
              field: 'managegroup_id',
              name: '그룹 아이디',
              desc: '-',
            },
            {
              field: 'managegroup_name',
              name: '그룹 이름',
              desc: '-',
            },
            {
              field: 'orderby_index',
              name: '정렬 순서',
              desc: '-',
            },
          ],
        },
        {
          title: '그룹 별 관리 대상 설정 (managegrp_obj)',
          tableData: [
            {
              pk: true,
              field: 'managegroup_id',
              name: '그룹 아이디',
              desc: 'managegrp 테이블과 managegroup_id Relatiopship 가짐',
            },
            {
              field: 'obj_id',
              name: '그룹 이름',
              desc: 'obj 테이블과 obj_id로 Relatiopship 가짐',
            },
            {
              field: 'orderby_index',
              name: '정렬 순서',
              desc: '-',
            },
          ],
        },
      ],
    };
  },
};
</script>

<style lang="scss" scoped>
.table-info:not(:last-child) {
  margin-bottom: 25px;
}
</style>
