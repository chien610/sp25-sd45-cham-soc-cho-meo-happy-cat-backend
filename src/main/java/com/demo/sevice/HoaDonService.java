package com.demo.sevice;

import com.demo.dto.HoaDonDTO;
import com.demo.entity.HoaDon;
import com.demo.repo.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoaDonService {
@Autowired
HoaDonRepository hoaDonRepository;
    public Page<HoaDonDTO> getHoaDonList(int page, int size, String soDienThoai, String phuongThucThanhToan) {
        Pageable pageable = PageRequest.of(page, size);
        return hoaDonRepository.searchHoaDon(soDienThoai, phuongThucThanhToan, pageable);
    }

}
