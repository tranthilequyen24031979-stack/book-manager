package com.example.book_manger.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NguoiDungDTO {

    private Integer id;
    private String tenDangNhap;
    private String hoTen;
    private String email;
    private String soDienThoai;
    private List<String> vaiTroList;
}
