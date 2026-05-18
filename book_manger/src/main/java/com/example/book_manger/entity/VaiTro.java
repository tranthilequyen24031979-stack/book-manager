package com.example.book_manger.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "vai_tro")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class VaiTro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ten_vai_tro", length = 50, nullable = false, unique = true)
    private String tenVaiTro;

    @ManyToMany(mappedBy = "vaiTroList")
    private List<NguoiDung> nguoiDungList;
}
