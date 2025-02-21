package com.demo.repo;

import com.demo.entity.DichVuChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DVCTRepository extends JpaRepository<DichVuChiTiet, Integer> {
    List<DichVuChiTiet> findByTenDichVuContainingIgnoreCase(String name);
    Page<DichVuChiTiet> findAll(Pageable pageable);
}
