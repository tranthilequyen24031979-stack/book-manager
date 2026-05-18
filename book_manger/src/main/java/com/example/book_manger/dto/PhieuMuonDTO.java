package com.example.book_manger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhieuMuonDTO {

    private Integer id;
    private Integer nguoiDungId;
    private String nguoiDungTen;
    private LocalDateTime ngayMuon;
    private LocalDateTime ngayHenTra;
    private String trangThai;
    private String ghiChu;
    private List<ChiTietPhieuMuonDTO> chiTietList;
}