package com.demo.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DichVuChiTietDTO {
    private Integer id;
    private String chungLoai;
    private Integer hangCan;
    private String tenDichVu;
    private String canNang;
    private BigDecimal gia;
    private String noiDung;
    private String tenLoaiDichVu;

}
