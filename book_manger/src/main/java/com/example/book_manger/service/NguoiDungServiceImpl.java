package com.example.book_manger.service;

import com.example.book_manger.dto.NguoiDungDTO;
import com.example.book_manger.entity.NguoiDung;
import com.example.book_manger.entity.VaiTro;
import com.example.book_manger.exception.ResourceNotFoundException;
import com.example.book_manger.repository.NguoiDungRepository;
import com.example.book_manger.repository.VaiTroRepository;
import com.example.book_manger.service.NguoiDungService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class NguoiDungServiceImpl implements NguoiDungService {

    private final NguoiDungRepository nguoiDungRepository;
    private final VaiTroRepository vaiTroRepository;
    private final PasswordEncoder passwordEncoder;

    public NguoiDungServiceImpl(NguoiDungRepository nguoiDungRepository,
                                VaiTroRepository vaiTroRepository,
                                PasswordEncoder passwordEncoder) {
        this.nguoiDungRepository = nguoiDungRepository;
        this.vaiTroRepository = vaiTroRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<NguoiDungDTO> getAllNguoiDung(Pageable pageable) {
        return nguoiDungRepository.findAll(pageable).map(this::convertToDTO);
    }

    @Override
    public Optional<NguoiDungDTO> getNguoiDungById(Integer id) {
        return nguoiDungRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public NguoiDungDTO createNguoiDung(NguoiDungDTO nguoiDungDTO) {
        if (isUserExists(nguoiDungDTO.getTenDangNhap())) {
            throw new IllegalArgumentException("Tên đăng nhập đã tồn tại");
        }

        if (isEmailExists(nguoiDungDTO.getEmail())) {
            throw new IllegalArgumentException("Email đã được sử dụng");
        }

        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setTenDangNhap(nguoiDungDTO.getTenDangNhap());
        nguoiDung.setMatKhau(passwordEncoder.encode("123456")); // Mật khẩu mặc định
        nguoiDung.setHoTen(nguoiDungDTO.getHoTen());
        nguoiDung.setEmail(nguoiDungDTO.getEmail());
        nguoiDung.setSoDienThoai(nguoiDungDTO.getSoDienThoai());

        // Thêm role mặc định
        VaiTro userRole = vaiTroRepository.findByTenVaiTro("ROLE_USER")
                .orElseGet(() -> {
                    VaiTro role = new VaiTro();
                    role.setTenVaiTro("ROLE_USER");
                    return vaiTroRepository.save(role);
                });

        nguoiDung.setVaiTroList(List.of(userRole));

        NguoiDung savedNguoiDung = nguoiDungRepository.save(nguoiDung);
        return convertToDTO(savedNguoiDung);
    }

    @Override
    public NguoiDungDTO updateNguoiDung(Integer id, NguoiDungDTO nguoiDungDTO) {
        NguoiDung nguoiDung = nguoiDungRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại"));

        nguoiDung.setHoTen(nguoiDungDTO.getHoTen());
        nguoiDung.setEmail(nguoiDungDTO.getEmail());
        nguoiDung.setSoDienThoai(nguoiDungDTO.getSoDienThoai());

        NguoiDung updatedNguoiDung = nguoiDungRepository.save(nguoiDung);
        return convertToDTO(updatedNguoiDung);
    }

    @Override
    public void deleteNguoiDung(Integer id) {
        if (!nguoiDungRepository.existsById(id)) {
            throw new ResourceNotFoundException("Người dùng không tồn tại");
        }
        nguoiDungRepository.deleteById(id);
    }

    @Override
    public Page<NguoiDungDTO> searchNguoiDung(String hoTen, Pageable pageable) {
        return nguoiDungRepository.findByHoTenContaining(hoTen, pageable).map(this::convertToDTO);
    }

    @Override
    public Optional<NguoiDungDTO> getNguoiDungByTenDangNhap(String tenDangNhap) {
        return nguoiDungRepository.findByTenDangNhap(tenDangNhap).map(this::convertToDTO);
    }

    @Override
    public boolean isUserExists(String tenDangNhap) {
        return nguoiDungRepository.findByTenDangNhap(tenDangNhap).isPresent();
    }

    @Override
    public boolean isEmailExists(String email) {
        return nguoiDungRepository.findByEmail(email).isPresent();
    }

    private NguoiDungDTO convertToDTO(NguoiDung nguoiDung) {
        NguoiDungDTO dto = new NguoiDungDTO();
        dto.setId(nguoiDung.getId());
        dto.setTenDangNhap(nguoiDung.getTenDangNhap());
        dto.setHoTen(nguoiDung.getHoTen());
        dto.setEmail(nguoiDung.getEmail());
        dto.setSoDienThoai(nguoiDung.getSoDienThoai());
        dto.setVaiTroList(nguoiDung.getVaiTroList().stream()
                .map(VaiTro::getTenVaiTro)
                .collect(Collectors.toList()));
        return dto;
    }
}

