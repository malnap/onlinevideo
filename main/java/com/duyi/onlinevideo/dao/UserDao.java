package com.duyi.onlinevideo.dao;

import com.duyi.onlinevideo.entity.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public interface UserDao {

    int insertUser(User user);

    User findUserByCondition(HashMap<String, Object> map);
}
