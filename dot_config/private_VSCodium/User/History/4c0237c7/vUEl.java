package restaurante;

import javax.swing.*;
import java.util.List;

public class pesquisa extends javax.swing.JFrame {

    public pesquisa() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        painel = new javax.swing.JPanel();
        titulo = new javax.swing.JLabel();
        tabela = new javax.swing.JComboBox<>();
        id = new javax.swing.JTextField();
        id_lbl = new javax.swing.JLabel();
        enviar = new javax.swing.JButton();
        sair = new javax.swing.JButton();
        limpar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        titulo.setFont(new java.awt.Font("Segoe UI", 0, 36));
        titulo.setText("Pesquisa");

        tabela.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
                "Clientes", "Filiais", "Funcionários", "Reservas", "Fornecedores",
                "Estoque", "Pratos", "Bebidas", "Ingredientes", "Pedidos", "Entregas" }));

        id_lbl.setText("ID");

        enviar.setText("Enviar");
        enviar.addActionListener(evt -> enviarActionPerformed(evt));

        sair.setText("Sair");
        sair.addActionListener(evt -> sairActionPerformed(evt));

        limpar.setText("Limpar");
        limpar.addActionListener(evt -> limparActionPerformed(evt));

        javax.swing.GroupLayout painelLayout = new javax.swing.GroupLayout(painel);
        painel.setLayout(painelLayout);
        painelLayout.setHorizontalGroup(
                painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(painelLayout.createSequentialGroup()
                                .addGap(119, 119, 119)
                                .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(titulo)
                                        .addGroup(painelLayout.createSequentialGroup()
                                                .addComponent(id_lbl)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(tabela, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelLayout.createSequentialGroup()
                                .addContainerGap(79, Short.MAX_VALUE)
                                .addComponent(enviar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sair)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(limpar)
                                .addGap(67, 67, 67))
        );
        painelLayout.setVerticalGroup(
                painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(painelLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(titulo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tabela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(id_lbl))
                                .addGap(18, 18, 18)
                                .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(enviar)
                                        .addComponent(sair)
                                        .addComponent(limpar))
                                .addContainerGap(64, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(painel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(painel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }

    private void enviarActionPerformed(java.awt.event.ActionEvent evt) {
        String idSTR = id.getText();
        int id;
        String table = (String) tabela.getSelectedItem();

        try {
            id = Integer.parseInt(idSTR); // Tenta converter o ID para inteiro
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Insira um número válido.");
            return;
        }

        if (table == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma tabela.");
            return;
        }

        if ("Clientes".equals(table)) {
            DatabaseCliente dbCliente = new DatabaseCliente();
            List<Cliente> clientes = dbCliente.readAll(id);

            if (clientes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhum cliente encontrado com o ID fornecido.");
            } else {
                StringBuilder info = new StringBuilder();
                for (Cliente cliente : clientes) {
                    info.append("ID: ").append(cliente.getId())
                            .append("\nNome: ").append(cliente.getNome())
                            .append("\nCPF: ").append(cliente.getCpf())
                            .append("\nEmail: ").append(cliente.getEmail())
                            .append("\nEndereço: ").append(cliente.getEndereco())
                            .append("\nTelefone: ").append(cliente.getTelefone())
                            .append("\nSexo: ").append(cliente.getSexo())
                            .append("\nIdade: ").append(cliente.getIdade())
                            .append("\nData de Cadastro: ").append(cliente.getDataCadastro())
                            .append("\n\n");
                }
                JOptionPane.showMessageDialog(this, info.toString());
            }
        }
        // Implementar as demais tabelas de forma similar (Filiais, Funcionários, etc.)
    }

    private void sairActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    private void limparActionPerformed(java.awt.event.ActionEvent evt) {
        id.setText("");
        tabela.setSelectedIndex(0);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new pesquisa().setVisible(true));
    }

    // Variables declaration
    private javax.swing.JTextField id;
    private javax.swing.JLabel id_lbl;
    private javax.swing.JButton limpar;
    private javax.swing.JPanel painel;
    private javax.swing.JButton sair;
    private javax.swing.JComboBox<String> tabela;
    private javax.swing.JLabel titulo;
    private javax.swing.JButton enviar;
    // End of variables declaration
}
