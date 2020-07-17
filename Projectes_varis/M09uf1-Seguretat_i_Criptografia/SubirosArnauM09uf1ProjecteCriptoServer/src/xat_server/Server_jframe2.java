/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xat_server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;


/**
 *
 * @author arnau
 */
public class Server_jframe2 extends javax.swing.JFrame {

    ArrayList fluxesSortidaClient; // stream
    ArrayList<String> clientUsuaris;
    
  //--------------------------------------------------------------------------------------------------------------  
 //--------------------------------------------------------------------------------------------------------------
    public class ClientHandler implements Runnable {

  /*   Subclasse 
     La Interfície pública Runnable ha de ser implementada per qualsevol classe les instàncies de la qual 
        estiguin destinades  a ser executades per un fil(thread). 
        La classe ha de definir un mètode sense arguments invocats run() 
      
        */      
       
  /* ClientHandler  estableix les bases per a la comunicació entre clients a través de la xarxa.
   ClientHandler  estén la classe Handler, per la qual cosa també estén Thread i 
   té un socket per a enviar i rebre xats i implmenta la interficie Runnable per la qual haurà de sobreescriure el metode run()*/
        
        BufferedReader lectorBuffer;
        Socket socols;
        PrintWriter escripturaClient;

        public ClientHandler(Socket clientSocols, PrintWriter usuari) {
            escripturaClient = usuari;
            try {
                socols = clientSocols;
                //la Clase  InputStreamReader  es pont en la conversió de bytes a caracters 
                InputStreamReader esLector = new InputStreamReader(socols.getInputStream());
                lectorBuffer = new BufferedReader(esLector);
            } catch (Exception ex) {
                textArea_xat.append("Error inesperat... \n");
            }

        }

        // sobreescriure el metode run  de l'interficie Runnable
        @Override
        public void run() {
            String missatge, s_connectat = "Conectat", s_desconnectat = "Desconectat", s_xat = "Xat";
            String[] arrayDades;

            try {
                while ((missatge = lectorBuffer.readLine()) != null) {
                    textArea_xat.append("\n<<S'ha rebut de " + missatge + "\n");
                    //metode split per dividir una cadena amb " :"
                    arrayDades = missatge.split(":");

                    if (arrayDades[2].equals(s_connectat)) {
                       missatgeDeClientServidor((arrayDades[0] + ":" + arrayDades[1] + ":" + s_xat));
                        usuariAfegir(arrayDades[0]);
                    } else if (arrayDades[2].equals(s_desconnectat)) {
                        missatgeDeClientServidor((arrayDades[0] + ":s'ha desconectat." + ":" + s_xat));
                        usuariEliminar(arrayDades[0]);
                    } else if (arrayDades[2].equals(s_xat)) {
                        missatgeDeClientServidor(missatge);
                    } else {
                        textArea_xat.append("No s'han complert les condicions \n");
                    }
                }
            } catch (Exception ex) {
                textArea_xat.append("Connexió perduda \n");
                ex.printStackTrace();
                fluxesSortidaClient.remove(escripturaClient);
            }
        }
    }

//-----------------------------------------------------------------------------------     
//----------------------------------------------------------------------------------  
    public class ServidorInici implements Runnable {

                /*  Subclasse 
         La classe Servidor Inici implementa la interficie Runnable
         que ha de ser implementada per qualsevol classe les instàncies de la qual estiguin destinades
        a ser executades per un fil. La classe ha de definir un mètode sense arguments invocats run
        
        */
        
        
        @Override
        public void run() {
            fluxesSortidaClient = new ArrayList();
            clientUsuaris = new ArrayList();

            try {
                ServerSocket serverSock = new ServerSocket(2222);

                while (true) {
                    Socket clientSocols = serverSock.accept();
                    PrintWriter escriptorImpressio = new PrintWriter(clientSocols.getOutputStream());
                    fluxesSortidaClient.add(escriptorImpressio);

                    Thread filOient = new Thread(new ClientHandler(clientSocols, escriptorImpressio));
                    filOient.start();
                    textArea_xat.append("Teniu connexió.. \n");
                }
            } catch (Exception ex) {
                textArea_xat.append("S'ha produït un error en fer una connexió \n");
            }
        }
    }

    public void usuariAfegir(String dadesAltaUsuari) {
        String missatge, afegir = ": :Conectat", FetOk = "Servidor: :Fet", nomUsuari = dadesAltaUsuari;
        clientUsuaris.add(nomUsuari);
        String[] llistaTemporal = new String[(clientUsuaris.size())];
        clientUsuaris.toArray(llistaTemporal);

        for (String tokenClau : llistaTemporal) {
            missatge = (tokenClau + afegir);
            missatgeDeClientServidor(missatge);
        }
        missatgeDeClientServidor(FetOk);
    }

    public void usuariEliminar(String dadesEliminarUsuari) {
       String missatge, afegir = ": :Conectat", FetOk = "Servidor: :Fet", nomUsuari = dadesEliminarUsuari;
        clientUsuaris.remove(nomUsuari);
        String[] llistaTemporal = new String[(clientUsuaris.size())];
       clientUsuaris.toArray(llistaTemporal);

        for (String tokenClau : llistaTemporal) {
            missatge = (tokenClau + afegir);
            missatgeDeClientServidor(missatge);
        }
        missatgeDeClientServidor(FetOk);
    }

