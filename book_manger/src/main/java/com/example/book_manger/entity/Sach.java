package com.example.book_manger.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "sach")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tieu_de", length = 255, nullable = false)
    private String tieuDe;

    @Column(name = "tac_gia", length = 150)
    private String tacGia;

    @Column(name = "isbn", length = 20, unique = true)
    private String isbn;

    @Column(name = "gia_ban", nullable = false)
    private BigDecimal giaBan;

    @Column(name = "phan_tram_giam_gia")
    private Integer phanTramGiamGia = 0;

    @Column(name = "so_luong_ton")
    private Integer soLuongTon = 0;

    @Column(name = "hinh_anh", length = 500)
    private String hinhAnh;

    @Column(name = "tom_tat", columnDefinition = "NVARCHAR(MAX)")
    private String tomTat;

    @ManyToOne
    @JoinColumn(name = "danh_muc_id")
    private DanhMuc danhMuc;

    @OneToMany(mappedBy = "sach", cascade = CascadeType.ALL)
    private List<DanhGia> danhGiaList;

    @OneToMany(mappedBy = "sach", cascade = CascadeType.ALL)
    private List<ChiTietPhieuMuon> chiTietPhieuMuonList;

    @OneToMany(mappedBy = "sach", cascade = CascadeType.ALL)
    private List<GioHang> gioHangList;

    @OneToMany(mappedBy = "sach", cascade = CascadeType.ALL)
    private List<ChiTietDonHang> chiTietDonHangList;

}
