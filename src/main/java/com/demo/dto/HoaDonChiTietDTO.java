package com.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoaDonChiTietDTO {
    private Integer hoaDonChiTietId;
    private Integer hoaDonId;
    private Long dichVuChiTietId;
    private String tenDichVu;
    private Integer soLuong;
    private BigDecimal donGia;
    private BigDecimal thanhTien;

}
