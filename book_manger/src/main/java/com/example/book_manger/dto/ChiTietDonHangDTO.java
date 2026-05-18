package com.example.book_manger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietDonHangDTO {

    private Integer id;

    private Integer donHangId;

    private Integer sachId;
    private String tenSach;

    private Integer soLuong;

    private BigDecimal giaDonVi;

    private BigDecimal soTienGiamGia;

    private BigDecimal thanhTien;
}
