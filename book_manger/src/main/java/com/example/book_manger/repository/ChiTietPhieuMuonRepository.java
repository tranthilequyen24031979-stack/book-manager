package com.example.book_manger.repository;

import com.example.book_manger.entity.ChiTietPhieuMuon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChiTietPhieuMuonRepository extends JpaRepository<ChiTietPhieuMuon, Integer> {

    List<ChiTietPhieuMuon> findByPhieuMuonId(Integer phieuMuonId);

    List<ChiTietPhieuMuon> findBySachId(Integer sachId);
}