    public void missatgeDeClientServidor(String missatge) {
        Iterator iterator_missatge = fluxesSortidaClient.iterator();

        while (iterator_missatge.hasNext()) {
            try {
                PrintWriter escriptorMissatge = (PrintWriter) iterator_missatge.next();
                escriptorMissatge.println(missatge);
                textArea_xat.append(">> Enviament: " + missatge + "\n"); // Imprimi en text area
                escriptorMissatge.flush();
                textArea_xat.setCaretPosition(textArea_xat.getDocument().getLength());

            } catch (Exception ex) {
                textArea_xat.append("S'ha produït un error en dir-ho a tothom. \n");
            }
        }
    }

 //-----------------------------------------------------------------------------------     
//----------------------------------------------------------------------------------     
    
    
    /**
     * Creates new form Server_jframe2
     */
    public Server_jframe2() {
        initComponents();
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea_xat = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        btn_inici_servidor = new javax.swing.JButton();
        btn_finalitzaServidor = new javax.swing.JButton();
        btn_usuaris_conectats = new javax.swing.JButton();
        btn_neteja_pantalla = new javax.swing.JButton();
        labelEXit = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        textArea_xat.setEditable(false);
        textArea_xat.setBackground(new java.awt.Color(0, 0, 0));
        textArea_xat.setColumns(20);
        textArea_xat.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        textArea_xat.setForeground(new java.awt.Color(0, 204, 0));
        textArea_xat.setRows(5);
        textArea_xat.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(102, 102, 102), null, null));
        jScrollPane1.setViewportView(textArea_xat);

        jPanel3.setBackground(new java.awt.Color(51, 0, 153));

        btn_inici_servidor.setBackground(new java.awt.Color(0, 0, 0));
        btn_inici_servidor.setForeground(new java.awt.Color(255, 255, 255));
        btn_inici_servidor.setText("INICIAR SERVIDOR");
        btn_inici_servidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_inici_servidorActionPerformed(evt);
            }
        });

        btn_finalitzaServidor.setBackground(new java.awt.Color(0, 0, 0));
        btn_finalitzaServidor.setForeground(new java.awt.Color(255, 255, 255));
        btn_finalitzaServidor.setText("FINALITZA SERVIDOR");
        btn_finalitzaServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_finalitzaServidorActionPerformed(evt);
            }
        });

        btn_usuaris_conectats.setBackground(new java.awt.Color(0, 0, 0));
        btn_usuaris_conectats.setForeground(new java.awt.Color(255, 255, 255));
        btn_usuaris_conectats.setText("USUARIS CONECTATS");
        btn_usuaris_conectats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_usuaris_conectatsActionPerformed(evt);
            }
        });

        btn_neteja_pantalla.setBackground(new java.awt.Color(0, 0, 0));
        btn_neteja_pantalla.setForeground(new java.awt.Color(255, 255, 255));
        btn_neteja_pantalla.setText("NETEJA PANTALLA");
        btn_neteja_pantalla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_neteja_pantallaActionPerformed(evt);
            }
        });

        labelEXit.setBackground(new java.awt.Color(255, 255, 204));
        labelEXit.setForeground(new java.awt.Color(0, 0, 0));
        labelEXit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_jframe/iconaExit2.png"))); // NOI18N
        labelEXit.setOpaque(true);
        labelEXit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEXitMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(btn_inici_servidor, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(btn_finalitzaServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelEXit)
                .addGap(124, 124, 124)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_usuaris_conectats, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(btn_neteja_pantalla, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_inici_servidor)
                    .addComponent(btn_usuaris_conectats))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_neteja_pantalla)
                    .addComponent(btn_finalitzaServidor))
                .addContainerGap(37, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelEXit, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(102, 102, 102), null, null));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("M09-Programacio de processos i serveis");

        jLabel2.setBackground(new java.awt.Color(51, 51, 51));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("CFGS DAM2");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Arnau Subiros");

        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_jframe/escola_fondo.png"))); // NOI18N
        jLabel4.setText("jLabel4");
        jLabel4.setOpaque(true);

        jLabel5.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_jframe/ico_xat.png"))); // NOI18N
        jLabel5.setText("SERVIDOR DEL XAT");
        jLabel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("UF1-Seguretat i Criptografia");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Escola del Clot");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)))
                .addGap(3, 3, 3))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_inici_servidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inici_servidorActionPerformed
        Thread filIniciarServidor = new Thread(new ServidorInici());
        filIniciarServidor.start();

        textArea_xat.append("S'ha iniciat el servidor del XAT...\n");
    }//GEN-LAST:event_btn_inici_servidorActionPerformed

    private void btn_finalitzaServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_finalitzaServidorActionPerformed
        try {
            textArea_xat.append("Voste es desconectara del servidor...\n");
          
            Thread.sleep(5000);                 //5000 milliseconds
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        textArea_xat.append("S'atura el servidor ... \n");

     
    }//GEN-LAST:event_btn_finalitzaServidorActionPerformed

    private void btn_usuaris_conectatsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_usuaris_conectatsActionPerformed
        textArea_xat.append("\n Usuaris en línia: \n");
        for (String current_user : clientUsuaris) {
            textArea_xat.append(current_user);
            textArea_xat.append("\n");
        }
    }//GEN-LAST:event_btn_usuaris_conectatsActionPerformed

    private void btn_neteja_pantallaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_neteja_pantallaActionPerformed
        textArea_xat.setText("");
    }//GEN-LAST:event_btn_neteja_pantallaActionPerformed

    private void labelEXitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEXitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_labelEXitMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Server_jframe2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server_jframe2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server_jframe2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server_jframe2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server_jframe2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_finalitzaServidor;
    private javax.swing.JButton btn_inici_servidor;
    private javax.swing.JButton btn_neteja_pantalla;
    private javax.swing.JButton btn_usuaris_conectats;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelEXit;
    private javax.swing.JTextArea textArea_xat;
    // End of variables declaration//GEN-END:variables
}
