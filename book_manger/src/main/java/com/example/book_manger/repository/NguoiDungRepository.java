package com.example.book_manger.repository;

import com.example.book_manger.entity.NguoiDung;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NguoiDungRepository extends JpaRepository<NguoiDung, Integer> {

    Optional<NguoiDung> findByTenDangNhap(String tenDangNhap);

    Optional<NguoiDung> findByEmail(String email);

    Page<NguoiDung> findByHoTenContaining(String hoTen, Pageable pageable);

    Page<NguoiDung> findAll(Pageable pageable);
}