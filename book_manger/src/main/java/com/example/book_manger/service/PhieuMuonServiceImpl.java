package com.example.book_manger.service;

import com.example.book_manger.dto.ChiTietPhieuMuonDTO;
import com.example.book_manger.dto.PhieuMuonDTO;
import com.example.book_manger.entity.ChiTietPhieuMuon;
import com.example.book_manger.entity.NguoiDung;
import com.example.book_manger.entity.PhieuMuon;
import com.example.book_manger.entity.Sach;
import com.example.book_manger.exception.ResourceNotFoundException;
import com.example.book_manger.repository.ChiTietPhieuMuonRepository;
import com.example.book_manger.repository.NguoiDungRepository;
import com.example.book_manger.repository.PhieuMuonRepository;
import com.example.book_manger.repository.SachRepository;
import com.example.book_manger.service.PhieuMuonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PhieuMuonServiceImpl implements PhieuMuonService {

    private final PhieuMuonRepository phieuMuonRepository;
    private final ChiTietPhieuMuonRepository chiTietPhieuMuonRepository;
    private final NguoiDungRepository nguoiDungRepository;
    private final SachRepository sachRepository;

    public PhieuMuonServiceImpl(PhieuMuonRepository phieuMuonRepository,
                                ChiTietPhieuMuonRepository chiTietPhieuMuonRepository,
                                NguoiDungRepository nguoiDungRepository,
                                SachRepository sachRepository) {
        this.phieuMuonRepository = phieuMuonRepository;
        this.chiTietPhieuMuonRepository = chiTietPhieuMuonRepository;
        this.nguoiDungRepository = nguoiDungRepository;
        this.sachRepository = sachRepository;
    }

    @Override
    public Page<PhieuMuonDTO> getAllPhieuMuon(Pageable pageable) {
        return phieuMuonRepository.findAll(pageable).map(this::convertToDTO);
    }

    @Override
    public Optional<PhieuMuonDTO> getPhieuMuonById(Integer id) {
        return phieuMuonRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public PhieuMuonDTO createPhieuMuon(PhieuMuonDTO phieuMuonDTO) {
        NguoiDung nguoiDung = nguoiDungRepository.findById(phieuMuonDTO.getNguoiDungId())
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại"));

        PhieuMuon phieuMuon = new PhieuMuon();
        phieuMuon.setNguoiDung(nguoiDung);
        phieuMuon.setNgayMuon(LocalDateTime.now());
        phieuMuon.setNgayHenTra(phieuMuonDTO.getNgayHenTra());
        phieuMuon.setTrangThai("ĐANG_MƯỢN");
        phieuMuon.setGhiChu(phieuMuonDTO.getGhiChu());

        PhieuMuon savedPhieu = phieuMuonRepository.save(phieuMuon);

        // Thêm chi tiết phiếu mượn
        if (phieuMuonDTO.getChiTietList() != null) {
            for (ChiTietPhieuMuonDTO chiTiet : phieuMuonDTO.getChiTietList()) {
                addSachToPhieu(savedPhieu.getId(), chiTiet.getSachId());
            }
        }

        return convertToDTO(savedPhieu);
    }

    @Override
    public PhieuMuonDTO updatePhieuMuon(Integer id, PhieuMuonDTO phieuMuonDTO) {
        PhieuMuon phieuMuon = phieuMuonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Phiếu mượn không tồn tại"));

        phieuMuon.setNgayHenTra(phieuMuonDTO.getNgayHenTra());
        phieuMuon.setGhiChu(phieuMuonDTO.getGhiChu());

        PhieuMuon updatedPhieu = phieuMuonRepository.save(phieuMuon);
        return convertToDTO(updatedPhieu);
    }

    @Override
    public void deletePhieuMuon(Integer id) {
        if (!phieuMuonRepository.existsById(id)) {
            throw new ResourceNotFoundException("Phiếu mượn không tồn tại");
        }
        phieuMuonRepository.deleteById(id);
    }

    @Override
    public Page<PhieuMuonDTO> searchPhieuMuon(String trangThai, Integer nguoiDungId, LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable) {
        return phieuMuonRepository.searchPhieuMuon(trangThai, nguoiDungId, fromDate, toDate, pageable)
                .map(this::convertToDTO);
    }

    @Override
    public PhieuMuonDTO traPhieuMuon(Integer phieuMuonId, Integer sachId) {
        PhieuMuon phieuMuon = phieuMuonRepository.findById(phieuMuonId)
                .orElseThrow(() -> new ResourceNotFoundException("Phiếu mượn không tồn tại"));

        Sach sach = sachRepository.findById(sachId)
                .orElseThrow(() -> new ResourceNotFoundException("Sách không tồn tại"));

        ChiTietPhieuMuon chiTiet = chiTietPhieuMuonRepository.findByPhieuMuonId(phieuMuonId)
                .stream()
                .filter(ct -> ct.getSach().getId().equals(sachId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Chi tiết phiếu mượn không tồn tại"));

        LocalDateTime ngayTraThucTe = LocalDateTime.now();
        chiTiet.setNgayTraThucTe(ngayTraThucTe);

        // Tính phạt nếu quá hạn
        if (ngayTraThucTe.isAfter(phieuMuon.getNgayHenTra())) {
            long daysLate = ChronoUnit.DAYS.between(phieuMuon.getNgayHenTra(), ngayTraThucTe);
            BigDecimal penalty = BigDecimal.valueOf(10000).multiply(BigDecimal.valueOf(daysLate));
            chiTiet.setTienPhat(penalty);
        }

        chiTietPhieuMuonRepository.save(chiTiet);

        // Cập nhật số lượng tồn
        sach.setSoLuongTon(sach.getSoLuongTon() + 1);
        sachRepository.save(sach);

        // Kiểm tra nếu tất cả sách đều trả thì cập nhật trạng thái phiếu
        List<ChiTietPhieuMuon> allChiTiet = chiTietPhieuMuonRepository.findByPhieuMuonId(phieuMuonId);
        boolean allReturned = allChiTiet.stream().allMatch(ct -> ct.getNgayTraThucTe() != null);
        if (allReturned) {
            phieuMuon.setTrangThai("ĐÃ_TRẢ");
            phieuMuonRepository.save(phieuMuon);
        }

        return convertToDTO(phieuMuon);
    }

    @Override
    public PhieuMuonDTO baoMatSach(Integer phieuMuonId, Integer sachId) {
        PhieuMuon phieuMuon = phieuMuonRepository.findById(phieuMuonId)
                .orElseThrow(() -> new ResourceNotFoundException("Phiếu mượn không tồn tại"));

        Sach sach = sachRepository.findById(sachId)
                .orElseThrow(() -> new ResourceNotFoundException("Sách không tồn tại"));

        ChiTietPhieuMuon chiTiet = chiTietPhieuMuonRepository.findByPhieuMuonId(phieuMuonId)
                .stream()
                .filter(ct -> ct.getSach().getId().equals(sachId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Chi tiết phiếu mượn không tồn tại"));

        chiTiet.setTinhTrangSach("MẤT");
        chiTiet.setTienPhat(sach.getGiaBan()); // Phạt = giá sách
        chiTietPhieuMuonRepository.save(chiTiet);

        phieuMuon.setTrangThai("MẤT_SÁCH");
        return convertToDTO(phieuMuonRepository.save(phieuMuon));
    }

    @Override
    public void addSachToPhieu(Integer phieuMuonId, Integer sachId) {
        PhieuMuon phieuMuon = phieuMuonRepository.findById(phieuMuonId)
                .orElseThrow(() -> new ResourceNotFoundException("Phiếu mượn không tồn tại"));

        Sach sach = sachRepository.findById(sachId)
                .orElseThrow(() -> new ResourceNotFoundException("Sách không tồn tại"));

        ChiTietPhieuMuon chiTiet = new ChiTietPhieuMuon();
        chiTiet.setPhieuMuon(phieuMuon);
        chiTiet.setSach(sach);
        chiTiet.setTinhTrangSach("Tốt");

        chiTietPhieuMuonRepository.save(chiTiet);

        // Giảm số lượng tồn
        sach.setSoLuongTon(sach.getSoLuongTon() - 1);
        sachRepository.save(sach);
    }

    @Override
    public void removeSachFromPhieu(Integer phieuMuonId, Integer sachId) {
        List<ChiTietPhieuMuon> chiTietList = chiTietPhieuMuonRepository.findByPhieuMuonId(phieuMuonId);
        ChiTietPhieuMuon chiTiet = chiTietList.stream()
                .filter(ct -> ct.getSach().getId().equals(sachId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Chi tiết phiếu mượn không tồn tại"));

        chiTietPhieuMuonRepository.deleteById(chiTiet.getId());

        // Tăng số lượng tồn
        Sach sach = sachRepository.findById(sachId)
                .orElseThrow(() -> new ResourceNotFoundException("Sách không tồn tại"));
        sach.setSoLuongTon(sach.getSoLuongTon() + 1);
        sachRepository.save(sach);
    }

    private PhieuMuonDTO convertToDTO(PhieuMuon phieuMuon) {
        PhieuMuonDTO dto = new PhieuMuonDTO();
        dto.setId(phieuMuon.getId());
        dto.setNguoiDungId(phieuMuon.getNguoiDung().getId());
        dto.setNguoiDungTen(phieuMuon.getNguoiDung().getHoTen());
        dto.setNgayMuon(phieuMuon.getNgayMuon());
        dto.setNgayHenTra(phieuMuon.getNgayHenTra());
        dto.setTrangThai(phieuMuon.getTrangThai());
        dto.setGhiChu(phieuMuon.getGhiChu());

        List<ChiTietPhieuMuonDTO> chiTietDTOList = phieuMuon.getChiTietPhieuMuonList().stream()
                .map(ct -> {
                    ChiTietPhieuMuonDTO ctDTO = new ChiTietPhieuMuonDTO();
                    ctDTO.setId(ct.getId());
                    ctDTO.setPhieuMuonId(ct.getPhieuMuon().getId());
                    ctDTO.setSachId(ct.getSach().getId());
                    ctDTO.setSachTieu(ct.getSach().getTieuDe());
                    ctDTO.setNgayTraThucTe(ct.getNgayTraThucTe());
                    ctDTO.setTienPhat(ct.getTienPhat());
                    ctDTO.setTinhTrangSach(ct.getTinhTrangSach());
                    return ctDTO;
                })
                .collect(Collectors.toList());

        dto.setChiTietList(chiTietDTOList);
        return dto;
    }
}
