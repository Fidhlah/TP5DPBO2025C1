public class Mahasiswa {
    private String nim;
    private String nama;
    private String jenisKelamin;
    private String transportasi; // Tambahan atribut transportasi ke kampus

    public Mahasiswa(String nim, String nama, String jenisKelamin, String transportasi) {
        this.nim = nim;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.transportasi = transportasi;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public void setTransportasi(String transportasi) {
        this.transportasi = transportasi;
    }

    public String getNim() {
        return this.nim;
    }

    public String getNama() {
        return this.nama;
    }

    public String getJenisKelamin() {
        return this.jenisKelamin;
    }

    public String getTransportasi() {
        return this.transportasi;
    }
}
