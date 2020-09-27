package com.duyi.onlinevideo.dao;

import com.duyi.onlinevideo.entity.Banner;

import java.util.List;

public interface BannerDao {

    int insertBanner(Banner banner);

    List<Banner> findBannerAll();
}
