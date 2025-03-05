package com.demo.sevice;

import com.demo.dto.HoaDonDTO;
import com.demo.repo.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class HoaDonService {
@Autowired
HoaDonRepository hoaDonRepository;
    public Page<HoaDonDTO> getHoaDonList(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);
        if (search != null && !search.isEmpty()) {
            return hoaDonRepository.findBySoDienThoaiContaining(search, pageable);
        }
        return hoaDonRepository.findAll(pageable).map(hoaDon ->
                new HoaDonDTO(
                        hoaDon.getId(),
                        hoaDon.getNgayLap(),
                        hoaDon.getTongTien(),
                        hoaDon.getPhuongThucThanhToan(),
                        hoaDon.getKhachHang().getSoDienThoai(),
                        hoaDon.getNhanVien().getId(),
                        hoaDon.getKhuyenMai() != null ? hoaDon.getKhuyenMai().getMaKM() : null,
                        hoaDon.getKhuyenMai() != null ? hoaDon.getKhuyenMai().getPhanTramGiam() : null
                )
        );
    }
}
