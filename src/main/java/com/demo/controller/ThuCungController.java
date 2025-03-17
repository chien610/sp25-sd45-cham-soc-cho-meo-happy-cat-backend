package com.demo.controller;

import com.demo.entity.ThuCung;
import com.demo.repo.ThuCungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/thu-cung")
public class ThuCungController {

    @Autowired
    ThuCungRepository thuCungRepo;

    // Hiển thị danh sách thú cưng với phân trang và tìm kiếm
    @GetMapping("/list")
    public Page<ThuCung> hienthi(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String gender) {

        Pageable pageable = PageRequest.of(page, size);

        if (search != null && !search.isEmpty() && gender != null && !gender.equals("All")) {
            return thuCungRepo.findByTenThuCungContainingAndGioiTinhAndXoa(search, gender, 0, pageable);
        } else if (search != null && !search.isEmpty()) {
            return thuCungRepo.findByTenThuCungContainingAndXoa(search, 0, pageable);
        } else if (gender != null && !gender.equals("All")) {
            return thuCungRepo.findByGioiTinhAndXoa(gender, 0, pageable);
        }

        return thuCungRepo.findAllActive(pageable);
    }

    // Thêm thú cưng mới
    @PostMapping("/add")
    public ResponseEntity<ThuCung> addThuCung(@RequestBody ThuCung thuCung) {
        ThuCung newThuCung = thuCungRepo.save(thuCung);
        return ResponseEntity.ok(newThuCung);
    }

    // Xóa thú cưng (đặt trường xoa = 1)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteThuCung(@PathVariable Long id) {
        return thuCungRepo.findById(id).map(thuCung -> {
            thuCung.setXoa(1);
            thuCungRepo.save(thuCung);
            return ResponseEntity.ok(Map.of("message", "Xóa thú cưng thành công")); // Trả về JSON
        }).orElseGet(() -> ResponseEntity.badRequest().body(Map.of("message", "Không tìm thấy thú cưng")));
    }

    // Cập nhật thông tin thú cưng
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateThuCung(@PathVariable Long id, @RequestBody ThuCung thuCungDetails) {
        Optional<ThuCung> optionalThuCung = thuCungRepo.findById(id);

        if (!optionalThuCung.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "Không tìm thấy thú cưng"));
        }

        ThuCung thuCung = optionalThuCung.get();
        thuCung.setTenThuCung(thuCungDetails.getTenThuCung());
        thuCung.setLoaiThuCung(thuCungDetails.getLoaiThuCung());
        thuCung.setTuoi(thuCungDetails.getTuoi());
        thuCung.setCanNang(thuCungDetails.getCanNang());
        thuCung.setGioiTinh(thuCungDetails.getGioiTinh());

        return ResponseEntity.ok(thuCungRepo.save(thuCung));
    }
}
