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
    public ResponseEntity<List<KhuyenMai>> getAllKhuyenMai() {
        List<KhuyenMai> list = khuyenMaiRepository.findAll();
        return ResponseEntity.ok(list.isEmpty() ? List.of() : list);
    }

    // 🟢 Lấy danh sách khuyến mãi có phân trang
    @GetMapping("/page")
    public ResponseEntity<Page<KhuyenMai>> getKhuyenMaiPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<KhuyenMai> result = khuyenMaiRepository.findAll(pageable);
        return ResponseEntity.ok(result);
    }

    // 🟢 Lấy khuyến mãi theo mã khuyến mãi (makm)
    @GetMapping("/{makm}")
    public ResponseEntity<KhuyenMai> getKhuyenMaiByMakm(@PathVariable String makm) {
        return khuyenMaiRepository.findById(makm)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔵 Thêm mới khuyến mãi
    @PostMapping
    public ResponseEntity<?> addKhuyenMai(@RequestBody KhuyenMai khuyenMai) {
        // Kiểm tra `makm` đã tồn tại chưa
        if (khuyenMaiRepository.existsById(khuyenMai.getMakm())) {
            return ResponseEntity.badRequest().body("Mã khuyến mãi đã tồn tại!");
        }
        KhuyenMai savedKhuyenMai = khuyenMaiRepository.save(khuyenMai);
        return ResponseEntity.ok(savedKhuyenMai);
    }

    // 🟡 Cập nhật khuyến mãi theo `makm`
    @PutMapping("/{makm}")
    public ResponseEntity<?> updateKhuyenMai(@PathVariable String makm, @RequestBody KhuyenMai updatedKhuyenMai) {
        return khuyenMaiRepository.findById(makm).map(km -> {
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

    // 🔴 Xóa khuyến mãi theo `makm`
    @DeleteMapping("/{makm}")
    public ResponseEntity<Void> deleteKhuyenMai(@PathVariable String makm) {
        if (!khuyenMaiRepository.existsById(makm)) {
            return ResponseEntity.notFound().build();
        }
        khuyenMaiRepository.deleteById(makm);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/search")
    public ResponseEntity<List<KhuyenMai>> searchKhuyenMai(@RequestParam String makm) {
        List<KhuyenMai> result = khuyenMaiRepository.findByMakmContaining(makm);
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
