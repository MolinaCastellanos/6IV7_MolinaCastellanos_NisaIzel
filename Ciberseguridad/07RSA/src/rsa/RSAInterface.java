/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rsa;
import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.math.BigInteger;

public class RSAInterface extends JFrame {

    private RSAAlgoritmo rsa;
    private JTextArea campoMensaje;
    private JTextArea areaCifrado, areaParametros;
    private JTextField campoDescifradoN, campoDescifradoD;
    private JTextArea areaDescifrado, campoTextoCifrado;
    int width = 800;
    int height = 650;

    void iniciarAlgoritmo() {
        rsa = new RSAAlgoritmo();
        rsa.tamPrimo = 8;
        rsa.generarPrimos();
        rsa.generarClaves();
    }
    public RSAInterface() {
        setTitle("RSA - Cifrado y Descifrado");
        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        iniciarAlgoritmo();
        // Estilo principal
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Cifrado y Descifrado RSA", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("🔐 Cifrar", crearPanelCifrado());
        tabs.addTab("🔓 Descifrar", crearPanelDescifrado());
        add(tabs, BorderLayout.CENTER);

        setVisible(true);
    }
    private JPanel crearPanelCifrado() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Área para mensaje a cifrar
        campoMensaje = new JTextArea(4, 40);
        campoMensaje.setLineWrap(true);
        campoMensaje.setWrapStyleWord(true);
        JScrollPane scrollMensaje = new JScrollPane(campoMensaje);

        // Resultado del cifrado
        areaCifrado = new JTextArea(4, 40);
        areaCifrado.setLineWrap(true);
        areaCifrado.setWrapStyleWord(true);
        areaCifrado.setEditable(false);
        JScrollPane scrollCifrado = new JScrollPane(areaCifrado);

        // Parámetros RSA
        areaParametros = new JTextArea(6, 40);
        areaParametros.setEditable(false);
        areaParametros.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaParametros.setText(
                "Valores generados:\n" +
                "p  = " + rsa.p + "\n" +
                "q  = " + rsa.q + "\n" +
                "n  = " + rsa.n + "\n" +
                "fi = " + rsa.fi + "\n" +
                "e  = " + rsa.e + "\n" +
                "d  = " + rsa.d
        );
        JScrollPane scrollParametros = new JScrollPane(areaParametros);

        // Botones
        JButton btnClaves = new JButton("Generar claves");
        JButton btnCifrar = new JButton("Cifrar mensaje");
        JButton btnCopiar = new JButton("Copiar cifrado");
        JButton btnLimpiar = new JButton("Limpiar");

        btnCifrar.addActionListener(e -> {
            String mensaje = campoMensaje.getText();
            BigInteger[] cifrado = rsa.cifrar(mensaje);
            StringBuilder resultado = new StringBuilder();
            for (BigInteger bi : cifrado) {
                resultado.append(bi.toString()).append(" ");
            }
            areaCifrado.setText(resultado.toString().trim());
        });

        btnCopiar.addActionListener(e -> {
            String texto = areaCifrado.getText();
            if (!texto.isEmpty()) {
                Toolkit.getDefaultToolkit()
                        .getSystemClipboard()
                        .setContents(new StringSelection(texto), null);
                JOptionPane.showMessageDialog(panel, "Texto copiado al portapapeles.");
            }
        });

        btnLimpiar.addActionListener(e -> {
            areaCifrado.setText("");
            campoMensaje.setText("");
            areaParametros.setText("");
        });
        
        btnClaves.addActionListener(e -> {
            iniciarAlgoritmo();
            areaParametros.setText(
                    "Valores generados:\n" +
                    "p  = " + rsa.p + "\n" +
                    "q  = " + rsa.q + "\n" +
                    "n  = " + rsa.n + "\n" +
                    "fi = " + rsa.fi + "\n" +
                    "e  = " + rsa.e + "\n" +
                    "d  = " + rsa.d
            );
            campoDescifradoN.setText(rsa.n.toString());
            campoDescifradoD.setText(rsa.d.toString());
        });
        
        // Añadir componentes de forma uniforme
        gbc.gridy = 0;
        panel.add(new JLabel("Mensaje a cifrar:"), gbc);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.2;
        panel.add(scrollMensaje, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 0;
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotones.add(btnCifrar);
        panelBotones.add(btnCopiar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnClaves);
        panel.add(panelBotones, gbc);

        gbc.gridy++;
        panel.add(new JLabel("Texto cifrado (números separados por espacio):"), gbc);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.2;
        panel.add(scrollCifrado, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 0;
        panel.add(new JLabel("Parámetros RSA generados:"), gbc);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.4;
        panel.add(scrollParametros, gbc);

        return panel;
    }

    private JPanel crearPanelDescifrado() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Campo de texto cifrado
        campoTextoCifrado = new JTextArea(3, 40);
        campoTextoCifrado.setLineWrap(true);
        campoTextoCifrado.setWrapStyleWord(true);
        JScrollPane scrollTextoCifrado = new JScrollPane(campoTextoCifrado);

        // Campos n y d
        campoDescifradoN = new JTextField(rsa.n.toString());
        campoDescifradoD = new JTextField(rsa.d.toString());

        // Área para el resultado del descifrado
        areaDescifrado = new JTextArea(4, 40);
        areaDescifrado.setLineWrap(true);
        areaDescifrado.setWrapStyleWord(true);
        areaDescifrado.setEditable(false);
        JScrollPane scrollDescifrado = new JScrollPane(areaDescifrado);

        JButton btnDescifrar = new JButton("Descifrar mensaje");
        JButton btnLimpiar = new JButton("Limpiar");
        
        btnDescifrar.addActionListener(e -> {
            try {
                BigInteger n = new BigInteger(campoDescifradoN.getText().trim());
                BigInteger d = new BigInteger(campoDescifradoD.getText().trim());

                String[] partes = campoTextoCifrado.getText().trim().split(" ");
                BigInteger[] cifrado = new BigInteger[partes.length];
                for (int i = 0; i < partes.length; i++) {
                    cifrado[i] = new BigInteger(partes[i]);
                }

                RSAAlgoritmo rsaCustom = new RSAAlgoritmo();
                rsaCustom.n = n;
                rsaCustom.d = d;

                String descifrado = rsaCustom.descifrar(cifrado);
                areaDescifrado.setText(descifrado);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en datos ingresados: " + ex.getMessage());
            }
        });

        btnLimpiar.addActionListener(e -> {
            campoTextoCifrado.setText("");
            areaDescifrado.setText("");
            campoDescifradoN.setText("");
            campoDescifradoD.setText("");
        });
        
        // Agregar componentes al panel (simétrico al cifrado)
        gbc.gridy = 0;
        panel.add(new JLabel("Texto cifrado (números separados por espacio):"), gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.2;
        panel.add(scrollTextoCifrado, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        panel.add(new JLabel("n:"), gbc);

        gbc.gridy++;
        panel.add(campoDescifradoN, gbc);

        gbc.gridy++;
        panel.add(new JLabel("d:"), gbc);

        gbc.gridy++;
        panel.add(campoDescifradoD, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 0;
        JPanel panelBotones2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotones2.add(btnDescifrar);
        panelBotones2.add(btnLimpiar);
        panel.add(panelBotones2, gbc);

        gbc.gridy++;
        panel.add(new JLabel("Mensaje descifrado:"), gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.3;
        panel.add(scrollDescifrado, gbc);

        return panel;
    }


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception ex) {
            System.err.println("No se pudo cargar FlatLaf: " + ex);
        }

        SwingUtilities.invokeLater(RSAInterface::new);
    }
}

