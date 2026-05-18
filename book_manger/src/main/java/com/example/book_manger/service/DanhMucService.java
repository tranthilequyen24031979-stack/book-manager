package com.example.book_manger.service;

import com.example.book_manger.dto.DanhMucDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DanhMucService {

    List<DanhMucDTO> getAllDanhMuc();

    Page<DanhMucDTO> getDanhMucPaged(Pageable pageable);

    Optional<DanhMucDTO> getDanhMucById(Integer id);

    DanhMucDTO createDanhMuc(DanhMucDTO danhMucDTO);

    DanhMucDTO updateDanhMuc(Integer id, DanhMucDTO danhMucDTO);

    void deleteDanhMuc(Integer id);

    Page<DanhMucDTO> searchDanhMuc(String tenDanhMuc, Pageable pageable);
}