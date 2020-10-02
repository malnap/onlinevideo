package com.duyi.onlinevideo.service.impl;

import com.duyi.onlinevideo.dao.ToolsTypeDao;
import com.duyi.onlinevideo.entity.ToolsType;
import com.duyi.onlinevideo.service.ToolsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolsTypeServiceImpl implements ToolsTypeService {

    @Autowired
    ToolsTypeDao toolsTypeDao;

    @Override
    public List<ToolsType> getToolsTypeAll() {
        return toolsTypeDao.findToolsTypeAll();
    }
}