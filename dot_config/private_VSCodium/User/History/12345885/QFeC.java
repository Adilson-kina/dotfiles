/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseCliente {

    private Connection connection;

    public DatabaseCliente() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/restaurante", "root", ""); // Ajuste sua senha e banco
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cliente> readAll(int id) {
        List<Cliente> clientes = new ArrayList<>();
        String query = "SELECT * FROM Clientes WHERE ID_cliente = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("ID_cliente"));
                cliente.setNome(rs.getString("Nome"));
                cliente.setCpf(rs.getString("Cpf"));
                cliente.setEmail(rs.getString("Email"));
                cliente.setEndereco(rs.getString("Endereco"));
                cliente.setTelefone(rs.getString("Telefone"));
                cliente.setSexo(rs.getString("Sexo"));
                cliente.setIdade(rs.getInt("Idade"));
                cliente.setDataCadastro(rs.getString("Data_cadastro"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return clientes;
    }
}
