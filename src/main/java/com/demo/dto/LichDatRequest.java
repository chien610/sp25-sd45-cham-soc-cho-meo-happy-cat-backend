package com.demo.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LichDatRequest {
    private String tenKhachHang;
    private String soDienThoai;
    private String email;
    private String tenThuCung;
    private LocalDate ngayDat;
    private String gioDat;
    private String tenDichVu;
    private Integer xoaLich;
    private String chungLoai;
    private Integer hangCan;
    private  Long lichDatId;
    private Long dichVuChiTietId;
    private BigDecimal gia;
}
