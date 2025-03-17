package com.demo.sevice;

import com.demo.dto.LichDatRequest;
import com.demo.entity.*;
import com.demo.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LichDatService {

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private ThuCungRepository thuCungRepository;

    @Autowired
    private DichVuChiTietRepository dichVuChiTietRepository;

    @Autowired
    private LichDatRepository lichDatRepository;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private CaLamRepository caLamRepository;

    @Autowired
    private PhanCongRepository phanCongRepository;
    @Autowired
    private EmailService emailService;

    @Transactional
    public void themMoiLichDat(LichDatRequest request) {
        // Kiểm tra khách hàng đã tồn tại hay chưa
        KhachHang khachHang = khachHangRepository.findBySoDienThoai(request.getSoDienThoai())
                .orElseGet(() -> {
                    KhachHang newKhachHang = new KhachHang();
                    newKhachHang.setTenKhachHang(request.getTenKhachHang());
                    newKhachHang.setSoDienThoai(request.getSoDienThoai());
                    newKhachHang.setEmail(request.getEmail());
                    return khachHangRepository.save(newKhachHang);
                });

        // Tạo mới thú cưng
        ThuCung thuCung = new ThuCung();
        thuCung.setTenThuCung(request.getTenThuCung());

        thuCung.setKhachHang(khachHang);
        thuCung = thuCungRepository.save(thuCung);

        DichVuChiTiet dichVuChiTiet = dichVuChiTietRepository
                .findByTenDichVuAndChungLoaiAndHangCan(request.getTenDichVu(), request.getChungLoai(), request.getHangCan())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy dịch vụ chi tiết phù hợp"));


        // Tạo mới lịch đặt
        LichDat lichDat = new LichDat();
        lichDat.setNgayDat(request.getNgayDat());
        lichDat.setGioDat(request.getGioDat());
        lichDat.setKhachHang(khachHang);
        lichDat.setThuCung(thuCung);
        lichDat.setDichVuChiTiet(dichVuChiTiet);
        lichDat.setXoaLich(request.getXoaLich());
        lichDatRepository.save(lichDat);
    }

    // Lấy danh sách tên dịch vụ
    public List<String> layDanhSachTenDichVu() {
        return dichVuChiTietRepository.findAll()
                .stream()
                .map(DichVuChiTiet::getTenDichVu)
                .distinct() // Loại bỏ tên trùng lặp
                .collect(Collectors.toList());
    }







    @Transactional
    public void suaLichDat(Long id, LichDatRequest request) {
        // Kiểm tra lịch đặt có tồn tại hay không
        LichDat lichDat = lichDatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch đặt với ID: " + id));

        // Kiểm tra khách hàng theo số điện thoại
        KhachHang khachHang = khachHangRepository.findBySoDienThoai(request.getSoDienThoai())
                .orElseGet(() -> {
                    // Nếu không tồn tại, tạo khách hàng mới
                    KhachHang newKhachHang = new KhachHang();
                    newKhachHang.setTenKhachHang(request.getTenKhachHang());
                    newKhachHang.setSoDienThoai(request.getSoDienThoai());
                    newKhachHang.setEmail(request.getEmail());
                    return khachHangRepository.save(newKhachHang);
                });

        // Nếu khách hàng đã tồn tại, cập nhật tên và email nếu thay đổi
        if (khachHang.getKhachHangId() != null) {
            boolean isUpdated = false;

            if (!khachHang.getTenKhachHang().equals(request.getTenKhachHang())) {
                khachHang.setTenKhachHang(request.getTenKhachHang());
                isUpdated = true;
            }

            if (!khachHang.getEmail().equals(request.getEmail())) {
                khachHang.setEmail(request.getEmail());
                isUpdated = true;
            }

            if (isUpdated) {
                khachHangRepository.save(khachHang);
            }
        }

        // Gán khách hàng mới hoặc đã cập nhật vào lịch đặt
        lichDat.setKhachHang(khachHang);

        // Cập nhật thông tin thú cưng nếu có thay đổi
        ThuCung thuCung = lichDat.getThuCung();
        if (!thuCung.getTenThuCung().equals(request.getTenThuCung())) {
            thuCung.setTenThuCung(request.getTenThuCung());
            thuCungRepository.save(thuCung);
        }

        // Cập nhật dịch vụ nếu có thay đổi
        DichVuChiTiet dichVuChiTiet = dichVuChiTietRepository
                .findByTenDichVuAndChungLoaiAndHangCan(request.getTenDichVu(), request.getChungLoai(), request.getHangCan())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy dịch vụ chi tiết phù hợp"));

        if (!lichDat.getDichVuChiTiet().equals(dichVuChiTiet)) {
            lichDat.setDichVuChiTiet(dichVuChiTiet);
        }

        // Cập nhật thông tin lịch đặt
        lichDat.setNgayDat(request.getNgayDat());
        lichDat.setGioDat(request.getGioDat());
        lichDat.setXoaLich(request.getXoaLich());

        // Lưu lại thay đổi
        lichDatRepository.save(lichDat);
    }

    public DichVuChiTiet timTheoThongTin(String tenDichVu, String chungLoai, Integer hangCan) {
        return dichVuChiTietRepository.findOneByTenDichVuAndChungLoaiAndHangCan(tenDichVu, chungLoai, hangCan);
    }

    @Transactional
    public void capNhatXoaLich(Long id, Integer xoaLich) {
        // Tìm lịch đặt theo ID
        LichDat lichDat = lichDatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch đặt với ID: " + id));

        // Cập nhật giá trị xoaLich
        lichDat.setXoaLich(xoaLich);

        // Lưu thay đổi
        lichDatRepository.save(lichDat);
    }


    public Page<LichDatRequest> layLichDatChuaPhanCong(int page, int size, String soDienThoai, LocalDate ngayDat) {
        Pageable pageable = PageRequest.of(page, size);
        return lichDatRepository.findLichDatChuaPhanCong(
                soDienThoai != null ? soDienThoai.trim() : null,
                ngayDat,
                pageable
        );
    }


    public List<NhanVien> laydsNhanVien() {
        return nhanVienRepository.findAll();
    }
    public List<CaLam> laydsCaLam() {
        return caLamRepository.findAll();
    }
    @Transactional
    public ResponseEntity<String> xacNhanLichDat(Long lichDatId, Integer nhanVienId, List<Long> caLamIds, LocalDate ngayDat) {
        try {
            LichDat lichDat = lichDatRepository.findById(lichDatId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch đặt với ID: " + lichDatId));

            NhanVien nhanVien = nhanVienRepository.findById(nhanVienId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên với ID: " + nhanVienId));

            // Lưu danh sách các ca làm
            List<PhanCong> danhSachPhanCong = new ArrayList<>();
            for (Long caLamId : caLamIds) {
                CaLam caLam = caLamRepository.findById(caLamId)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy ca làm với ID: " + caLamId));

                // Kiểm tra nhân viên có bị trùng lịch không
                int count = phanCongRepository.countPhanCongTrung(nhanVienId, caLamId, ngayDat);
                if (count > 0) {
                    return ResponseEntity.badRequest().body("Nhân viên đã có lịch trong ca " + caLam.getTenCa());
                }

                PhanCong phanCong = new PhanCong();
                phanCong.setLichDat(lichDat);
                phanCong.setNhanVien(nhanVien);
                phanCong.setCaLam(caLam);
                phanCong.setNgayDat(ngayDat);
                danhSachPhanCong.add(phanCong);
            }

            // Lưu tất cả phân công vào DB
            phanCongRepository.saveAll(danhSachPhanCong);

            // Cập nhật nhân viên cho lịch đặt
            lichDat.setNhanVien(nhanVien);
            lichDatRepository.save(lichDat);

            // Lấy email khách hàng từ lịch đặt
            String emailKhachHang = lichDat.getKhachHang().getEmail();

            // Tạo nội dung email
            StringBuilder caLamStr = new StringBuilder();
            for (Long caLamId : caLamIds) {
                CaLam caLam = caLamRepository.findById(caLamId).orElse(null);
                if (caLam != null) {
                    caLamStr.append("⏳ ").append(caLam.getTenCa()).append("\n");
                }
            }

            String subject = "Xác nhận lịch đặt thành công!";
            String body = "Chào " + lichDat.getKhachHang().getTenKhachHang() + ",\n\n"
                    + "Lịch đặt của bạn đã được xác nhận thành công.\n"
                    + "📅 Ngày: " + ngayDat + "\n"
                    + caLamStr
                    + "Dịch vụ: " + lichDat.getDichVuChiTiet().getTenDichVu() + "\n"
                    + "👨‍💼 Nhân viên: " + nhanVien.getTenNhanVien() + "\n"
                    + "Cảm ơn bạn đã sử dụng dịch vụ!";

            emailService.sendEmail(emailKhachHang, subject, body);

            return ResponseEntity.ok("Xác nhận lịch đặt thành công!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi server: " + e.getMessage());
        }
    }


    public Page<LichDatRequest> getLichDatList(int page, int size, String search, LocalDate ngayDat) {
        Pageable pageable = PageRequest.of(page, size);

        return lichDatRepository.findLichDatBySearchAndNgayDatAndNhanVienIdNotNull(search != null ? search.trim() : null, ngayDat, pageable);
    }


}
