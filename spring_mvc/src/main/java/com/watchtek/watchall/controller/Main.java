package com.watchtek.watchall.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
  public static void main(String[] args) {
    // 원본 데이터
    List<Map<String, Object>> originalList = new ArrayList<>();
    Map<String, Object> map1 = new HashMap<>();
    map1.put("컬럼", "값");
    List<Object> children1 = new ArrayList<>();
    children1.add("값1");
    children1.add("값2");
    children1.add("값3");
    map1.put("children", children1);
    originalList.add(map1);

    Map<String, Object> map2 = new HashMap<>();
    map2.put("컬럼", "값");
    List<Object> children2 = new ArrayList<>();
    children2.add("값4");
    children2.add("값5");
    children2.add("값6");
    map2.put("children", children2);
    originalList.add(map2);

    // 변경된 데이터
    List<Map<String, Object>> changedList = new ArrayList<>();
    for (Map<String, Object> originalMap : originalList) {
      Map<String, Object> changedMap = new HashMap<>();
      changedMap.put("컬럼", originalMap.get("컬럼"));

      List<Map<String, Object>> newChildren = new ArrayList<>();
      List<Object> originalChildren = (List<Object>) originalMap.get("children");
      for (int i = 0; i < originalChildren.size(); i++) {
        Map<String, Object> newChild = new HashMap<>();
        newChild.put("id", "추가값");
        newChild.put("node", originalChildren.get(i));
        newChildren.add(newChild);
      }
      changedMap.put("children", newChildren);
      changedList.add(changedMap);
    }

    // 결과 출력
    System.out.println(changedList);
  }
}