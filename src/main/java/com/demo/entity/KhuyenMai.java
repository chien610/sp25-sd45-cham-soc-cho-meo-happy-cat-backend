package com.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "khuyen_mai")
public class KhuyenMai {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "khuyen_mai_id")
    private Integer id;

    @Column(name = "makm", length = 50)
    private String maKM;

    @Column(name = "mo_ta", length = 255)
    private String moTa;

    @Column(name = "phan_tram_giam")
    private Float phanTramGiam;

    @Column(name = "ngay_bat_dau", nullable = false)
    private LocalDate ngayBatDau;

    @Column(name = "ngay_ket_thuc", nullable = false)
    private LocalDate ngayKetThuc;

    @Column(name = "dieu_kien", length = 255)
    private String dieuKien;

    @Column(name = "xoa", columnDefinition = "INT DEFAULT 0")
    private Integer xoa;
}

