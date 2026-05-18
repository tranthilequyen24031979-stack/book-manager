package com.example.book_manger.service;

import com.example.book_manger.dto.ThongBaoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ThongBaoService {

    List<ThongBaoDTO> getThongBaoByNguoiDungId(Integer nguoiDungId);

    Page<ThongBaoDTO> getThongBaoByNguoiDungIdPaged(Integer nguoiDungId, Pageable pageable);

    Optional<ThongBaoDTO> getThongBaoById(Integer id);

    ThongBaoDTO createThongBao(ThongBaoDTO thongBaoDTO);

    void deleteThongBao(Integer id);

    void markAsRead(Integer id);

    List<ThongBaoDTO> getUnreadThongBaoByNguoiDungId(Integer nguoiDungId);

    int getUnreadCount(Integer nguoiDungId);
}
