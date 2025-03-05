package com.demo.repo;

import com.demo.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KhuyenMaiRepository extends JpaRepository<NhanVien,Integer> {
}
