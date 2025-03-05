package com.demo.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lich_dat")
public class LichDat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lich_dat_id")
    private Long lichDatId;

    @Column(name = "ngay_dat", nullable = false)
    private LocalDate ngayDat;

    @Column(name = "gio_dat", length = 50)
    private String gioDat;

    @Column(name = "xoa", columnDefinition = "INT DEFAULT 0")
    private Integer xoaLich;

    @ManyToOne
    @JoinColumn(name = "khach_hang_id")
    private KhachHang khachHang;


    @ManyToOne
    @JoinColumn(name = "dich_vu_chi_tiet_id")
    private DichVuChiTiet dichVuChiTiet;

    @ManyToOne
    @JoinColumn(name = "thu_cung_id")
    private ThuCung thuCung;
}
