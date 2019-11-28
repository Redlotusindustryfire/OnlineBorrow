package com.attjl.online_borrow.repository;

import com.attjl.online_borrow.entity.DeliveryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface DeliveryInfoRepository extends JpaRepository<DeliveryInfo,Integer> {

    List<DeliveryInfo> findByUsername(String username);

    @Transactional
    @Modifying(clearAutomatically = true)   //更新图书馆地址
    @Query("update DeliveryInfo deInfo set deInfo.name=?1,deInfo.phone=?2,deInfo.address=?3 where deInfo.username=?4")
    int updateDeliveryInfo(String name,String phone,String address,String username);
}
