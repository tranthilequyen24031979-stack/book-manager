package com.example.book_manger.service;

import com.example.book_manger.dto.NguoiDungDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface NguoiDungService {

    Page<NguoiDungDTO> getAllNguoiDung(Pageable pageable);

    Optional<NguoiDungDTO> getNguoiDungById(Integer id);

    NguoiDungDTO createNguoiDung(NguoiDungDTO nguoiDungDTO);

    NguoiDungDTO updateNguoiDung(Integer id, NguoiDungDTO nguoiDungDTO);

    void deleteNguoiDung(Integer id);

    Page<NguoiDungDTO> searchNguoiDung(String hoTen, Pageable pageable);

    Optional<NguoiDungDTO> getNguoiDungByTenDangNhap(String tenDangNhap);

    boolean isUserExists(String tenDangNhap);

    boolean isEmailExists(String email);
}
