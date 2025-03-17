package com.demo.controller;

import com.demo.entity.LoaiDichVu;
import com.demo.repo.LoaiDichVuRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@Transactional
@RestController
@RequestMapping("/api/loai-dich-vu")
public class LoaiDichVuController {
    @Autowired
    LoaiDichVuRepostory loaiDichVuRepository;


    // Lấy danh sách tất cả loại dịch vụ (có phân trang)
    @GetMapping("/list")
    public Page<LoaiDichVu> hienthi(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String search)
    {

        Pageable pageable = PageRequest.of(page, size);

            if (search != null && !search.isEmpty()) {
                return loaiDichVuRepository.findByTenLoaiDichVuAndXoa("%" + search + "%", 0, pageable);
            }

        return loaiDichVuRepository.findAllActive(pageable);
    }
    @PostMapping("/add")
    public ResponseEntity<LoaiDichVu> addLoaiDichVu(@RequestBody LoaiDichVu loaiDichVu) {
            if (loaiDichVu.getXoa() == null) {
                loaiDichVu.setXoa(0); // Đặt giá trị mặc định là 0
            }

        LoaiDichVu newLoaiDichVu = loaiDichVuRepository.save(loaiDichVu);

        return ResponseEntity.ok(newLoaiDichVu);
    }

    // Cập nhật loại dịch vụ
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLoaiDichVu(@PathVariable Long id, @RequestBody LoaiDichVu loaiDichVuDetails) {
        Optional<LoaiDichVu> optionalLoaiDichVu = loaiDichVuRepository.findById(id);

        if (!optionalLoaiDichVu.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "Không tìm thấy loại dịch vụ"));
        }

        LoaiDichVu ldv = optionalLoaiDichVu.get();
        ldv.setTenLoaiDichVu(loaiDichVuDetails.getTenLoaiDichVu());

        return ResponseEntity.ok(loaiDichVuRepository.save(ldv));
    }

    // Xóa loại dịch vụ (cập nhật trạng thái xóa thay vì xóa khỏi DB)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLoaiDichVu(@PathVariable Long id) {
        return loaiDichVuRepository.findById(id).map(kh -> {
            kh.setXoa(1);
            loaiDichVuRepository.save(kh);
            return ResponseEntity.ok(Map.of("message", "Xóa loại dich vụ thành công")); // Trả về JSON
        }).orElseGet(() -> ResponseEntity.badRequest().body(Map.of("m+essage", "Không tìm thấy loại dịch vụ")));
    }
}
