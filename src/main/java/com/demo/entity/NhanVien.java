package com.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "nhan_vien")
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nhan_vien_id")
    private Integer id;

    @Column(name = "ten_nhan_vien", nullable = false, length = 255)
    private String tenNhanVien;

    @Column(name = "sdt", nullable = false, length = 15)
    private String sdt;

    @Column(name = "dia_chi", nullable = false, length = 255)
    private String diaChi;

    @Column(name = "chuc_vu", length = 100)
    private String chucVu;

    @Column(name = "gioi_tinh", length = 10)
    private String gioiTinh;

    @Column(name = "email", unique = true, length = 255)
    private String email;

    @Column(name = "xoa", columnDefinition = "INT DEFAULT 0")
    private Integer xoa;
}
