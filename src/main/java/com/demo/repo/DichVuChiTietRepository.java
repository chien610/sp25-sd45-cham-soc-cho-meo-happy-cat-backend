package com.demo.repo;

import com.demo.entity.DichVuChiTiet;
import com.demo.entity.LoaiDichVu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DichVuChiTietRepository extends JpaRepository<DichVuChiTiet,Long> {
    Optional<DichVuChiTiet> findByTenDichVuAndChungLoaiAndHangCan(String tenDichVu, String chungLoai, Integer hangCan);

    DichVuChiTiet findOneByTenDichVuAndChungLoaiAndHangCan(String tenDichVu, String chungLoai, Integer hangCan);

    Page<DichVuChiTiet> findByTenDichVuContainingIgnoreCase(String tenDichVu, Pageable pageable);
    Page<DichVuChiTiet> findByLoaiDichVu(LoaiDichVu loaiDichVu, Pageable pageable);
    @Query("SELECT d FROM DichVuChiTiet d WHERE " +
            "(COALESCE(:search, '') = '' OR d.tenDichVu LIKE %:search%) " +
            "AND (COALESCE(:loaiDichVu, '') = '' OR d.loaiDichVu.tenLoaiDichVu = :loaiDichVu) " +
            "AND (COALESCE(:minGia, 0) <= d.gia) " +
            "AND (COALESCE(:maxGia, 2147483647) >= d.gia) " +
            "AND d.xoa = 0")
    Page<DichVuChiTiet> findByFilters(
            @Param("search") String search,
            @Param("loaiDichVu") String loaiDichVu,
            @Param("minGia") Integer minGia,
            @Param("maxGia") Integer maxGia,
            Pageable pageable
    );


    @EntityGraph(attributePaths = {"loaiDichVu"})
    List<DichVuChiTiet> findAll();

    @Query("SELECT dvct FROM DichVuChiTiet dvct WHERE dvct.xoa = 0")
    Page<DichVuChiTiet> findAllActive(Pageable pageable);

}
