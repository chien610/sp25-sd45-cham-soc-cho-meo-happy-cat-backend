package com.demo.repo;

import com.demo.entity.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface KhachHangRepository extends JpaRepository<KhachHang,Integer> {

    Page<KhachHang> findByTenKhachHangContainingAndXoa(String tenKhachHang, Integer xoa, Pageable pageable);

    Page<KhachHang> findByGioiTinhAndXoa(String gioiTinh, Integer xoa, Pageable pageable);

    Page<KhachHang> findByTenKhachHangContainingAndGioiTinhAndXoa(String tenKhachHang, String gioiTinh, Integer xoa, Pageable pageable);

    Page<KhachHang> findByTenKhachHangContainingOrSoDienThoaiContainingAndXoa(String tenKhachHang, String soDienThoai, Integer xoa, Pageable pageable);

    Page<KhachHang> findByTenKhachHangContainingOrSoDienThoaiContainingAndGioiTinhAndXoa(String tenKhachHang, String soDienThoai, String gioiTinh, Integer xoa, Pageable pageable);

    @Query("SELECT k FROM KhachHang k WHERE k.xoa = 0")
    Page<KhachHang> findAllActive(Pageable pageable);
}

