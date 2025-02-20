package com.demo.controller;

import com.demo.entity.KhachHang;
import com.demo.repo.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/khach-hang")

public class KhachHangController {

    @Autowired
    KhachHangRepository khachHangRepo;

//@GetMapping("/list")
//public Page<KhachHang> hienthi(
//        @RequestParam(defaultValue = "0") int page,
//        @RequestParam(defaultValue = "5") int size,
//        @RequestParam(required = false) String search,
//        @RequestParam(required = false) String gender) {
//
//    Pageable pageable = PageRequest.of(page, size);
//
//    if (search != null && !search.isEmpty() && gender != null && !gender.equals("All")) {
//        return khachHangRepo.findByTenKhachHangContainingAndGioiTinhAndXoa(search, gender, 0, pageable);
//    } else if (search != null && !search.isEmpty()) {
//        return khachHangRepo.findByTenKhachHangContainingAndXoa(search, 0, pageable);
//    } else if (gender != null && !gender.equals("All")) {
//        return khachHangRepo.findByGioiTinhAndXoa(gender, 0, pageable);
//    }
//
//    return khachHangRepo.findAllActive(pageable);
//}
@GetMapping("/list")
public Page<KhachHang> hienthi(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(required = false) String search,
        @RequestParam(required = false) String gender) {

    Pageable pageable = PageRequest.of(page, size);

    if (search != null && !search.isEmpty() && gender != null && !gender.equals("All")) {
        return khachHangRepo.findByTenKhachHangContainingOrSoDienThoaiContainingAndGioiTinhAndXoa(search, search, gender, 0, pageable);
    } else if (search != null && !search.isEmpty()) {
        return khachHangRepo.findByTenKhachHangContainingOrSoDienThoaiContainingAndXoa(search, search, 0, pageable);
    } else if (gender != null && !gender.equals("All")) {
        return khachHangRepo.findByGioiTinhAndXoa(gender, 0, pageable);
    }

    return khachHangRepo.findAllActive(pageable);
}
    @PostMapping("/add")
    public ResponseEntity<KhachHang> addKhachHang(@RequestBody KhachHang khachHang) {
        KhachHang newKhachHang = khachHangRepo.save(khachHang);
        return ResponseEntity.ok(newKhachHang);
    }

//    @PostMapping("/add")
//    public ResponseEntity<?> addKhachHang(@Valid @RequestBody KhachHang khachHang) {
//        // Kiểm tra email đã tồn tại chưa
//        if (khachHangRepo.findByEmail(khachHang.getEmail()).isPresent()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(Map.of("message", "Email đã tồn tại"));
//        }
//
//        // Kiểm tra số điện thoại đã tồn tại chưa
//        if (khachHangRepo.findBySoDienThoai(khachHang.getSoDienThoai()).isPresent()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(Map.of("message", "Số điện thoại đã tồn tại"));
//        }
//
//        KhachHang newKhachHang = khachHangRepo.save(khachHang);
//        return ResponseEntity.ok(newKhachHang);
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteKhachHang(@PathVariable Integer id) {
        return khachHangRepo.findById(id).map(kh -> {
            kh.setXoa(1);
            khachHangRepo.save(kh);
            return ResponseEntity.ok(Map.of("message", "Xóa khách hàng thành công")); // Trả về JSON
        }).orElseGet(() -> ResponseEntity.badRequest().body(Map.of("message", "Không tìm thấy khách hàng")));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateKhachHang(@PathVariable Integer id, @RequestBody KhachHang khachHangDetails) {
        Optional<KhachHang> optionalKhachHang = khachHangRepo.findById(id);

        if (!optionalKhachHang.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "Không tìm thấy khách hàng"));
        }

        KhachHang kh = optionalKhachHang.get();
        kh.setTenKhachHang(khachHangDetails.getTenKhachHang());
        kh.setSoDienThoai(khachHangDetails.getSoDienThoai());
        kh.setEmail(khachHangDetails.getEmail());
        kh.setGioiTinh(khachHangDetails.getGioiTinh());

        return ResponseEntity.ok(khachHangRepo.save(kh));
    }


//    @PutMapping("/update/{id}")
//    public ResponseEntity<?> updateKhachHang(@PathVariable Integer id, @Valid @RequestBody KhachHang khachHangDetails) {
//        Optional<KhachHang> optionalKhachHang = khachHangRepo.findById(id);
//
//        if (optionalKhachHang.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(Map.of("message", "Không tìm thấy khách hàng"));
//        }
//
//        KhachHang kh = optionalKhachHang.get();
//
//        // Kiểm tra email trùng (chỉ kiểm tra nếu email mới khác email cũ)
//        if (!kh.getEmail().equals(khachHangDetails.getEmail()) &&
//                khachHangRepo.findByEmail(khachHangDetails.getEmail()).isPresent()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(Map.of("message", "Email đã tồn tại"));
//        }
//
//        // Kiểm tra số điện thoại trùng (chỉ kiểm tra nếu số mới khác số cũ)
//        if (!kh.getSoDienThoai().equals(khachHangDetails.getSoDienThoai()) &&
//                khachHangRepo.findBySoDienThoai(khachHangDetails.getSoDienThoai()).isPresent()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(Map.of("message", "Số điện thoại đã tồn tại"));
//        }
//
//        kh.setTenKhachHang(khachHangDetails.getTenKhachHang());
//        kh.setSoDienThoai(khachHangDetails.getSoDienThoai());
//        kh.setEmail(khachHangDetails.getEmail());
//        kh.setGioiTinh(khachHangDetails.getGioiTinh());
//
//        return ResponseEntity.ok(khachHangRepo.save(kh));
//    }



}
