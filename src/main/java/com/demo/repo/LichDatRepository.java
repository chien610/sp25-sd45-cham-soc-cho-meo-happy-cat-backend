package com.demo.repo;

import com.demo.dto.LichDatRequest;
import com.demo.entity.LichDat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LichDatRepository extends JpaRepository<LichDat,Long> {
    @Query("SELECT new com.demo.dto.LichDatRequest(" +
            "kh.tenKhachHang, kh.soDienThoai, kh.email, tc.tenThuCung, " +
            "ld.ngayDat, ld.gioDat, dv.tenDichVu, ld.xoaLich, dv.chungLoai, dv.hangCan, ld.lichDatId, dv.id, dv.gia) " +
            "FROM LichDat ld " +
            "JOIN ld.khachHang kh " +
            "JOIN ld.thuCung tc " +
            "JOIN ld.dichVuChiTiet dv " +
            "WHERE ld.nhanVien.id IS NOT NULL " +  // Chỉ lấy bản ghi có nhân viên
            "AND (:search IS NULL OR :search = '' OR kh.tenKhachHang LIKE %:search% OR kh.soDienThoai LIKE %:search%) " +
            "AND (:ngayDat IS NULL OR ld.ngayDat = :ngayDat) " +
            "ORDER BY ld.gioDat ASC"
    )
    Page<LichDatRequest> findLichDatBySearchAndNgayDatAndNhanVienIdNotNull(
            @Param("search") String search,
            @Param("ngayDat") LocalDate ngayDat,
            Pageable pageable);


    @Query("SELECT new com.demo.dto.LichDatRequest(" +
            "kh.tenKhachHang, kh.soDienThoai, kh.email, tc.tenThuCung, " +
            "ld.ngayDat, ld.gioDat, dv.tenDichVu, ld.xoaLich, dv.chungLoai, dv.hangCan, ld.lichDatId, dv.id, dv.gia) " +
            "FROM LichDat ld " +
            "JOIN ld.khachHang kh " +
            "JOIN ld.thuCung tc " +
            "JOIN ld.dichVuChiTiet dv " +
            "WHERE ld.nhanVien.id IS NULL " +  // Chỉ lấy lịch chưa phân công nhân viên
            "AND (:soDienThoai IS NULL OR kh.soDienThoai LIKE %:soDienThoai%) " +
            "AND (:ngayDat IS NULL OR ld.ngayDat = :ngayDat) " +
            "ORDER BY ld.gioDat ASC"
    )
    Page<LichDatRequest> findLichDatChuaPhanCong(
            @Param("soDienThoai") String soDienThoai,
            @Param("ngayDat") LocalDate ngayDat,
            Pageable pageable
    );

}
