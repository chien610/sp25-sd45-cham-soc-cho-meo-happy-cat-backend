package com.demo.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoaDonDTO {
    private Integer hoaDonId;
    private LocalDateTime ngayLap;
    private BigDecimal tongTien;
    private String phuongThucThanhToan;
    private String soDienThoai;
    private Integer nhanVienId;
    private String maKhuyenMai;
    private Float phanTramGiam;
}
