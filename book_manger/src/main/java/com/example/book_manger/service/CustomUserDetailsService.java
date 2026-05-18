package com.example.book_manger.service;

import com.example.book_manger.entity.NguoiDung;
import com.example.book_manger.repository.NguoiDungRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final NguoiDungRepository nguoiDungRepository;

    public CustomUserDetailsService(NguoiDungRepository nguoiDungRepository) {
        this.nguoiDungRepository = nguoiDungRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String tenDangNhap) throws UsernameNotFoundException {
        NguoiDung nguoiDung = nguoiDungRepository.findByTenDangNhap(tenDangNhap)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user: " + tenDangNhap));

        var authorities = nguoiDung.getVaiTroList().stream()
                .map(vt -> new SimpleGrantedAuthority(vt.getTenVaiTro()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                nguoiDung.getTenDangNhap(),
                nguoiDung.getMatKhau(),
                authorities
        );
    }
}
