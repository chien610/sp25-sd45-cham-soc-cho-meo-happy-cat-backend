package com.demo.controller;

import com.demo.entity.CaLam;
import com.demo.entity.NhanVien;
import com.demo.entity.PhanCong;
import com.demo.repo.CaLamRepository;
import com.demo.repo.NhanVienRepository;
import com.demo.repo.PhanCongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/phan-cong")
public class PhanCongController {

    @Autowired
    CaLamRepository caLamRepository;
    @Autowired
    NhanVienRepository nhanVienRepository;
    @Autowired
    PhanCongRepository phanCongRepository;


    @GetMapping("/lich-lam-viec")
    public ResponseEntity<Map<String, Object>> layLichLamViec(@RequestParam String ngay) {
        LocalDate ngayDat = LocalDate.parse(ngay);

        // Lấy danh sách ca làm và nhân viên
        List<CaLam> danhSachCaLam = caLamRepository.findAll();
        List<NhanVien> danhSachNhanVien = nhanVienRepository.findAll();

        // Lấy danh sách phân công theo ngày
        List<PhanCong> danhSachPhanCong = phanCongRepository.findByNgayDat(ngayDat);

        // Tạo ma trận phân công
        Map<Long, Map<Long, Boolean>> phanCongMatrix = new HashMap<>();
        for (CaLam ca : danhSachCaLam) {
            phanCongMatrix.put(ca.getCaLamId(), new HashMap<>());
            for (NhanVien nv : danhSachNhanVien) {
                boolean daPhanCong = danhSachPhanCong.stream()
                        .anyMatch(pc -> pc.getCaLam().getCaLamId().equals(ca.getCaLamId()) &&
                                pc.getNhanVien().getId().equals(nv.getId()));
                phanCongMatrix.get(ca.getCaLamId()).put(nv.getId().longValue(), daPhanCong);
            }
        }

        // Trả về kết quả
        Map<String, Object> ketQua = new HashMap<>();
        ketQua.put("ca_lam", danhSachCaLam);
        ketQua.put("nhan_vien", danhSachNhanVien);
        ketQua.put("phan_cong", phanCongMatrix);
        return ResponseEntity.ok(ketQua);
    }
}
