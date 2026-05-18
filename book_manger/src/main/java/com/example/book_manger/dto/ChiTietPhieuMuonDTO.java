package com.example.book_manger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietPhieuMuonDTO {

    private Integer id;
    private Integer phieuMuonId;
    private Integer sachId;
    private String sachTieu;
    private LocalDateTime ngayTraThucTe;
    private BigDecimal tienPhat;
    private String tinhTrangSach;
}
