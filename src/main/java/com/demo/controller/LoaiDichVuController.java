package com.demo.controller;

import com.demo.entity.LoaiDichVu;
import com.demo.repo.LoaiDichVuRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@Transactional
@RestController
@RequestMapping("/api/loai-dich-vu")
public class LoaiDichVuController {
    @Autowired
    private final LoaiDichVuRepostory loaiDichVuRepository;

    public LoaiDichVuController(LoaiDichVuRepostory loaiDichVuRepository) {
        this.loaiDichVuRepository = loaiDichVuRepository;
    }

    // Lấy danh sách tất cả loại dịch vụ (có phân trang)
    @GetMapping
    public Page<LoaiDichVu> getAllLoaiDichVu(@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5); // 5 dòng mỗi trang
        return loaiDichVuRepository.findAll(pageable);
    }

    // Lấy loại dịch vụ theo ID
    @GetMapping("/{id}")
    public ResponseEntity<LoaiDichVu> getLoaiDichVuById(@PathVariable Long id) {
        Optional<LoaiDichVu> loaiDichVu = loaiDichVuRepository.findById(id);
        return loaiDichVu.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Tìm kiếm loại dịch vụ theo tên (có phân trang)
    @GetMapping("/search")
    public Page<LoaiDichVu> searchLoaiDichVu(@RequestParam String keyword,
                                             @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5); // 5 dòng mỗi trang
        return loaiDichVuRepository.findByTenLoaiDichVuContainingIgnoreCase(keyword, pageable);
    }

    // Thêm mới loại dịch vụ
    @PostMapping
    public LoaiDichVu createLoaiDichVu(@RequestBody LoaiDichVu loaiDichVu) {
        return loaiDichVuRepository.save(loaiDichVu);
    }

    // Cập nhật loại dịch vụ
    @PutMapping("/{id}")
    public ResponseEntity<LoaiDichVu> updateLoaiDichVu(@PathVariable Long id, @RequestBody LoaiDichVu loaiDichVuDetails) {
        return loaiDichVuRepository.findById(id).map(loaiDichVu -> {
            loaiDichVu.setTenLoaiDichVu(loaiDichVuDetails.getTenLoaiDichVu());
            loaiDichVu.setXoa(loaiDichVuDetails.getXoa());
            return ResponseEntity.ok(loaiDichVuRepository.save(loaiDichVu));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Xóa loại dịch vụ (cập nhật trạng thái xóa thay vì xóa khỏi DB)
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLoaiDichVu(@PathVariable Long id) {
        return loaiDichVuRepository.findById(id).map(loaiDichVu -> {
            loaiDichVu.setXoa(1); // Đánh dấu đã xóa
            loaiDichVuRepository.save(loaiDichVu);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
