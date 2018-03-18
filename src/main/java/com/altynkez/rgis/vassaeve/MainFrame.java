package com.altynkez.rgis.vassaeve;

import com.altynkez.rgis.vassaeve.entity.Patient;
import com.altynkez.rgis.vassaeve.utils.DbUtils;
import com.altynkez.rgis.vassaeve.utils.EntityDescriptions;
import com.vassaeve.commons.CommonComparator;
import com.vassaeve.db.MyTableModel;
import java.awt.Cursor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author vassaeve
 */
public class MainFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private static final long serialVersionUID = 839932036713957084L;
    private static final Logger LOGGER = LoggerFactory.getLogger(MainFrame.class);

    private MyTableModel<Patient> patientModel;
    private List<Patient> patientList;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();

        postInit();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        patientsPanel = new javax.swing.JPanel();
        patientsDBGrid = new com.vassaeve.db.DBPanel();
        protocolsPanel = new javax.swing.JPanel();
        protocolDBGrid = new com.vassaeve.db.DBPanel();
        statusBar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Протоколы");

        jPanel1.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(300);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        patientsPanel.setLayout(new java.awt.BorderLayout());

        patientsDBGrid.setRemoveButtonVisible(false);
        patientsDBGrid.setSearchButtonVisible(false);
        patientsDBGrid.addDBListener(new com.vassaeve.db.event.DBListener() {
            public void tableClicked(com.vassaeve.db.event.DBEvent evt) {
            }
            public void printSelected(com.vassaeve.db.event.DBEvent evt) {
            }
            public void searchSelected(com.vassaeve.db.event.DBEvent evt) {
            }
            public void editSelected(com.vassaeve.db.event.DBEvent evt) {
            }
            public void removeSelected(com.vassaeve.db.event.DBEvent evt) {
            }
            public void newSelected(com.vassaeve.db.event.DBEvent evt) {
            }
            public void refreshSelected(com.vassaeve.db.event.DBEvent evt) {
                patientsDBGridRefreshSelected(evt);
            }
        });
        patientsPanel.add(patientsDBGrid, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(patientsPanel);

        protocolsPanel.setLayout(new java.awt.BorderLayout());

        protocolDBGrid.setSearchButtonVisible(false);
        protocolsPanel.add(protocolDBGrid, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(protocolsPanel);

        jPanel1.add(jSplitPane1, java.awt.BorderLayout.CENTER);
        jPanel1.add(statusBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void patientsDBGridRefreshSelected(com.vassaeve.db.event.DBEvent evt) {//GEN-FIRST:event_patientsDBGridRefreshSelected
        loadPatients(null);
    }//GEN-LAST:event_patientsDBGridRefreshSelected

    private void loadPatients(Map<String, String> filter) {

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (patientModel == null) {
            patientModel = new MyTableModel<>(Patient.class);

            Map<String, EntityDescriptions.FieldDescription> descriptions = EntityDescriptions.PATIENT.getFieldsDescriptions();
            int colNum = 0;

            for (String fieldName : descriptions.keySet()) {
                EntityDescriptions.FieldDescription description = descriptions.get(fieldName);
                if (description.isVisible()) {
                    patientModel.addColumnDescription(colNum, fieldName, description.getDescription());
                    colNum++;
                }
            }
        }

        try {

            patientList = DbUtils.loadAllPatients(null);
            Collections.sort(patientList, new CommonComparator("birth"));

            patientModel.setRows(patientList);
            patientsDBGrid.setModel(patientModel);

            int count = patientsDBGrid.getRowCount();
            if (count > 0) {
                patientsDBGrid.getTable().scrollRectToVisible(patientsDBGrid.getTable().getCellRect(0, 0, true));
                patientsDBGrid.getTable().setRowSelectionInterval(0, 0);
                changePatient();
            }
            //DRIVERS.setDefaultRenderer(String.class, new DriverColorCell());
        } catch (IOException | ClassNotFoundException | IllegalAccessException | SQLException ex) {
            LOGGER.error("ex", ex);
        } finally {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    private void postInit() {

        patientsDBGrid.addPropertyChangeListener(this);

        SwingUtilities.invokeLater(() -> {
            try {
                if (DbUtils.countPatient() != 0) {

                    statusBar.setText("Загружается список пациентов");
                    loadPatients(null);
                    statusBar.setText("Список пациентов загружен");
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            } catch (IOException | ClassNotFoundException | IllegalAccessException | SQLException ex) {
                JOptionPane.showMessageDialog(this, "ошибка выборки данных", "error", JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    private void changePatient() {
     
        int modelIndex = patientsDBGrid.convertRowIndexToModel(patientsDBGrid.getSelectedRow());
        Patient patient = patientModel.getRow(modelIndex);
        if (patient != null) {
            patientsDBGrid.firePropertyChange("patient", 0, patient.getId().hashCode()); //id получим дальше
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JPanel jPanel1;
    javax.swing.JSplitPane jSplitPane1;
    com.vassaeve.db.DBPanel patientsDBGrid;
    javax.swing.JPanel patientsPanel;
    com.vassaeve.db.DBPanel protocolDBGrid;
    javax.swing.JPanel protocolsPanel;
    javax.swing.JLabel statusBar;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propname = evt.getPropertyName();
        if (evt.getOldValue() != evt.getNewValue()) {
            if ("patient".equalsIgnoreCase(propname)) {

                loadProtocols();

            }
        }
    }

    private void loadProtocols() {
        LOGGER.trace("loadProtocols");
    }
}
