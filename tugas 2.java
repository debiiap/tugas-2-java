/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ut;

/**
 *
 * @author AVC
 */
import java.util.Scanner;

class Menu {
    String nama;
    int harga;
    String kategori;

    Menu(String nama, int harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }
}

public class Ut {

    static Scanner input = new Scanner(System.in);

    static Menu[] daftarMenu = new Menu[20];
    static int jumlahMenu = 8;

    static String[] pesananNama = new String[50];
    static int[] pesananJumlah = new int[50];
    static int jumlahPesanan = 0;

    public static void main(String[] args) {

        // Data awal minimal 4 makanan + 4 minuman
        daftarMenu[0] = new Menu("Nasi Goreng", 20000, "Makanan");
        daftarMenu[1] = new Menu("Ayam Geprek", 25000, "Makanan");
        daftarMenu[2] = new Menu("Mie Ayam", 18000, "Makanan");
        daftarMenu[3] = new Menu("Sate Ayam", 22000, "Makanan");

        daftarMenu[4] = new Menu("Es Teh", 8000, "Minuman");
        daftarMenu[5] = new Menu("Jus Alpukat", 15000, "Minuman");
        daftarMenu[6] = new Menu("Es Jeruk", 9000, "Minuman");
        daftarMenu[7] = new Menu("Kopi Hitam", 12000, "Minuman");

        while (true) {
            System.out.println("\n===== MENU UTAMA =====");
            System.out.println("1. Menu Pelanggan");
            System.out.println("2. Menu Pemilik Restoran");
            System.out.println("3. Keluar");
            System.out.print("Pilih: ");

            int pilih = input.nextInt();

            switch (pilih) {
                case 1: menuPelanggan(); break;
                case 2: menuPemilik(); break;
                case 3: System.exit(0);
                default: System.out.println("Pilihan salah!");
            }
        }
    }

    // ========================= MENU PELANGGAN =========================
    static void menuPelanggan() {
        jumlahPesanan = 0;

        while (true) {
            System.out.println("\n===== MENU PELANGGAN =====");
            tampilkanMenu();

            System.out.print("Masukkan nama menu (atau ketik 'selesai'): ");
            input.nextLine();
            String nama = input.nextLine();

            if (nama.equalsIgnoreCase("selesai")) break;

            int index = cariMenu(nama);

            if (index == -1) {
                System.out.println("Menu tidak ditemukan!");
                continue;
            }

            System.out.print("Jumlah: ");
            int jumlah = input.nextInt();

            pesananNama[jumlahPesanan] = daftarMenu[index].nama;
            pesananJumlah[jumlahPesanan] = jumlah;
            jumlahPesanan++;

            System.out.println("Ditambahkan!");
        }

        cetakStruk();
    }

    // ========================= MENAMPILKAN MENU =========================
    static void tampilkanMenu() {
        System.out.println("\n--- MAKANAN ---");
        for (int i = 0; i < jumlahMenu; i++) {
            if (daftarMenu[i].kategori.equals("Makanan")) {
                System.out.println((i + 1) + ". " + daftarMenu[i].nama +
                        " - Rp " + daftarMenu[i].harga);
            }
        }

        System.out.println("\n--- MINUMAN ---");
        for (int i = 0; i < jumlahMenu; i++) {
            if (daftarMenu[i].kategori.equals("Minuman")) {
                System.out.println((i + 1) + ". " + daftarMenu[i].nama +
                        " - Rp " + daftarMenu[i].harga);
            }
        }
    }

    // ========================= CARI MENU =========================
    static int cariMenu(String nama) {
        for (int i = 0; i < jumlahMenu; i++) {
            if (daftarMenu[i].nama.equalsIgnoreCase(nama))
                return i;
        }
        return -1;
    }

