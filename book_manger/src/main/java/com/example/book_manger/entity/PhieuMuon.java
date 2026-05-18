package com.example.book_manger.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "phieu_muon")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhieuMuon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "nguoi_dung_id")
    private NguoiDung nguoiDung;

    @Column(name = "ngay_muon")
    private LocalDateTime ngayMuon;

    @Column(name = "ngay_hen_tra", nullable = false)
    private LocalDateTime ngayHenTra;

    @Column(name = "trang_thai", length = 50)
    private String trangThai = "ĐANG_MƯỢN";

    @Column(name = "ghi_chu", columnDefinition = "NVARCHAR(MAX)")
    private String ghiChu;

    @OneToMany(mappedBy = "phieuMuon", cascade = CascadeType.ALL)
    private List<ChiTietPhieuMuon> chiTietPhieuMuonList;

    @PrePersist
    protected void onCreate() {
        if (ngayMuon == null) {
            ngayMuon = LocalDateTime.now();
        }
    }
}
