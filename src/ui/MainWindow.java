/******************************************************************************
 *                                                                            *
 *  (C) Copyright ${year} Berrehal Ouala Eddine & Benghezal Ines.             *
 *  G2 L3 GL.                                                                 *
 *  Mini projet Module DAC.                                                   *
 *  Simulation d'un parking                                                   *
 *  avec gestion des concurrences en utilisant des sémaphores                 *
 *                                                                            *
 ******************************************************************************/
package ui;

import com.bulenkov.darcula.DarculaLaf;
import system.Params;
import system.enums.CellType;
import system.gestion_de_concurrence.SeGarer;
import system.gestion_de_concurrence.Semaphore;
import system.models.Parking;
import system.utils.CarsInit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;

import static system.enums.CellType.PARK;
import static system.enums.CellType.ROAD;

@SuppressWarnings("unused")
public class MainWindow extends JFrame {

    private static final int parkingSize = Params.PARKING_SIZE;//divisor of 550
    private static Semaphore sortie;
    private static Semaphore entree;
    public static int nbrVoituresEntrees;
    public static int nbrPlacesHandiocc, nbrPlacesnormalocc;
    private static int nbrV;
    private static int nbrSortisAttente;
    private static int nbrParkCells;
    private static int nbrHandiParkCells;
    private static Semaphore videNormal, videHandi;
    private static JLabel nbrPlaces, nbrPlacesLibres, nbcPlacesOccupied, nbrVoitures, nbrEnAttenteEntree, nbrAttenteSortie, parkingSizeLbl;
    private LinkedList<SeGarer> voituresThreadsList;
    private Parking parking;
    private JPanel parkingPanel;
    private boolean stopped;

