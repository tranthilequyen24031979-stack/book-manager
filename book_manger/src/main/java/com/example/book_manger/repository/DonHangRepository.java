package com.example.book_manger.repository;

import com.example.book_manger.entity.DonHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonHangRepository extends JpaRepository<DonHang, Integer> {

    Page<DonHang> findByTrangThai(String trangThai, Pageable pageable);

    Page<DonHang> findByNguoiDungId(Integer nguoiDungId, Pageable pageable);

    Page<DonHang> findAll(Pageable pageable);
}