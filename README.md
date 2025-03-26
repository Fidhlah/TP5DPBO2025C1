# Janji
Saya Muhammad Hafidh Fadhilah dengan NIM 2305672 mengerjakan Tugas Praktikum 5 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

# Struktur Program
Aplikasi ini terdiri dari beberapa file utama:
1. **Mahasiswa.java**  
   - Model class untuk merepresentasikan data mahasiswa.
   - Berisi atribut `nim`, `nama`, `jenisKelamin`, dan `transportasi`.
   - Memiliki getter dan setter untuk mengakses dan mengubah data.

2. **Database.java**  
   - Class untuk menghubungkan aplikasi dengan database MySQL.
   - Menyediakan metode untuk mengeksekusi query SELECT, INSERT, UPDATE, dan DELETE.

3. **Menu.java**  
   - Class utama dengan GUI berbasis `JFrame`.
   - Berisi form input, tabel untuk menampilkan data, dan tombol aksi seperti Tambah, Update, dan Hapus.
   - Menghubungkan tampilan dengan database menggunakan `Database.java`.
     
4. **Menu.form**
   ![Screenshot 2025-03-26 163531](https://github.com/user-attachments/assets/cfab25d4-3c43-4437-baac-26674d619ef5)

## Alur Program

### 1. Inisialisasi Program
- Program dijalankan melalui `Menu.java`.
- Window utama dibuat menggunakan `JFrame` dengan ukuran 600x560 px.
- Data mahasiswa dimuat dari database MySQL menggunakan `Database.java`.

### 2. Tampilan UI (User Interface)
Di dalam `JFrame`, terdapat beberapa komponen utama:
- **Tabel Mahasiswa (`JTable`)** → Menampilkan daftar mahasiswa yang tersimpan dalam database.
- **Input Form** → Berisi:
  - `JTextField nimField` → Input NIM mahasiswa.
  - `JTextField namaField` → Input nama mahasiswa.
  - `JComboBox jenisKelaminComboBox` → Pilih jenis kelamin.
  - `JComboBox transportasiComboBox` → Pilih transportasi ke kampus.
- **Tombol Aksi:**
  - **Add** / **Update** → Menambahkan atau mengubah data mahasiswa di database.
  - **Delete** → Menghapus data mahasiswa dari database.
  - **Cancel** → Mengosongkan form input.

### 3. Proses CRUD (Create, Read, Update, Delete)
#### a. Menambah Data Mahasiswa
1. Pengguna mengisi **NIM, Nama, Jenis Kelamin, dan Transportasi** di form input.
2. Klik tombol **"Add"**, data baru disimpan ke database menggunakan `Database.java`.
3. Tabel diperbarui agar data baru muncul.

#### b. Mengupdate Data Mahasiswa
1. Klik salah satu baris di tabel → Data akan muncul di form input.
2. Tombol **"Add"** berubah menjadi **"Update"**.
3. Pengguna mengubah data, lalu klik **"Update"**.
4. Data diperbarui di database dan tabel diperbarui.

#### c. Menghapus Data Mahasiswa
1. Klik salah satu baris di tabel.
2. Tombol **"Delete"** akan muncul.
3. Klik **"Delete"**, lalu konfirmasi penghapusan.
4. Data dihapus dari database dan tabel diperbarui.

### 4. Penyimpanan Data
- Saat program berjalan, data mahasiswa disimpan dalam database MySQL.
- Setiap perubahan data akan langsung diperbarui ke database, sehingga data tetap tersimpan meskipun program ditutup.

# Dokumentasi Output
https://github.com/user-attachments/assets/58e0a71a-4ea5-4832-8fe1-50495b374445



