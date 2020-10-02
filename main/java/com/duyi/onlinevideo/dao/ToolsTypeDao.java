package com.duyi.onlinevideo.dao;

import com.duyi.onlinevideo.entity.ToolsType;

import java.util.List;

public interface ToolsTypeDao {
    int insertToolsType(ToolsType toolsType);

    List<ToolsType> findToolsTypeAll();
}