    /**
     * Creates new form NewJFrame
     */
    private MainWindow() {
        initComponents();
        customizeComponents();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        try {
            // select Look and Feel
            UIManager.setLookAndFeel(new DarculaLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        EventQueue.invokeLater(() -> new MainWindow().setVisible(true));
    }

    /**
     * getters pour les semaphores
     **/
    public static Semaphore getVideNormal() {
        return videNormal;
    }

    public static Semaphore getSortie() {
        return sortie;
    }

    public static Semaphore getEntree() {
        return entree;
    }

    public static Semaphore getVideHandi() {
        return videHandi;
    }

    public static JLabel getNbrPlaces() {
        return nbrPlaces;
    }

    private static void setNbrPlaces(String nbrPlaces) {
        MainWindow.nbrPlaces.setText(nbrPlaces);
    }

    public static JLabel getNbrPlacesLibres() {
        return nbrPlacesLibres;
    }

    private static void setNbrPlacesLibres(String nbrPlacesLibres) {
        MainWindow.nbrPlacesLibres.setText(nbrPlacesLibres);
    }

    public static JLabel getNbrPlacesOccupees() {
        return nbcPlacesOccupied;
    }

    private static void setNbrPlacesOccupees(String nbrPlacesOccupees) {
        MainWindow.nbcPlacesOccupied.setText(nbrPlacesOccupees);
    }

    public static JLabel getNbrVoitures() {
        return nbrVoitures;
    }

    private static void setNbrVoitures(String nbrVoitures) {
        nbrV = Integer.parseInt(nbrVoitures);
        MainWindow.nbrVoitures.setText(nbrVoitures);
    }

    public static JLabel getNbrEnAttenteEntree() {
        return nbrEnAttenteEntree;
    }

    private static void setNbrEnAttenteEntree(String nbrEnAttenteEntree) {
        MainWindow.nbrEnAttenteEntree.setText(nbrEnAttenteEntree);
    }

    public static JLabel getNbrAttenteSortie() {
        return nbrAttenteSortie;
    }

    private static void setNbrAttenteSortie(String nbrAttenteSortie) {
        MainWindow.nbrAttenteSortie.setText(nbrAttenteSortie);
    }

    public static JLabel getParkingSizeLbl() {
        return parkingSizeLbl;
    }

    public static void setParkingSizeLbl(String parkingSizeLbl) {
        MainWindow.parkingSizeLbl.setText(parkingSizeLbl);
    }

    public static void updateMainViewSema(String name, int n) {
       /* if (name.equals(entree.getName()))
            setNbrEnAttenteEntree("" + Math.abs(n));*/
        if (name.equals(sortie.getName()))
            setNbrAttenteSortie("" + Math.abs(n));
      /*  if (name.equals(videNormal.getName()))
            setNbrPlacesLibres("" + n);*/
    }

    public static void updateView() {
        setNbrPlaces("" + (nbrHandiParkCells + nbrParkCells));
        setNbrPlacesLibres("" + ((nbrHandiParkCells + nbrParkCells) - (nbrPlacesnormalocc + nbrPlacesHandiocc)));
        setNbrPlacesOccupees("" + (nbrPlacesnormalocc + nbrPlacesHandiocc));
        setNbrEnAttenteEntree("" + (nbrV - nbrVoituresEntrees));
        setNbrAttenteSortie("" + Math.abs(nbrSortisAttente));
    }

    /**
     * methode appelée lors du clique sur le boutton settings
     */
    private void btnSettingsActionPerformed(ActionEvent evt) {

    }

    /**
     * methode appelée lors du clique sur le boutton start
     */
    private void btnStartActionPerformed(ActionEvent evt) {
        if (!stopped)
            launchVoitures();
        else {
          /* Create and display the form */
            EventQueue.invokeLater(() -> new MainWindow().setVisible(true));
            this.dispose();
        }

    }

    /**
     * methode appelée lors du clique sur le boutton stop
     */
    private void btnStopActionPerformed(ActionEvent evt) {
        stopVoitures();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        stopped = false;
        JPanel jPanel1 = new JPanel();
        JPanel parking_box = new JPanel();
        parkingPanel = new JPanel();
        JPanel jPanel4 = new JPanel();
        JButton btnStart = new JButton();
        JButton btnStop = new JButton();
        JLabel jLabel1 = new JLabel();
/*
        JButton btnSettings = new JButton();
*/
        nbrPlaces = new JLabel();
        JLabel jLabel3 = new JLabel();
        nbrPlacesLibres = new JLabel();
        JLabel jLabel5 = new JLabel();
        nbcPlacesOccupied = new JLabel();
        JLabel jLabel7 = new JLabel();
        nbrVoitures = new JLabel();
        JLabel jLabel9 = new JLabel();
        nbrEnAttenteEntree = new JLabel();
        JLabel jLabel11 = new JLabel();
        nbrAttenteSortie = new JLabel();
        JLabel jLabel13 = new JLabel();
        parkingSizeLbl = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
        setResizable(false);

        jPanel1.setMinimumSize(new Dimension(550, 550));
        jPanel1.setPreferredSize(new Dimension(550, 550));

        parking_box.setBackground(new Color(153, 153, 153));
        parking_box.setMinimumSize(new Dimension(550, 550));
        parking_box.setPreferredSize(new Dimension(550, 550));

        parkingPanel.setBackground(new Color(204, 204, 204));
        parkingPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        parkingPanel.setMinimumSize(new Dimension(0, 0));
        parkingPanel.setPreferredSize(new Dimension(550, 550));
        parkingPanel.setLayout(new GridLayout(parkingSize, parkingSize));

        GroupLayout parking_boxLayout = new GroupLayout(parking_box);
        parking_box.setLayout(parking_boxLayout);
        parking_boxLayout.setHorizontalGroup(
                parking_boxLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(parkingPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        parking_boxLayout.setVerticalGroup(
                parking_boxLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(parking_boxLayout.createSequentialGroup()
                                .addComponent(parkingPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel4.setMinimumSize(new Dimension(250, 0));
        jPanel4.setPreferredSize(new Dimension(250, 247));

        btnStart.setText("demarrer");
        btnStart.setActionCommand("");
        btnStart.addActionListener(this::btnStartActionPerformed);

        btnStop.setText("Arreter");
        btnStop.addActionListener(this::btnStopActionPerformed);

        jLabel1.setText("toutes les places :");

  /*      btnSettings.setText("parametres");
        btnSettings.addActionListener(this::btnSettingsActionPerformed);
*/
        nbrPlaces.setText("0");

        jLabel3.setText("places libres (N) :");

        nbrPlacesLibres.setText("0");

        jLabel5.setText("places occupées (N) : ");

        nbcPlacesOccupied.setText("0");

        jLabel7.setText("nbr voitures:");

        nbrVoitures.setText("0");

        jLabel9.setText("en attente (E):");

        nbrEnAttenteEntree.setText("0");

        jLabel11.setText("en attente (s):");

        nbrAttenteSortie.setText("0");

        jLabel13.setText("parking size:");

        parkingSizeLbl.setText("" + parkingSize + "*" + parkingSize);

        GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(btnStop, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnStart, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
/*
                        .addComponent(btnSettings, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
*/
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nbrPlaces, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nbcPlacesOccupied, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(nbrPlacesLibres, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(nbrVoitures))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(jLabel9)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(nbrEnAttenteEntree))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(jLabel11)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(nbrAttenteSortie))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(jLabel13)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(parkingSizeLbl)))
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnStart)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnStop)
/*                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSettings)*/
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(nbrPlaces))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(nbrPlacesLibres))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(nbcPlacesOccupied))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(nbrVoitures))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9)
                                        .addComponent(nbrEnAttenteEntree))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel11)
                                        .addComponent(nbrAttenteSortie))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel13)
                                        .addComponent(parkingSizeLbl))
                                .addContainerGap(83, Short.MAX_VALUE))
        );

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(parking_box, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                                .addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(parking_box, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, 339, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(50, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 800, GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }

    /**
     * This method is called from within the constructor to customize the form.
     */
    private void customizeComponents() {
        //on instantie un objet parking avec la taille du parking
        parking = new Parking(parkingSize);
        nbrParkCells = 0;
        //on initialise la liste des voitures
        initVoituresList();
        //on remplie le parking avec des cases selon un algo
        for (int i = 1; i <= parkingSize; i++) {
            //si la ligne i est une ligne de parking on ajoute une ligne de parking en i
            if (isParkRow(i))
                addParkRow(i);
                //sinn la ligne i est une ligne de route on ajoute une ligne de route en i
            else
                addRoadRow(i);
        }
        //on initialise les semaphores
        initSemaphores();
    }

    /**
     * methode pour ajouter une ligne de route
     */
    private void addRoadRow(int row) {
        for (int j = 1; j <= parkingSize; j++)
            addRoadCell(row, j);
    }

    /**
     * methode pour ajouter une ligne de route
     * selon l'algorithme suivant
     * si multiple de 6 => route
     * sinon
     * si juste avant un multiple de 6 =>park handi
     * sinon => park
     */
    private void addParkRow(int row) {
        for (int j = 1; j <= parkingSize; j++) {
            if (j % 6 != 0) {
                if (((j + 1) % 6 == 0 || (j - 1) % 6 == 0) &&
                        (j != 1) &&
                        j != parkingSize) {
                    addHandiParkCell(row, j);
                } else if (row == parkingSize || row == parkingSize - 1)
                    addHandiParkCell(row, j);
                else
                    addParkCell(row, j);
            } else
                addRoadCell(row, j);
        }
    }

    /**
     * methode pour tester si i est une ligne de park
     * (ligne de park = multiple de 2 -pour avoir une alternance entre les lignes-)
     */
    private boolean isParkRow(int i) {
        return i % 2 == 0;
    }

    /**
     * methode pour ajouter une case de park
     * (to the form and to the parking object)
     */
    private void addParkCell(int row, int column) {
        JPanel pan = new JPanel();
        pan.setBackground(Color.gray);
        parkingPanel.add(pan);
        parking.addCellToParking(pan, row, column, PARK);
        nbrParkCells++;
    }

    /**
     * methode pour ajouter une case de park pour handi
     * (to the form and to the parking object)
     */
    private void addHandiParkCell(int row, int column) {
        JPanel pan = new JPanel();
        pan.setBackground(Color.pink);
        parkingPanel.add(pan);
        parking.addCellToParking(pan, row, column, CellType.HANDI);
        nbrHandiParkCells++;
    }

    /**
     * methode pour ajouter une case de route
     * (to the form and to the parking object)
     */
    private void addRoadCell(int row, int column) {
        JPanel pan = new JPanel();
        pan.setBackground(Color.decode("#303030"));
        parkingPanel.add(pan);
        parking.addCellToParking(pan, row, column, ROAD);
    }

    /**
     * methode pour initialiser les voitures
     * selon Settings
     */
    private void initVoituresList() {
        int nbrVoituresNrml = Params.NBR_VOITURES_NRML;
        int nbrVoituresSpcl = Params.NBR_VOITURES_HANDI;
        int nbrVoituresAbo = Params.NBR_VOITURES_ABO;
        setNbrVoitures("" + (nbrVoituresNrml + nbrVoituresSpcl + nbrVoituresAbo));
        voituresThreadsList = new CarsInit(nbrVoituresAbo, nbrVoituresNrml, nbrVoituresSpcl, parking).getSeGarrers();
    }

    /**
     * methode pour lancer touts les threads des voitures
     */
    private void launchVoitures() {
        initVoituresList();
        for (SeGarer sg : voituresThreadsList) {
            sg.start();
        }
    }

    /**
     * methode pour arreter touts les threads des voitures
     */
    @SuppressWarnings("deprecation")
    private synchronized void stopVoitures() {
        for (SeGarer sg : voituresThreadsList) {
            if (sg.isAlive())
                sg.stop();
        }
        stopped = true;
    }

    /**
     * methode pour initialiser les semaphores selon settings
     */
    private void initSemaphores() {
        videHandi = new Semaphore(nbrHandiParkCells, "VideHandi");
        videNormal = new Semaphore(nbrParkCells, "VideNormal");
        setNbrPlaces("" + (nbrHandiParkCells + nbrParkCells));
        setNbrPlacesLibres("" + (nbrHandiParkCells + nbrParkCells));
        setNbrPlacesOccupees("" + 0);
        entree = new Semaphore(1, "Entrée");
        sortie = new Semaphore(1, "Sortie");
    }
}
