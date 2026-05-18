package com.example.book_manger.repository;

import com.example.book_manger.entity.DanhMuc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc, Integer> {

    Optional<DanhMuc> findByTenDanhMuc(String tenDanhMuc);

    Page<DanhMuc> findByTenDanhMucContaining(String tenDanhMuc, Pageable pageable);
}
