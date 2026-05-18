package com.example.book_manger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThongBaoDTO {

    private Integer id;
    private Integer nguoiDungId;
    private String tieuDe;
    private String noiDung;
    private LocalDateTime ngayTao;
    private Boolean daDoc;
    private String loaiThongBao;
}
