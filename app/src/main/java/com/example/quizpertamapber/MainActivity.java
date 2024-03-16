package com.example.quizpertamapber;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText Nama, Kode, Jumlah;
    Button proses;
    RadioGroup radioGroup;
    RadioButton rbplatinum, rbpremium, rbvip;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Nama = findViewById(R.id.Konsumen);
        Kode = findViewById(R.id.TeksKode);
        Jumlah = findViewById(R.id.TeksJumlahBarang);
        proses = findViewById(R.id.TombolProses);
        rbplatinum = findViewById(R.id.rbPLATINUM);
        rbpremium = findViewById(R.id.rbPREMIUM);
        rbvip = findViewById(R.id.rbVIP);
        radioGroup = findViewById(R.id.radioGroup);

        proses.setOnClickListener(v -> {
            String namaPembeli = Nama.getText().toString().trim();
            String kodeBarang = Kode.getText().toString().trim();
            int jumlahBarang = Integer.parseInt(Jumlah.getText().toString().trim());

            if (kodeBarang.isEmpty()) {
                Toast.makeText(MainActivity.this, "Harap masukkan kode barang!", Toast.LENGTH_SHORT).show();
                return;
            }

            double hargaBarang = getHargaBarang(kodeBarang);
            if (hargaBarang == -1) {
                Toast.makeText(MainActivity.this, "Kode barang tidak valid!", Toast.LENGTH_SHORT).show();
                return;
            }


            String MerekBarang = getMerekBarang(kodeBarang);
            if (MerekBarang == null) {
                Toast.makeText(MainActivity.this, "Kode barang tidak valid!", Toast.LENGTH_SHORT).show();
                return;
            }
            double totalHarga = hargaBarang * jumlahBarang;
            double diskon = hitungDiskon(totalHarga);
            double totalBayar = totalHarga - diskon - hitungPotonganHarga(totalHarga);
            double potonganHarga = hitungPotonganHarga(totalHarga);
            tampilkanBon(namaPembeli, kodeBarang, MerekBarang, hargaBarang, jumlahBarang, totalHarga, potonganHarga, diskon, totalBayar);
        });
    }

    private double getHargaBarang(String kodeBarang) {
        switch (kodeBarang.toUpperCase()) {
            case "LV3":
                return 6666666;
            case "AA5":
                return 9999999;
            case "MP3":
                return 28999999;
            default:
                return -1;
                //LV3,AA5,MP3//
        }
    }
    private String getMerekBarang (String kodeBarang) {
        switch (kodeBarang.toUpperCase()) {
            case "LV3":
                return "Lenovo V14 Gen 3";
            case "AA5":
                return "Acer Aspire 5";
            case "MP3":
                return "MACBOOK PRO M3";
            default:
                return null;
            //LV3,AA5,MP3//
        }
    }



    private double hitungPotonganHarga(double totalHarga) {
        double potonganHarga = 0;
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId == R.id.rbPLATINUM) {
            potonganHarga = totalHarga * 0.1;
        } else if (selectedRadioButtonId == R.id.rbPREMIUM) {
            potonganHarga = totalHarga * 0.05;
        } else if (selectedRadioButtonId == R.id.rbVIP) {
            potonganHarga = totalHarga * 0.02;
        }
        return potonganHarga;
    }
    private double hitungDiskon(double totalHarga) {
        if (totalHarga > 10000000) {
            return 100000;
        }
        return 0;
    }

    private void tampilkanBon(String namaPembeli, String kodeBarang, String merekBarang, double hargaBarang, int jumlahBarang, double totalHarga, double potonganHarga, double diskon, double totalBayar) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        String hargabarang = "Rp " + decimalFormat.format(hargaBarang);
        String hargaJumlah = "Rp " + decimalFormat.format(jumlahBarang);
        String hargatotal = "Rp " + decimalFormat.format(totalHarga);
        String hargabayar = "Rp " + decimalFormat.format(totalBayar);
        String hargaPotongan = "Rp " + decimalFormat.format(potonganHarga);
        String Diskon = "Rp " + decimalFormat.format(diskon);

        // Buat pesan bon belanja dalam format teks
        String bonMessage = "Transaksi Hari Ini : " + "\n\n"
                + "Nama Pembeli: " + namaPembeli + "\n\n"
                + "Kode Barang: " + kodeBarang + "\n\n"
                + "Merek Barang: " + merekBarang + "\n\n"
                + "Harga Barang Satuan: " + hargabarang + "\n\n"
                + "Jumlah Barang Dibeli: " + hargaJumlah + "\n\n"
                + "Total Harga: " + hargatotal + "\n\n"
                + "Diskon MemberShip : " + hargaPotongan + "\n\n"
                + "Diskon Harga : " + Diskon + "\n\n"
                + "Total Pembayaran: " + hargabayar;

        Intent intent = new Intent(MainActivity.this, Teks_activity.class);
        // Sertakan data bon belanja ke dalam Intent
        intent.putExtra("bonMessage", bonMessage);
        // Mulai activity BonBelanjaActivity
        startActivity(intent);
    }


}
