package com.demo.entity;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "thu_cung")
public class ThuCung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thu_cung_id")
    private Long id;

    @Column(name = "ten_thu_cung", length = 255)
    private String tenThuCung;

    @Column(name = "loai_thu_cung", length = 255)
    private String loaiThuCung; // Chó/Mèo

    @Column(name = "tuoi")
    private Integer tuoi;

    @Column(name = "can_nang")
    private Float canNang;

    @Column(name = "gioi_tinh", length = 10)
    private String gioiTinh;

    @Column(name = "xoa", columnDefinition = "INT DEFAULT 0")
    private Integer xoa;

    @ManyToOne
    @JoinColumn(name = "khach_hang_id")
    private KhachHang khachHang;
}
