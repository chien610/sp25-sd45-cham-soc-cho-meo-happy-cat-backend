package com.demo.repo;

import com.demo.entity.ThuCung;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ThuCungRepository extends JpaRepository<ThuCung,Long> {
    // Tìm kiếm theo tên thú cưng và trạng thái chưa xóa
    Page<ThuCung> findByTenThuCungContainingAndXoa(String tenThuCung, Integer xoa, Pageable pageable);

    // Tìm kiếm theo giới tính và trạng thái chưa xóa
    Page<ThuCung> findByGioiTinhAndXoa(String gioiTinh, Integer xoa, Pageable pageable);

    // Tìm kiếm theo tên thú cưng, giới tính, và trạng thái chưa xóa
    Page<ThuCung> findByTenThuCungContainingAndGioiTinhAndXoa(String tenThuCung, String gioiTinh, Integer xoa, Pageable pageable);

    // Lấy tất cả thú cưng chưa bị xóa
    @Query("SELECT t FROM ThuCung t WHERE t.xoa = 0")
    Page<ThuCung> findAllActive(Pageable pageable);
}
