package com.demo.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dich_vu_chi_tiet")
public class DichVuChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dich_vu_chi_tiet_id")
    private Long id;

    @Column(name = "ten_dich_vu", nullable = false, length = 255)
    private String tenDichVu;

    @Column(name = "gia", nullable = false, precision = 18, scale = 2)
    private BigDecimal gia;

    @Column(name = "noi_dung", nullable = false, length = 500)
    private String noiDung;

    @Column(name = "xoa", columnDefinition = "INT DEFAULT 0")
    private Integer xoa;

    @Column(name = "chung_loai")
    private String chungLoai;

    @Column(name = "hang_can")
    private Integer hangCan;
    @ManyToOne
    @JoinColumn(name = "loai_dich_vu_id")
    private LoaiDichVu loaiDichVu;
}
