package com.example.book_manger.repository;
import com.example.book_manger.entity.Sach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface SachRepository extends JpaRepository<Sach, Integer> {

    Optional<Sach> findByIsbn(String isbn);

    Page<Sach> findByTieuDeContaining(String tieuDe, Pageable pageable);

    Page<Sach> findByTacGiaContaining(String tacGia, Pageable pageable);

    Page<Sach> findByDanhMucId(Integer danhMucId, Pageable pageable);

    Page<Sach> findByGiaBanBetween(BigDecimal minGia, BigDecimal maxGia, Pageable pageable);

    @Query("SELECT s FROM Sach s WHERE " +
            "(:tieuDe IS NULL OR s.tieuDe LIKE %:tieuDe%) AND " +
            "(:tacGia IS NULL OR s.tacGia LIKE %:tacGia%) AND " +
            "(:danhMucId IS NULL OR s.danhMuc.id = :danhMucId)")
    Page<Sach> searchSach(
            @Param("tieuDe") String tieuDe,
            @Param("tacGia") String tacGia,
            @Param("danhMucId") Integer danhMucId,
            Pageable pageable
    );

    Page<Sach> findAll(Pageable pageable);
}
