package com.example.book_manger.repository;

import com.example.book_manger.entity.DanhGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DanhGiaRepository extends JpaRepository<DanhGia, Integer> {

    List<DanhGia> findBySachId(Integer sachId);

    Page<DanhGia> findBySachId(Integer sachId, Pageable pageable);
}
