package com.example.book_manger.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "don_hang")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "nguoi_dung_id")
    private NguoiDung nguoiDung;

    @Column(name = "ngay_dat")
    private LocalDateTime ngayDat;

    @Column(name = "trang_thai", length = 50)
    private String trangThai = "CHỜ_XỬ_LÝ";

    @Column(name = "tong_tien")
    private BigDecimal tongTien;

    @Column(name = "dia_chi_giao_hang", length = 500)
    private String diaChiGiaoHang;

    @Column(name = "so_dien_thoai_nhan", length = 15)
    private String soDienThoaiNhan;

    @ManyToOne
    @JoinColumn(name = "ma_giam_gia_id")
    private MaGiamGia maGiamGia;

    @OneToMany(mappedBy = "donHang", cascade = CascadeType.ALL)
    private List<ChiTietDonHang> chiTietDonHangList;

    @PrePersist
    protected void onCreate() {
        if (ngayDat == null) {
            ngayDat = LocalDateTime.now();
        }
    }
}
