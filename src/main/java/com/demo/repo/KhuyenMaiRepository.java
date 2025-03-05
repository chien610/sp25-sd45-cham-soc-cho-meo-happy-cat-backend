package com.demo.repo;

import com.demo.entity.KhuyenMai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface KhuyenMaiRepository extends JpaRepository<KhuyenMai, Integer> {

    // 🔍 Tìm kiếm khuyến mãi theo mô tả (tùy chọn)
    List<KhuyenMai> findByMoTaContaining(String moTa);

    // 🔍 Tìm kiếm khuyến mãi theo phần trăm giảm giá (tùy chọn)
    List<KhuyenMai> findByPhanTramGiamGreaterThanEqual(Float phanTramGiam);

    // 🔍 Lọc theo khoảng ngày bắt đầu và ngày kết thúc
    @Query("SELECT k FROM KhuyenMai k WHERE (:startDate IS NULL OR k.ngayBatDau >= :startDate) " +
            "AND (:endDate IS NULL OR k.ngayKetThuc <= :endDate)")
    List<KhuyenMai> filterByDateRange(@Param("startDate") LocalDate startDate,
                                      @Param("endDate") LocalDate endDate);
}
