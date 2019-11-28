package com.attjl.online_borrow.repository;

import com.attjl.online_borrow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer>,JpaSpecificationExecutor<User>{

    List<User> findByUsername(String username);

    @Transactional
    @Modifying(clearAutomatically = true)      //修改密码
    @Query("update User user set user.password=?1 where user.id=?2")
    int updateUserPw(String pw,Integer id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update User user set user.nickname=?1,user.sex=?2 where user.id=?3")
    int updateUserInfo(String nickname,String sex,Integer id);

}
