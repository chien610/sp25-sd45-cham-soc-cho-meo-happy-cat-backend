package com.demo.repo;

import com.demo.entity.DichVuChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DichVuChiTietRepository extends JpaRepository<DichVuChiTiet,Long> {
    Optional<DichVuChiTiet> findByTenDichVuAndChungLoaiAndHangCan(String tenDichVu, String chungLoai, Integer hangCan);

    DichVuChiTiet findOneByTenDichVuAndChungLoaiAndHangCan(String tenDichVu, String chungLoai, Integer hangCan);


}
