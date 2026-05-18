package com.example.book_manger.service;
import com.example.book_manger.dto.DanhMucDTO;
import com.example.book_manger.entity.DanhMuc;
import com.example.book_manger.exception.ResourceNotFoundException;
import com.example.book_manger.repository.DanhMucRepository;
import com.example.book_manger.service.DanhMucService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DanhMucServiceImpl implements DanhMucService {

    private final DanhMucRepository danhMucRepository;

    public DanhMucServiceImpl(DanhMucRepository danhMucRepository) {
        this.danhMucRepository = danhMucRepository;
    }

    @Override
    public List<DanhMucDTO> getAllDanhMuc() {
        return danhMucRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<DanhMucDTO> getDanhMucPaged(Pageable pageable) {
        return danhMucRepository.findAll(pageable).map(this::convertToDTO);
    }

    @Override
    public Optional<DanhMucDTO> getDanhMucById(Integer id) {
        return danhMucRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public DanhMucDTO createDanhMuc(DanhMucDTO danhMucDTO) {
        DanhMuc danhMuc = new DanhMuc();
        danhMuc.setTenDanhMuc(danhMucDTO.getTenDanhMuc());
        danhMuc.setMoTa(danhMucDTO.getMoTa());

        DanhMuc savedDanhMuc = danhMucRepository.save(danhMuc);
        return convertToDTO(savedDanhMuc);
    }

    @Override
    public DanhMucDTO updateDanhMuc(Integer id, DanhMucDTO danhMucDTO) {
        DanhMuc danhMuc = danhMucRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Danh mục không tồn tại"));

        danhMuc.setTenDanhMuc(danhMucDTO.getTenDanhMuc());
        danhMuc.setMoTa(danhMucDTO.getMoTa());

        DanhMuc updatedDanhMuc = danhMucRepository.save(danhMuc);
        return convertToDTO(updatedDanhMuc);
    }

    @Override
    public void deleteDanhMuc(Integer id) {
        if (!danhMucRepository.existsById(id)) {
            throw new ResourceNotFoundException("Danh mục không tồn tại");
        }
        danhMucRepository.deleteById(id);
    }

    @Override
    public Page<DanhMucDTO> searchDanhMuc(String tenDanhMuc, Pageable pageable) {
        return danhMucRepository.findByTenDanhMucContaining(tenDanhMuc, pageable)
                .map(this::convertToDTO);
    }

    private DanhMucDTO convertToDTO(DanhMuc danhMuc) {
        DanhMucDTO dto = new DanhMucDTO();
        dto.setId(danhMuc.getId());
        dto.setTenDanhMuc(danhMuc.getTenDanhMuc());
        dto.setMoTa(danhMuc.getMoTa());
        return dto;
    }
}
