package com.example.book_manger.service;

import com.example.book_manger.dto.ThongBaoDTO;
import com.example.book_manger.entity.NguoiDung;
import com.example.book_manger.entity.ThongBao;
import com.example.book_manger.exception.ResourceNotFoundException;
import com.example.book_manger.repository.NguoiDungRepository;
import com.example.book_manger.repository.ThongBaoRepository;
import com.example.book_manger.service.ThongBaoService;
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
public class ThongBaoServiceImpl implements ThongBaoService {

    private final ThongBaoRepository thongBaoRepository;
    private final NguoiDungRepository nguoiDungRepository;

    public ThongBaoServiceImpl(ThongBaoRepository thongBaoRepository,
                               NguoiDungRepository nguoiDungRepository) {
        this.thongBaoRepository = thongBaoRepository;
        this.nguoiDungRepository = nguoiDungRepository;
    }

    @Override
    public List<ThongBaoDTO> getThongBaoByNguoiDungId(Integer nguoiDungId) {
        return thongBaoRepository.findByNguoiDungId(nguoiDungId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ThongBaoDTO> getThongBaoByNguoiDungIdPaged(Integer nguoiDungId, Pageable pageable) {
        return thongBaoRepository.findByNguoiDungId(nguoiDungId, pageable).map(this::convertToDTO);
    }

    @Override
    public Optional<ThongBaoDTO> getThongBaoById(Integer id) {
        return thongBaoRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public ThongBaoDTO createThongBao(ThongBaoDTO thongBaoDTO) {
        NguoiDung nguoiDung = nguoiDungRepository.findById(thongBaoDTO.getNguoiDungId())
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại"));

        ThongBao thongBao = new ThongBao();
        thongBao.setNguoiDung(nguoiDung);
        thongBao.setTieuDe(thongBaoDTO.getTieuDe());
        thongBao.setNoiDung(thongBaoDTO.getNoiDung());
        thongBao.setNgayTao(LocalDateTime.now());
        thongBao.setDaDoc(false);
        thongBao.setLoaiThongBao(thongBaoDTO.getLoaiThongBao());

        ThongBao savedThongBao = thongBaoRepository.save(thongBao);
        return convertToDTO(savedThongBao);
    }

    @Override
    public void deleteThongBao(Integer id) {
        if (!thongBaoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Thông báo không tồn tại");
        }
        thongBaoRepository.deleteById(id);
    }

    @Override
    public void markAsRead(Integer id) {
        ThongBao thongBao = thongBaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Thông báo không tồn tại"));
        thongBao.setDaDoc(true);
        thongBaoRepository.save(thongBao);
    }

    @Override
    public List<ThongBaoDTO> getUnreadThongBaoByNguoiDungId(Integer nguoiDungId) {
        return thongBaoRepository.findByNguoiDungIdAndDaDocFalse(nguoiDungId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public int getUnreadCount(Integer nguoiDungId) {
        return (int) thongBaoRepository.findByNguoiDungIdAndDaDocFalse(nguoiDungId).size();
    }

    private ThongBaoDTO convertToDTO(ThongBao thongBao) {
        ThongBaoDTO dto = new ThongBaoDTO();
        dto.setId(thongBao.getId());
        dto.setNguoiDungId(thongBao.getNguoiDung().getId());
        dto.setTieuDe(thongBao.getTieuDe());
        dto.setNoiDung(thongBao.getNoiDung());
        dto.setNgayTao(thongBao.getNgayTao());
        dto.setDaDoc(thongBao.getDaDoc());
        dto.setLoaiThongBao(thongBao.getLoaiThongBao());
        return dto;
    }
}
