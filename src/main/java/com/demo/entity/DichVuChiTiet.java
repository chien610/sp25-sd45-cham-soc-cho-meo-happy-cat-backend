package com.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "dich_vu_chi_tiet")
public class DichVuChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dich_vu_chi_tiet_id")
    private Integer dichVuChiTietId;

    @Column(name = "ten_dich_vu")
    private String tenDichVu;

    @Column(name = "gia")
    private BigDecimal gia;

    @Column(name = "noi_dung")
    private String noiDung;

    @Column(name = "xoa")
    private Integer xoa = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loai_dich_vu_id")
    private LoaiDichVu loaiDichVu;

}
