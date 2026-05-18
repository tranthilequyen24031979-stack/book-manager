package com.example.book_manger.repository;

import com.example.book_manger.entity.PhieuMuon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PhieuMuonRepository extends JpaRepository<PhieuMuon, Integer> {

    Page<PhieuMuon> findByTrangThai(String trangThai, Pageable pageable);

    Page<PhieuMuon> findByNguoiDungId(Integer nguoiDungId, Pageable pageable);

    Page<PhieuMuon> findByNguoiDung_HoTenContaining(String hoTen, Pageable pageable);

    @Query("SELECT p FROM PhieuMuon p WHERE " +
            "(:trangThai IS NULL OR p.trangThai = :trangThai) AND " +
            "(:nguoiDungId IS NULL OR p.nguoiDung.id = :nguoiDungId) AND " +
            "(p.ngayMuon >= :fromDate OR :fromDate IS NULL) AND " +
            "(p.ngayMuon <= :toDate OR :toDate IS NULL)")
    Page<PhieuMuon> searchPhieuMuon(
            @Param("trangThai") String trangThai,
            @Param("nguoiDungId") Integer nguoiDungId,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            Pageable pageable
    );

    List<PhieuMuon> findByTrangThaiAndNgayHenTraBefore(String trangThai, LocalDateTime ngayHenTra);

    Page<PhieuMuon> findAll(Pageable pageable);
}