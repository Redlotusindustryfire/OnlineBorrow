package com.attjl.online_borrow.repository;

import com.attjl.online_borrow.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BorrowRepository extends JpaRepository<Borrow,Integer> {

    List<Borrow> findByUsername(String username);

    @Query("select b from Borrow b where b.username=?1 and b.type=?2")
    List<Borrow> findDelivering(String username,Integer type);  //找到正在配送的图书

    List<Borrow> findByType(Integer type);

    List<Borrow> findByLibraryType(Integer libraryType);

    @Query("select b from Borrow b where b.type=?1 and b.libraryType=?2")
    List<Borrow> findReturnApplication(Integer type,Integer libraryType);  //找到需要处理的还书申请

}
