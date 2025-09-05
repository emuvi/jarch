package br.com.pointel.jarch.gear;

import javax.swing.SwingUtilities;
import br.com.pointel.jarch.mage.WizDesk;

public class RunChaseDesk extends javax.swing.JFrame {

    private RunChase chase;
    
    RunChaseDesk(String title) {
        initComponents();
        setTitle(title + " - RunChase");
        initFrame();
    }
    
    private void initFrame() {
        WizDesk.initFrame(this);
        WizDesk.initEscaper(this);
    }
    
    void setChase(RunChase chase) {
        this.chase = chase;
    }
    
    void setPaused() {
        SwingUtilities.invokeLater(() -> {
            buttonPauseResume.setText("Resume");
        });
    }
    
    void setResumed() {
        SwingUtilities.invokeLater(() -> {
            buttonPauseResume.setText("Pause");
        });
    }
    
    void setStopped() {
        SwingUtilities.invokeLater(() -> {
            buttonPauseResume.setEnabled(false);
            buttonStop.setEnabled(false);
            buttonStop.setText("Stopped");
        });
    }
    
    void setFinished() {
        SwingUtilities.invokeLater(() -> {
            buttonPauseResume.setEnabled(false);
            buttonStop.setEnabled(false);
            buttonPauseResume.setText("Done");
            viewProgress.setValue(viewProgress.getMaximum());
        });
    }
    
    void setProgressSize(int size) {
        SwingUtilities.invokeLater(() -> {
            viewProgress.setMaximum(size);
        });
    }
    
    void setProgressDone(int done) {
        SwingUtilities.invokeLater(() -> {
            viewProgress.setValue(done);
        });
    }
    
    void putInfo(String info) {
        SwingUtilities.invokeLater(() -> {
            fieldStatus.append(info);
            fieldStatus.append("\n");
        });    
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonPauseResume = new javax.swing.JButton();
        buttonStop = new javax.swing.JButton();
        scrollStatus = new javax.swing.JScrollPane();
        fieldStatus = new javax.swing.JTextArea();
        viewProgress = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("RunChase");

        buttonPauseResume.setText("Pause");
        buttonPauseResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPauseResumeActionPerformed(evt);
            }
        });

        buttonStop.setText("Stop");
        buttonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonStopActionPerformed(evt);
            }
        });

        fieldStatus.setEditable(false);
        fieldStatus.setColumns(20);
        fieldStatus.setRows(5);
        scrollStatus.setViewportView(fieldStatus);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonPauseResume)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonStop)
                        .addGap(18, 18, 18)
                        .addComponent(viewProgress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(buttonPauseResume)
                        .addComponent(buttonStop))
                    .addComponent(viewProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scrollStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonPauseResumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPauseResumeActionPerformed
        chase.toggle();
    }//GEN-LAST:event_buttonPauseResumeActionPerformed

    private void buttonStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonStopActionPerformed
        chase.stop();
    }//GEN-LAST:event_buttonStopActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonPauseResume;
    private javax.swing.JButton buttonStop;
    private javax.swing.JTextArea fieldStatus;
    private javax.swing.JScrollPane scrollStatus;
    private javax.swing.JProgressBar viewProgress;
    // End of variables declaration//GEN-END:variables
}
