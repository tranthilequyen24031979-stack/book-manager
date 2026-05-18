package com.example.book_manger.service;

import com.example.book_manger.exception.ResourceNotFoundException;
import com.example.book_manger.repository.DonHangRepository;
import com.example.book_manger.service.DonHangService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class DonHangServiceImpl implements DonHangService {

    private final DonHangRepository donHangRepository;

    public DonHangServiceImpl(DonHangRepository donHangRepository) {
        this.donHangRepository = donHangRepository;
    }

    @Override
    public Page<Object> getAllDonHang(Pageable pageable) {
        return donHangRepository.findAll(pageable).map(dh -> (Object) dh);
    }

    @Override
    public Optional<Object> getDonHangById(Integer id) {
        return donHangRepository.findById(id).map(dh -> (Object) dh);
    }

    @Override
    public Page<Object> getDonHangByTrangThai(String trangThai, Pageable pageable) {
        return donHangRepository.findByTrangThai(trangThai, pageable).map(dh -> (Object) dh);
    }
}
