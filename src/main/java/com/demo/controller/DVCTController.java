package com.demo.controller;

import com.demo.entity.DichVuChiTiet;
import com.demo.repo.DVCTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@Transactional
@RestController
@RequestMapping("/api/dich-vu-chi-tiet")
public class DVCTController {
    @Autowired
    private DVCTRepository dichVuChiTietRepository;

    // Lấy tất cả các dịch vụ chi tiết
    @GetMapping
    public List<DichVuChiTiet> getAllDichVuChiTiet() {
        return dichVuChiTietRepository.findAll();
    }

    // Lấy dịch vụ chi tiết theo ID
    @GetMapping("/{id}")
    public ResponseEntity<DichVuChiTiet> getDichVuChiTietById(@PathVariable Integer id) {
        Optional<DichVuChiTiet> dichVuChiTiet = dichVuChiTietRepository.findById(id);
        return dichVuChiTiet.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Tạo mới dịch vụ chi tiết
    @PostMapping
    public DichVuChiTiet createDichVuChiTiet(@RequestBody DichVuChiTiet dichVuChiTiet) {
        return dichVuChiTietRepository.save(dichVuChiTiet);
    }

    // Cập nhật dịch vụ chi tiết
    @PutMapping("/{id}")
    public ResponseEntity<DichVuChiTiet> updateDichVuChiTiet(@PathVariable Integer id, @RequestBody DichVuChiTiet dichVuChiTiet) {
        if (!dichVuChiTietRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        dichVuChiTiet.setDichVuChiTietId(id);
        return ResponseEntity.ok(dichVuChiTietRepository.save(dichVuChiTiet));
    }

    // Xóa dịch vụ chi tiết
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDichVuChiTiet(@PathVariable Integer id) {
        if (!dichVuChiTietRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        dichVuChiTietRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Tìm kiếm dịch vụ chi tiết theo tên
    @GetMapping("/search")
    public List<DichVuChiTiet> searchDichVuChiTietByName(@RequestParam String name) {
        return dichVuChiTietRepository.findByTenDichVuContainingIgnoreCase(name);
    }

    // Phân trang dịch vụ chi tiết
    @GetMapping("/page")
    public Page<DichVuChiTiet> getDichVuChiTietPaginated(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return dichVuChiTietRepository.findAll(pageable);
    }
}
