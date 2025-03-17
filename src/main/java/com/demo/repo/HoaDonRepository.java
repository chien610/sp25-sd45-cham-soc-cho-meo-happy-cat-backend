package com.demo.repo;

import com.demo.dto.HoaDonDTO;
import com.demo.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HoaDonRepository extends JpaRepository<HoaDon,Integer> {
    @Query("SELECT new com.demo.dto.HoaDonDTO(h.id, h.ngayLap, h.tongTien, h.phuongThucThanhToan, " +
            "k.soDienThoai, nv.id, km.maKM, km.phanTramGiam) " +
            "FROM HoaDon h " +
            "JOIN h.khachHang k " +
            "JOIN h.nhanVien nv " +
            "LEFT JOIN h.khuyenMai km " +
            "WHERE (:search IS NULL OR k.soDienThoai LIKE %:search%)")
    Page<HoaDonDTO> findBySoDienThoaiContaining(@Param("search") String search, Pageable pageable);

    Page<HoaDon> findAll(Pageable pageable);

    @Query("SELECT hd FROM HoaDon hd WHERE hd.khachHang.soDienThoai = :soDienThoai")
    List<HoaDon> findByKhachHangSoDienThoai(@Param("soDienThoai") String soDienThoai);

    @Query("SELECT hd FROM HoaDon hd WHERE hd.khachHang.soDienThoai = :soDienThoai AND hd.phuongThucThanhToan = :phuongThuc")
    List<HoaDon> findBySoDienThoaiAndPhuongThucThanhToan(
            @Param("soDienThoai") String soDienThoai,
            @Param("phuongThuc") String phuongThuc
    );
}
