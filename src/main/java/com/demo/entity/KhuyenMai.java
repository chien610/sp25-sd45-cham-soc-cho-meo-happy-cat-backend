package com.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "khuyen_mai")
public class KhuyenMai {
    @Id
    @Column(name = "makm", unique = true, nullable = false)
    private String makm;

    private String moTa;
    private float phanTramGiam;
    @Column(columnDefinition = "DATE")
    private LocalDate ngayBatDau;

    @Column(columnDefinition = "DATE")
    private LocalDate ngayKetThuc;

    private String dieuKien;

    private int xoa;
}
