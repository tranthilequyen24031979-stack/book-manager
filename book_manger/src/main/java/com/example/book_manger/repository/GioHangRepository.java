package com.example.book_manger.repository;

import com.example.book_manger.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang, Integer> {
    List<GioHang> findByNguoiDungId(Integer nguoiDungId);
}
