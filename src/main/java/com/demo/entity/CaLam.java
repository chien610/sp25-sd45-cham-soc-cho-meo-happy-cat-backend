package com.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "ca_lam")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaLam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ca_lam_id")
    private Long caLamId;

    @Column(name = "ten_ca", nullable = false, length = 50)
    private String tenCa;

    @Column(name = "gio_bat_dau", nullable = false)
    private LocalTime gioBatDau;

    @Column(name = "gio_ket_thuc", nullable = false)
    private LocalTime gioKetThuc;

    @Column(name = "xoa", columnDefinition = "INT DEFAULT 0")
    private Integer xoa = 0;
}
