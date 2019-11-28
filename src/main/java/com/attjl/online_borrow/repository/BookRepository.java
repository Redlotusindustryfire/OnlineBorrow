package com.attjl.online_borrow.repository;

import com.attjl.online_borrow.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer>,JpaSpecificationExecutor<Book> {
    @Query("select b from Book b where b.title like %?1% or b.author like %?2% or b.category like %?3% or b.username=?4")
    List<Book> findByStm(String title,String author,String category,String username); //搜索查询

    @Query("select b from Book b where b.title like %?1% or b.author like %?2% or b.category like %?3% or b.username=?4 order by b.bw_amount DESC ")
    List<Book> findByStmSort(String title,String author,String category,String username);//按照搜索条件显示页面后，按照借阅量排序

    @Query("select b from Book b where b.title like %?1% or b.author like %?2% or b.category like %?3% or b.username=?4 order by (b.bw_amount/b.price) DESC ")
    List<Book> findByStmIntegrate(String title,String author,String category,String username);//按照搜索条件显示页面后，按照综合排序

    @Query("select b from Book b where b.title like %?1% or b.author like %?2% or b.category like %?3% or b.username=?4 order by b.price DESC ")
    List<Book> findByStmPriceDesc(String title,String author,String category,String username);//按照搜索条件显示页面后，按照价格降序排序

    @Query("select b from Book b where b.title like %?1% or b.author like %?2% or b.category like %?3% or b.username=?4 order by b.price Asc ")
    List<Book> findByStmPriceAsc(String title,String author,String category,String username);//按照搜索条件显示页面后，按照价格升序排序

    @Query("select b from Book b order by b.bw_amount desc ")
    List<Book> showPopular();

    List<Book> findByCategory(String category);

    List<Book> findByUsername(String username);

    @Query("select b from Book b where b.username=?1 order by b.bw_amount DESC ")
    List<Book> findLibraryBooks(String username);   //按借阅量降序显示图书馆图书

    @Query("select b from Book b where b.username=?1 and (b.category like %?2% or b.title like %?3% or b.author like %?4%)")
    List<Book> findLibraryBooksByStm(String username,String category,String title,String author);//按搜索条件查找图书馆图书


    Book findById(Integer id);

    List<Book> findByNumbering(String numbering);

    @Query("select b from Book b where b.numbering=?1")
    Book findNumbering(String numbering);

    List<Book> findByAuthorOrCategory(String author,String category);

    @Query("select b from Book b where b.category=?1 order by b.bw_amount DESC " )
    List<Book> findBySort(String category);   //按照借阅量进行降序排列

    @Query("select b from  Book b where b.category=?1 order by (b.bw_amount/b.price) DESC ")
    List<Book> findIntegrate(String category); //利用借阅量除以单价来作为综合能力的衡量标准

    @Query("select b from  Book b where b.category=?1 order by b.price DESC ")
    List<Book> findPriceDesc(String category); //按照价格降序进行排序

    @Query("select b from  Book b where b.category=?1 order by b.price ASC ")
    List<Book> findPriceAsc(String category); //按照价格升序进行排序

    @Transactional
    @Modifying(clearAutomatically = true)  //保存修改后的图书信息
    @Query("update Book b set b.image=?1,b.numbering=?2,b.title=?3,b.category=?4,b.author=?5,b.stock=?6,b.max_day=?7,b.price=?8 where b.id=?9")
    int bookModifySave(String image,String numbering,String title,String category,String author,Integer stock,Integer max_day,Double price,Integer id);
}
