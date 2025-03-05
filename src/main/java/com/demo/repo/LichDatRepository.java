package com.demo.repo;

import com.demo.dto.LichDatRequest;
import com.demo.entity.LichDat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface LichDatRepository extends JpaRepository<LichDat,Long> {
    @Query("SELECT new com.demo.dto.LichDatRequest(" +
            "kh.tenKhachHang, kh.soDienThoai, kh.email, tc.tenThuCung, " +
            "ld.ngayDat, ld.gioDat, dv.tenDichVu, ld.xoaLich, dv.chungLoai, dv.hangCan, ld.lichDatId, dv.id, dv.gia) " +
            "FROM LichDat ld " +
            "JOIN ld.khachHang kh " +
            "JOIN ld.thuCung tc " +
            "JOIN ld.dichVuChiTiet dv " +
            "WHERE (:search IS NULL OR kh.tenKhachHang LIKE CONCAT('%', :search, '%') OR kh.soDienThoai LIKE CONCAT('%', :search, '%')) " +
            "AND ld.xoaLich = :xoa " +
            "AND (:ngayDat IS NULL OR ld.ngayDat = :ngayDat) " + // Nếu không truyền ngày thì bỏ qua điều kiện này
            "ORDER BY ld.gioDat ASC"
    )
    Page<LichDatRequest> findByKhachHangTenContainingOrKhachHangSoDienThoaiContainingAndXoaAndNgayDat(
            @Param("search") String search,
            @Param("xoa") Integer xoa,
            @Param("ngayDat") LocalDate ngayDat,  // Có thể null
            Pageable pageable);

    @Query("SELECT new com.demo.dto.LichDatRequest(" +
            "kh.tenKhachHang, kh.soDienThoai, kh.email, tc.tenThuCung, " +
            "ld.ngayDat, ld.gioDat, dv.tenDichVu, ld.xoaLich, dv.chungLoai, dv.hangCan, ld.lichDatId, dv.id, dv.gia) " +
            "FROM LichDat ld " +
            "JOIN ld.khachHang kh " +
            "JOIN ld.thuCung tc " +
            "JOIN ld.dichVuChiTiet dv " +
            "WHERE ld.xoaLich = :xoa " +
            "AND (:ngayDat IS NULL OR ld.ngayDat = :ngayDat) " + // Nếu không truyền ngày thì bỏ qua điều kiện này
            "ORDER BY ld.gioDat ASC"
    )
    Page<LichDatRequest> findAllByXoaAndNgayDat(
            @Param("xoa") Integer xoa,
            @Param("ngayDat") LocalDate ngayDat,  // Có thể null
            Pageable pageable);

}
