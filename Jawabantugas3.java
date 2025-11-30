/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.jawabantugas3;

import java.io.*;
import java.util.*;

// =====================
// ABSTRACT & INHERITANCE
// =====================
abstract class Item {
    protected String nama;
    protected int harga;

    public Item(String nama, int harga) {
        this.nama = nama;
        this.harga = harga;
    }

    public String getNama() { return nama; }
    public int getHarga() { return harga; }

    public abstract String getKategori(); // POLYMORPHISM
}

class Makanan extends Item {
    public Makanan(String nama, int harga) {
        super(nama, harga);
    }

    @Override
    public String getKategori() {
        return "Makanan";
    }
}

class Minuman extends Item {
    public Minuman(String nama, int harga) {
        super(nama, harga);
    }

    @Override
    public String getKategori() {
        return "Minuman";
    }
}


public class Jawabantugas3 {

    static Scanner input = new Scanner(System.in);
    static ArrayList<Item> daftarMenu = new ArrayList<>();

    public static void main(String[] args) {

        bacaFile();  // load file jika ada

        int pilihan;

        do {
            System.out.println("\n=== PROGRAM RESTORAN (Tugas 3) ===");
            System.out.println("1. Lihat Menu");
            System.out.println("2. Tambah Menu");
            System.out.println("3. Pesan");
            System.out.println("4. Simpan Menu ke File");
            System.out.println("5. Keluar");
            System.out.print("Pilih: ");

            try {
                pilihan = Integer.parseInt(input.nextLine());
            } catch (Exception e) {
                System.out.println("Input harus angka!");
                continue;
            }

            switch (pilihan) {
                case 1 -> tampilkanMenu();
                case 2 -> tambahMenu();
                case 3 -> pesan();
                case 4 -> simpanFile();
                case 5 -> {
                    System.out.println("Keluar...");
                    return;
                }
                default -> System.out.println("Pilihan tidak valid!");
            }

        } while (true);
    }

    static void tampilkanMenu() {
        System.out.println("\n--- Daftar Menu ---");
        if (daftarMenu.isEmpty()) {
            System.out.println("Belum ada menu.");
        }
        for (int i = 0; i < daftarMenu.size(); i++) {
            Item m = daftarMenu.get(i);
            System.out.println((i + 1) + ". " + m.getNama() + " - Rp" + m.getHarga()
                    + " [" + m.getKategori() + "]");
        }
    }

    static void tambahMenu() {
        System.out.print("Nama Menu: ");
        String nama = input.nextLine();

        System.out.print("Harga: ");
        int harga;

        try {
            harga = Integer.parseInt(input.nextLine());
        } catch (Exception e) {
            System.out.println("Harga harus angka!");
            return;
        }

        System.out.print("Kategori (1=Makanan, 2=Minuman): ");
        int k = Integer.parseInt(input.nextLine());

        if (k == 1) daftarMenu.add(new Makanan(nama, harga));
        else if (k == 2) daftarMenu.add(new Minuman(nama, harga));
        else {
            System.out.println("Kategori tidak valid!");
            return;
        }

        System.out.println("Menu berhasil ditambahkan!");
    }

    static void pesan() {
        tampilkanMenu();
        if (daftarMenu.isEmpty()) return;

        System.out.print("Pilih menu: ");
        int pilih = Integer.parseInt(input.nextLine());

        if (pilih < 1 || pilih > daftarMenu.size()) {
            System.out.println("Pilihan invalid!");
            return;
        }

        Item m = daftarMenu.get(pilih - 1);

        System.out.println("Anda memesan: " + m.getNama());
        System.out.print("Jumlah: ");
        int jumlah = Integer.parseInt(input.nextLine());

        int total = jumlah * m.getHarga();
        System.out.println("Total harga: Rp" + total);
    }

    static void simpanFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("menu.txt"))) {
            for (Item m : daftarMenu) {
                bw.write(m.getNama() + ";" + m.getHarga() + ";" + m.getKategori());
                bw.newLine();
            }
            System.out.println("Menu berhasil disimpan ke file!");
        } catch (IOException e) {
            System.out.println("Terjadi error saat menyimpan file.");
        }
    }

    static void bacaFile() {
        File file = new File("menu.txt");
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");

                String nama = data[0];
                int harga = Integer.parseInt(data[1]);
                String kategori = data[2];

                if (kategori.equals("Makanan"))
                    daftarMenu.add(new Makanan(nama, harga));
                else
                    daftarMenu.add(new Minuman(nama, harga));
            }

            System.out.println("Menu berhasil dimuat dari file!");
        } catch (Exception e) {
            System.out.println("File rusak atau format tidak sesuai.");
        }
    }
}
