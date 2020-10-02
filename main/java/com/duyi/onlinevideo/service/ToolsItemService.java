package com.duyi.onlinevideo.service;

import com.duyi.onlinevideo.entity.ToolsItem;
import com.github.pagehelper.PageInfo;

public interface ToolsItemService {

    PageInfo<ToolsItem> getToolsItem(int typeId);

    PageInfo<ToolsItem> getToolsItemAll();

}
