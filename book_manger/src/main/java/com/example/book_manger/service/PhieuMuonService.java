package com.example.book_manger.service;

import com.example.book_manger.dto.ChiTietPhieuMuonDTO;
import com.example.book_manger.dto.PhieuMuonDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PhieuMuonService {

    Page<PhieuMuonDTO> getAllPhieuMuon(Pageable pageable);

    Optional<PhieuMuonDTO> getPhieuMuonById(Integer id);

    PhieuMuonDTO createPhieuMuon(PhieuMuonDTO phieuMuonDTO);

    PhieuMuonDTO updatePhieuMuon(Integer id, PhieuMuonDTO phieuMuonDTO);

    void deletePhieuMuon(Integer id);

    Page<PhieuMuonDTO> searchPhieuMuon(String trangThai, Integer nguoiDungId, LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);

    PhieuMuonDTO traPhieuMuon(Integer phieuMuonId, Integer sachId);

    PhieuMuonDTO baoMatSach(Integer phieuMuonId, Integer sachId);

    void addSachToPhieu(Integer phieuMuonId, Integer sachId);

    void removeSachFromPhieu(Integer phieuMuonId, Integer sachId);
}