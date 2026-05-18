package com.example.book_manger.service;

import com.example.book_manger.dto.DanhGiaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DanhGiaService {

    List<DanhGiaDTO> getDanhGiaBySachId(Integer sachId);

    Page<DanhGiaDTO> getDanhGiaBySachIdPaged(Integer sachId, Pageable pageable);

    Optional<DanhGiaDTO> getDanhGiaById(Integer id);

    DanhGiaDTO createDanhGia(DanhGiaDTO danhGiaDTO);

    DanhGiaDTO updateDanhGia(Integer id, DanhGiaDTO danhGiaDTO);

    void deleteDanhGia(Integer id);

    double getAverageDiemBySachId(Integer sachId);
}