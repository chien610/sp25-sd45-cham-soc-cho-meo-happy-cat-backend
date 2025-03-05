package com.demo.controller;

import com.demo.entity.KhuyenMai;
import com.demo.repo.KhuyenMaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/khuyenmai")
public class KhuyenMaiController {

    @Autowired
    private KhuyenMaiRepository khuyenMaiRepository;

    // 🟢 Lấy danh sách tất cả khuyến mãi
    @GetMapping
    public ResponseEntity<Page<KhuyenMai>> getAllKhuyenMai(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<KhuyenMai> result = khuyenMaiRepository.findAll(pageable);
        return ResponseEntity.ok(result);
    }

    // 🟢 Lấy khuyến mãi theo ID
    @GetMapping("/{khuyenMaiId}")
    public ResponseEntity<KhuyenMai> getKhuyenMaiById(@PathVariable Integer khuyenMaiId) {
        return khuyenMaiRepository.findById(khuyenMaiId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔵 Thêm mới khuyến mãi
    @PostMapping
    public ResponseEntity<?> addKhuyenMai(@RequestBody KhuyenMai khuyenMai) {
        KhuyenMai savedKhuyenMai = khuyenMaiRepository.save(khuyenMai);
        return ResponseEntity.ok(savedKhuyenMai);
    }

    // 🟡 Cập nhật khuyến mãi theo ID
    @PutMapping("/{khuyenMaiId}")
    public ResponseEntity<?> updateKhuyenMai(@PathVariable Integer khuyenMaiId, @RequestBody KhuyenMai updatedKhuyenMai) {
        return khuyenMaiRepository.findById(khuyenMaiId).map(km -> {
            km.setMoTa(updatedKhuyenMai.getMoTa());
            km.setPhanTramGiam(updatedKhuyenMai.getPhanTramGiam());
            km.setNgayBatDau(updatedKhuyenMai.getNgayBatDau());
            km.setNgayKetThuc(updatedKhuyenMai.getNgayKetThuc());
            km.setDieuKien(updatedKhuyenMai.getDieuKien());
            km.setXoa(updatedKhuyenMai.getXoa());

            KhuyenMai updated = khuyenMaiRepository.save(km);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    // 🔴 Xóa khuyến mãi theo ID
    @DeleteMapping("/{khuyenMaiId}")
    public ResponseEntity<Void> deleteKhuyenMai(@PathVariable Integer khuyenMaiId) {
        if (!khuyenMaiRepository.existsById(khuyenMaiId)) {
            return ResponseEntity.notFound().build();
        }
        khuyenMaiRepository.deleteById(khuyenMaiId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<KhuyenMai>> searchKhuyenMai(@RequestParam String moTa) {
        List<KhuyenMai> result = khuyenMaiRepository.findByMoTaContaining(moTa);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<KhuyenMai>> filterKhuyenMai(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayBatDau,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayKetThuc) {

        List<KhuyenMai> result = khuyenMaiRepository.findAll().stream()
                .filter(km -> (ngayBatDau == null || !km.getNgayBatDau().isBefore(ngayBatDau)))
                .filter(km -> (ngayKetThuc == null || !km.getNgayKetThuc().isAfter(ngayKetThuc)))
                .toList();

        return ResponseEntity.ok(result);
    }

}
