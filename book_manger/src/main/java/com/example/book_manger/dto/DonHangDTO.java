package com.example.book_manger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonHangDTO {

    private Integer id;

    // Người dùng
    private Integer nguoiDungId;
    private String tenNguoiDung;

    // Thông tin đơn hàng
    private LocalDateTime ngayDat;

    private String trangThai;

    private BigDecimal tongTien;

    private String diaChiGiaoHang;

    private String soDienThoaiNhan;

    // Mã giảm giá
    private Integer maGiamGiaId;
    private String tenMaGiamGia;

    // Danh sách chi tiết đơn hàng
    private List<ChiTietDonHangDTO> chiTietDonHangList;
}
