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
            "WHERE (:soDienThoai IS NULL OR k.soDienThoai LIKE %:soDienThoai%) " +
            "AND (:phuongThuc IS NULL OR LOWER(h.phuongThucThanhToan) = LOWER(:phuongThuc))")
    Page<HoaDonDTO> searchHoaDon(@Param("soDienThoai") String soDienThoai,
                                 @Param("phuongThuc") String phuongThuc,
                                 Pageable pageable);

    Page<HoaDon> findAll(Pageable pageable);
}
