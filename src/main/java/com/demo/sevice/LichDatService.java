package com.demo.sevice;

import com.demo.dto.LichDatRequest;
import com.demo.entity.DichVuChiTiet;
import com.demo.entity.KhachHang;
import com.demo.entity.LichDat;
import com.demo.entity.ThuCung;
import com.demo.repo.DichVuChiTietRepository;
import com.demo.repo.KhachHangRepository;
import com.demo.repo.LichDatRepository;
import com.demo.repo.ThuCungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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




    public Page<LichDatRequest> getLichDatList(int page, int size, String search, int xoa, LocalDate ngayDat) {
        Pageable pageable = PageRequest.of(page, size);
        if (search != null && !search.isEmpty()) {
            return lichDatRepository.findByKhachHangTenContainingOrKhachHangSoDienThoaiContainingAndXoaAndNgayDat(
                    search, xoa, ngayDat, pageable);
        }

        return lichDatRepository.findAllByXoaAndNgayDat(xoa, ngayDat, pageable);
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


}
