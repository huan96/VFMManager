package com.haui.huantd.vfmmanager;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by huand on 02/10/18.
 */

public class Product implements Serializable {
    private String id;
    private String tieuDe;
    private String idNguoiBan;
    private String gia;
    private String danhMuc;
    private String loaiSP;
    private String tinh;
    private String huyen;
    private String thoiGian;
    private String chiTiet;
    private String urlImage;
    private boolean isConfirm;
    public Product() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getIdNguoiBan() {
        return idNguoiBan;
    }

    public void setIdNguoiBan(String idNguoiBan) {
        this.idNguoiBan = idNguoiBan;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(String danhMuc) {
        this.danhMuc = danhMuc;
    }

    public String getLoaiSP() {
        return loaiSP;
    }

    public void setLoaiSP(String loaiSP) {
        this.loaiSP = loaiSP;
    }

    public String getTinh() {
        return tinh;
    }

    public void setTinh(String tinh) {
        this.tinh = tinh;
    }

    public String getHuyen() {
        return huyen;
    }

    public void setHuyen(String huyen) {
        this.huyen = huyen;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getChiTiet() {
        return chiTiet;
    }

    public void setChiTiet(String chiTiet) {
        this.chiTiet = chiTiet;
    }

    public Product(String id, String tieuDe, String idNguoiBan, String gia, String danhMuc,
                   String loaiSP, String tinh, String huyen, String thoiGian,
                   String chiTiet, String urlImage) {
        this.id = id;
        this.tieuDe = tieuDe;
        this.idNguoiBan = idNguoiBan;
        this.gia = gia;
        this.danhMuc = danhMuc;
        this.loaiSP = loaiSP;
        this.tinh = tinh;
        this.huyen = huyen;
        this.thoiGian = thoiGian;
        this.chiTiet = chiTiet;
        this.urlImage = urlImage;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    public boolean isConfirm() {
        return isConfirm;
    }

    public void setConfirm(boolean confirm) {
        isConfirm = confirm;
    }
}
