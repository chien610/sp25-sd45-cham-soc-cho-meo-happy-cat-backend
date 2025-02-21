package com.demo.repo;

import com.demo.entity.LoaiDichVu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoaiDichVuRepository extends JpaRepository<LoaiDichVu,Integer> {
    List<LoaiDichVu> findByTenLoaiDichVuContainingIgnoreCase(String name);
    Page<LoaiDichVu> findAll(Pageable pageable);
}
