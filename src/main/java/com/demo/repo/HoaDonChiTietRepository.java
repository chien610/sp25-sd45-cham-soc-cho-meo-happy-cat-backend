package com.demo.repo;

import com.demo.dto.HoaDonChiTietDTO;
import com.demo.entity.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet,Integer> {
    @Query("SELECT new com.demo.dto.HoaDonChiTietDTO( " +
            "hct.id, hct.hoaDon.id, hct.dichVuChiTiet.id, hct.dichVuChiTiet.tenDichVu, " +
            "hct.soLuong, hct.donGia, CAST(hct.soLuong AS BigDecimal) * hct.donGia ) " +
            "FROM HoaDonChiTiet hct " +
            "WHERE hct.hoaDon.id = :hoaDonId")
    List<HoaDonChiTietDTO> findByHoaDonId(@Param("hoaDonId") Integer hoaDonId);

}
