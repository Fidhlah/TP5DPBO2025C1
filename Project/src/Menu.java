import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Menu extends JFrame {
    public static void main(String[] args) {
        Menu window = new Menu();                                   // buat object window
        window.setSize(600, 560);                      // atur ukuran window
        window.setLocationRelativeTo(null);                         // letakkan window di tengah layar
        window.setContentPane(window.mainPanel);                    // isi window
        window.getContentPane().setBackground(Color.WHITE);         // ubah warna background
        window.setVisible(true);                                    // tampilkan window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      // agar program ikut berhenti saat window diclose
    }

    private int selectedIndex = -1;
    private Database database;
    private JPanel mainPanel;
    private JTextField nimField;
    private JTextField namaField;
    private JTable mahasiswaTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox jenisKelaminComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel nimLabel;
    private JLabel namaLabel;
    private JLabel jenisKelaminLabel;
    private JComboBox transportasiComboBox;

    public Menu() {
        database = new Database();
        mahasiswaTable.setModel(setTable());
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        jenisKelaminComboBox.setModel(new DefaultComboBoxModel(new String[]{"", "Laki-laki", "Perempuan"}));
        transportasiComboBox.setModel(new DefaultComboBoxModel(new String[]{"", "Jalan Kaki", "Kendaraan Pribadi", "Kendaraan Umum"}));
        deleteButton.setVisible(false);

        addUpdateButton.addActionListener(e -> {
            if (selectedIndex == -1) {
                insertData();
            } else {
                updateData();
            }
        });

        deleteButton.addActionListener(e -> {
            if (selectedIndex >= 0) {
                deleteData();
            }
        });

//        cancelButton.addActionListener(e -> clearForm());
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });


        mahasiswaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedIndex = mahasiswaTable.getSelectedRow();
                
                nimField.setText(mahasiswaTable.getValueAt(selectedIndex, 1).toString());
                namaField.setText(mahasiswaTable.getValueAt(selectedIndex, 2).toString());
                jenisKelaminComboBox.setSelectedItem(mahasiswaTable.getValueAt(selectedIndex, 3).toString());
                transportasiComboBox.setSelectedItem(mahasiswaTable.getValueAt(selectedIndex, 4).toString());
                addUpdateButton.setText("Update");
                deleteButton.setVisible(true);
            }
        });
    }

    public final DefaultTableModel setTable() {
        Object[] kolom = {"No.", "NIM", "Nama", "Jenis Kelamin", "Transportasi"};
        DefaultTableModel temp = new DefaultTableModel(null, kolom);

        try {
            ResultSet resultSet = database.selectQuery("SELECT * FROM mahasiswa");

            int i=0;
            while (resultSet.next()){
                Object[] row = new Object[5];
                row[0] = i + 1;
                row[1] = resultSet.getString("nim");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getString("jenis_kelamin");
                row[4] = resultSet.getString("transportasi");


                temp.addRow(row);
                i++;
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return temp;
    }

    public void insertData() {
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String transportasi = transportasiComboBox.getSelectedItem().toString();

        // Validasi: Pastikan semua kolom terisi
        if (nim.isEmpty() || nama.isEmpty() || jenisKelamin.isEmpty() || transportasi.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua kolom harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validasi: Cek apakah NIM sudah ada di database
        String checkQuery = "SELECT COUNT(*) FROM mahasiswa WHERE nim = '" + nim + "'";
        try {
            ResultSet resultSet = database.selectQuery(checkQuery);
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "NIM sudah terdaftar, gunakan NIM lain!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat mengecek NIM!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        String sql = "INSERT INTO mahasiswa (nim, nama, jenis_kelamin, transportasi) VALUES ('" + nim + "', '" + nama + "', '" + jenisKelamin + "', '" + transportasi + "')";
        database.insertUpdateDeleteQuery(sql);

        mahasiswaTable.setModel(setTable());

        clearForm();

        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan!");
    }

    public void updateData() {
        String nimBaru = nimField.getText().trim();
        String nama = namaField.getText().trim();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String transportasi = transportasiComboBox.getSelectedItem().toString();

        // Validasi: Pastikan semua kolom terisi
        if (nimBaru.isEmpty() || nama.isEmpty() || jenisKelamin.isEmpty() || transportasi.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua kolom harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Ambil NIM lama dari baris yang dipilih di tabel
            String nimLama = mahasiswaTable.getValueAt(selectedIndex, 1).toString();

            // Jika NIM baru berbeda dari NIM lama, lakukan pengecekan
            if (!nimBaru.equals(nimLama)) {
                // Cek apakah NIM baru sudah ada di database
                String checkQuery = "SELECT COUNT(*) FROM mahasiswa WHERE nim = '" + nimBaru + "'";
                ResultSet resultSet = database.selectQuery(checkQuery);
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(null, "NIM sudah terdaftar, gunakan NIM lain!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Update data di database
            String sql = "UPDATE mahasiswa SET nim = '" + nimBaru + "', nama = '" + nama +
                    "', jenis_kelamin = '" + jenisKelamin + "', transportasi = '" + transportasi +
                    "' WHERE nim = '" + nimLama + "'";
            database.insertUpdateDeleteQuery(sql);

            mahasiswaTable.setModel(setTable());
            clearForm();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat mengupdate data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void deleteData() {
        int result = JOptionPane.showConfirmDialog(null, "Hapus data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            String nim = nimField.getText();

            String sql = "DELETE FROM mahasiswa WHERE nim = '" + nim + "'";
            database.insertUpdateDeleteQuery(sql);

            mahasiswaTable.setModel(setTable());

            clearForm();

            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
        }
    }

    public void clearForm() {
        nimField.setText("");
        namaField.setText("");
        jenisKelaminComboBox.setSelectedIndex(0);
        transportasiComboBox.setSelectedIndex(0);
        addUpdateButton.setText("Add");
        deleteButton.setVisible(false);
        selectedIndex = -1;
    }
}
