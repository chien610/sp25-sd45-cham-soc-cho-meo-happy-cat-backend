package com.demo.controller;

import com.demo.dto.HoaDonDTO;
import com.demo.entity.HoaDon;
import com.demo.sevice.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/hoa-don")
public class HoaDonController {
    @Autowired
    HoaDonService hoaDonService;

    @GetMapping("/list")
    public Page<HoaDonDTO> getHoaDonList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String soDienThoai,
            @RequestParam(required = false) String phuongThucThanhToan) {

        return hoaDonService.getHoaDonList(page, size, soDienThoai, phuongThucThanhToan);
    }
}
