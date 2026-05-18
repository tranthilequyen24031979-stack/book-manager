package com.example.book_manger.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SachDTO {

    private Integer id;
    private String tieuDe;
    private String tacGia;
    private String isbn;
    private BigDecimal giaBan;
    private Integer phanTramGiamGia;
    private Integer soLuongTon;
    private String hinhAnh;
    private String tomTat;
    private Integer danhMucId;
    private String danhMucTen;
}
