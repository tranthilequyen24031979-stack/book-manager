package com.example.book_manger.service;

import com.example.book_manger.dto.SachDTO;
import com.example.book_manger.entity.DanhMuc;
import com.example.book_manger.entity.Sach;
import com.example.book_manger.exception.ResourceNotFoundException;
import com.example.book_manger.repository.DanhMucRepository;
import com.example.book_manger.repository.SachRepository;
import com.example.book_manger.service.SachService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class SachServiceImpl implements SachService {

    private final SachRepository sachRepository;
    private final DanhMucRepository danhMucRepository;

    public SachServiceImpl(SachRepository sachRepository, DanhMucRepository danhMucRepository) {
        this.sachRepository = sachRepository;
        this.danhMucRepository = danhMucRepository;
    }

    @Override
    public Page<SachDTO> getAllSach(Pageable pageable) {
        return sachRepository.findAll(pageable).map(this::convertToDTO);
    }

    @Override
    public Optional<SachDTO> getSachById(Integer id) {
        return sachRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public SachDTO createSach(SachDTO sachDTO) {
        if (sachDTO.getIsbn() != null && sachRepository.findByIsbn(sachDTO.getIsbn()).isPresent()) {
            throw new IllegalArgumentException("ISBN đã tồn tại");
        }

        Sach sach = new Sach();
        sach.setTieuDe(sachDTO.getTieuDe());
        sach.setTacGia(sachDTO.getTacGia());
        sach.setIsbn(sachDTO.getIsbn());
        sach.setGiaBan(sachDTO.getGiaBan());
        sach.setPhanTramGiamGia(sachDTO.getPhanTramGiamGia() != null ? sachDTO.getPhanTramGiamGia() : 0);
        sach.setSoLuongTon(sachDTO.getSoLuongTon() != null ? sachDTO.getSoLuongTon() : 0);
        sach.setHinhAnh(sachDTO.getHinhAnh());
        sach.setTomTat(sachDTO.getTomTat());

        if (sachDTO.getDanhMucId() != null) {
            DanhMuc danhMuc = danhMucRepository.findById(sachDTO.getDanhMucId())
                    .orElseThrow(() -> new ResourceNotFoundException("Danh mục không tồn tại"));
            sach.setDanhMuc(danhMuc);
        }

        Sach savedSach = sachRepository.save(sach);
        return convertToDTO(savedSach);
    }

    @Override
    public SachDTO updateSach(Integer id, SachDTO sachDTO) {
        Sach sach = sachRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sách không tồn tại"));

        sach.setTieuDe(sachDTO.getTieuDe());
        sach.setTacGia(sachDTO.getTacGia());
        sach.setGiaBan(sachDTO.getGiaBan());
        sach.setPhanTramGiamGia(sachDTO.getPhanTramGiamGia() != null ? sachDTO.getPhanTramGiamGia() : 0);
        sach.setSoLuongTon(sachDTO.getSoLuongTon() != null ? sachDTO.getSoLuongTon() : 0);
        sach.setHinhAnh(sachDTO.getHinhAnh());
        sach.setTomTat(sachDTO.getTomTat());

        if (sachDTO.getDanhMucId() != null) {
            DanhMuc danhMuc = danhMucRepository.findById(sachDTO.getDanhMucId())
                    .orElseThrow(() -> new ResourceNotFoundException("Danh mục không tồn tại"));
            sach.setDanhMuc(danhMuc);
        }

        Sach updatedSach = sachRepository.save(sach);
        return convertToDTO(updatedSach);
    }

    @Override
    public void deleteSach(Integer id) {
        if (!sachRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sách không tồn tại");
        }
        sachRepository.deleteById(id);
    }

    @Override
    public Page<SachDTO> searchSach(String tieuDe, String tacGia, Integer danhMucId, Pageable pageable) {
        return sachRepository.searchSach(tieuDe, tacGia, danhMucId, pageable).map(this::convertToDTO);
    }

    @Override
    public Optional<SachDTO> getSachByIsbn(String isbn) {
        return sachRepository.findByIsbn(isbn).map(this::convertToDTO);
    }

    @Override
    public boolean isIsbnExists(String isbn, Integer excludeId) {
        Optional<Sach> sach = sachRepository.findByIsbn(isbn);
        if (sach.isPresent()) {
            return !sach.get().getId().equals(excludeId);
        }
        return false;
    }

    private SachDTO convertToDTO(Sach sach) {
        SachDTO dto = new SachDTO();
        dto.setId(sach.getId());
        dto.setTieuDe(sach.getTieuDe());
        dto.setTacGia(sach.getTacGia());
        dto.setIsbn(sach.getIsbn());
        dto.setGiaBan(sach.getGiaBan());
        dto.setPhanTramGiamGia(sach.getPhanTramGiamGia());
        dto.setSoLuongTon(sach.getSoLuongTon());
        dto.setHinhAnh(sach.getHinhAnh());
        dto.setTomTat(sach.getTomTat());
        if (sach.getDanhMuc() != null) {
            dto.setDanhMucId(sach.getDanhMuc().getId());
            dto.setDanhMucTen(sach.getDanhMuc().getTenDanhMuc());
        }
        return dto;
    }
}