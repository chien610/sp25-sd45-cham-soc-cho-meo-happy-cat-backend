package com.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Table(name = "phan_cong")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhanCong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phan_cong_id")
    private Long phanCongId;

    @Column(name = "ngay_dat")
    private LocalDate ngayDat;
    @ManyToOne
    @JoinColumn(name = "lich_dat_id", nullable = false)
    private LichDat lichDat;


    @ManyToOne
    @JoinColumn(name = "nhan_vien_id", nullable = false)
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "ca_lam_id", nullable = false)
    private CaLam caLam;
}