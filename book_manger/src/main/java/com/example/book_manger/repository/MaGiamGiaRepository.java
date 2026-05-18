package com.example.book_manger.repository;

import com.example.book_manger.entity.MaGiamGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaGiamGiaRepository extends JpaRepository<MaGiamGia, Integer> {

    Optional<MaGiamGia> findByMaCode(String maCode);
}
