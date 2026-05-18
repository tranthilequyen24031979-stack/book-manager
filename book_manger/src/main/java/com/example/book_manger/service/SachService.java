package com.example.book_manger.service;

import com.example.book_manger.dto.SachDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SachService {

    Page<SachDTO> getAllSach(Pageable pageable);

    Optional<SachDTO> getSachById(Integer id);

    SachDTO createSach(SachDTO sachDTO);

    SachDTO updateSach(Integer id, SachDTO sachDTO);

    void deleteSach(Integer id);

    Page<SachDTO> searchSach(String tieuDe, String tacGia, Integer danhMucId, Pageable pageable);

    Optional<SachDTO> getSachByIsbn(String isbn);

    boolean isIsbnExists(String isbn, Integer excludeId);
}
