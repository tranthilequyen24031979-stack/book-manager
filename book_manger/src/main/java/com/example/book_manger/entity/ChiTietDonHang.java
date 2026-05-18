package com.example.book_manger.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "chi_tiet_don_hang")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietDonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "don_hang_id")
    private DonHang donHang;

    @ManyToOne
    @JoinColumn(name = "sach_id")
    private Sach sach;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "gia_don_vi")
    private BigDecimal giaDonVi;

    @Column(name = "so_tien_giam_gia")
    private BigDecimal soTienGiamGia = BigDecimal.ZERO;
}
