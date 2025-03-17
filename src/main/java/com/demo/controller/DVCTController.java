package com.demo.controller;

import com.demo.dto.DichVuChiTietDTO;
import com.demo.entity.DichVuChiTiet;
import com.demo.entity.LoaiDichVu;
import com.demo.repo.DichVuChiTietRepository;
import com.demo.repo.LoaiDichVuRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/dich-vu-chi-tiet")
public class DVCTController {

    @Autowired
    DichVuChiTietRepository dichVuChiTietRepository;

    @Autowired
    LoaiDichVuRepostory loaiDichVuRepository;


    @GetMapping("/list")
    public Page<DichVuChiTiet> hienthi(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String loaiDichVu,
            @RequestParam(required = false) Integer minGia,
            @RequestParam(required = false) Integer maxGia) {

        Pageable pageable = PageRequest.of(page, size);

        // Đảm bảo giá trị min/max không null để tránh lỗi khi truy vấn
        if (minGia == null) minGia = 0;
        if (maxGia == null) maxGia = Integer.MAX_VALUE;

        return dichVuChiTietRepository.findByFilters(
                (search != null && !search.isEmpty()) ? search.trim() : null,
                (loaiDichVu != null && !loaiDichVu.isEmpty()) ? loaiDichVu.trim() : null,
                (minGia != null) ? minGia : 0,
                (maxGia != null) ? maxGia : Integer.MAX_VALUE,
                pageable
        );
    }



    // Lấy dịch vụ theo ID
    @GetMapping("/{id}")
    public ResponseEntity<DichVuChiTiet> getDichVuChiTietById(@PathVariable Long id) {
        Optional<DichVuChiTiet> dichVuChiTiet = dichVuChiTietRepository.findById(id);
        return dichVuChiTiet.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Tìm kiếm dịch vụ theo tên (có phân trang)
    @GetMapping("/search")
    public Page<DichVuChiTiet> searchDichVuChiTiet(@RequestParam String keyword,
                                                   @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return dichVuChiTietRepository.findByTenDichVuContainingIgnoreCase(keyword, pageable);
    }

    // Lọc danh sách dịch vụ theo loại dịch vụ
    @GetMapping("/by-loai")
    public ResponseEntity<Page<DichVuChiTiet>> getDichVuChiTietByLoai(@RequestParam Long id,
                                                                      @RequestParam(defaultValue = "0") int page) {
        Optional<LoaiDichVu> loaiDichVu = loaiDichVuRepository.findById(id);
        if (loaiDichVu.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(dichVuChiTietRepository.findByLoaiDichVu(loaiDichVu.get(), pageable));
    }

    // Thêm mới dịch vụ
    @PostMapping("/add")
    public DichVuChiTiet addDichVuChiTiet(@RequestBody DichVuChiTietDTO dto) {
        // Kiểm tra loại dịch vụ có tồn tại không
        List<LoaiDichVu> loaiDichVus = loaiDichVuRepository.findByTenLoaiDichVu(dto.getTenLoaiDichVu());
        LoaiDichVu loaiDichVu;

        if (loaiDichVus.isEmpty()) {
            // Nếu không tìm thấy, tạo mới loại dịch vụ
            loaiDichVu = new LoaiDichVu();
            loaiDichVu.setTenLoaiDichVu(dto.getTenLoaiDichVu());
            loaiDichVu = loaiDichVuRepository.save(loaiDichVu);
        } else {
            // Nếu có nhiều bản ghi, lấy bản ghi đầu tiên
            loaiDichVu = loaiDichVus.get(0);
        }

        // Tạo mới dịch vụ chi tiết
        DichVuChiTiet dichVuChiTiet = new DichVuChiTiet();
        dichVuChiTiet.setTenDichVu(dto.getTenDichVu());
        dichVuChiTiet.setChungLoai(dto.getChungLoai());
        dichVuChiTiet.setHangCan(dto.getHangCan());
        dichVuChiTiet.setGia(dto.getGia());
        dichVuChiTiet.setNoiDung(dto.getNoiDung());
        dichVuChiTiet.setLoaiDichVu(loaiDichVu);
        dichVuChiTiet.setXoa(0); // Đánh dấu chưa bị xóa

        return dichVuChiTietRepository.save(dichVuChiTiet);
    }


    // Cập nhật dịch vụ
    @PutMapping("/update/{id}")
    public ResponseEntity<DichVuChiTiet> updateDichVuChiTiet(@PathVariable Long id, @RequestBody DichVuChiTietDTO dto) {
        // Tìm dịch vụ chi tiết theo ID
        DichVuChiTiet dichVuChiTiet = dichVuChiTietRepository.findById(id).orElse(null);
        if (dichVuChiTiet == null) {
            return ResponseEntity.notFound().build();
        }

        // Kiểm tra loại dịch vụ có tồn tại không
        List<LoaiDichVu> loaiDichVus = loaiDichVuRepository.findByTenLoaiDichVu(dto.getTenLoaiDichVu());
        LoaiDichVu loaiDichVu;

        if (loaiDichVus.isEmpty()) {
            // Nếu không tìm thấy, tạo mới loại dịch vụ
            loaiDichVu = new LoaiDichVu();
            loaiDichVu.setTenLoaiDichVu(dto.getTenLoaiDichVu());
            loaiDichVu = loaiDichVuRepository.save(loaiDichVu);
        } else {
            // Nếu có nhiều bản ghi, chọn bản ghi đầu tiên
            loaiDichVu = loaiDichVus.get(0);
        }

        // Cập nhật thông tin dịch vụ chi tiết
        dichVuChiTiet.setTenDichVu(dto.getTenDichVu());
        dichVuChiTiet.setChungLoai(dto.getChungLoai());
        dichVuChiTiet.setHangCan(dto.getHangCan());
        dichVuChiTiet.setGia(dto.getGia());
        dichVuChiTiet.setNoiDung(dto.getNoiDung());
        dichVuChiTiet.setLoaiDichVu(loaiDichVu);

        // Lưu lại dịch vụ chi tiết và trả về kết quả
        return ResponseEntity.ok(dichVuChiTietRepository.save(dichVuChiTiet));
    }



    // Xóa dịch vụ (đánh dấu xóa thay vì xóa khỏi DB)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDVCT(@PathVariable Long id) {
        return dichVuChiTietRepository.findById(id).map(kh -> {
            kh.setXoa(1);
            dichVuChiTietRepository.save(kh);
            return ResponseEntity.ok(Map.of("message", "Xóa dịch vụ thành công")); // Trả về JSON
        }).orElseGet(() -> ResponseEntity.badRequest().body(Map.of("message", "Không tìm thấy dịch vụ")));
    }



    @GetMapping("/loai-dich-vu")
    public List<String> getLoaiDichVuNames() {
        return loaiDichVuRepository.findAll()
                .stream()
                .filter(loaiDichVu -> loaiDichVu.getXoa() == 0)
                .map(LoaiDichVu::getTenLoaiDichVu)
                .collect(Collectors.toList());
    }
}
