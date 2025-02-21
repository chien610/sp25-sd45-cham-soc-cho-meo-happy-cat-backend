package com.demo.controller;

import com.demo.entity.LoaiDichVu;
import com.demo.repo.LoaiDichVuRepository;
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
@RequestMapping("/api/loai-dich-vu")
public class LoaiDichVuController {
    @Autowired
    private LoaiDichVuRepository loaiDichVuRepository;

    // Lấy tất cả các loại dịch vụ
    @GetMapping
    public List<LoaiDichVu> getAllLoaiDichVu() {
        return loaiDichVuRepository.findAll();
    }

    // Lấy loại dịch vụ theo ID
    @GetMapping("/{id}")
    public ResponseEntity<LoaiDichVu> getLoaiDichVuById(@PathVariable Integer id) {
        Optional<LoaiDichVu> loaiDichVu = loaiDichVuRepository.findById(id);
        return loaiDichVu.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Tạo mới loại dịch vụ
    @PostMapping
    public LoaiDichVu createLoaiDichVu(@RequestBody LoaiDichVu loaiDichVu) {
        return loaiDichVuRepository.save(loaiDichVu);
    }

    // Cập nhật loại dịch vụ
    @PutMapping("/{id}")
    public ResponseEntity<LoaiDichVu> updateLoaiDichVu(@PathVariable Integer id, @RequestBody LoaiDichVu loaiDichVu) {
        if (!loaiDichVuRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        loaiDichVu.setLoaiDichVuId(id);
        return ResponseEntity.ok(loaiDichVuRepository.save(loaiDichVu));
    }

    // Xóa loại dịch vụ
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoaiDichVu(@PathVariable Integer id) {
        if (!loaiDichVuRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        loaiDichVuRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Tìm kiếm loại dịch vụ theo tên
    @GetMapping("/search")
    public List<LoaiDichVu> searchLoaiDichVuByName(@RequestParam String name) {
        return loaiDichVuRepository.findByTenLoaiDichVuContainingIgnoreCase(name);
    }

    // Phân trang loại dịch vụ
    @GetMapping("/page")
    public Page<LoaiDichVu> getLoaiDichVuPaginated(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return loaiDichVuRepository.findAll(pageable);
    }
}
