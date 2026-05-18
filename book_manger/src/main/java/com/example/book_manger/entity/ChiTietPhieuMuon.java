package com.example.book_manger.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "chi_tiet_phieu_muon")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietPhieuMuon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "phieu_muon_id")
    private PhieuMuon phieuMuon;

    @ManyToOne
    @JoinColumn(name = "sach_id")
    private Sach sach;

    @Column(name = "ngay_tra_thuc_te")
    private LocalDateTime ngayTraThucTe;

    @Column(name = "tien_phat")
    private BigDecimal tienPhat = BigDecimal.ZERO;

    @Column(name = "tinh_trang_sach", length = 255)
    private String tinhTrangSach;
}
