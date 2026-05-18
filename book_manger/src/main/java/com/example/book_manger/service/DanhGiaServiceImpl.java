package com.example.book_manger.service;

import com.example.book_manger.dto.DanhGiaDTO;
import com.example.book_manger.entity.DanhGia;
import com.example.book_manger.entity.NguoiDung;
import com.example.book_manger.entity.Sach;
import com.example.book_manger.exception.ResourceNotFoundException;
import com.example.book_manger.repository.DanhGiaRepository;
import com.example.book_manger.repository.NguoiDungRepository;
import com.example.book_manger.repository.SachRepository;
import com.example.book_manger.service.DanhGiaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DanhGiaServiceImpl implements DanhGiaService {

    private final DanhGiaRepository danhGiaRepository;
    private final SachRepository sachRepository;
    private final NguoiDungRepository nguoiDungRepository;

    public DanhGiaServiceImpl(DanhGiaRepository danhGiaRepository,
                              SachRepository sachRepository,
                              NguoiDungRepository nguoiDungRepository) {
        this.danhGiaRepository = danhGiaRepository;
        this.sachRepository = sachRepository;
        this.nguoiDungRepository = nguoiDungRepository;
    }

    @Override
    public List<DanhGiaDTO> getDanhGiaBySachId(Integer sachId) {
        return danhGiaRepository.findBySachId(sachId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<DanhGiaDTO> getDanhGiaBySachIdPaged(Integer sachId, Pageable pageable) {
        return danhGiaRepository.findBySachId(sachId, pageable).map(this::convertToDTO);
    }

    @Override
    public Optional<DanhGiaDTO> getDanhGiaById(Integer id) {
        return danhGiaRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public DanhGiaDTO createDanhGia(DanhGiaDTO danhGiaDTO) {
        Sach sach = sachRepository.findById(danhGiaDTO.getSachId())
                .orElseThrow(() -> new ResourceNotFoundException("Sách không tồn tại"));

        NguoiDung nguoiDung = nguoiDungRepository.findById(danhGiaDTO.getNguoiDungId())
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại"));

        DanhGia danhGia = new DanhGia();
        danhGia.setSach(sach);
        danhGia.setNguoiDung(nguoiDung);
        danhGia.setDiemSo(danhGiaDTO.getDiemSo());
        danhGia.setBinhLuan(danhGiaDTO.getBinhLuan());
        danhGia.setNgayTao(LocalDateTime.now());

        DanhGia savedDanhGia = danhGiaRepository.save(danhGia);
        return convertToDTO(savedDanhGia);
    }

    @Override
    public DanhGiaDTO updateDanhGia(Integer id, DanhGiaDTO danhGiaDTO) {
        DanhGia danhGia = danhGiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Đánh giá không tồn tại"));

        danhGia.setDiemSo(danhGiaDTO.getDiemSo());
        danhGia.setBinhLuan(danhGiaDTO.getBinhLuan());

        DanhGia updatedDanhGia = danhGiaRepository.save(danhGia);
        return convertToDTO(updatedDanhGia);
    }

    @Override
    public void deleteDanhGia(Integer id) {
        if (!danhGiaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Đánh giá không tồn tại");
        }
        danhGiaRepository.deleteById(id);
    }

    @Override
    public double getAverageDiemBySachId(Integer sachId) {
        List<DanhGia> danhGiaList = danhGiaRepository.findBySachId(sachId);
        if (danhGiaList.isEmpty()) {
            return 0.0;
        }
        return danhGiaList.stream()
                .mapToInt(DanhGia::getDiemSo)
                .average()
                .orElse(0.0);
    }

    private DanhGiaDTO convertToDTO(DanhGia danhGia) {
        DanhGiaDTO dto = new DanhGiaDTO();
        dto.setId(danhGia.getId());
        dto.setSachId(danhGia.getSach().getId());
        dto.setSachTieu(danhGia.getSach().getTieuDe());
        dto.setNguoiDungId(danhGia.getNguoiDung().getId());
        dto.setNguoiDungTen(danhGia.getNguoiDung().getHoTen());
        dto.setDiemSo(danhGia.getDiemSo());
        dto.setBinhLuan(danhGia.getBinhLuan());
        dto.setNgayTao(danhGia.getNgayTao());
        return dto;
    }
}
