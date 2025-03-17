package com.demo.repo;

import com.demo.entity.KhachHang;
import com.demo.entity.LoaiDichVu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoaiDichVuRepostory extends JpaRepository<LoaiDichVu,Long> {
    Page<LoaiDichVu> findByTenLoaiDichVuContainingIgnoreCase(String tenLoaiDichVu, Pageable pageable);
//    LoaiDichVu findByTenLoaiDichVu(String tenLoaiDichVu);

    @Query("SELECT l FROM LoaiDichVu l WHERE l.tenLoaiDichVu = :tenLoaiDichVu")
    List<LoaiDichVu> findByTenLoaiDichVu(@Param("tenLoaiDichVu") String tenLoaiDichVu);


    @Query("SELECT ldv FROM LoaiDichVu ldv WHERE LOWER(ldv.tenLoaiDichVu) LIKE LOWER(:search) AND ldv.xoa = :xoa")
    Page<LoaiDichVu> findByTenLoaiDichVuAndXoa(@Param("search") String search, @Param("xoa") int xoa, Pageable pageable);


    @Query("SELECT ldv FROM LoaiDichVu ldv WHERE ldv.xoa = 0")
    Page<LoaiDichVu> findAllActive(Pageable pageable);
}
