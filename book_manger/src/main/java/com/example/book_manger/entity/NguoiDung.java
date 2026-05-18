package com.example.book_manger.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "nguoi_dung")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NguoiDung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ten_dang_nhap", length = 50, unique = true, nullable = false)
    private String tenDangNhap;

    @Column(name = "mat_khau", length = 500, nullable = false)
    private String matKhau;

    @Column(name = "ho_ten", length = 100)
    private String hoTen;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "so_dien_thoai", length = 15)
    private String soDienThoai;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "quyen_nguoi_dung",
            joinColumns = @JoinColumn(name = "nguoi_dung_id"),
            inverseJoinColumns = @JoinColumn(name = "vai_tro_id")
    )
    private List<VaiTro> vaiTroList;

    @OneToMany(mappedBy = "nguoiDung", cascade = CascadeType.ALL)
    private List<DonHang> donHangList;

    @OneToMany(mappedBy = "nguoiDung", cascade = CascadeType.ALL)
    private List<PhieuMuon> phieuMuonList;

    @OneToMany(mappedBy = "nguoiDung", cascade = CascadeType.ALL)
    private List<DanhGia> danhGiaList;

    @OneToMany(mappedBy = "nguoiDung", cascade = CascadeType.ALL)
    private List<GioHang> gioHangList;

    @OneToMany(mappedBy = "nguoiDung", cascade = CascadeType.ALL)
    private List<ThongBao> thongBaoList;
}
