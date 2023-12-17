package actionlistener;

import java.awt.event.*; // Mengimpor paket untuk menangani peristiwa dalam GUI
import java.io.*; // Mengimpor paket untuk operasi input/output
import java.util.List; // Mengimpor paket untuk menggunakan interface List
import javax.swing.*; // Mengimpor paket untuk membuat GUI
import javax.swing.filechooser.FileNameExtensionFilter; // Mengimpor paket untuk filter ekstensi file
import biodata.BiodataFrame; // Mengimpor kelas BiodataFrame dari paket biodata
import biodata.Biodata; // Mengimpor kelas Biodata dari paket biodata

// Kelas yang menangani aksi untuk menyimpan data ke file
public class SaveToFileActionListener implements ActionListener {
    private final BiodataFrame biodataFrame; // Frame Biodata untuk tindakan yang terkait dengan dialog
    private final List<Biodata> biodataList; // Daftar biodata yang akan disimpan ke file

    // Konstruktor untuk SaveToFileActionListener
    public SaveToFileActionListener(BiodataFrame biodataFrame, List<Biodata> biodataList) {
        this.biodataFrame = biodataFrame; // Menginisialisasi frame biodata
        this.biodataList = biodataList; // Menginisialisasi daftar biodata
    }

    // Metode yang dijalankan ketika aksi 'Simpan ke File' dipicu
    public void actionPerformed(ActionEvent e) {
        // Menampilkan dialog konfirmasi untuk menyimpan data ke file
        int confirmation = JOptionPane.showConfirmDialog(this.biodataFrame,
                "Apakah anda yakin ingin menyimpan data ke file?",
                "Form Biodata",
                JOptionPane.YES_NO_OPTION);

        // Jika pengguna menyetujui untuk menyimpan data
        if (confirmation == JOptionPane.YES_OPTION) {
            // Membuat objek file chooser
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Simpan Data ke File"); // Mengatur judul dialog

            // Menambahkan filter untuk jenis file teks dan CSV pada file chooser
            FileNameExtensionFilter textFilter = new FileNameExtensionFilter("File Teks (.txt)", "txt");
            FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("File CSV (.csv)", "csv");
            fileChooser.addChoosableFileFilter(textFilter);
            fileChooser.addChoosableFileFilter(csvFilter);
            fileChooser.setFileFilter(textFilter); // Mengatur filter default ke file teks

            // Menampilkan dialog untuk memilih lokasi penyimpanan file
            int userSelection = fileChooser.showSaveDialog(this.biodataFrame);

            // Jika lokasi penyimpanan file dipilih
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                try {
                    // Mendapatkan filter yang dipilih oleh pengguna
                    FileNameExtensionFilter selectedFilter = (FileNameExtensionFilter) fileChooser.getFileFilter();
                    String extension = selectedFilter.getExtensions()[0];
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();

                    // Memastikan ekstensi file sesuai dengan pilihan pengguna
                    if (!filePath.toLowerCase().endsWith("." + extension)) {
                        selectedFile = new File(filePath + "." + extension);
                    }

                    // Membuat writer untuk menulis ke file yang dipilih
                    FileWriter writer = new FileWriter(selectedFile);

                    // Menulis data biodata ke dalam file
                    for (Biodata biodata : biodataList) {
                        String data = String.format("%s,%s,%s,%s\n",
                                biodata.getNama(),
                                biodata.getNoTelepon(),
                                biodata.getJenisKelamin(),
                                biodata.getAlamat());
                        writer.write(data);
                    }

                    // Menutup writer
                    writer.close();

                    // Menampilkan pesan bahwa data berhasil disimpan ke file
                    JOptionPane.showMessageDialog(this.biodataFrame,
                            "Data berhasil disimpan ke file ",
                            "Perhatian",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    // Menampilkan error jika terjadi kesalahan saat menyimpan
                    ex.printStackTrace();
                }
            }
        }
    }
}