    // ========================= CETAK STRUK =========================
    static void cetakStruk() {
        System.out.println("\n===== STRUK PEMBAYARAN =====");

        int subtotal = 0;
        int minumanCount = 0;

        for (int i = 0; i < jumlahPesanan; i++) {
            int idx = cariMenu(pesananNama[i]);
            int harga = daftarMenu[idx].harga;
            int totalItem = harga * pesananJumlah[i];

            System.out.println(pesananNama[i] + " x" + pesananJumlah[i] +
                    " - Rp " + totalItem);

            subtotal += totalItem;

            if (daftarMenu[idx].kategori.equals("Minuman")) {
                minumanCount += pesananJumlah[i];
            }
        }

        // Promo B1G1 minuman
        int diskonB1G1 = 0;
        if (subtotal > 50000 && minumanCount >= 2) {
            int hargaMinuman = cariHargaMinumanTermurah();
            diskonB1G1 = hargaMinuman;
            System.out.println("Promo Beli 1 Gratis 1 Minuman = -Rp " + hargaMinuman);
        }

        // Diskon 10%
        int diskon10 = 0;
        if (subtotal > 100000) {
            diskon10 = subtotal / 10;
            System.out.println("Diskon 10% = -Rp " + diskon10);
        }

        int setelahDiskon = subtotal - diskon10 - diskonB1G1;
        int pajak = setelahDiskon / 10;
        int service = 20000;
        int totalAkhir = setelahDiskon + pajak + service;

        System.out.println("---------------------------");
        System.out.println("Subtotal        : Rp " + subtotal);
        System.out.println("Setelah Diskon  : Rp " + setelahDiskon);
        System.out.println("Pajak 10%       : Rp " + pajak);
        System.out.println("Biaya Layanan   : Rp " + service);
        System.out.println("TOTAL AKHIR     : Rp " + totalAkhir);
        System.out.println("---------------------------");
        System.out.println("Terima kasih!");
    }

    static int cariHargaMinumanTermurah() {
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < jumlahMenu; i++) {
            if (daftarMenu[i].kategori.equals("Minuman")) {
                if (daftarMenu[i].harga < min) min = daftarMenu[i].harga;
            }
        }
        return min;
    }

    // ========================= MENU PEMILIK =========================
    static void menuPemilik() {
        while (true) {
            System.out.println("\n===== MENU PEMILIK =====");
            System.out.println("1. Tambah Menu");
            System.out.println("2. Ubah Harga Menu");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Kembali");
            System.out.print("Pilih: ");

            int p = input.nextInt();

            switch (p) {
                case 1: tambahMenu(); break;
                case 2: ubahHargaMenu(); break;
                case 3: hapusMenu(); break;
                case 4: return;
                default: System.out.println("Pilihan salah!");
            }
        }
    }

    // ========================= TAMBAH MENU =========================
    static void tambahMenu() {
        input.nextLine();
        System.out.print("Nama menu baru: ");
        String nama = input.nextLine();

        System.out.print("Harga: ");
        int harga = input.nextInt();
        input.nextLine();

        System.out.print("Kategori (Makanan/Minuman): ");
        String kategori = input.nextLine();

        daftarMenu[jumlahMenu] = new Menu(nama, harga, kategori);
        jumlahMenu++;

        System.out.println("Menu berhasil ditambahkan!");
    }

    // ========================= UBAH HARGA MENU =========================
    static void ubahHargaMenu() {
        tampilkanMenu();
        System.out.print("Masukkan nomor menu yang ingin diubah: ");
        int no = input.nextInt() - 1;

        if (no < 0 || no >= jumlahMenu) {
            System.out.println("Nomor salah!");
            return;
        }

        input.nextLine();
        System.out.print("Yakin ubah harga? (Ya/Tidak): ");
        String konf = input.nextLine();

        if (konf.equalsIgnoreCase("Ya")) {
            System.out.print("Harga baru: ");
            int hargaBaru = input.nextInt();

            daftarMenu[no].harga = hargaBaru;
            System.out.println("Harga berhasil diubah!");
        } else {
            System.out.println("Batal mengubah.");
        }
    }

    // ========================= HAPUS MENU =========================
    static void hapusMenu() {
        tampilkanMenu();

        System.out.print("Masukkan nomor menu yang ingin dihapus: ");
        int no = input.nextInt() - 1;

        if (no < 0 || no >= jumlahMenu) {
            System.out.println("Nomor salah!");
            return;
        }

        input.nextLine();
        System.out.print("Yakin hapus menu? (Ya/Tidak): ");
        String konf = input.nextLine();

        if (konf.equalsIgnoreCase("Ya")) {
            for (int i = no; i < jumlahMenu - 1; i++) {
                daftarMenu[i] = daftarMenu[i + 1];
            }
            jumlahMenu--;

            System.out.println("Menu berhasil dihapus!");
        } else {
            System.out.println("Batal menghapus.");
        }
    }
}
