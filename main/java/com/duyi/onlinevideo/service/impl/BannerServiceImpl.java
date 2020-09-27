package com.duyi.onlinevideo.service.impl;

import com.duyi.onlinevideo.dao.BannerDao;
import com.duyi.onlinevideo.entity.Banner;
import com.duyi.onlinevideo.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    private  BannerDao bannerDao;

    @Autowired
    public BannerServiceImpl(BannerDao bannerDao) {
        this.bannerDao = bannerDao;
    }

    @Override
    public List<Banner> getIndexBanner() {
        return bannerDao.findBannerAll();
    }
}
