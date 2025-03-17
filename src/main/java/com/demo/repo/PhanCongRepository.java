package com.demo.repo;

import com.demo.entity.NhanVien;
import com.demo.entity.PhanCong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PhanCongRepository extends JpaRepository<PhanCong,Long> {
    List<PhanCong> findByNgayDat(LocalDate ngayDat);
    List<PhanCong> findByNhanVienAndNgayDat(NhanVien nhanVien, LocalDate ngayDat);
    @Query("SELECT COUNT(pc) FROM PhanCong pc WHERE pc.nhanVien.id = :nhanVienId AND pc.caLam.caLamId = :caLamId AND pc.ngayDat = :ngayDat")
    int countPhanCongTrung(@Param("nhanVienId") Integer nhanVienId, @Param("caLamId") Long caLamId, @Param("ngayDat") LocalDate ngayDat);
}
