**1. Topicul proiectului**

Proiectul consta in crearea unei aplicatii Java pentru redimensionarea imaginilor utilizand biblioteca Swing. Aplicatia permite utilizatorului sa incarce imagini din sistemul local, sa specifice noile dimensiuni si sa salveze rezultatul intr-un format compatibil (PNG).

**2. Cerinte**

Limbaj de programare: Java

Bibliotice folosite: Swing, AWT, AWT Image, ImageIO

Sistem de operare: Windows, macOS, Linux.

IDE recomandat: IntelliJ IDEA, Eclipse

Java JDK: Versiunea 8 sau cea mai recenta

**3. Mediul de dezvoltare**

CPU: Apple M2

Memorie RAM: 16GB

Sistem de operare: macOS Sequoia 15.3.2

IDE: IntelliJ IDEA 2024.3.4.1

**4. Structura aplicatiei**

Clasa principala: ImageResizer

Extinde JFrame si implementeaza ActionListener pentru a gestiona evenimentele interfetei grafice.

Componentele principalte ale interfetei: JButton, JTextField, JLabel, JPanel, JFileChoose

**Metode principale**

main(String[] args):

Initializeaza aplicatia si seteaza UI-ul pe Swing (SwingUtilities.invokeLater()).

actionPerformed(ActionEvent e):

Gestioneaza evenimentele produse de interfata grafica. In functie de butonul apasat (openButton, resizeButton, saveButton), se executa urmatoarele:

openButton: Deschide un JFileChooser pentru selectarea unei imagini.

resizeButton: Redimensioneaza imaginea introdusa la marimile introduse de utilizator.

saveButton: Salveaza imaginea redimensionata folosind ImagineIO.write().

resizeImage(BufferedImage, originalImage, int witdth, int height): Utilizeaza getScaledInstace() pentru redimensionarea imaginii si Graphics2D pentru a o desena pe un BufferedImage nou. Returneaza imaginea redimensionata.

**5. Rezultate experimentale**

Am testat aplicatia cu imagini de diferite dimensiuni pentru a masura timpul de procesare al redimensionarii.

Test1: 275x183 - 800x600 - 46ms

Test2: 3840x2160 - 1280 720 - 199ms

Test3: 3840x2160 - 1920x1080 - 230ms
