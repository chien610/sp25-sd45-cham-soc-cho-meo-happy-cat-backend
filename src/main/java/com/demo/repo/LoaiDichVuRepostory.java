package com.demo.repo;

import com.demo.entity.LoaiDichVu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoaiDichVuRepostory extends JpaRepository<LoaiDichVu,Long> {
    Page<LoaiDichVu> findByTenLoaiDichVuContainingIgnoreCase(String tenLoaiDichVu, Pageable pageable);
    LoaiDichVu findByTenLoaiDichVu(String tenLoaiDichVu);
}
