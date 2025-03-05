package com.demo.controller;

import com.demo.dto.HoaDonChiTietDTO;
import com.demo.repo.HoaDonChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/hoa-don-chi-tiet")
public class HoaDonChiTietController {
    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;

    @GetMapping("/{hoaDonId}")
    public List<HoaDonChiTietDTO> getHoaDonChiTiet(@PathVariable Integer hoaDonId) {
        return hoaDonChiTietRepository.findByHoaDonId(hoaDonId);
    }
}
