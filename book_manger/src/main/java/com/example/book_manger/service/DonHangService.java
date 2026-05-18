package com.example.book_manger.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DonHangService {

    Page<Object> getAllDonHang(Pageable pageable);

    Optional<Object> getDonHangById(Integer id);

    Page<Object> getDonHangByTrangThai(String trangThai, Pageable pageable);
}
