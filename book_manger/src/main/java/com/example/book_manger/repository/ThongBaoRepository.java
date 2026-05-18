package com.example.book_manger.repository;

import com.example.book_manger.entity.ThongBao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThongBaoRepository extends JpaRepository<ThongBao, Integer> {

    List<ThongBao> findByNguoiDungId(Integer nguoiDungId);

    Page<ThongBao> findByNguoiDungId(Integer nguoiDungId, Pageable pageable);

    List<ThongBao> findByNguoiDungIdAndDaDocFalse(Integer nguoiDungId);
}
