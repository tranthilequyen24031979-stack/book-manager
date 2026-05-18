package com.example.book_manger.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ma_giam_gia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaGiamGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_code", length = 20, unique = true, nullable = false)
    private String maCode;

    @Column(name = "phan_tram_giam")
    private Integer phanTramGiam;

    @Column(name = "mo_ta", length = 255)
    private String moTa;

    @Column(name = "ngay_bat_dau")
    private LocalDateTime ngayBatDau;

    @Column(name = "ngay_ket_thuc")
    private LocalDateTime ngayKetThuc;

    @Column(name = "so_luong_su_dung")
    private Integer soLuongSuDung = 0;

    @Column(name = "trang_thai")
    private Boolean trangThai = true;

    @OneToMany(mappedBy = "maGiamGia")
    private List<DonHang> donHangList;
}
