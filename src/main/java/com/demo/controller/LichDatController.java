package com.demo.controller;

import com.demo.dto.LichDatRequest;
import com.demo.dto.XacNhanLichDatRequest;
import com.demo.entity.CaLam;
import com.demo.entity.DichVuChiTiet;
import com.demo.entity.LichDat;
import com.demo.entity.NhanVien;
import com.demo.sevice.LichDatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/lich-dat")
@RequiredArgsConstructor
public class LichDatController {
    @Autowired
    private LichDatService lichDatService;


    @GetMapping("/list")
    public Page<LichDatRequest> getLichDat(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayDat) {

        return lichDatService.getLichDatList(page, size, search, ngayDat);
    }


    @PostMapping("/them-moi")
    public ResponseEntity<String> themMoiLichDat(@RequestBody LichDatRequest request) {
        lichDatService.themMoiLichDat(request);
        return ResponseEntity.ok("Đặt lịch thành công!");
    }

    @PutMapping("/sua/{id}")
    public ResponseEntity<String> suaLichDat(@PathVariable Long id, @RequestBody LichDatRequest request) {
        lichDatService.suaLichDat(id, request);
        return ResponseEntity.ok("Cập nhật lịch đặt thành công!");
    }

    // API lấy danh sách tên dịch vụ
    @GetMapping("/danh-sach-ten-dich-vu")
    public ResponseEntity<List<String>> layDanhSachTenDichVu() {
        return ResponseEntity.ok(lichDatService.layDanhSachTenDichVu());
    }
    @GetMapping("/tim-id-va-gia")
    public ResponseEntity<?> timIdVaGia(
            @RequestParam String tenDichVu,
            @RequestParam String chungLoai,
            @RequestParam Integer hangCan
    ) {
        DichVuChiTiet dichVu = lichDatService.timTheoThongTin(tenDichVu, chungLoai, hangCan);

        if (dichVu != null) {
            return ResponseEntity.ok(Map.of(
                    "id", dichVu.getId(),
                    "gia", dichVu.getGia()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy dịch vụ");
        }
    }

    @PutMapping("/cap-nhat-xoa-lich/{id}")
    public ResponseEntity<String> capNhatXoaLich(@PathVariable Long id, @RequestParam Integer xoaLich) {
        lichDatService.capNhatXoaLich(id, xoaLich);
        return ResponseEntity.ok("Cập nhật xóa lịch thành công!");
    }

    @GetMapping("/chua-phan-cong")
    public ResponseEntity<Page<LichDatRequest>> layLichDatChuaPhanCong(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String soDienThoai,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayDat) {

        Page<LichDatRequest> danhSach = lichDatService.layLichDatChuaPhanCong(page, size, soDienThoai, ngayDat);
        return ResponseEntity.ok(danhSach);
    }

    @GetMapping("/nhan-vien")
    public ResponseEntity<List<NhanVien>> laydsnhanvien() {
        List<NhanVien> nhanViens = lichDatService.laydsNhanVien();
        return ResponseEntity.ok(nhanViens);
    }
    @GetMapping("/ca-lam")
    public ResponseEntity<List<CaLam>> laydscalam() {
        List<CaLam> caLams = lichDatService.laydsCaLam();
        return ResponseEntity.ok(caLams);
    }

    // Xác nhận lịch đặt cho nhân viên
    @PostMapping("/xac-nhan")
    public ResponseEntity<String> xacNhanLichDat(@RequestBody XacNhanLichDatRequest request) {
        return lichDatService.xacNhanLichDat(
                request.getLichDatId(),
                request.getNhanVienId(),
                request.getCaLamIds(), // Chuyển từ 1 ca làm sang danh sách ca làm
                request.getNgayDat()
        );
    }


}
