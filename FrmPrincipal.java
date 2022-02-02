package proyectoautomatas;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import java_cup.runtime.Symbol;

import javax.sound.sampled.SourceDataLine;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FrmPrincipal extends javax.swing.JFrame {
    JFileChooser seleccionar=new JFileChooser();
    File archivo;
    FileOutputStream salida;

    public void imprimirMiNombre(){
        System.out.println("Jorge Alejandro Zabaleta Mazariegos");
        System.out.println("Estuve aquí");
        System.out.println("Estuve aquí");
    }
   
    public FrmPrincipal() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    public String GuardarArchivo(File archivo, String documento){
        String mensaje=null;
        try{
            salida=new FileOutputStream(archivo);
            byte[] bytxt = documento.getBytes();
            salida.write(bytxt);
            mensaje="Archivo Guardado";
        }catch(Exception e){}
        return mensaje;
    }

    private void analizarLexico() throws IOException{
        int cont = 1;
        //se lee el archivo que tiene tomado en el area de texto
        String expr = (String) txtArchivo.getText();
        Lexer lexer = new Lexer(new StringReader(expr));
        String resultado = "TOKEN "  + "\t\tSIMBOLO\n";
        //evalua sea el caso para imprimir el token del analizador lexico
        while (true) {
            Tokens token = lexer.yylex();
            if (token == null) {
                txtLexico.setText(resultado);
                return;
            }
            switch (token) {
                case Linea:
                    cont++;
                    resultado += "LINEA " + cont + "\n";
                    break;
                case EsInstrumento:
                    resultado += "  <Instrumento>\t\t" + lexer.lexeme + "\n";
                    break;
                case TipoDeInstrumento:
                    resultado += "  <Tipo>\t\t" + lexer.lexeme + "\n";
                    break;
                case EsMarca:
                    resultado += "  <Marca>\t\t" + lexer.lexeme + "\n";
                    break;
                case ElPrecio:
                    resultado += "  <Precio>\t\t" + lexer.lexeme + "\n";
                    break;
                case TipoDeUso:
                    resultado += "  <Uso>\t\t" + lexer.lexeme + "\n";
                    break;
                case TipoDeEntrega:
                    resultado += "  <Entrega>\t\t" + lexer.lexeme + "\n";
                    break;
                case TipoDePago:
                    resultado += "  <Pago>\t\t" + lexer.lexeme + "\n";
                    break;
                case EsImagen:
                    resultado += "  <Imagen>\t\t" + lexer.lexeme + "\n";
                    break;
                case Negrita:
                    resultado += "  <Negrita>\t\t" + lexer.lexeme + "\n";
                    break;
                case Instrumento:
                    resultado += "  <PalabraInstrumento>\t\t" + lexer.lexeme + "\n";
                    break;
                case Tipo:
                    resultado += "  <PalabraTipo>\t\t" + lexer.lexeme + "\n";
                    break;
                case Marca:
                    resultado += "  <PalabraMarca>\t\t" + lexer.lexeme + "\n";
                    break;
                case Precio:
                    resultado += "  <PalabraPrecio>\t\t" + lexer.lexeme + "\n";
                    break;
                case Uso:
                    resultado += "  <PalabraUso>\t\t\t" + lexer.lexeme + "\n";
                    break;
                case Entrega:
                    resultado += "  <PalabraEntrega>\t\t" + lexer.lexeme + "\n";
                    break;
                case Pago:
                    resultado += "  <PalabraPago>\t\t\t" + lexer.lexeme + "\n";
                    break;
                case Imagen:
                    resultado += "  <PalabraImagen>\t\t\t" + lexer.lexeme + "\n";
                    break;
                case Orientacion:
                    resultado += "  <PalabraOrientacion>\t" + lexer.lexeme + "\n";
                    break;
                case Error:
                    resultado += "  <Simbolo no definido>\n";
                    break;
                default:
                    resultado += "  < " + lexer.lexeme + " >\n";
                    break;
            }
        }
    }
    
    private void analizarSintactico() throws IOException{
        String ST = txtArchivo.getText();
        Sintax s = new Sintax(new proyectoautomatas.LexerCup(new StringReader(ST)));
        //realiza la evaluacion con la sintaxis predefinida
        try {
            s.parse();
            //se imprime si la escritura es correcta
            txtSintactico.setText("Analisis realizado correctamente");
            txtSintactico.setForeground(new Color(25, 111, 61));
            codigoHTML();
        } catch (Exception ex) {
            Symbol sym = s.getS();
            //imprime que es incorrecta junto al error en la fila y columna
            txtSintactico.setText("Error de sintaxis. Linea: " + (sym.right + 1) + " Columna: " + (sym.left + 1) + ", Texto: \"" + sym.value + "\"");
            txtSintactico.setForeground(Color.red);
        }
    }
    
    private void codigoHTML() throws IOException{
        String resultado = "<html>\n" + "<head>\n" + "<title>Venta de Instrumentos</title>\n" + "</head>"+"\n<body>\n";
        txtHTML.setText(resultado);
        int cont = 1;
        String expr = (String) txtArchivo.getText();
        Lexer lexer = new Lexer(new StringReader(expr));
        
        while (true) {
            Tokens token = lexer.yylex();
            if (token == null) {
                resultado += "</body>\n" + "</html>";
                txtHTML.setText(resultado);
                return;
            }
            switch (token) {
                case Linea:
                    cont++;
                    resultado += "LINEA " + cont + "\n";
                    break;
                case EsInstrumento:
                    resultado += lexer.lexeme + " </b> " + " <br> " + "\n";
                    break;
                case TipoDeInstrumento:
                    resultado += lexer.lexeme + " <br> " + "\n";
                    break;
                case EsMarca:
                    resultado += lexer.lexeme + " <br> " + "\n";
                    break;
                case ElPrecio:
                    resultado += lexer.lexeme + " </b> " + " <br> " + "\n";
                    break;
                case TipoDeUso:
                    resultado += lexer.lexeme + " <br> " + "\n";
                    break;
                case TipoDeEntrega:
                    resultado += lexer.lexeme + " <br> " + "\n";
                    break;
                case TipoDePago:
                    resultado += lexer.lexeme + " <br> " + "\n";
                    break;
                case EsImagen:
                    resultado += " <br> " + lexer.lexeme + " <br> " + "\n";
                    break;
                case Negrita:
                        if("SI".equals(lexer.lexeme)){
                        resultado += " <b> ";} else{}
                    break;
                default:
                    resultado += "  < " + lexer.lexeme + " >\n";
                    break;
            }
        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtSintactico = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtLexico = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtArchivo = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnAbrir = new javax.swing.JToggleButton();
        btnAnalizarLex = new javax.swing.JButton();
        btnLimpiarLex = new javax.swing.JButton();
        btnAnalizarSin = new javax.swing.JButton();
        btnLimpiarSin = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtHTML = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));

        txtSintactico.setColumns(20);
        txtSintactico.setRows(5);
        jScrollPane3.setViewportView(txtSintactico);

        txtLexico.setColumns(20);
        txtLexico.setRows(5);
        jScrollPane2.setViewportView(txtLexico);

        txtArchivo.setColumns(20);
        txtArchivo.setRows(5);
        txtArchivo.setAutoscrolls(false);
        jScrollPane4.setViewportView(txtArchivo);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setText("Archivo");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setText("Analizador Lexico");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setText("Analizador Sintactico");

        btnAbrir.setText("Abrir archivo");
        btnAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirActionPerformed(evt);
            }
        });

        btnAnalizarLex.setText("Analizar");
        btnAnalizarLex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalizarLexActionPerformed(evt);
            }
        });

        btnLimpiarLex.setText("Limpiar");
        btnLimpiarLex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarLexActionPerformed(evt);
            }
        });

        btnAnalizarSin.setText("Analizar");
        btnAnalizarSin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalizarSinActionPerformed(evt);
            }
        });

        btnLimpiarSin.setText("Limpiar");
        btnLimpiarSin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarSinActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar archivo");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setText("Codigo HTML");

        txtHTML.setColumns(20);
        txtHTML.setRows(5);
        jScrollPane5.setViewportView(txtHTML);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(43, 43, 43))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAbrir)
                                .addGap(13, 13, 13)))
                        .addGap(85, 85, 85)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jLabel2)
                                .addGap(95, 95, 95)
                                .addComponent(jLabel3)
                                .addGap(113, 113, 113)
                                .addComponent(jLabel4))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(btnAnalizarLex)
                                .addGap(30, 30, 30)
                                .addComponent(btnLimpiarLex)
                                .addGap(99, 99, 99)
                                .addComponent(btnAnalizarSin)
                                .addGap(18, 18, 18)
                                .addComponent(btnLimpiarSin)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnGuardar)
                                .addGap(54, 54, 54)))))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAbrir)
                    .addComponent(btnAnalizarLex)
                    .addComponent(btnLimpiarLex)
                    .addComponent(btnAnalizarSin)
                    .addComponent(btnLimpiarSin)
                    .addComponent(btnGuardar))
                .addGap(0, 35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File archivo = new File(chooser.getSelectedFile().getAbsolutePath());
        //lee el archivo que deseamos elegir en el computador
        try {
            //imprime el archivo en el area de texto
            String ST = new String(Files.readAllBytes(archivo.toPath()));
            txtArchivo.setText(ST);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAbrirActionPerformed

    private void btnAnalizarLexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalizarLexActionPerformed
        // TODO add your handling code here:
         try {
             //hace el analisis llamando la función
            analizarLexico();
        } catch (IOException ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAnalizarLexActionPerformed

    private void btnLimpiarLexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarLexActionPerformed
        // TODO add your handling code here:
        //limpia el area de texto de Analisis lexico
        txtLexico.setText(null);
    }//GEN-LAST:event_btnLimpiarLexActionPerformed

    private void btnAnalizarSinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalizarSinActionPerformed
        
         try {
             //hace el analisis llamando la función
            analizarSintactico();
        } catch (IOException ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAnalizarSinActionPerformed

    private void btnLimpiarSinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarSinActionPerformed
        // TODO add your handling code here:
        //limpia el area de texto de Analisis sintactico
        txtSintactico.setText(null);
    }//GEN-LAST:event_btnLimpiarSinActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        if(seleccionar.showDialog(null,"Guardar")==JFileChooser.APPROVE_OPTION){
            archivo=seleccionar.getSelectedFile();
            if(archivo.getName().endsWith("html")){
                String Documento=txtHTML.getText();
                String mensaje=GuardarArchivo(archivo,Documento);
                if(mensaje!=null){
                    JOptionPane.showMessageDialog(rootPane,mensaje);
                }else{
                    JOptionPane.showMessageDialog(null,"Archivo No Compatible"); 
                }
            }else{
                JOptionPane.showMessageDialog(null, "Guardar documento de Texto");
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

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
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnAbrir;
    private javax.swing.JButton btnAnalizarLex;
    private javax.swing.JButton btnAnalizarSin;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiarLex;
    private javax.swing.JButton btnLimpiarSin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextArea txtArchivo;
    private javax.swing.JTextArea txtHTML;
    private javax.swing.JTextArea txtLexico;
    private javax.swing.JTextArea txtSintactico;
    // End of variables declaration//GEN-END:variables
}
