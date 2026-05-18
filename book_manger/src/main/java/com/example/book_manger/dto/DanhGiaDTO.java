package com.example.book_manger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DanhGiaDTO {

    private Integer id;
    private Integer sachId;
    private String sachTieu;
    private Integer nguoiDungId;
    private String nguoiDungTen;
    private Integer diemSo;
    private String binhLuan;
    private LocalDateTime ngayTao;
}
