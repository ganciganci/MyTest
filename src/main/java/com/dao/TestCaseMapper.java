package com.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface TestCaseMapper {
    List<Map<String, Object>> getData();
}
