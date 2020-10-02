package com.duyi.onlinevideo.dao;

import com.duyi.onlinevideo.entity.ToolsItem;

import java.util.HashMap;
import java.util.List;

public interface ToolsItemDao {

    int insertToolsItem(ToolsItem toolsItem);

    List<ToolsItem> findToolsItemByCondition(HashMap<String, Object> map);
}
