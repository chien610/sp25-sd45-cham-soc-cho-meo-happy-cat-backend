package com.demo.repo;

import com.demo.entity.KhuyenMai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface KhuyenMaiRepository extends JpaRepository<KhuyenMai , String> {
    KhuyenMai findByMakm(String makm);
    List<KhuyenMai> findByMakmContainingIgnoreCase(String makm);
    List<KhuyenMai> findByMakmContaining(String makm);

    // 🔍 Lọc theo khoảng ngày bắt đầu và ngày kết thúc
    @Query("SELECT k FROM KhuyenMai k WHERE (:startDate IS NULL OR k.ngayBatDau >= :startDate) " +
            "AND (:endDate IS NULL OR k.ngayKetThuc <= :endDate)")
    List<KhuyenMai> filterByDateRange(@Param("startDate") LocalDate startDate,
                                      @Param("endDate") LocalDate endDate);
}
